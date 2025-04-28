package com.example.btl1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.btl1.R;
import com.example.btl1.models.Question;

public class QuestionFragment extends Fragment {

    private TextView tvQuestionIndex;
    private TextView tvQuestionContent;
    private ImageView imgQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;

    private Question question;
    private String userAnswer;  // Lưu đáp án của người dùng

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Ánh xạ các views
        tvQuestionIndex = view.findViewById(R.id.tvQuestionIndex);
        tvQuestionContent = view.findViewById(R.id.tvQuestionContent);
        imgQuestion = view.findViewById(R.id.imgQuestion);
        rgOptions = view.findViewById(R.id.rgOptions);
        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);
        rbOption4 = view.findViewById(R.id.rbOption4);

        // Nhận dữ liệu từ Bundle
        question = (Question) getArguments().getSerializable("cau_hoi");
        int questionIndex = getArguments().getInt("question_index", -1);

        // Hiển thị số thứ tự câu hỏi
        if (questionIndex != -1) {
            tvQuestionIndex.setText("Câu " + (questionIndex + 1));
        }

        if (question != null) {
            // Hiển thị nội dung câu hỏi
            tvQuestionContent.setText(question.getNoiDungCauHoi());

            // Hiển thị hình ảnh nếu có
            String imageName = question.getHinhAnh();
            if (imageName != null && !imageName.isEmpty()) {
                int imageResId = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
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
        }

        // Xử lý sự kiện chọn đáp án
        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbOption1) {
                userAnswer = "dap_an_1";
            } else if (checkedId == R.id.rbOption2) {
                userAnswer = "dap_an_2";
            } else if (checkedId == R.id.rbOption3) {
                userAnswer = "dap_an_3";
            } else if (checkedId == R.id.rbOption4) {
                userAnswer = "dap_an_4";
            }

            android.util.Log.d("UserAnswer", "Câu hỏi: " + question.getMaCauHoi() + " - Đáp án chọn: " + userAnswer);
        });

        return view;
    }

    // Getter để lấy đáp án người dùng chọn
    public String getUserAnswer() {
        return userAnswer;
    }

}
