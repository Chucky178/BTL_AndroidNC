package com.example.btl1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.models.Exam;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.DeThiViewHolder> {
        private Context context;
        private List<Exam> deThiList;

        public ExamAdapter(Context context, List<Exam> deThiList) {
            this.context = context;
            this.deThiList = deThiList;
        }

        @NonNull
        @Override
        public DeThiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(context).inflate(R.layout.item_exam, parent, false);
            return new DeThiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DeThiViewHolder holder, int position) {
            Exam exam = deThiList.get(position);
            holder.tvTenDe.setText(exam.getTen_de());
        }

        @Override
        public int getItemCount() {
            return deThiList.size();
        }

        public static class DeThiViewHolder extends RecyclerView.ViewHolder {
            TextView tvTenDe;

            public DeThiViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTenDe = itemView.findViewById(R.id.tvTenDe);
            }
        }
}
