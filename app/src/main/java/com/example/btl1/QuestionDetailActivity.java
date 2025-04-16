package com.example.btl1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionDetailActivity extends AppCompatActivity {
    private TextView tvQuestionContent, tvExplanation;
    private ImageView imgQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnSubmit;
    private Question question;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        // Ánh xạ các thành phần
        tvQuestionContent = findViewById(R.id.tvQuestionContent);
        imgQuestion = findViewById(R.id.imgQuestion);

        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        tvExplanation = findViewById(R.id.tvExplanation);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Nhận câu hỏi từ Intent
        question = (Question) getIntent().getSerializableExtra("cau hoi");        if (question == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy câu hỏi!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Hiển thị nội dung câu hỏi
        tvQuestionContent.setText(question.getNoiDungCauHoi());

        String imageName = question.getHinhAnh();
        if (imageName != null && !imageName.isEmpty()) {
            // Lấy ID tài nguyên ảnh từ tên ảnh
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResId != 0) {
                imgQuestion.setImageResource(imageResId);  // Đặt ảnh vào ImageView
            } else {
                imgQuestion.setImageResource(android.R.drawable.ic_menu_help);  // Ảnh mặc định
            }
        } else {
            imgQuestion.setVisibility(View.GONE);  // Ẩn ImageView nếu không có ảnh
        }
        // Hiển thị các đáp án (nếu có)
        rbOption1.setText(question.getDapAn1());
        rbOption1.setVisibility(question.getDapAn1().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption2.setText(question.getDapAn2());
        rbOption2.setVisibility(question.getDapAn2().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption3.setText(question.getDapAn3());
        rbOption3.setVisibility(question.getDapAn3().isEmpty() ? View.GONE : View.VISIBLE);

        rbOption4.setText(question.getDapAn4());
        rbOption4.setVisibility(question.getDapAn4().isEmpty() ? View.GONE : View.VISIBLE);

        tvExplanation.setVisibility(View.GONE);

        // Xử lý khi nhấn nút kiểm tra đáp án
        btnSubmit.setOnClickListener(v -> {
            int selectedId = rgOptions.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedAnswer = "";
            if (selectedRadioButton == rbOption1) selectedAnswer = "dap_an_1";
            if (selectedRadioButton == rbOption2) selectedAnswer = "dap_an_2";
            if (selectedRadioButton == rbOption3) selectedAnswer = "dap_an_3";
            if (selectedRadioButton == rbOption4) selectedAnswer = "dap_an_4";

            if (selectedAnswer.equals(question.getDapAnDung())) {
                Toast.makeText(this, "Chính xác!", Toast.LENGTH_SHORT).show();
                tvExplanation.setText("Giải thích: " + question.getGiaiThichCauHoi());
                tvExplanation.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Sai! Đáp án đúng là: " + getCorrectAnswerText(), Toast.LENGTH_LONG).show();
                tvExplanation.setText("Giải thích: " + question.getGiaiThichCauHoi());
                tvExplanation.setVisibility(View.VISIBLE);
            }
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
}
