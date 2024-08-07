package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.DailyActivity;
import com.project.petmanagement.petmanagement.repositories.DailyActivityRepository;

@Component
public class DailyActivitySeeder {
    @Autowired
    private DailyActivityRepository repository;

    public void seed() {
        DailyActivity d1 = DailyActivity.builder()
                .id(1L)
                .name("Tắm")
                .description("Tắm cho thú cưng sạch sẽ và thoải mái hàng ngày để loại bỏ bụi bẩn và lớp dầu trên da.")
                .build();

        DailyActivity d2 = DailyActivity.builder()
                .id(2L)
                .name("Cho ăn")
                .description("Cung cấp thức ăn đủ dinh dưỡng và đúng lượng cho thú cưng hàng ngày để duy trì sức khỏe tốt.")
                .build();

        DailyActivity d3 = DailyActivity.builder()
                .id(3L)
                .name("Đi dạo")
                .description("Dắt thú cưng đi dạo ngoài trời để tập thể dục và thư giãn hàng ngày.")
                .build();

        DailyActivity d4 = DailyActivity.builder()
                .id(4L)
                .name("Chơi cùng")
                .description("Dành thời gian chơi cùng thú cưng để tạo mối quan hệ gần gũi và giảm căng thẳng.")
                .build();

        DailyActivity d5 = DailyActivity.builder()
                .id(5L)
                .name("Kiểm tra sức khỏe")
                .description("Kiểm tra sức khỏe cho thú cưng hàng ngày để phát hiện sớm các vấn đề sức khỏe.")
                .build();

        DailyActivity d6 = DailyActivity.builder()
                .id(6L)
                .name("Học mới")
                .description("Dạy thú cưng một kỹ năng mới hoặc một lệnh lúc nào cũng là một hoạt động tốt.")
                .build();

        DailyActivity d7 = DailyActivity.builder()
                .id(7L)
                .name("Ngủ")
                .description("Đảm bảo thú cưng có đủ thời gian ngủ để phục hồi và nạp năng lượng.")
                .build();

        DailyActivity d8 = DailyActivity.builder()
                .id(8L)
                .name("Chăm sóc lông")
                .description("Chải lông và chăm sóc da cho thú cưng để giữ lông mềm mại và tránh tình trạng rối.")
                .build();

        DailyActivity d9 = DailyActivity.builder()
                .id(9L)
                .name("Thăm bác sĩ thú y")
                .description("Duy trì lịch hẹn kiểm tra định kỳ với bác sĩ thú y để đảm bảo sức khỏe của thú cưng.")
                .build();

        DailyActivity d10 = DailyActivity.builder()
                .id(10L)
                .name("Thư giãn")
                .description("Cho thú cưng thư giãn và nghỉ ngơi trong một không gian yên tĩnh và thoải mái.")
                .build();

        DailyActivity d11 = DailyActivity.builder()
                .id(11L)
                .name("Học lỏm")
                .description("Dùng thời gian để học lỏm và khám phá môi trường xung quanh.")
                .build();

        DailyActivity d12 = DailyActivity.builder()
                .id(12L)
                .name("Thư giãn ngoài trời")
                .description("Cho thú cưng thư giãn và vận động ngoài trời để tận hưởng không khí trong lành.")
                .build();

        DailyActivity d13 = DailyActivity.builder()
                .id(13L)
                .name("Chơi đùa")
                .description("Dành thời gian chơi đùa và tạo niềm vui cho thú cưng hàng ngày.")
                .build();

        DailyActivity d14 = DailyActivity.builder()
                .id(14L)
                .name("Tập thể dục")
                .description("Thực hiện các bài tập thể dục như nhảy dây hoặc chạy để giữ thú cưng khỏe mạnh.")
                .build();

        DailyActivity d15 = DailyActivity.builder()
                .id(15L)
                .name("Tham gia hoạt động nhóm")
                .description("Tham gia các hoạt động nhóm hoặc tổ chức sự kiện dành cho thú cưng để tạo mối quan hệ xã hội.")
                .build();

        DailyActivity d16 = DailyActivity.builder()
                .id(16L)
                .name("Dùng đồ chơi")
                .description("Cung cấp đồ chơi phù hợp để thú cưng giải trí và tăng cường kỹ năng.")
                .build();

        DailyActivity d17 = DailyActivity.builder()
                .id(17L)
                .name("Thiền")
                .description("Cho thú cưng tham gia các hoạt động thiền để giảm căng thẳng và tăng cường tâm trí.")
                .build();

        DailyActivity d18 = DailyActivity.builder()
                .id(18L)
                .name("Chơi xã giao")
                .description("Dẫn thú cưng ra ngoài gặp gỡ và chơi với các thú cưng khác để phát triển kỹ năng giao tiếp xã hội.")
                .build();

        DailyActivity d19 = DailyActivity.builder()
                .id(19L)
                .name("Tạo môi trường mới")
                .description("Tạo môi trường mới và thú vị cho thú cưng bằng cách thay đổi cấu trúc hoặc sắp xếp không gian.")
                .build();

        DailyActivity d20 = DailyActivity.builder()
                .id(20L)
                .name("Chia sẻ thời gian")
                .description("Dành thời gian chia sẻ và tạo kỷ niệm với thú cưng hàng ngày.")
                .build();

        repository.saveAll(
                Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20));
    }
}
