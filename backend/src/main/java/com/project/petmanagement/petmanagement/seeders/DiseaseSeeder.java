package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.Disease;
import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.repositories.DiseaseRepository;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;

@Component
public class DiseaseSeeder {
    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    public void seed() {
        List<Species> species = speciesRepository.findAll();

        Disease d1 = Disease.builder()
                .id(1L)
                .symptoms("Mệt mỏi, mất sự ngon miệng, nôn mửa")
                .description("Viêm Ruột Dạng Chó (Parvo) là một căn bệnh virus cực kỳ lây lan ảnh hưởng đến chó, đặc biệt là các chó con. Nếu không được điều trị kịp thời, nó có thể gây tử vong.")
                .name("Viêm Ruột Dạng Chó (Parvo)")
                .species(species.get(0))
                .build();

        Disease d2 = Disease.builder()
                .id(2L)
                .symptoms("Ho, hắt hơi, chảy dịch mũi")
                .description("Hội Chứng Bệnh Hô Hấp Nhiễm Trùng Ở Chó (CIRDC), còn được biết đến với tên gọi ho địt, là một bệnh dịch hô hấp lây lan ở chó. Thường lây lan trong các khu vực mà chó tiếp xúc gần nhau, như các nhà chó hoặc công viên cho chó.")
                .name("Hội Chứng Bệnh Hô Hấp Nhiễm Trùng Ở Chó (CIRDC)")
                .species(species.get(0))
                .build();

        Disease d3 = Disease.builder()
                .id(3L)
                .symptoms("Ngứa, rụng lông, nhiễm trùng da")
                .description("Dị ứng Da Atopic Ở Chó, phổ biến được biết đến với tên gọi dị ứng chó, là một tình trạng da mãn tính gây ngứa và viêm nhiễm. Nó có thể được kích thích bởi các chất gây dị ứng khác nhau, bao gồm phấn hoa, chấn động bụi và một số loại thức ăn.")
                .name("Dị ứng Da Atopic Ở Chó")
                .species(species.get(0))
                .build();

        Disease d4 = Disease.builder()
                .id(4L)
                .symptoms("Đi khập khiễng, khớp sưng, cứng cỏi")
                .description("Viêm Khớp Sưng Ở Chó, còn được biết đến với tên gọi bệnh thoái hóa khớp, là một tình trạng tiến triển ảnh hưởng đến các khớp ở chó. Nó được đặc trưng bởi viêm nhiễm, đau và mất khả năng di chuyển, thường dẫn đến đi chập chờn.")
                .name("Viêm Khớp Sưng Ở Chó")
                .species(species.get(0))
                .build();

        Disease d5 = Disease.builder()
                .id(5L)
                .symptoms("Sốt, mệt mỏi, giảm sự ngon miệng")
                .description("Bệnh Canine Distemper là một căn bệnh virus ảnh hưởng đến hệ hô hấp, tiêu hóa và thần kinh ở chó. Nó rất lây lan và có thể gây tử vong, đặc biệt là ở chó con và chó chưa tiêm phòng.")
                .name("Bệnh Canine Distemper")
                .species(species.get(0))
                .build();

        Disease d6 = Disease.builder()
                .id(6L)
                .symptoms("Mắt đỏ, dịch mắt, nhắm mắt")
                .description("Viêm Mạc Mắt Ở Chó, còn được biết đến với tên gọi viêm mắt hồng, là một tình trạng viêm nhiễm của màng nhầy, màng mỏng bao quanh bề mặt nội của miệng và phần trắng của mắt. Nó có thể được gây ra bởi các nhiễm trùng, dị ứng hoặc chất kích ứng.")
                .name("Viêm Mạc Mắt Ở Chó")
                .species(species.get(0))
                .build();

        Disease d7 = Disease.builder()
                .id(7L)
                .symptoms("Nôn mửa, tiêu chảy, đau bụng")
                .description("Viêm Dạ Dày - Ruột Ở Chó là một sự viêm nhiễm của dạ dày và ruột ở chó. Nó có thể được gây ra bởi các nhiễm trùng, không kiểm soát dinh dưỡng hoặc ký sinh trùng. Triệu chứng bao gồm nôn mửa, tiêu chảy và đau bụng.")
                .name("Viêm Dạ Dày - Ruột Ở Chó")
                .species(species.get(0))
                .build();

        Disease d8 = Disease.builder()
                .id(8L)
                .symptoms("Ho, khả năng tập thể dục giảm, ngất xỉu")
                .description("Bệnh Sống Tròng Ở Chó là một căn bệnh nghiêm trọng và có thể gây tử vong do sự nhiễm sắc ký sinh trùng sống trong trái tim và các mạch máu của chó. Nó được lây lan qua muỗi và có thể dẫn đến suy tim nếu không được điều trị.")
                .name("Bệnh Sống Tròng Ở Chó")
                .species(species.get(0))
                .build();

        Disease d9 = Disease.builder()
                .id(9L)
                .symptoms("Đi khập khiễng, sưng, đi chập chờn")
                .description("Bệnh Lyme Ở Chó là một nhiễm khuẩn được truyền từ ve. Nó có thể gây viêm khớp, đi chập chờn và sốt ở chó bị ảnh hưởng. Việc điều trị kịp thời bằng kháng sinh là cần thiết để ngăn chặn các biến chứng kéo dài.")
                .name("Bệnh Lyme Ở Chó")
                .species(species.get(0))
                .build();

        Disease d10 = Disease.builder()
                .id(10L)
                .symptoms("Khát nước quá mức, đi tiểu thường xuyên, mất cân nặng")
                .description("Bệnh Tiểu Đường Ở Chó là một rối loạn chuyển hóa được đặc trưng bởi mức đường trong máu cao do sự sản xuất insulin không đủ hoặc kháng insulin. Nó đòi hỏi quản lý suốt đời bằng cách tiêm insulin và kiểm soát chế độ ăn uống.")
                .name("Bệnh Tiểu Đường Ở Chó")
                .species(species.get(0))
                .build();
        Disease d11 = Disease.builder()
                .id(11L)
                .symptoms("Mệt mỏi, mất sự ngon miệng, nôn mửa")
                .description("Bệnh Viêm Ruột Dạng Mèo, còn được gọi là Bệnh Viêm Ruột Dạng Mèo, là một căn bệnh virus cực kỳ lây lan ảnh hưởng đến mèo. Nó có thể gây nôn mửa nặng, tiêu chảy và mất nước, đặc biệt nguy hiểm đối với mèo con.")
                .name("Bệnh Viêm Ruột Dạng Mèo (Distemper)")
                .species(species.get(1))
                .build();

        Disease d12 = Disease.builder()
                .id(12L)
                .symptoms("Hắt hơi, chảy nước mũi, chảy nước mắt")
                .description("Bệnh Nhiễm Trùng Hô Hấp Trên Ở Mèo (URI) là một căn bệnh virus hoặc vi khuẩn phổ biến ảnh hưởng đến mỡi, họng và khu vực xoang mũi ở mèo. Nó có thể gây ra các triệu chứng như hắt hơi, tiêu chảy mũi, và viêm kết mạc.")
                .name("Bệnh Nhiễm Trùng Hô Hấp Trên Ở Mèo (URI)")
                .species(species.get(1))
                .build();

        Disease d13 = Disease.builder()
                .id(13L)
                .symptoms("Ngứa, rụng lông, vết da")
                .description("Dị ứng Da Do Ve Mèo (FAD) là một tình trạng da do phản ứng dị ứng với dịch nhọt của ve. Mèo mắc FAD có thể gặp ngứa nặng, rụng lông, và viêm nhiễm da, đặc biệt là xung quanh đuôi và cổ.")
                .name("Dị ứng Da Do Ve Mèo (FAD)")
                .species(species.get(1))
                .build();

        Disease d14 = Disease.builder()
                .id(14L)
                .symptoms("Mệt mỏi, giảm sự ngon miệng, mất cân nặng")
                .description("Bệnh Thận Mạn Của Mèo là một tình trạng tiến triển ảnh hưởng đến thận ở mèo, dẫn đến giảm chức năng thận theo thời gian. Triệu chứng có thể bao gồm mệt mỏi, giảm sự ngon miệng, mất cân nặng, và cảm giác khát nước và tiểu tiện tăng lên.")
                .name("Bệnh Thận Mạn Của Mèo")
                .species(species.get(1))
                .build();

        Disease d15 = Disease.builder()
                .id(15L)
                .symptoms("Cảm giác khát nước tăng, tiểu tiện tăng, mất cân nặng")
                .description("Bệnh Tiểu Đường Ở Mèo là một rối loạn chuyển hóa được đặc trưng bởi mức đường trong máu cao do sự sản xuất insulin không đủ hoặc kháng insulin. Triệu chứng có thể bao gồm cảm giác khát nước tăng, tiểu tiện tăng, mất cân nặng, và mệt mỏi.")
                .name("Bệnh Tiểu Đường Ở Mèo")
                .species(species.get(1))
                .build();

        Disease d16 = Disease.builder()
                .id(16L)
                .symptoms("Đau nhức, khớp sưng, không muốn di chuyển")
                .description("Bệnh Thoái Hóa Khớp Ở Mèo là một căn bệnh thoái hóa khớp tiến triển ảnh hưởng đến mèo, gây ra đau nhức, sưng và giảm khả năng di chuyển trong các khớp. Thường thấy ở mèo già nhưng cũng có thể xảy ra ở mèo trẻ do chấn thương hoặc yếu tố di truyền.")
                .name("Bệnh Thoái Hóa Khớp Ở Mèo")
                .species(species.get(1))
                .build();

        Disease d17 = Disease.builder()
                .id(17L).symptoms("Nôn mửa, tiêu chảy, đau bụng")
                .description("Viêm Dạ Dày - Ruột Ở Mèo là một sự viêm nhiễm của dạ dày và ruột ở mèo. Nó có thể được gây ra bởi các nhiễm trùng, không kiểm soát dinh dưỡng hoặc ký sinh trùng. Triệu chứng bao gồm nôn mửa, tiêu chảy và đau bụng.")
                .name("Viêm Dạ Dày - Ruột Ở Mèo")
                .species(species.get(1))
                .build();

        Disease d18 = Disease.builder()
                .id(18L)
                .symptoms("Khó thở, ho, ngạt thở")
                .description("Bệnh Sống Trùng Ở Mèo là một căn bệnh nghiêm trọng và có thể gây tử vong do sự nhiễm trùng sắc ký sinh trùng sống trong trái tim và các mạch máu của mèo. Nó được lây lan qua muỗi và có thể dẫn đến suy tim nếu không được điều trị.")
                .name("Bệnh Sống Trùng Ở Mèo")
                .species(species.get(1))
                .build();

        Disease d19 = Disease.builder()
                .id(19L)
                .symptoms("Làm chập chờn, sưng khớp, mất khả năng di chuyển")
                .description("Bệnh Viêm Khớp Sưng Ở Mèo, còn được biết đến với tên gọi là bệnh thoái hóa khớp, là một tình trạng tiến triển ảnh hưởng đến các khớp ở mèo. Nó được đặc trưng bởi viêm nhiễm, đau và mất khả năng di chuyển, thường dẫn đến đi chập chờn.")
                .name("Bệnh Viêm Khớp Sưng Ở Mèo")
                .species(species.get(1))
                .build();

        Disease d20 = Disease.builder()
                .id(20L)
                .symptoms("Khó thở, ho, ngạt thở")
                .description("Bệnh Sống Trùng Ở Mèo là một căn bệnh nghiêm trọng và có thể gây tử vong do sự nhiễm trùng sắc ký sinh trùng sống trong trái tim và các mạch máu của mèo. Nó được lây lan qua muỗi và có thể dẫn đến suy tim nếu không được điều trị.")
                .name("Bệnh Sống Trùng Ở Mèo")
                .species(species.get(1))
                .build();

        diseaseRepository.saveAll(
                Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20));
    }
}
