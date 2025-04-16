package com.example.btl1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl1.R;

public class LyThuyetFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ly_thuyet, container, false);
        TextView textView = view.findViewById(R.id.textLyThuyet);
        textView.setText("Nội dung phần Lý thuyết bạn muốn hiển thị ở đây...");
        return view;
    }
}
