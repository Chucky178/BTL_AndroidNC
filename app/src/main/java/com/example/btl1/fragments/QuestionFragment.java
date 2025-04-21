package com.example.btl1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.btl1.R;
import com.example.btl1.models.Question;

public class QuestionFragment extends Fragment {
    private TextView tvQuestionContent, tvExplanation;
    private ImageView imgQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    private Button btnSubmit;
    private Question question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        tvQuestionContent = view.findViewById(R.id.tvQuestionContent);
        imgQuestion = view.findViewById(R.id.imgQuestion);
        rgOptions = view.findViewById(R.id.rgOptions);
        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);
        rbOption4 = view.findViewById(R.id.rbOption4);
        tvExplanation = view.findViewById(R.id.tvExplanation);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Nhận câu hỏi từ Bundle
        question = (Question) getArguments().getSerializable("cau_hoi");

        if (question != null) {
            tvQuestionContent.setText(question.getNoiDungCauHoi());

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

            // Hiển thị đáp án
            rbOption1.setText(question.getDapAn1());
            rbOption2.setText(question.getDapAn2());
            rbOption3.setText(question.getDapAn3());
            rbOption4.setText(question.getDapAn4());

            btnSubmit.setOnClickListener(v -> {
                int selectedId = rgOptions.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Vui lòng chọn một đáp án!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = view.findViewById(selectedId);
                String selectedAnswer = "";
                if (selectedRadioButton == rbOption1) selectedAnswer = "dap_an_1";
                if (selectedRadioButton == rbOption2) selectedAnswer = "dap_an_2";
                if (selectedRadioButton == rbOption3) selectedAnswer = "dap_an_3";
                if (selectedRadioButton == rbOption4) selectedAnswer = "dap_an_4";

                if (selectedAnswer.equals(question.getDapAnDung())) {
                    Toast.makeText(getActivity(), "Chính xác!", Toast.LENGTH_SHORT).show();
                    tvExplanation.setText("Giải thích: " + question.getGiaiThichCauHoi());
                    tvExplanation.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Sai! Đáp án đúng là: " + getCorrectAnswerText(), Toast.LENGTH_LONG).show();
                    tvExplanation.setText("Giải thích: " + question.getGiaiThichCauHoi());
                    tvExplanation.setVisibility(View.VISIBLE);
                }
            });
        }

        return view;
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
