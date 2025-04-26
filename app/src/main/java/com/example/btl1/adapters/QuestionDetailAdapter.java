package com.example.btl1.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.btl1.fragments.QuestionDetailFragment;
import com.example.btl1.models.Question;

import java.util.List;

public class QuestionDetailAdapter extends FragmentStateAdapter {
    private List<Question> questionList;

    public QuestionDetailAdapter(@NonNull FragmentActivity activity, List<Question> questionList) {
        super(activity);   // CHỈ cần truyền FragmentActivity
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionDetailFragment.newInstance(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
