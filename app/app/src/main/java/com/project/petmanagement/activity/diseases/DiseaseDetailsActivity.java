package com.project.petmanagement.activity.diseases;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.Disease;
import com.project.petmanagement.models.entity.Prevention;
import com.project.petmanagement.models.entity.Treatment;

public class DiseaseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        ImageView btnBack = findViewById(R.id.btn_back);
        TextView nameDisease = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView symptoms = findViewById(R.id.symptoms);
        TextView preventions = findViewById(R.id.preventions);
        TextView treatments = findViewById(R.id.treatments);
        Disease disease = (Disease) getIntent().getSerializableExtra("disease");
        if (disease != null) {
            nameDisease.setText(disease.getName());
            description.setText(disease.getDescription());
            symptoms.setText(disease.getSymptoms());
            String textPreventions = "";
            for (Prevention prevention : disease.getPreventions()) {
                textPreventions += "- " + prevention.getName() + ": " + prevention.getDescription() + "\n";
            }
            preventions.setText(textPreventions);
            String textTreatments = "";
            for (Treatment treatment : disease.getTreatments()) {
                textTreatments += "- " + treatment.getName() + ": " + treatment.getDescription() + "\n";
            }
            treatments.setText(textTreatments);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}