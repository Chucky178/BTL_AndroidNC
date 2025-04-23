package com.example.btl1.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl1.R;

public class ThucHanhFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuc_hanh, container, false);
        TextView textView = view.findViewById(R.id.textThucHanh);
        String fullText = "Hướng dẫn bài thi sa hình A1\n" +
                "\n" +
                "Hiện nay, trong việc thi bằng lái xe máy thì phần thi thực hành chấm điểm tự động bằng chip. Do máy tự động chấm điểm nên việc thi thực hành tỷ lệ trượt nhiều hơn so với trước. Qua quá trình tổ chức thi sát hạch qua rất nhiều học việc đúc kết ra một số mẹo cần chuẩn bị trước để thi đảm bảo đỗ 100%\n" +
                "\n" +
                "Tập trước vòng số 8 ở nhà.\n" +
                "Phần thi thực hành trượt nhiều nhất là ở phần thi vòng số 8. Do đó, việc bạn đã biết đi xe máy thành thạo nhưng chưa tập qua vòng số 8 thì cũng rất dễ trượt. Bạn nên tập qua vòng số 8 càng nhiều lần càng tốt. Ít nhất là lúc đi xe vào vòng số 8 không còn cảm thấy loạng choạng.\n" +
                "\n" +
                "Ngày thi lên sân thi thật sớm.\n" +
                "Tại sao chúng tôi khuyên bạn như vậy? Bởi vì xe của bạn thì bạn quen với nó, bạn đi rất ngon lành. Nhưng xe của sân thi thì khác nhiều đấy, nếu bạn không đến sớm để làm quen thì cũng rất dễ trượt. Hơn nữa, buổi thi thường sẽ có rất đông người. Nếu bạn đến sớm thì không phải chờ lâu để được tập xe. Còn nếu đến muộn thì khả năng phải xếp hàng dài dài mới đến lượt mình.\n" +
                "\n" +
                "Cách đi để không chạm vạch.\n" +
                "Đi xe vào từ mép ngoài bên trái, bởi vì như thế thì bánh sau mới không bị đè lên vạch. Nếu bạn đi vào từ mép ngoài bên phải thì nhìn bánh trước vào rất ngon lành, nhưng rất tiếc bánh sau sẽ bị đè lên vạch ngay. Tượng tự lúc ra khỏi vòng số 8 cũng như vậy. Bản phải cho xe ra từ mép ngoài bên trái, nếu không bánh sau của xe cũng sẽ đè lên vạch.\n" +
                "\n" +
                "Lưu ý: Lúc thi thực hành bạn nên để xe ở số 3 mà đi. Bởi vì số 4 thì có thể sẽ hơi yếu, nhưng số 1, số 2 thì sẽ bị giật mạnh. Phần đường gồ gền bạn nên đề số 3 đi và nên đi chậm chậm vừa phải nếu không muốn bay luôn cả người.\n" +
                "\n" +
                "Có 4 bài thi chính:\n" +
                "\n" +
                "Bài 1: Chạy theo vòng số 8" +
                "\n" +
                "Khi nghe gọi tên vào thi phần thực hành lái xe mô tô thí sinh nhận xe, đội mũ bảo hiểm, gạt chống xe và nổ máy đưa xe vào vị trí xuất phát.\n" +
                "Trong phần thi số 1 thí sinh phải nhớ nguyên tắc vào 8 ra 3, phải chạy đủ vòng. Nên chạy với tốc độ vừa phải trong suốt lộ trình.\n" +
                "Khi nghe hiệu lệnh cửa người chấm thi thí sinh bắt đầu phần thi số 1 của bài thi thực hành thi sát hạch lái xe mô tô.\n" +
                "Gài số vào hình, xuất phát rẽ phải đi 1 vòng số 8 theo mũi tên đỏ sau đó vòng ra cửa số 2 theo hướng mũi tên xanh.\n" +
                "Lưu ý: Trong phần thi náy thí sinh nên để số 2 đi lương cho đầm máy. Khi chạy sa hình số 8, thí sinh nên hạ nhẹ ga xuống. Tuy nhiên tốc độ không nên quá chậm, nếu chạy nhanh thí sinh có thể rà thắng từ từ để phần bánh trước của xe bám vào vành ngoài của vòng số 8. Đầy là phần thi khó nhất của bài thi thực hành thi sát hạnc lái xe mô tô và giám khỏi cũng thường quan sát kỹ phần thi.\n" +
                "\n" +
                "Bài 2: Đi vào đường hẹp" +
                "\n" +
                "Trong phần thi này đường đi khá hẹp nên các bạn thí sinh cần lưu ý chỉ nên đi với tốc độ vừa phải và đi giữa đường để tránh bị chạm vạch.\n" +
                "\n" +
                "Bài 3: Chạy đường quanh co" +
                "\n" +
                "Trong phần 3 của bài thi thực hành thi sát hạch lái xe mô tô, thí sinh nên nhả ga đều đều để xe chạy nhịp nhành. Phần thi này đòi hỏi học viên cần có kỹ thuật đi vòng cua.\n" +
                "\n" +
                "Bài 4: Chạy đường gồ ghề" +
                "\n" +
                "Đây là phần thi thực hành cuối cùng. Các thí sinh phải giữ vững tay lái của mình để tránh tình trạng mất lái, không nên đi với tốc độ quá chậm vì sẽ dễ ngã xe ảnh hưởng đến bài thi.\n" +
                "\n" +
                "Thông tin vòng số 8 thi bằng lái xe máy\n" +
                "Như vậy là với 6 mẹo thi thực hành bằng lái xe a1 mà chúng tôi chia sẻ ở trên. Chắc chắn rằng bạn làm theo sẽ đạt tỉ lệ độ gần như tuyệt đối đấy ạ. Có khá nhiều bạn có thắc mắc không biết kích thước vòng số 8 để thi bằng lái xe máy rộng bao nhiêu cm. Sao mà nhìn nó nhỏ vậy thì làm sao mà đi được trong cái vòng nhỏ như thế được chứ ???\n" +
                "\n" +
                "Khi nhìn vào ảnh, chúng ta sẽ thấy kích thước của chúng khá là nhỏ. Nhìn có lẽ là điều khiển xe máy khá là khó khăn. Kích thước thật của vòng số 8 theo quy định của Bộ GTVT được đo với bán kính 2 vòng mỗi vòng 2,3 m. Khoảng cách giữa tâm của 2 đường tròn ấy là 5,7 m. Vị trí đi hẹp nhất xung quanh 2 đường tròn này rộng 0,7 m. Ở góc vào của hình số 8 có cửa vào nghiêng 45 độ so với trục OO’ và cửa ra thì đặt vuông góc với trục này.\n";

        //In đậm, can giua, chinh co chu dòng dau
        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);
        int firstLineEnd = fullText.indexOf("\n");
        builder.setSpan(new StyleSpan(Typeface.BOLD), 0, firstLineEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, firstLineEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(20, true), 0, firstLineEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //In dam
        String[] linesToBold = {
                "Tập trước vòng số 8 ở nhà.",
                "Ngày thi lên sân thi thật sớm.",
                "Cách đi để không chạm vạch.",
                "Có 4 bài thi chính:",
                "Bài 1: Chạy theo vòng số 8",
                "Bài 2: Đi vào đường hẹp",
                "Bài 3: Chạy đường quanh co",
                "Bài 4: Chạy đường gồ ghề"
        };

// In đậm từng dòng trong danh sách
        for (String line : linesToBold) {
            int start = fullText.indexOf(line);
            if (start != -1) {
                int end = start + line.length();
                builder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
//in nghieng
        String[] linesToItalic = {
                "Bài 1: Chạy theo vòng số 8",
                "Bài 2: Đi vào đường hẹp",
                "Bài 3: Chạy đường quanh co",
                "Bài 4: Chạy đường gồ ghề"
        };

        for (String line : linesToItalic) {
            int start = fullText.indexOf(line);
            if (start != -1) {
                int end = start + line.length();
                builder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        textView.setText(builder);
//        textView.setText(
//                "Hướng dẫn bài thi sa hình A1\n" +
//                        "Hiện nay, trong việc thi bằng lái xe máy thì phần thi thực hành chấm điểm tự động bằng chip. Do máy tự động chấm điểm nên việc thi thực hành tỷ lệ trượt nhiều hơn so với trước. Qua quá trình tổ chức thi sát hạch qua rất nhiều học việc đúc kết ra một số mẹo cần chuẩn bị trước để thi đảm bảo đỗ 100%\n" +
//                        "\n" +
//                        "Tập trước vòng số 8 ở nhà.\n" +
//                        "\n" +
//                        "\n" +
//                        "Phần thi thực hành trượt nhiều nhất là ở phần thi vòng số 8. Do đó, việc bạn đã biết đi xe máy thành thạo nhưng chưa tập qua vòng số 8 thì cũng rất dễ trượt. Bạn nên tập qua vòng số 8 càng nhiều lần càng tốt. Ít nhất là lúc đi xe vào vòng số 8 không còn cảm thấy loạng choạng.\n" +
//                        "\n" +
//                        "Ngày thi lên sân thi thật sớm.\n" +
//                        "\n" +
//                        "\n" +
//                        "Tại sao chúng tôi khuyên bạn như vậy? Bởi vì xe của bạn thì bạn quen với nó, bạn đi rất ngon lành. Nhưng xe của sân thi thì khác nhiều đấy, nếu bạn không đến sớm để làm quen thì cũng rất dễ trượt. Hơn nữa, buổi thi thường sẽ có rất đông người. Nếu bạn đến sớm thì không phải chờ lâu để được tập xe. Còn nếu đến muộn thì khả năng phải xếp hàng dài dài mới đến lượt mình.\n" +
//                        "\n" +
//                        "Cách đi để không chạm vạch.\n" +
//                        "\n" +
//                        "\n" +
//                        "Đi xe vào từ mép ngoài bên trái, bởi vì như thế thì bánh sau mới không bị đè lên vạch. Nếu bạn đi vào từ mép ngoài bên phải thì nhìn bánh trước vào rất ngon lành, nhưng rất tiếc bánh sau sẽ bị đè lên vạch ngay. Tượng tự lúc ra khỏi vòng số 8 cũng như vậy. Bản phải cho xe ra từ mép ngoài bên trái, nếu không bánh sau của xe cũng sẽ đè lên vạch.\n" +
//                        "\n" +
//                        "Lưu ý: Lúc thi thực hành bạn nên để xe ở số 3 mà đi. Bởi vì số 4 thì có thể sẽ hơi yếu, nhưng số 1, số 2 thì sẽ bị giật mạnh. Phần đường gồ gền bạn nên đề số 3 đi và nên đi chậm chậm vừa phải nếu không muốn bay luôn cả người.\n" +
//                        "\n" +
//                        "Có 4 bài thi chính:\n" +
//                        "Bài 1: Chạy theo vòng số 8\n" +
//                        "Bài 2: Đi vào đường hẹp\n" +
//                        "Bài 3: Chạy đường quanh co\n" +
//                        "Bài 4: Chạy đường gồ ghề\n" +
//                        "Thông tin vòng số 8 thi bằng lái xe máy\n" +
//                        "Bài 1: Chạy theo vòng số 8\n" +
//                        "Khi nghe gọi tên vào thi phần thực hành lái xe mô tô thí sinh nhận xe, đội mũ bảo hiểm, gạt chống xe và nổ máy đưa xe vào vị trí xuất phát.\n" +
//                        "Trong phần thi số 1 thí sinh phải nhớ nguyên tắc vào 8 ra 3, phải chạy đủ vòng. Nên chạy với tốc độ vừa phải trong suốt lộ trình.\n" +
//                        "Khi nghe hiệu lệnh cửa người chấm thi thí sinh bắt đầu phần thi số 1 của bài thi thực hành thi sát hạch lái xe mô tô.\n" +
//                        "Gài số vào hình, xuất phát rẽ phải đi 1 vòng số 8 theo mũi tên đỏ sau đó vòng ra cửa số 2 theo hướng mũi tên xanh.\n" +
//                        "Lưu ý: Trong phần thi náy thí sinh nên để số 2 đi lương cho đầm máy. Khi chạy sa hình số 8, thí sinh nên hạ nhẹ ga xuống. Tuy nhiên tốc độ không nên quá chậm, nếu chạy nhanh thí sinh có thể rà thắng từ từ để phần bánh trước của xe bám vào vành ngoài của vòng số 8. Đầy là phần thi khó nhất của bài thi thực hành thi sát hạnc lái xe mô tô và giám khỏi cũng thường quan sát kỹ phần thi.\n" +
//                        "Về đầu trang\n" +
//                        "Bài 2: Đi vào đường hẹp\n" +
//                        "Trong phần thi này đường đi khá hẹp nên các bạn thí sinh cần lưu ý chỉ nên đi với tốc độ vừa phải và đi giữa đường để tránh bị chạm vạch.\n" +
//                        "\n" +
//                        "Về đầu trang\n" +
//                        "Bài 3: Chạy đường quanh co\n" +
//                        "Trong phần 3 của bài thi thực hành thi sát hạch lái xe mô tô, thí sinh nên nhả ga đều đều để xe chạy nhịp nhành. Phần thi này đòi hỏi học viên cần có kỹ thuật đi vòng cua.\n" +
//                        "\n" +
//                        "Về đầu trang\n" +
//                        "Bài 4: Chạy đường gồ ghề\n" +
//                        "Đây là phần thi thực hành cuối cùng. Các thí sinh phải giữ vững tay lái của mình để tránh tình trạng mất lái, không nên đi với tốc độ quá chậm vì sẽ dễ ngã xe ảnh hưởng đến bài thi.\n" +
//                        "\n" +
//                        "Về đầu trang\n" +
//                        "Thông tin vòng số 8 thi bằng lái xe máy\n" +
//                        "Như vậy là với 6 mẹo thi thực hành bằng lái xe a1 mà chúng tôi chia sẻ ở trên. Chắc chắn rằng bạn làm theo sẽ đạt tỉ lệ độ gần như tuyệt đối đấy ạ. Có khá nhiều bạn có thắc mắc không biết kích thước vòng số 8 để thi bằng lái xe máy rộng bao nhiêu cm. Sao mà nhìn nó nhỏ vậy thì làm sao mà đi được trong cái vòng nhỏ như thế được chứ ???\n" +
//                        "\n" +
//                        "Khi nhìn vào ảnh, chúng ta sẽ thấy kích thước của chúng khá là nhỏ. Nhìn có lẽ là điều khiển xe máy khá là khó khăn. Kích thước thật của vòng số 8 theo quy định của Bộ GTVT được đo với bán kính 2 vòng mỗi vòng 2,3 m. Khoảng cách giữa tâm của 2 đường tròn ấy là 5,7 m. Vị trí đi hẹp nhất xung quanh 2 đường tròn này rộng 0,7 m. Ở góc vào của hình số 8 có cửa vào nghiêng 45 độ so với trục OO’ và cửa ra thì đặt vuông góc với trục này.\n"
//        );
        return view;
    }
}
