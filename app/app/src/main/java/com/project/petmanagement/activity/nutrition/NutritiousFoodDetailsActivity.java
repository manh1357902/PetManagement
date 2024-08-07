package com.project.petmanagement.activity.nutrition;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.models.enums.QualityEnum;

public class NutritiousFoodDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritious_food_details);
        TextView textNameNutritiousFood = findViewById(R.id.name_nutritious);
        TextView textNutrition = findViewById(R.id.nutritions);
        TextView textDescription = findViewById(R.id.description);
        TextView textIngredients = findViewById(R.id.ingredients);
        ImageView imageNutritious = findViewById(R.id.image_nutritious);
        TextView quality = findViewById(R.id.quality);
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
        NutritiousFood nutritiousFood = (NutritiousFood) getIntent().getSerializableExtra("nutritiousFood");
        if (nutritiousFood != null) {
            textNameNutritiousFood.setText(nutritiousFood.getName());
            textDescription.setText(nutritiousFood.getDescription());
            String nutrition = nutritiousFood.getNutrition().replace(",", "\n");
            textNutrition.setText(nutrition);
            String ingredients = nutritiousFood.getIngredient().replace(",", "\n");
            textIngredients.setText(ingredients);
            Glide.with(NutritiousFoodDetailsActivity.this)
                    .load(nutritiousFood.getImage())
                    .error(R.drawable.no_image)
                    .into(imageNutritious);
            if (nutritiousFood.getQuality() == QualityEnum.LOW) {
                String strQuality = "Thấp";
                quality.setText(strQuality);
                quality.setTextColor(ContextCompat.getColor(NutritiousFoodDetailsActivity.this, R.color.red));
            } else if (nutritiousFood.getQuality() == QualityEnum.MEDIUM_LOW) {
                String strQuality = "Trung bình thấp";
                quality.setText(strQuality);
                quality.setTextColor(ContextCompat.getColor(NutritiousFoodDetailsActivity.this, R.color.orange1));
            } else if (nutritiousFood.getQuality() == QualityEnum.MEDIUM) {
                String strQuality = "Trung bình";
                quality.setText(strQuality);
                quality.setTextColor(ContextCompat.getColor(NutritiousFoodDetailsActivity.this, R.color.yellow));
            } else if (nutritiousFood.getQuality() == QualityEnum.MEDIUM_HIGH) {
                String strQuality = "Trung bình cao";
                quality.setText(strQuality);
                quality.setTextColor(ContextCompat.getColor(NutritiousFoodDetailsActivity.this, R.color.blue));
            } else {
                String strQuality = "Cao";
                quality.setText(strQuality);
                quality.setTextColor(ContextCompat.getColor(NutritiousFoodDetailsActivity.this, R.color.green));
            }
        }
    }
}