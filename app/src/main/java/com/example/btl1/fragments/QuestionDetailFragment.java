package com.example.btl1.fragments;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl1.R;
import com.example.btl1.models.Question;

public class QuestionDetailFragment extends Fragment {
    private TextView tvQuestionContent, tvExplanation, tvQuestionIndex, tvAnswer;
    private ImageView imgQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnSubmit;
    private Question question;
    private LinearLayout layoutExplanation;
    public static QuestionDetailFragment newInstance(Question question) {
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("cau_hoi", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ
        layoutExplanation = view.findViewById(R.id.layoutExplanation);
        tvQuestionContent = view.findViewById(R.id.tvQuestionContent);
        imgQuestion = view.findViewById(R.id.imgQuestion);
        rgOptions = view.findViewById(R.id.rgOptions);
        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);
        rbOption4 = view.findViewById(R.id.rbOption4);
        tvExplanation = view.findViewById(R.id.tvExplanation);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        tvQuestionIndex = view.findViewById(R.id.tvQuestionIndex);
        tvAnswer = view.findViewById(R.id.tvAnswer);
        // Nhận câu hỏi từ arguments
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("cau_hoi");
            Log.d("Cau hoi", question.getGiaiThichCauHoi());
        }

        if (question == null) {
            Toast.makeText(getContext(), "Lỗi: Không tìm thấy câu hỏi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (question == null) {
            Toast.makeText(getContext(), "Lỗi: Không tìm thấy câu hỏi!", Toast.LENGTH_SHORT).show();
            return;
        }

        String thuTu = question.getThuTu();
        if ("1".equals(question.getIsCauDiemLiet())) {
            thuTu += " *"; // nếu câu điểm liệt thì thêm dấu *
        }
        tvQuestionIndex.setText(thuTu);

        // Hiển thị nội dung câu hỏi
        tvQuestionContent.setText(question.getNoiDungCauHoi());

        String imageName = question.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
            if (imageResId != 0) {
                imgQuestion.setImageResource(imageResId);
            } else {
                imgQuestion.setImageResource(android.R.drawable.ic_menu_help);
            }
        } else {
            imgQuestion.setVisibility(View.GONE);
        }

        // Hiển thị các đáp án
        rbOption1.setText("1. " + question.getDapAn1());
        rbOption1.setVisibility(question.getDapAn1().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption2.setText("2. " + question.getDapAn2());
        rbOption2.setVisibility(question.getDapAn2().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption3.setText("3. " + question.getDapAn3());
        rbOption3.setVisibility(question.getDapAn3().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption4.setText("4. " + question.getDapAn4());
        rbOption4.setVisibility(question.getDapAn4().isEmpty() ? View.GONE : View.VISIBLE);

        tvExplanation.setVisibility(View.GONE);

        // Xử lý khi nhấn nút kiểm tra đáp án
        btnSubmit.setOnClickListener(v -> {
            int selectedId = rgOptions.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = view.findViewById(selectedId);
            String selectedAnswer = "";

            if (selectedRadioButton == rbOption1) selectedAnswer = "dap_an_1";
            if (selectedRadioButton == rbOption2) selectedAnswer = "dap_an_2";
            if (selectedRadioButton == rbOption3) selectedAnswer = "dap_an_3";
            if (selectedRadioButton == rbOption4) selectedAnswer = "dap_an_4";

            // Đặt lại màu cho tất cả RadioButton về mặc định trước
            resetOptionsColor();

            // Bôi màu xanh cho đáp án đúng
            highlightCorrectAnswer();

            if (selectedAnswer.equals(question.getDapAnDung())) {
//                tvAnswer.setText("Chính xác!");

//                Toast.makeText(getContext(), "Chính xác!", Toast.LENGTH_SHORT).show();
            } else {
//                tvAnswer.setText("Sai! Đáp án đúng là: " + getCorrectAnswerText());
//                Toast.makeText(getContext(), "Sai! Đáp án đúng là: " + getCorrectAnswerText(), Toast.LENGTH_LONG).show();
                // Nếu chọn sai thì bôi đỏ đáp án người dùng chọn
                selectedRadioButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            tvExplanation.setText(question.getGiaiThichCauHoi());
            tvExplanation.setVisibility(View.VISIBLE);
//            tvAnswer.setVisibility(View.VISIBLE);
            layoutExplanation.setVisibility(View.VISIBLE);

        });

    }

    private String getCorrectAnswerText() {
        switch (question.getDapAnDung()) {
            case "dap_an_1": return question.getDapAn1();
            case "dap_an_2": return question.getDapAn2();
            case "dap_an_3": return question.getDapAn3();
            case "dap_an_4": return question.getDapAn4();
            default: return "";
        }
    }
    private void resetOptionsColor() {
        rbOption1.setTextColor(getResources().getColor(android.R.color.black));
        rbOption2.setTextColor(getResources().getColor(android.R.color.black));
        rbOption3.setTextColor(getResources().getColor(android.R.color.black));
        rbOption4.setTextColor(getResources().getColor(android.R.color.black));
    }

    private void highlightCorrectAnswer() {
        RadioButton correctButton = null;

        switch (question.getDapAnDung()) {
            case "dap_an_1":
                correctButton = rbOption1;
                break;
            case "dap_an_2":
                correctButton = rbOption2;
                break;
            case "dap_an_3":
                correctButton = rbOption3;
                break;
            case "dap_an_4":
                correctButton = rbOption4;
                break;
        }

        if (correctButton != null) {
            correctButton.setTextColor(getResources().getColor(R.color.correct_answer));
            correctButton.setTypeface(null, android.graphics.Typeface.BOLD); // IN ĐẬM
        }
    }


}
