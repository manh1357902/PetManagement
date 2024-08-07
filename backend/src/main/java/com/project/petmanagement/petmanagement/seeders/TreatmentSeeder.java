package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.Disease;
import com.project.petmanagement.petmanagement.models.entity.Treatment;
import com.project.petmanagement.petmanagement.repositories.DiseaseRepository;
import com.project.petmanagement.petmanagement.repositories.TreatmentRepository;

@Component
public class TreatmentSeeder {
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;

    public void seed() {
        List<Disease> diseases = diseaseRepository.findAll();

        // Treatments for Disease d1
        Treatment t1_1 = Treatment.builder()
                .id(1L)
                .name("Tiêm vaccine")
                .description("Tiêm vaccine phòng ngừa virus Panleukopenia cho mèo để bảo vệ chúng khỏi bị nhiễm bệnh.")
                .disease(diseases.get(0))
                .build();

        Treatment t1_2 = Treatment.builder()
                .id(2L).name("Hỗ trợ điều trị tại nhà")
                .description("Cung cấp chăm sóc tại nhà bằng cách đảm bảo mèo được nghỉ ngơi đủ, cung cấp nước và thức ăn dễ tiêu hóa, và theo dõi triệu chứng để bảo đảm sự hồi phục.")
                .disease(diseases.get(0))
                .build();

        // Treatments for Disease d2
        Treatment t2_1 = Treatment.builder()
                .id(3L)
                .name("Tăng cường hệ miễn dịch")
                .description("Cung cấp thức ăn giàu chất dinh dưỡng và bổ sung vitamin để tăng cường hệ miễn dịch của mèo trong việc chống lại nhiễm trùng.")
                .disease(diseases.get(1))
                .build();

        Treatment t2_2 = Treatment.builder()
                .id(4L)
                .name("Xử lý triệu chứng")
                .description("Sử dụng thuốc giảm đau và thuốc chống viêm để giảm các triệu chứng như hắt hơi và chảy nước mũi.")
                .disease(diseases.get(1))
                .build();

        // Treatments for Disease diseases.get(2)
        Treatment t3_1 = Treatment.builder()
                .id(5L)
                .name("Xử lý vết bẩn ve")
                .description("Tắm và chải lông mèo để loại bỏ bẩn ve, cùng với việc sử dụng thuốc chống ve như giọt, thuốc xịt hoặc viên uống.")
                .disease(diseases.get(2))
                .build();

        Treatment t3_2 = Treatment.builder()
                .id(6L)
                .name("Giảm ngứa và viêm")
                .description("Sử dụng thuốc giảm ngứa và thuốc kháng viêm da để giảm triệu chứng và làm dịu vùng da bị kích ứng.")
                .disease(diseases.get(2))
                .build();

        // Treatments for Disease diseases.get(3)
        Treatment t4_1 = Treatment.builder()
                .id(7L)
                .name("Kiểm soát chế độ ăn uống")
                .description("Cung cấp chế độ ăn giàu protein và thấp phosphorus để giảm tác động lên thận và hỗ trợ sức khỏe thận cho mèo.")
                .disease(diseases.get(3))
                .build();

        Treatment t4_2 = Treatment.builder()
                .id(8L)
                .name("Điều trị triệu chứng")
                .description("Sử dụng thuốc giảm đau và thuốc chống viêm để giảm triệu chứng như mệt mỏi và giảm sự ngon miệng.")
                .disease(diseases.get(3))
                .build();

        // Treatments for Disease diseases.get(4)
        Treatment t5_1 = Treatment.builder()
                .id(9L)
                .name("Quản lý chế độ ăn uống")
                .description("Thực hiện chế độ ăn uống giàu chất xơ và thấp carbohydrate để kiểm soát mức đường huyết của mèo.")
                .disease(diseases.get(4))
                .build();

        Treatment t5_2 = Treatment.builder()
                .id(10L)
                .name("Tiêm insulin")
                .description("Mèo có thể cần tiêm insulin theo chỉ định của bác sĩ thú y để điều trị và kiểm soát bệnh tiểu đường.")
                .disease(diseases.get(4))
                .build();

        // Treatments for Disease diseases.get(5)
        Treatment t6_1 = Treatment.builder()
                .id(11L)
                .name("Giảm đau và viêm")
                .description("Sử dụng thuốc giảm đau và thuốc chống viêm để giảm triệu chứng và làm dịu vùng khớp bị viêm.")
                .disease(diseases.get(5))
                .build();

        Treatment t6_2 = Treatment.builder()
                .id(12L)
                .name("Hỗ trợ vận động")
                .description("Thực hiện các biện pháp hỗ trợ vận động như tập luyện nhẹ và vận động hàng ngày để duy trì sự linh hoạt của khớp.")
                .disease(diseases.get(5))
                .build();

        // Treatments for Disease diseases.get(6)
        Treatment t7_1 = Treatment.builder()
                .id(13L)
                .name("Quản lý chế độ ăn uống")
                .description("Cung cấp chế độ ăn dễ tiêu hóa và giàu chất dinh dưỡng để giảm tác động lên dạ dày và ruột.")
                .disease(diseases.get(6))
                .build();

        Treatment t7_2 = Treatment.builder()
                .id(14L)
                .name("Điều trị triệu chứng")
                .description("Sử dụng thuốc chống nôn và thuốc kháng viêm để giảm các triệu chứng như nôn mửa và đau bụng.")
                .disease(diseases.get(6))
                .build();

        // Treatments for Disease diseases.get(7)
        Treatment t8_1 = Treatment.builder()
                .id(15L)
                .name("Điều trị nhiễm khuẩn")
                .description("Sử dụng kháng sinh hoặc các loại thuốc kháng vi khuẩn khác để điều trị và ngăn chặn sự lây lan của nhiễm trùng.")
                .disease(diseases.get(7))
                .build();

        Treatment t8_2 = Treatment.builder()
                .id(16L)
                .name("Hỗ trợ hô hấp")
                .description("Cung cấp hỗ trợ hô hấp như sử dụng máy hít hoặc oxy hóa để giảm các triệu chứng như khó thở.")
                .disease(diseases.get(7))
                .build();

        // Treatments for Disease diseases.get(8)
        Treatment t9_1 = Treatment.builder()
                .id(17L)
                .name("Điều trị nhiễm khuẩn")
                .description("Sử dụng kháng sinh hoặc các loại thuốc kháng vi khuẩn khác để điều trị và ngăn chặn sự lây lan của nhiễm trùng.")
                .disease(diseases.get(8))
                .build();

        Treatment t9_2 = Treatment.builder()
                .id(18L)
                .name("Quản lý triệu chứng")
                .description("Sử dụng thuốc chống nôn và thuốc giảm đau để giảm các triệu chứng như nôn mửa và đau bụng.")
                .disease(diseases.get(8))
                .build();

        // Treatments for Disease diseases.get(9)
        Treatment t10_1 = Treatment.builder()
                .id(19L)
                .name("Kiểm soát chế độ ăn uống")
                .description("Cung cấp chế độ ăn giàu chất xơ và thấp carbohydrate để kiểm soát mức đường huyết của mèo.")
                .disease(diseases.get(9))
                .build();

        Treatment t10_2 = Treatment.builder()
                .id(20L)
                .name("Điều trị insulin")
                .description("Mèo có thể cần tiêm insulin theo chỉ định của bác sĩ thú y để điều trị và kiểm soát bệnh tiểu đường.")
                .disease(diseases.get(9))
                .build();

        // Treatments for Disease diseases.get(10)
        Treatment t11_1 = Treatment.builder()
                .id(21L)
                .name("Cung cấp nước và dưỡng chất")
                .description("Giữ cho mèo được hydrat hóa bằng cách cung cấp nước và thức ăn dễ tiêu hóa như thức ăn ướt hoặc thức ăn giàu nước.")
                .disease(diseases.get(10))
                .build();

        Treatment t11_2 = Treatment.builder()
                .id(22L)
                .name("Tiêm chống nôn")
                .description("Mèo có thể được tiêm thuốc chống nôn để giảm cảm giác buồn nôn và ngăn mửa.")
                .disease(diseases.get(10))
                .build();

        // Treatments for Disease diseases.get(11)
        Treatment t12_1 = Treatment.builder()
                .id(23L)
                .name("Khử trùng môi trường")
                .description("Dọn dẹp môi trường sống của mèo và sử dụng các chất khử trùng như nước sát khuẩn để giảm nguy cơ lây lan bệnh.")
                .disease(diseases.get(11))
                .build();

        Treatment t12_2 = Treatment.builder()
                .id(24L)
                .name("Thuốc kháng sinh")
                .description("Các loại thuốc kháng sinh có thể được sử dụng để điều trị các trường hợp nhiễm trùng vi khuẩn li.")
                .disease(diseases.get(11))
                .build();

        // Treatments for Disease diseases.get(12)
        Treatment t13_1 = Treatment.builder()
                .id(25L)
                .name("Xử lý vết bẩn ve")
                .description("Tắm và chải lông mèo để loại bỏ bẩn ve, cùng với việc sử dụng thuốc chống ve như giọt, thuốc xịt hoặc viên uống.")
                .disease(diseases.get(12))
                .build();

        Treatment t13_2 = Treatment.builder()
                .id(26L)
                .name("Giảm ngứa và viêm")
                .description("Sử dụng thuốc giảm ngứa và thuốc kháng viêm da để giảm triệu chứng và làm dịu vùng da bị kích ứng.")
                .disease(diseases.get(12))
                .build();

        // Treatments for Disease diseases.get(13)
        Treatment t14_1 = Treatment.builder()
                .id(27L)
                .name("Kiểm soát chế độ ăn uống")
                .description("Cung cấp chế độ ăn giàu protein và thấp phosphorus để giảm tác động lên thận và hỗ trợ sức khỏe thận cho mèo.")
                .disease(diseases.get(13))
                .build();

        Treatment t14_2 = Treatment.builder()
                .id(28L)
                .name("Điều trị triệu chứng")
                .description("Sử dụng thuốc giảm đau và thuốc chống viêm để giảm triệu chứng như mệt mỏi và giảm sự ngon miệng.")
                .disease(diseases.get(13))
                .build();

        // Treatments for Disease diseases.get(14)
        Treatment t15_1 = Treatment.builder()
                .id(29L)
                .name("Quản lý chế độ ăn uống")
                .description("Thực hiện chế độ ăn uống giàu chất xơ và thấp carbohydrate để kiểm soát mức đường huyết của mèo.")
                .disease(diseases.get(14))
                .build();

        Treatment t15_2 = Treatment.builder()
                .id(30L)
                .name("Tiêm insulin")
                .description("Mèo có thể cần tiêm insulin theo chỉ định của bác sĩ thú y để điều trị và kiểm soát bệnh tiểu đường.")
                .disease(diseases.get(14))
                .build();

        // Treatments for Disease diseases.get(15)
        Treatment t16_1 = Treatment.builder()
                .id(31L)
                .name("Giảm đau và viêm")
                .description("Sử dụng thuốc giảm đau và thuốc chống viêm để giảm triệu chứng và làm dịu vùng khớp bị viêm.")
                .disease(diseases.get(15))
                .build();

        Treatment t16_2 = Treatment.builder()
                .id(32L)
                .name("Hỗ trợ vận động")
                .description("Thực hiện các biện pháp hỗ trợ vận động như tập luyện nhẹ và vận động hàng ngày để duy trì sự linh hoạt của khớp.")
                .disease(diseases.get(15))
                .build();

        // Treatments for Disease diseases.get(16)
        Treatment t17_1 = Treatment.builder()
                .id(33L)
                .name("Quản lý chế độ ăn uống")
                .description("Cung cấp chế độ ăn dễ tiêu hóa và giàu chất dinh dưỡng để giảm tác động lên dạ dày và ruột.")
                .disease(diseases.get(16))
                .build();

        Treatment t17_2 = Treatment.builder()
                .id(34L)
                .name("Điều trị triệu chứng")
                .description("Sử dụng thuốc chống nôn và thuốc kháng viêm để giảm các triệu chứng như nôn mửa và đau bụng.")
                .disease(diseases.get(16))
                .build();

        // Treatments for Disease diseases.get(17)
        Treatment t18_1 = Treatment.builder()
                .id(35L)
                .name("Điều trị nhiễm khuẩn")
                .description("Sử dụng kháng sinh hoặc các loại thuốc kháng vi khuẩn khác để điều trị và ngăn chặn sự lây lan của nhiễm trùng.")
                .disease(diseases.get(17))
                .build();

        Treatment t18_2 = Treatment.builder()
                .id(36L)
                .name("Hỗ trợ hô hấp")
                .description("Cung cấp hỗ trợ hô hấp như sử dụng máy hít hoặc oxy hóa để giảm các triệu chứng như khó thở.")
                .disease(diseases.get(17))
                .build();

        // Treatments for Disease diseases.get(18)
        Treatment t19_1 = Treatment.builder()
                .id(37L)
                .name("Điều trị nhiễm khuẩn")
                .description("Sử dụng kháng sinh hoặc các loại thuốc kháng vi khuẩn khác để điều trị và ngăn chặn sự lây lan của nhiễm trùng.")
                .disease(diseases.get(18))
                .build();

        Treatment t19_2 = Treatment.builder()
                .id(38L)
                .name("Quản lý triệu chứng")
                .description("Sử dụng thuốc chống nôn và thuốc giảm đau để giảm các triệu chứng như nôn mửa và đau bụng.")
                .disease(diseases.get(18))
                .build();

        // Treatments for Disease diseases.get(19)
        Treatment t20_1 = Treatment.builder()
                .id(39L)
                .name("Kiểm soát chế độ ăn uống")
                .description("Cung cấp chế độ ăn giàu chất xơ và thấp carbohydrate để kiểm soát mức đường huyết của mèo.")
                .disease(diseases.get(19))
                .build();

        Treatment t20_2 = Treatment.builder()
                .id(40L)
                .name("Điều trị insulin")
                .description("Mèo có thể cần tiêm insulin theo chỉ định của bác sĩ thú y để điều trị và kiểm soát bệnh tiểu đường.")
                .disease(diseases.get(19))
                .build();

        treatmentRepository.saveAll(
                Arrays.asList(t1_1, t1_2, t2_1, t2_2, t3_1, t3_2, t4_1, t4_2, t5_1, t5_2, t6_1, t6_2, t7_1, t7_2, t8_1, t8_2, t9_1, t9_2, t10_1, t10_2, t11_1, t11_2, t12_1, t12_2, t13_1, t13_2, t14_1, t14_2, t15_1, t15_2, t16_1, t16_2, t17_1, t17_2, t18_1, t18_2, t19_1, t19_2, t20_1, t20_2));
    }
}
