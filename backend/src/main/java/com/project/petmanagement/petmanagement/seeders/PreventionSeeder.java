package com.project.petmanagement.petmanagement.seeders;

import com.project.petmanagement.petmanagement.models.entity.Disease;
import com.project.petmanagement.petmanagement.models.entity.Prevention;
import com.project.petmanagement.petmanagement.repositories.DiseaseRepository;
import com.project.petmanagement.petmanagement.repositories.PreventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PreventionSeeder {
    @Autowired
    private PreventionRepository preventionRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;

    public void seed() {
        List<Disease> diseaseList = diseaseRepository.findAll();

        // Prevention objects for Disease d1
        Prevention p1_1 = Prevention.builder().id(1L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(0).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(0)).build();
        Prevention p1_2 = Prevention.builder().id(2L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(0).getName() + " cho thú cưng.").disease(diseaseList.get(0)).build();

        // Prevention objects for Disease d2
        Prevention p2_1 = Prevention.builder().id(3L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(1).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(1)).build();
        Prevention p2_2 = Prevention.builder().id(4L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(1).getName() + " cho thú cưng.").disease(diseaseList.get(1)).build();

        // Prevention objects for Disease d3
        Prevention p3_1 = Prevention.builder().id(5L).name("Kiểm tra sức khỏe định kỳ").description("Thực hiện kiểm tra sức khỏe định kỳ cho thú cưng để phát hiện và điều trị sớm các triệu chứng của " + diseaseList.get(2).getName() + ".").disease(diseaseList.get(2)).build();
        Prevention p3_2 = Prevention.builder().id(6L).name("Chăm sóc lông và da").description("Chải lông và chăm sóc da cho thú cưng hàng ngày để giảm nguy cơ lây nhiễm " + diseaseList.get(2).getName() + " qua vết trầy xước hoặc da hỏng.").disease(diseaseList.get(2)).build();

        // Prevention objects for Disease d4
        Prevention p4_1 = Prevention.builder().id(7L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(3).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(3)).build();
        Prevention p4_2 = Prevention.builder().id(8L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(3).getName() + " cho thú cưng.").disease(diseaseList.get(3)).build();

        // Prevention objects for Disease d5
        Prevention p5_1 = Prevention.builder().id(9L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(4).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(4)).build();
        Prevention p5_2 = Prevention.builder().id(10L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(4).getName() + " cho thú cưng.").disease(diseaseList.get(4)).build();

        // Prevention objects for Disease d6
        Prevention p6_1 = Prevention.builder().id(11L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(5).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(5)).build();
        Prevention p6_2 = Prevention.builder().id(12L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(5).getName() + " cho thú cưng.").disease(diseaseList.get(5)).build();

        // Prevention objects for Disease d7
        Prevention p7_1 = Prevention.builder().id(13L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(6).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(6)).build();
        Prevention p7_2 = Prevention.builder().id(14L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(6).getName() + " cho thú cưng.").disease(diseaseList.get(6)).build();

        // Prevention objects for Disease d8
        Prevention p8_1 = Prevention.builder().id(15L).name("Kiểm tra sức khỏe định kỳ").description("Thực hiện kiểm tra sức khỏe định kỳ cho thú cưng để phát hiện và điều trị sớm các triệu chứng của " + diseaseList.get(7).getName() + ".").disease(diseaseList.get(7)).build();
        Prevention p8_2 = Prevention.builder().id(16L).name("Chăm sóc lông và da").description("Chải lông và chăm sóc da cho thú cưng hàng ngày để giảm nguy cơ lây nhiễm " + diseaseList.get(7).getName() + " qua vết trầy xước hoặc da hỏng.").disease(diseaseList.get(7)).build();

        // Prevention objects for Disease d9
        Prevention p9_1 = Prevention.builder().id(17L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(8).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(8)).build();
        Prevention p9_2 = Prevention.builder().id(18L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(8).getName() + " cho thú cưng.").disease(diseaseList.get(8)).build();

        // Prevention objects for Disease d10
        Prevention p10_1 = Prevention.builder().id(19L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(9).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(9)).build();
        Prevention p10_2 = Prevention.builder().id(20L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(9).getName() + " cho thú cưng.").disease(diseaseList.get(9)).build();

        // Prevention objects for Disease d11
        Prevention p11_1 = Prevention.builder().id(21L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(10).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(10)).build();
        Prevention p11_2 = Prevention.builder().id(22L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(10).getName() + " cho thú cưng.").disease(diseaseList.get(10)).build();

        // Prevention objects for Disease d12
        Prevention p12_1 = Prevention.builder().id(23L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(11).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(11)).build();
        Prevention p12_2 = Prevention.builder().id(24L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(11).getName() + " cho thú cưng.").disease(diseaseList.get(11)).build();

        // Prevention objects for Disease d13
        Prevention p13_1 = Prevention.builder().id(25L).name("Kiểm tra sức khỏe định kỳ").description("Thực hiện kiểm tra sức khỏe định kỳ cho thú cưng để phát hiện và điều trị sớm các triệu chứng của " + diseaseList.get(12).getName() + ".").disease(diseaseList.get(12)).build();
        Prevention p13_2 = Prevention.builder().id(26L).name("Chăm sóc lông và da").description("Chải lông và chăm sóc da cho thú cưng hàng ngày để giảm nguy cơ lây nhiễm " + diseaseList.get(12).getName() + " qua vết trầy xước hoặc da hỏng.").disease(diseaseList.get(12)).build();

        // Prevention objects for Disease d14
        Prevention p14_1 = Prevention.builder().id(27L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(13).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(13)).build();
        Prevention p14_2 = Prevention.builder().id(28L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(13).getName() + " cho thú cưng.").disease(diseaseList.get(13)).build();

        // Prevention objects for Disease d15
        Prevention p15_1 = Prevention.builder().id(29L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(14).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(14)).build();
        Prevention p15_2 = Prevention.builder().id(30L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(14).getName() + " cho thú cưng.").disease(diseaseList.get(14)).build();

        // Prevention objects for Disease d16
        Prevention p16_1 = Prevention.builder().id(31L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(15).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(15)).build();
        Prevention p16_2 = Prevention.builder().id(32L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(15).getName() + " cho thú cưng.").disease(diseaseList.get(15)).build();

        // Prevention objects for Disease d17
        Prevention p17_1 = Prevention.builder().id(33L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(16).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(16)).build();
        Prevention p17_2 = Prevention.builder().id(34L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(16).getName() + " cho thú cưng.").disease(diseaseList.get(16)).build();

        // Prevention objects for Disease d18
        Prevention p18_1 = Prevention.builder().id(35L).name("Kiểm tra sức khỏe định kỳ").description("Thực hiện kiểm tra sức khỏe định kỳ cho thú cưng để phát hiện và điều trị sớm các triệu chứng của " + diseaseList.get(17).getName() + ".").disease(diseaseList.get(17)).build();
        Prevention p18_2 = Prevention.builder().id(36L).name("Chăm sóc lông và da").description("Chải lông và chăm sóc da cho thú cưng hàng ngày để giảm nguy cơ lây nhiễm " + diseaseList.get(17).getName() + " qua vết trầy xước hoặc da hỏng.").disease(diseaseList.get(17)).build();

        // Prevention objects for Disease d19
        Prevention p19_1 = Prevention.builder().id(37L).name("Tiêm vaccine phòng tránh").description("Tiêm vaccine phòng tránh bệnh " + diseaseList.get(18).getName() + " theo lịch trình của bác sĩ thú y để giảm nguy cơ lây nhiễm.").disease(diseaseList.get(18)).build();
        Prevention p19_2 = Prevention.builder().id(38L).name("Giữ vệ sinh môi trường sống").description("Giữ môi trường sống sạch sẽ và khô ráo để giảm nguy cơ lây nhiễm " + diseaseList.get(18).getName() + " cho thú cưng.").disease(diseaseList.get(18)).build();

        // Prevention objects for Disease d20
        Prevention p20_1 = Prevention.builder().id(39L).name("Thúc đẩy tiêm vaccine định kỳ").description("Thúc đẩy việc tiêm vaccine phòng tránh bệnh " + diseaseList.get(19).getName() + " định kỳ theo lịch trình của bác sĩ thú y.").disease(diseaseList.get(19)).build();
        Prevention p20_2 = Prevention.builder().id(40L).name("Rửa sạch nơi sinh sống").description("Rửa sạch nơi sinh sống và đồ chơi của thú cưng để giảm nguy cơ lây nhiễm " + diseaseList.get(19).getName() + " cho thú cưng.").disease(diseaseList.get(19)).build();

        preventionRepository.saveAll(
                Arrays.asList(p1_1, p1_2, p2_1, p2_2, p3_1, p3_2, p4_1, p4_2, p5_1, p5_2, p6_1, p6_2, p7_1, p7_2, p8_1, p8_2, p9_1, p9_2, p10_1, p10_2, p11_1, p11_2, p12_1, p12_2, p13_1, p13_2, p14_1, p14_2, p15_1, p15_2, p16_1, p16_2, p17_1, p17_2, p18_1, p18_2, p19_1, p19_2, p20_1, p20_2));
    }
}
