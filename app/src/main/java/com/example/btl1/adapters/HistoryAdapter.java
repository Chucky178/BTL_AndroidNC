package com.example.btl1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.models.Result;
import com.example.btl1.activities.ResultActivity; // Import ResultActivity
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<Result> resultList;

    public HistoryAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
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

        // Lấy tên đề thi từ Firebase thay vì chỉ hiển thị mã đề
        loadExamNameFromFirebase(result.getMa_de(), holder.tvExamName);

        holder.tvScore.setText(result.getDiem_so() + " / " + result.getTong_so_cau());

        // Format timestamp to readable date
        String formattedDate = result.getNgay_lam();
        holder.tvTime.setText("Ngày làm: " + formattedDate);
        holder.tvExamCode.setText("Mã đề: " + result.getMa_de());

        String status = result.getTrang_thai();
        if (status.equals("Đạt")) {
            holder.tvStatus.setText(status);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tvStatus.setText(status);
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        int minutes = result.getThoi_gian_hoan_thanh() / 60;
        int seconds = result.getThoi_gian_hoan_thanh() % 60;
        holder.tvExamTime.setText("Thời gian: " + minutes + " phút " + seconds + " giây");

        // Set click listener to navigate to ResultActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra("score", result.getDiem_so());
            intent.putExtra("totalQuestions", result.getTong_so_cau());
            intent.putExtra("time", result.getThoi_gian_hoan_thanh());
            intent.putExtra("ma_ket_qua", result.getMa_ket_qua());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvExamName, tvScore, tvTime, tvExamCode, tvStatus, tvExamTime;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExamName = itemView.findViewById(R.id.tv_examName);
            tvScore = itemView.findViewById(R.id.tv_exam_score);
            tvTime = itemView.findViewById(R.id.tv_exam_date);
            tvExamCode = itemView.findViewById(R.id.tv_exam_code);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvExamTime = itemView.findViewById(R.id.tv_exam_time);
        }
    }
    private void loadExamNameFromFirebase(String maDe, TextView tvExamName) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("de_thi");

        ref.orderByChild("ma_de").equalTo(maDe)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                String tenDe = child.child("ten_de").getValue(String.class);
                                if (tenDe != null) {
                                    tvExamName.setText(tenDe);
                                } else {
                                    tvExamName.setText("Không rõ tên đề");
                                }
                            }
                        } else {
                            tvExamName.setText("Không tìm thấy đề thi");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        tvExamName.setText("Lỗi tải tên đề");
                    }
                });
    }

}
