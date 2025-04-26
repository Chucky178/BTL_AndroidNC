package com.example.btl1.adapters;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.List;
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
        String message = "Nội dung: " + question.getNoiDungCauHoi() + "\n\n"
                + "Đáp án 1: " + question.getDapAn1() + "\n"
                + "Đáp án 2: " + question.getDapAn2() + "\n"
                + "Đáp án 3: " + question.getDapAn3() + "\n"
                + "Đáp án 4: " + question.getDapAn4() + "\n\n"
                + "Đáp án đúng: " + question.getDapAnDung() + "\n"
                + "Bạn chọn: " + userAnswer + "\n\n"
                + "Giải thích: " + question.getGiaiThichCauHoi();

        new AlertDialog.Builder(context)
                .setTitle("Chi tiết câu hỏi")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}

