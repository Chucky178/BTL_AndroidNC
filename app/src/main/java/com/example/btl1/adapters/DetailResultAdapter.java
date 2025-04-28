package com.example.btl1.adapters;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.btl1.R;
import com.example.btl1.database.entity.DetailResultEntity;
import com.example.btl1.fragments.QuestionFragment;
import com.example.btl1.models.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailResultAdapter extends BaseAdapter {

    private Context context;
    private List<DetailResultEntity> detailResultList;

    public DetailResultAdapter(Context context, List<DetailResultEntity> detailResultList) {
        this.context = context;
        this.detailResultList = detailResultList;
    }

    @Override
    public int getCount() {
        return detailResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_result, parent, false);

        TextView tvQuestionNumber = view.findViewById(R.id.tvQuestionNumber);
        DetailResultEntity entity = detailResultList.get(position);

        tvQuestionNumber.setText("Câu " + (position + 1));
        if ("dung".equalsIgnoreCase(entity.getStatus())) {
            tvQuestionNumber.setBackgroundResource(R.drawable.bg_correct);
        } else if ("sai".equalsIgnoreCase(entity.getStatus())) {
            tvQuestionNumber.setBackgroundResource(R.drawable.bg_wrong);
        } else {
            tvQuestionNumber.setBackgroundResource(R.drawable.bg_default);
        }

        view.setOnClickListener(v -> {
            loadQuestionAndShowDialog(entity);
        });

        return view;
    }

    private void loadQuestionAndShowDialog(DetailResultEntity entity) {
        String questionId = entity.getQuestionId();
        Log.d("LoadQuestion", "Đang tải câu hỏi với ID: " + questionId);  // Log ID câu hỏi

        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference("cau hoi");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;  // Biến đánh dấu có tìm thấy câu hỏi hay không
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    String maCauHoi = questionSnapshot.child("ma_cau_hoi").getValue(String.class);
                    if (questionId.equals(maCauHoi)) {
                        // Tạo đối tượng Question từ Firebase
                        Question question = new Question();
                        question.setMaCauHoi(maCauHoi);
                        question.setNoiDungCauHoi(questionSnapshot.child("noi_dung_cau_hoi").getValue(String.class));
                        question.setDapAn1(questionSnapshot.child("dap_an_1").getValue(String.class));
                        question.setDapAn2(questionSnapshot.child("dap_an_2").getValue(String.class));
                        question.setDapAn3(questionSnapshot.child("dap_an_3").getValue(String.class));
                        question.setDapAn4(questionSnapshot.child("dap_an_4").getValue(String.class));
                        question.setHinhAnh(questionSnapshot.child("hinh_anh").getValue(String.class));
                        question.setDapAnDung(questionSnapshot.child("dap_an_dung").getValue(String.class));
                        question.setGiaiThichCauHoi(questionSnapshot.child("giai_thich_cau_hoi").getValue(String.class));

                        // Hiển thị AlertDialog
                        showQuestionDialog(question, entity.getSelectedAnswer());
                        found = true;  // Đánh dấu đã tìm thấy câu hỏi
                        break;
                    }
                }
                if (!found) {
                    Log.d("LoadQuestion", "Không tìm thấy câu hỏi với ID: " + questionId);  // Log khi không tìm thấy câu hỏi
                    Toast.makeText(context, "Không tìm thấy câu hỏi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Lỗi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showQuestionDialog(Question question, String userAnswer) {
        // Inflate layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_question_detail, null);

        // Ánh xạ view
        TextView tvContent = dialogView.findViewById(R.id.tvContent);
        ImageView imgQuestion = dialogView.findViewById(R.id.imgQuestion);
        RadioGroup rgOptions = dialogView.findViewById(R.id.rgOptions);
        RadioButton rbOption1 = dialogView.findViewById(R.id.rbOption1);
        RadioButton rbOption2 = dialogView.findViewById(R.id.rbOption2);
        RadioButton rbOption3 = dialogView.findViewById(R.id.rbOption3);
        RadioButton rbOption4 = dialogView.findViewById(R.id.rbOption4);
        TextView tvExplanation = dialogView.findViewById(R.id.tvExplanation);

        // Set nội dung câu hỏi
        tvContent.setText(question.getNoiDungCauHoi());

        // Set hình ảnh nếu có
        String imageName = question.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (imageResId != 0) {
                imgQuestion.setImageResource(imageResId);
                imgQuestion.setVisibility(View.VISIBLE);
            } else {
                imgQuestion.setVisibility(View.GONE);
            }
        } else {
            imgQuestion.setVisibility(View.GONE);
        }

        // Set đáp án
        setupOption(rbOption1, question.getDapAn1());
        setupOption(rbOption2, question.getDapAn2());
        setupOption(rbOption3, question.getDapAn3());
        setupOption(rbOption4, question.getDapAn4());

        // Xử lý bôi màu đáp án đúng
        highlightCorrectAnswer(question.getDapAnDung(), userAnswer, rbOption1, rbOption2, rbOption3, rbOption4);

        // Set giải thích
        tvExplanation.setText(question.getGiaiThichCauHoi());

        // Tạo Dialog
        new AlertDialog.Builder(context)
                .setTitle("Chi tiết câu hỏi")
                .setView(dialogView)
                .setPositiveButton("OK", null)
                .show();
    }

    private void setupOption(RadioButton rb, String text) {
        if (text == null || text.isEmpty()) {
            rb.setVisibility(View.GONE);
        } else {
            rb.setText(text);
            rb.setVisibility(View.VISIBLE);
        }
    }

    private void highlightCorrectAnswer(String correctAnswerKey, String userAnswerKey,
                                        RadioButton rbOption1, RadioButton rbOption2,
                                        RadioButton rbOption3, RadioButton rbOption4) {
        // Mapping key với RadioButton
        Map<String, RadioButton> map = new HashMap<>();
        map.put("dap_an_1", rbOption1);
        map.put("dap_an_2", rbOption2);
        map.put("dap_an_3", rbOption3);
        map.put("dap_an_4", rbOption4);

        for (Map.Entry<String, RadioButton> entry : map.entrySet()) {
            RadioButton rb = entry.getValue();
            if (rb.getVisibility() == View.VISIBLE) {
                String key = entry.getKey();

                // Không dùng setEnabled(false)
                rb.setClickable(false);
                rb.setFocusable(false);

                // Gán mặc định màu đen trước
                rb.setTextColor(ContextCompat.getColor(context, R.color.black));
                rb.setTypeface(Typeface.DEFAULT); // Mặc định bình thường

                // Nếu là đáp án đúng
                if (key.equals(correctAnswerKey)) {
                    rb.setTextColor(ContextCompat.getColor(context, R.color.correct_answer)); // màu xanh
                    rb.setTypeface(Typeface.DEFAULT_BOLD); // In đậm
                }

                // Nếu người dùng chọn đáp án này
                if (key.equals(userAnswerKey)) {
                    rb.setChecked(true); // Select vào đáp án người dùng chọn

                    // Nếu chọn sai
                    if (!userAnswerKey.equals(correctAnswerKey)) {
                        rb.setTextColor(ContextCompat.getColor(context, R.color.wrong_answer)); // màu đỏ
                    }
                }
            }
        }
    }

}

