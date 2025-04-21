package com.example.btl1.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.btl1.fragments.QuestionFragment;
import com.example.btl1.models.Question;

import java.util.List;

public class QuestionPagerAdapter extends FragmentStateAdapter {
    private List<Question> questions;

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
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
}
