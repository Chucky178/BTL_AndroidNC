package com.example.btl1.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl1.R;
import com.example.btl1.activities.QuestionListActivity;
import com.example.btl1.models.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<Topic> topics;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClick(Topic topic);
    }

    // Hàm set listener từ bên ngoài (MainActivity)
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public TopicAdapter(Context context, List<Topic> topics) {
        this.context = context;
        this.topics = topics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topics.get(position);
        if (topic == null) {
            Log.w("TopicAdapter", "Topic tại vị trí " + position + " là null");
            return;
        }

        // Đặt tên nhóm câu hỏi
        String topicName = topic.getTen_nhom_cau_hoi();
        holder.tvTopicName.setText(topicName != null ? topicName : "Không có tên");

        // Đặt số câu hỏi
        holder.tvQuestionCount.setText(topic.getSo_cau_hoi() + " câu");

        // Lấy và đặt ảnh từ resource ID
        String imageName = topic.getHinh_anh();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = context.getResources().getIdentifier(
                    imageName, "drawable", context.getPackageName());
            if (imageResId != 0) {
                holder.imgTopic.setImageResource(imageResId);
            } else {
                Log.w("TopicAdapter", "Không tìm thấy drawable: " + imageName);
                holder.imgTopic.setImageResource(android.R.drawable.ic_menu_help); // Ảnh mặc định
            }
        } else {
            Log.w("TopicAdapter", "Tên ảnh rỗng tại: " + topicName);
            holder.imgTopic.setImageResource(android.R.drawable.ic_menu_help); // Ảnh mặc định
        }

        // Sử dụng interface thay vì xử lý trực tiếp
//        holder.itemView.setOnClickListener(v -> {
//            if (onItemClickListener != null) {
//                onItemClickListener.onItemClick(topic);
//            }
//        });
        holder.itemView.setOnClickListener(view -> {
            Topic selectedTopic = topics.get(position); // Lấy đúng kiểu
            String maNhom = selectedTopic.getMa_nhom_cau_hoi(); // Lấy mã nhóm
            String tenNhom = selectedTopic.getTen_nhom_cau_hoi();
            Intent intent = new Intent(context, QuestionListActivity.class);

            switch (maNhom) {
                case "NCH00":  // TẤT CẢ CÂU HỎI
                    intent.putExtra("hien_tat_ca", true);
                    intent.putExtra("ten_nhom", "Tất cả câu hỏi");
                    break;

                case "NCH01":  // CÂU HỎI ĐIỂM LIỆT
                    intent.putExtra("hien_diem_liet", true);
                    intent.putExtra("ten_nhom", "20 câu điểm liệt");
                    break;

                default: // Các nhóm còn lại
                    intent.putExtra("ma_nhom_cau_hoi", maNhom);
                    intent.putExtra("ten_nhom", tenNhom);
                    break;
            }

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return topics != null ? topics.size() : 0; // Tránh crash nếu topics null
    }

    // ViewHolder để lưu các view item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopicName, tvQuestionCount;
        ImageView imgTopic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTopicName = itemView.findViewById(R.id.tv_topic_name);
            tvQuestionCount = itemView.findViewById(R.id.tvQuestionCount);
            imgTopic = itemView.findViewById(R.id.img_topic);
        }
    }
}
