//package com.example.btl1.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.btl1.R;
//import com.example.btl1.models.Result;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class HistoryAdapter extends ArrayAdapter<Result> {
////    private Context context;
////    private List<Result> results;
////
////    public HistoryAdapter(Context context, List<Result> results) {
////        super(context, R.layout.item_history, results);
////        this.context = context;
////        this.results = results;
////    }
////
////    @Override
////    public View getView(int position, View convertView, ViewGroup parent) {
////        if (convertView == null) {
////            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
////        }
////        TextView tvExamName = convertView.findViewById(R.id.tvExamName);
////        TextView tvScore = convertView.findViewById(R.id.tvScore);
////        TextView tvTime = convertView.findViewById(R.id.tvTime);
////
////        Result result = results.get(position);
////        tvExamName.setText(result.getExamName());
////        tvScore.setText("Điểm: " + result.getScore());
////        Date date = new Date(result.getTimestamp());
////        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
////        String formattedDate = sdf.format(date);
////        tvTime.setText("Thời gian: " + formattedDate);
////
////        return convertView;
////    }
//}
