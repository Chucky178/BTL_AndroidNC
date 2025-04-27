package com.example.btl1.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl1.R;
import com.example.btl1.fragments.MenuFragment;

public class Decree168Activity extends AppCompatActivity {
    TextView tvNghiDinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_decree168);
        tvNghiDinh = findViewById(R.id.textNghiDinh);
// Thêm MenuFragment vào Activity
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuContainer, new MenuFragment());  // menuContainer là container để chứa fragment
            transaction.commit();
        }
        getSupportActionBar().setTitle("Ôn thi GPLX A1 - Nghị định 168");
        String fullText =
                "Nghị định 168/2024/NĐ-CP, có hiệu lực từ ngày 1/1/2025, thay thế Nghị định 100/2019/NĐ-CP, đã bổ sung và điều chỉnh nhiều quy định liên quan đến người điều khiển xe máy hạng A1 (dưới 125cc). Dưới đây là những điểm mới và nổi bật:\n" +
                        "\n" +
                        "1. Tăng mức phạt cho hành vi không có hoặc sử dụng giấy phép lái xe không hợp lệ\n" +
                        "Người điều khiển xe máy hạng A1 sẽ bị phạt tiền từ 2.000.000 đồng đến 4.000.000 đồng nếu vi phạm một trong các hành vi sau: \n" +
                        "\t• \tKhông có giấy phép lái xe hoặc sử dụng giấy phép lái xe đã bị trừ hết điểm.\n" +
                        "\t• \tSử dụng giấy phép lái xe không do cơ quan có thẩm quyền cấp, bị tẩy xóa, không còn hiệu lực hoặc không phù hợp với loại xe đang điều khiển.\n" +
                        "\t• \tSử dụng giấy phép lái xe quốc tế mà không mang theo giấy phép lái xe quốc gia phù hợp.\n" +
                        "\t• \tSử dụng giấy phép lái xe không hợp lệ (số phôi không trùng với số phôi được cấp mới nhất trong hệ thống quản lý). \n" +
                        "Đây là mức phạt tăng so với quy định trước đây, nhằm nâng cao ý thức chấp hành pháp luật của người tham gia giao thông .\n" +
                        "\n" +
                        "2. Xử phạt nghiêm hành vi tự ý thay đổi kết cấu xe (độ xe)\n" +
                        "Từ ngày 1/1/2025, hành vi tự ý thay đổi khung, máy, hình dáng, kích thước, đặc tính của xe (thường gọi là \"độ xe\") sẽ bị xử phạt như sau: \n" +
                        "\t• \tCá nhân: Phạt tiền từ 4.000.000 đồng đến 6.000.000 đồng.\n" +
                        "\t• \tTổ chức: Phạt tiền từ 8.000.000 đồng đến 12.000.000 đồng. \n" +
                        "Ngoài ra, hành vi này còn có thể bị trừ điểm giấy phép lái xe nếu vi phạm nhiều lần hoặc gây hậu quả nghiêm trọng.\n" +
                        "\n" +
                        "3. Áp dụng hệ thống trừ điểm giấy phép lái xe\n" +
                        "Nghị định 168/2024/NĐ-CP chính thức áp dụng hệ thống trừ điểm giấy phép lái xe. Mỗi giấy phép lái xe sẽ có một số điểm nhất định, và người điều khiển phương tiện sẽ bị trừ điểm khi vi phạm các quy định giao thông. Khi bị trừ hết điểm, giấy phép lái xe sẽ bị thu hồi và người vi phạm phải tham gia khóa đào tạo, sát hạch lại để được cấp lại giấy phép.\n" +
                        "\n" +
                        "4. Quy định lệ phí sát hạch lái xe hạng A1\n" +
                        "Theo Thông tư 37/2023/TT-BTC và Thông tư 63/2023/TT-BTC, mức lệ phí sát hạch lái xe hạng A1 được quy định như sau: \n" +
                        "\t• \tSát hạch lý thuyết: 60.000 đồng/lần.\n" +
                        "\t• \tSát hạch thực hành: 70.000 đồng/lần. \n" +
                        "Ngoài ra, từ ngày 1/12/2023 đến hết ngày 31/12/2025, lệ phí cấp mới, cấp lại, cấp đổi giấy phép lái xe (quốc gia và quốc tế) theo hình thức trực tuyến là 115.000 đồng/lần cấp.\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);
//In dam
        String[] linesToBold = {
                "1. Tăng mức phạt cho hành vi không có hoặc sử dụng giấy phép lái xe không hợp lệ",
                "2. Xử phạt nghiêm hành vi tự ý thay đổi kết cấu xe (độ xe)",
                "3. Áp dụng hệ thống trừ điểm giấy phép lái xe",
                "4. Quy định lệ phí sát hạch lái xe hạng A1"
        };

// In đậm từng dòng trong danh sách
        for (String line : linesToBold) {
            int start = fullText.indexOf(line);
            if (start != -1) {
                int end = start + line.length();
                builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tvNghiDinh.setText(builder);
    }
}