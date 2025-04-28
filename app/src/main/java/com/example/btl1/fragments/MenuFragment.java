package com.example.btl1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.btl1.R;
import com.example.btl1.activities.Decree168Activity;
import com.example.btl1.activities.ExamActivity;
import com.example.btl1.activities.HistoryActivity;
import com.example.btl1.activities.MainActivity;
import com.example.btl1.activities.TipsActivity;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {
    private LinearLayout layoutQuestion, layoutExam, layoutHistory, layoutTips, layoutDecree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_bottom, container, false);

        // Ánh xạ các LinearLayout từ layout
        layoutQuestion = view.findViewById(R.id.layoutQuestion);
        layoutExam = view.findViewById(R.id.layoutExam);
        layoutHistory = view.findViewById(R.id.layoutHistory);
        layoutTips = view.findViewById(R.id.layoutTips);
        layoutDecree = view.findViewById(R.id.layoutDecree);

        // Thiết lập sự kiện click chung cho các mục menu
        View.OnClickListener menuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                // kiểm tra ID của view
                if (v.getId() == R.id.layoutQuestion) {
                    intent = new Intent(getActivity(), MainActivity.class);
                } else if (v.getId() == R.id.layoutExam) {
                    intent = new Intent(getActivity(), ExamActivity.class);
                } else if (v.getId() == R.id.layoutHistory) {
                    intent = new Intent(getActivity(), HistoryActivity.class);
                } else if (v.getId() == R.id.layoutTips) {
                    intent = new Intent(getActivity(), TipsActivity.class);
                } else if (v.getId() == R.id.layoutDecree) {
                    intent = new Intent(getActivity(), Decree168Activity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        };

        // Gán OnClickListener cho tất cả các mục menu
        layoutQuestion.setOnClickListener(menuClickListener);
        layoutExam.setOnClickListener(menuClickListener);
        layoutHistory.setOnClickListener(menuClickListener);
        layoutTips.setOnClickListener(menuClickListener);
        layoutDecree.setOnClickListener(menuClickListener);

        return view;
    }
}
