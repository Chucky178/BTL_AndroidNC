package com.example.btl1.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.btl1.fragments.QuestionFragment;
import com.example.btl1.models.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;
    private Map<Integer, String> userAnswers = new HashMap<>();

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }
    public void setUserAnswer(int position, String answer) {
        userAnswers.put(position, answer);
    }
    @Override
    public Fragment createFragment(int position) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("cau_hoi", questions.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private String layNoiDungDapAn(Question question, String dapAn) {
        switch (dapAn) {
            case "dap_an_1": return question.getDapAn1();
            case "dap_an_2": return question.getDapAn2();
            case "dap_an_3": return question.getDapAn3();
            case "dap_an_4": return question.getDapAn4();
            default: return "";
        }
    }
    public String getUserAnswer(int position) {
        return userAnswers.get(position);
    }


}
