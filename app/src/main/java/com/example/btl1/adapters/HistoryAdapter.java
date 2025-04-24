package com.example.btl1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.models.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<Result> resultList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Result result);
    }

    public HistoryAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Result result = resultList.get(position);

        holder.tvExamName.setText(result.getMa_de()); // Display exam name or ID
        holder.tvScore.setText("Điểm: " + result.getDiem_so() + " / " + result.getTong_so_cau());

        // Format timestamp to readable date
        String formattedDate = result.getNgay_lam();
        holder.tvTime.setText("Ngày làm: " + formattedDate);
        holder.tvExamCode.setText("Mã đề: " + result.getMa_de());

        String status = result.getTrang_thai();
        if(status.equals("đạt")){
            holder.tvStatus.setText("Trạng thái: " + status);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tvStatus.setText("Trạng thái: " + status);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvExamName, tvScore, tvTime, tvExamCode, tvStatus;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExamName = itemView.findViewById(R.id.tv_exam_date);
            tvScore = itemView.findViewById(R.id.tv_exam_score);
            tvTime = itemView.findViewById(R.id.tv_exam_date);
            tvExamCode = itemView.findViewById(R.id.tv_examName);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}