package com.project.petmanagement.petmanagement.seeders;

import java.util.Arrays;
import java.util.List;

import com.project.petmanagement.petmanagement.models.entity.Species;
import com.project.petmanagement.petmanagement.models.enums.QualityEnum;
import com.project.petmanagement.petmanagement.repositories.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.petmanagement.petmanagement.models.entity.FoodType;
import com.project.petmanagement.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.petmanagement.repositories.FoodTypeRepository;
import com.project.petmanagement.petmanagement.repositories.NutritiousFoodRepository;

@Component
public class NutritiousFoodSeeder {
    @Autowired
    private NutritiousFoodRepository NutritiousFoodRepository;

    @Autowired
    private FoodTypeRepository foodTypeRepository;
    @Autowired
    private SpeciesRepository speciesRepository;

    public void seed() {
        List<FoodType> listFoodTypes = foodTypeRepository.findAll();
        List<Species> speciesList = speciesRepository.findAll();

        NutritiousFood nutri1 = NutritiousFood.builder()
                .id(1L)
                .image("https://images.albertsons-media.com/is/image/ABS/960165945-ECOM?$ng-ecom-pdp-desktop$&defaultImage=Not_Available")
                .name("Miếng Snack Sức Khỏe Acme")
                .nutrition("Protein: 24 gram,Năng lượng : 99 calo,Chất béo: 0.3 gram")
                .quality(QualityEnum.MEDIUM)
                .ingredient("500gr thịt ức gà không xương,1 củ tỏi,3 cây sả,100gr lá chanh,ớt tươi,1 củ gừng")
                .foodType(listFoodTypes.get(0))
                .species(speciesList.get(0))
                .description("Thức ăn khô cao cấp được bổ sung các loại vitamin và khoáng chất cho sức khỏe tổng thể.")
                .build();
        nutri1.setFoodType(listFoodTypes.get(0));

        NutritiousFood nutri2 = NutritiousFood.builder()
                .id(2L)
                .image("https://down-ph.img.susercontent.com/file/ph-11134207-7r98w-lmg9p8schxu742")
                .name("NutriPaws Món Gà Lựa Chọn")
                .nutrition("Protein: 12 gram,Năng lượng : 34 calo,Chất béo: 1 gram")
                .quality(QualityEnum.MEDIUM)
                .foodType(listFoodTypes.get(0))
                .ingredient("Tim Cừu, cá hồi, hạt lanh, óc xanh New Zealand, taurine, rong biển xấy...")
                .foodType(listFoodTypes.get(2))
                .description("Thức ăn ướt đóng hộp với các miếng gà thơm ngon trong nước sốt đậm đà.")
                .build();
        nutri2.setFoodType(listFoodTypes.get(2));

        NutritiousFood nutri3 = NutritiousFood.builder()
                .id(3L)
                .name("Bữa Tiệc Salmon Tự Nhiên")
                .image("https://petsvilla.co.uk/cdn/shop/products/pamico-mix-meal-freeze-dried-rabbit-liver-120g-756944.jpg?v=1701877018&width=1946")
                .nutrition("Protein: 30 gram,Năng lượng : 20 calo,Chất béo: 0.5 gram")
                .quality(QualityEnum.MEDIUM)
                .foodType(listFoodTypes.get(0))
                .ingredient("Dầu và Bột Thư Giãn (400MG), Bột Cúc La Mã (200MG), L-glutamin (100MG), Hoa Passiflora (120MG)")
                .foodType(listFoodTypes.get(3))
                .description("Thức ăn sống kết hợp giữa cá hồi tươi và các chất dinh dưỡng cần thiết.")
                .build();
        nutri3.setFoodType(listFoodTypes.get(3));

        NutritiousFood nutri4 = NutritiousFood.builder()
                .id(4L)
                .name("Sự Kết Hợp Thịt Bò Hoàn Hảo")
                .image("https://m.media-amazon.com/images/I/91FRI4sCsHL._AC_UF894,1000_QL80_.jpg")
                .nutrition("Protein: 22 gram,Năng lượng : 48 calo,Chất béo: 2 gram")
                .quality(QualityEnum.MEDIUM)
                .foodType(listFoodTypes.get(0))
                .ingredient("Bột Garbanzo, Bột đậu, Hạt lanh, Dầu hạt lanh, Tinh bột sắn...")
                .foodType(listFoodTypes.get(4))
                .description("Sự kết hợp ngon miệng giữa thịt bò, gạo và rau cải cho một chế độ ăn cân đối.")
                .build();
        nutri4.setFoodType(listFoodTypes.get(4));

        NutritiousFood nutri5 = NutritiousFood.builder()
                .id(5L)
                .name("WhiskerWell Hỗn Hợp Không Chứa Ngũ Cốc")
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_D03QKy94z5MUPcE8sZxKTS4ohbyfexdPNUrAF_8ssQ&s")
                .nutrition("Protein: 10 gram,Năng lượng : 20 calo,Chất béo: 0.76 gram")
                .quality(QualityEnum.MEDIUM)
                .foodType(listFoodTypes.get(0))
                .ingredient("Magiê Stearate, Lecithin hướng dương, Bột dừa, Dầu hương liệu...")
                .foodType(listFoodTypes.get(4))
                .description("Thức ăn khô không chứa ngũ cốc với hỗn hợp thịt thật và rau cải.")
                .build();
        nutri5.setFoodType(listFoodTypes.get(5));

        NutritiousFood nutri6 = NutritiousFood.builder()
                .id(6L)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTkdVB-tutvsSK_UaFErp69i0QvsvzaWNMnjq9dLxaYgQ&s")
                .name("Chế Độ Ẩm Thực Được Chỉ Định Bởi Bác Sĩ Thú Y")
                .nutrition("Protein: 15 gram,Năng lượng : 50 calo,Chất béo: 3 gram")
                .quality(QualityEnum.HIGH)
                .foodType(listFoodTypes.get(1))
                .ingredient("Thịt gà, rau cải, cám gạo, dầu cá, vitamin và khoáng chất")
                .foodType(listFoodTypes.get(4))
                .description("Chế độ ăn được tạo ra dành riêng cho từng thú cưng dựa trên tình trạng sức khỏe của chúng.")
                .build();
        nutri6.setFoodType(listFoodTypes.get(4));

        NutritiousFood nutri7 = NutritiousFood.builder()
                .id(7L)
                .image("https://m.media-amazon.com/images/I/61LPxgv7IcL._AC_UF1000,1000_QL80_.jpg")
                .name("Hạt Khô Hữu Cơ Chứa Gà và Quinoa Organic")
                .nutrition("Protein: 18 gram,Năng lượng : 70 calo,Chất béo: 2.5 gram")
                .quality(QualityEnum.HIGH)
                .foodType(listFoodTypes.get(1))
                .ingredient("Gà hữu cơ, hạt quinoa, hạt hướng dương, vitamin và khoáng chất tự nhiên")
                .foodType(listFoodTypes.get(6))
                .description("Thức ăn hữu cơ và tự nhiên, không chứa hóa chất và chất bảo quản độc hại.")
                .build();
        nutri7.setFoodType(listFoodTypes.get(6));

        NutritiousFood nutri8 = NutritiousFood.builder()
                .id(8L)
                .image("https://i.etsystatic.com/14477933/r/il/19d9d8/4814089181/il_300x300.4814089181_51y9.jpg")
                .name("DentalDelish Hương Bạc Hà Thơm Mát")
                .nutrition("Protein: 8 gram,Năng lượng : 25 calo,Chất béo: 1.2 gram")
                .quality(QualityEnum.LOW)
                .foodType(listFoodTypes.get(1))
                .ingredient("Bột gạo nếp, bạc hà tươi, dầu cá ngừ, dầu hạt cải, tinh chất bạc hà")
                .foodType(listFoodTypes.get(8))
                .description("Bánh giúp làm sạch răng và cung cấp hơi thở thơm mát cho thú cưng của bạn.")
                .build();
        nutri8.setFoodType(listFoodTypes.get(8));

        NutritiousFood nutri9 = NutritiousFood.builder()
                .id(9L)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSaNIigbhkUXaA5_zsfnriYfhwLGfyauNaaG-uiWnoQMg&s")
                .name("RAWvolution Thịt Bò Tươi Lạnh")
                .nutrition("Protein: 20 gram,Năng lượng : 60 calo,Chất béo: 2.8 gram")
                .quality(QualityEnum.HIGH)
                .foodType(listFoodTypes.get(1))
                .ingredient("Thịt bò tươi, rau cải, dầu cá hồi, tinh chất rau mùi, vitamin và khoáng chất tự nhiên")
                .foodType(listFoodTypes.get(2))
                .description("Thức ăn tươi lạnh giúp tăng cường hệ miễn dịch và cung cấp năng lượng dồi dào.")
                .build();
        nutri9.setFoodType(listFoodTypes.get(2));

        NutritiousFood nutri10 = NutritiousFood.builder()
                .id(10L)
                .image("https://www.cottagedelight.co.uk/wp-content/uploads/2024/01/CD250055-Cheese-Makers-Pickle.png")
                .name("TastyTreats Món Phô Mai Thơm Ngon")
                .nutrition("Protein: 5 gram,Năng lượng : 15 calo,Chất béo: 0.8 gram")
                .quality(QualityEnum.LOW)
                .foodType(listFoodTypes.get(1))
                .ingredient("Bột gạo, phô mai cheddar, bột mỳ, dầu thực vật, hương liệu tự nhiên")
                .foodType(listFoodTypes.get(9))
                .description("Bánh thưởng thơm ngon giúp kích thích vị giác và tạo niềm vui cho thú cưng.")
                .build();
        nutri10.setFoodType(listFoodTypes.get(9));

        NutritiousFoodRepository.saveAll(
                Arrays.asList(nutri1, nutri2, nutri3, nutri4, nutri5, nutri6, nutri7, nutri8, nutri9, nutri10));
    }
}
