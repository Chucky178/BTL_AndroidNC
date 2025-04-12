package com.example.btl1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private List<Topic> topicList;
    private DatabaseReference dbRef;
    private ValueEventListener connectionListener;
    private ValueEventListener topicsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTopics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topicList = new ArrayList<>();
        adapter = new TopicAdapter(this, topicList);
        recyclerView.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance().getReference("nhom_cau_hoi");

        checkFirebaseConnection();
        loadTopics();

        // Sửa lỗi setOnItemClickListener
        adapter.setOnItemClickListener(topic -> {
            Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
            intent.putExtra("ma_nhom_cau_hoi", topic.getMa_nhom_cau_hoi());
            startActivity(intent);
        });
    }

    // Kiểm tra kết nối Firebase
    private void checkFirebaseConnection() {
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectionListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean connected = snapshot.getValue(Boolean.class);
                if (connected != null && connected) {
                    Log.d("Firebase", "Kết nối Firebase thành công!");
                } else {
                    Log.w("Firebase", "Không kết nối được Firebase!");
                    Toast.makeText(MainActivity.this, "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Lỗi kết nối Firebase", error.toException());
                Toast.makeText(MainActivity.this, "Lỗi kết nối: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        testRef.addValueEventListener(connectionListener);
    }

    // Hàm tải danh sách chủ đề từ Firebase
    private void loadTopics() {
        topicsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topicList.clear(); // Xóa danh sách trước khi cập nhật
                if (snapshot.exists()) {
                    for (DataSnapshot topicSnapshot : snapshot.getChildren()) {
                        try {
                            Topic topic = topicSnapshot.getValue(Topic.class);
                            if (topic != null) {
                                topicList.add(topic);
                                Log.d("Firebase", "Đã tải: " + topic.getTen_nhom_cau_hoi());
                            } else {
                                Log.w("Firebase", "Topic null tại index: " + topicSnapshot.getKey());
                            }
                        } catch (Exception e) {
                            Log.e("Firebase", "Lỗi parse dữ liệu tại: " + topicSnapshot.getKey(), e);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (topicList.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Không có dữ liệu chủ đề!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.w("Firebase", "Không tìm thấy dữ liệu tại nhánh 'nhom_cau_hoi'");
                    Toast.makeText(MainActivity.this, "Không có dữ liệu trên Firebase!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Lỗi khi tải dữ liệu", error.toException());
                Toast.makeText(MainActivity.this, "Lỗi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        dbRef.addValueEventListener(topicsListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Gỡ bỏ các listener để tránh memory leak
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        if (connectionListener != null) {
            testRef.removeEventListener(connectionListener);
        }
        if (topicsListener != null) {
            dbRef.removeEventListener(topicsListener);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("topicList", new ArrayList<>(topicList));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Topic> savedList = savedInstanceState.getParcelableArrayList("topicList");
        if (savedList != null) {
            topicList.clear();
            topicList.addAll(savedList);
            adapter.notifyDataSetChanged();
        }
    }
}