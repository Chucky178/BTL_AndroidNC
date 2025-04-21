package com.example.btl1.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.models.Result;

import java.util.List;

public class ResultDetailAdapter extends RecyclerView.Adapter<ResultDetailAdapter.ViewHolder> {
    private List<Result> chiTietList;

    public ResultDetailAdapter(List<Result> chiTietList) {
        this.chiTietList = chiTietList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ChiTietKetQua chiTiet = chiTietList.get(position);
//        holder.questionIdText.setText("Câu hỏi: " + chiTiet.getMa_cau_hoi());
//        holder.selectedAnswerText.setText("Đáp án chọn: " + chiTiet.getDap_an_chon());
//        holder.correctAnswerText.setText("Đáp án đúng: " + chiTiet.getDap_an_dung());
//        holder.statusText.setText("Trạng thái: " + chiTiet.getTrang_thai());
    }

    @Override
    public int getItemCount() { return chiTietList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionIdText, selectedAnswerText, correctAnswerText, statusText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionIdText = itemView.findViewById(R.id.questionIdText);
            selectedAnswerText = itemView.findViewById(R.id.selectedAnswerText);
            correctAnswerText = itemView.findViewById(R.id.correctAnswerText);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }

}
