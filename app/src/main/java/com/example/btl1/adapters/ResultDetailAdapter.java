package com.example.btl1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.models.ResultDetail;

import java.util.List;

public class ResultDetailAdapter extends RecyclerView.Adapter<ResultDetailAdapter.ViewHolder> {

    private List<ResultDetail> resultDetails;

    public ResultDetailAdapter(List<ResultDetail> resultDetails) {
        this.resultDetails = resultDetails;
    }

    @NonNull
    @Override
    public ResultDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultDetailAdapter.ViewHolder holder, int position) {
        ResultDetail detail = resultDetails.get(position);
        holder.txtMaCauHoi.setText("Câu hỏi: " + detail.getMa_cau_hoi());
        holder.txtDapAnChon.setText("Đáp án chọn: " + detail.getDap_an_chon());
        holder.txtDapAnDung.setText("Đáp án đúng: " + detail.getDap_an_dung());

        if (detail.getTrang_thai().equalsIgnoreCase("Đúng")) {
            holder.txtTrangThai.setText("Kết quả: Đúng");
            holder.txtTrangThai.setTextColor(0xFF00AA00); // Xanh lá
        } else {
            holder.txtTrangThai.setText("Kết quả: Sai");
            holder.txtTrangThai.setTextColor(0xFFFF0000); // Đỏ
        }
    }

    @Override
    public int getItemCount() {
        return resultDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaCauHoi, txtDapAnChon, txtDapAnDung, txtTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaCauHoi = itemView.findViewById(R.id.txtMaCauHoi);
            txtDapAnChon = itemView.findViewById(R.id.txtDapAnChon);
            txtDapAnDung = itemView.findViewById(R.id.txtDapAnDung);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
        }
    }
}
