package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.nutrition.NutritiousFoodDetailsActivity;
import com.project.petmanagement.models.entity.NutritiousFood;

import java.util.List;

public class NutritionsRecyclerViewAdapter extends RecyclerView.Adapter<NutritionsRecyclerViewAdapter.NutritionsViewHolder> {
    private List<NutritiousFood> nutritiousFoods;
    private Context context;

    @SuppressLint("NotifyDataSetChanged")
    public void setNutritiousFoods(List<NutritiousFood> nutritiousFoods) {
        this.nutritiousFoods = nutritiousFoods;
        notifyDataSetChanged();
    }

    public NutritionsRecyclerViewAdapter(Context context, List<NutritiousFood> nutritiousFoods) {
        this.context = context;
        this.nutritiousFoods = nutritiousFoods;
    }

    @NonNull
    @Override
    public NutritionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutritious_food, parent, false);
        return new NutritionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionsViewHolder holder, int position) {
        final NutritiousFood nutritiousFood = nutritiousFoods.get(position);
        holder.nameNutritions.setText(nutritiousFood.getName());
        holder.description.setText(nutritiousFood.getDescription());
        Glide.with(context)
                .load(nutritiousFood.getImage())
                .error(R.drawable.no_image)
                .into(holder.imageNutritious);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, NutritiousFoodDetailsActivity.class);
            intent.putExtra("nutritiousFood", nutritiousFood);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (nutritiousFoods != null) {
            return nutritiousFoods.size();
        }
        return 0;
    }

    public static class NutritionsViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameNutritions;
        private final TextView description;
        private final ImageView imageNutritious;
        private final LinearLayout layout;

        public NutritionsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameNutritions = itemView.findViewById(R.id.name_nutritions);
            description = itemView.findViewById(R.id.desciption);
            imageNutritious = itemView.findViewById(R.id.image_nutritious);
            layout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
