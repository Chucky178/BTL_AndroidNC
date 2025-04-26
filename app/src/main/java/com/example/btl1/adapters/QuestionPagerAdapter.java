package com.example.btl1.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
    private Map<Integer, QuestionFragment> fragmentMap = new HashMap<>();

    public QuestionPagerAdapter(FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }

    //    public void setUserAnswer(int position, String answer) {
//        fragmentMap.put(position, answer);
//    }
    @Override
    public Fragment createFragment(int position) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("cau_hoi", questions.get(position));  // Gửi câu hỏi vào Fragment
        args.putInt("question_index", position);  // Gửi chỉ số câu hỏi
        fragment.setArguments(args);
        fragmentMap.put(position, fragment);

        return fragment;
    }





    @Override
    public int getItemCount() {
        return questions.size();
    }
//    public String getUserAnswer(int position) {
//        QuestionFragment fragment = fragmentMap.get(position);
//        if (fragment != null) {
//            return fragment.getUserAnswer();  // lấy key dạng "dap_an_1"
//        }
//        return null;
//    }
public String getUserAnswer(int position) {
    // Lấy fragment từ fragmentMap
    QuestionFragment fragment = fragmentMap.get(position);

    // Log để kiểm tra xem fragment có được tìm thấy không
    Log.d("QuestionPagerAdapter", "Fragment for position " + position + ": " + fragment);

    if (fragment != null) {
        // Log để kiểm tra đáp án người dùng đã chọn
        String userAnswer = fragment.getUserAnswer();
        Log.d("QuestionPagerAdapter", "User answer for position " + position + ": " + userAnswer);
        return userAnswer;
    }

    // Log khi không tìm thấy fragment
    Log.d("QuestionPagerAdapter", "No fragment found for position " + position);
    return null;
}

}
