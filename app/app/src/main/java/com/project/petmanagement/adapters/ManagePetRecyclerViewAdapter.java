package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.pet.PetDetailsActivity;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.utils.FormatDateUtils;

import java.util.List;

public class ManagePetRecyclerViewAdapter extends RecyclerView.Adapter<ManagePetRecyclerViewAdapter.PetViewHolder> {
    private List<Pet> petList;
    private final Context context;

    public ManagePetRecyclerViewAdapter(Context context, List<Pet> petList) {
        this.petList = petList;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Pet> petList) {
        this.petList = petList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        final Pet pet = petList.get(position);
        if (pet.getImage() != null) {
            Glide.with(context)
                    .load(pet.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.imagePet);
        } else {
            holder.imagePet.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_image));
        }
        holder.namePet.setText(pet.getFullName());
        if (pet.getGender() == 1) {
            holder.imageGenderPet.setImageResource(R.drawable.baseline_male_24);
        } else {
            holder.imageGenderPet.setImageResource(R.drawable.baseline_female_24);
        }
        ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.agePet.setText(FormatDateUtils.calculate(pet.getDateOfBirth()));
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetDetailsActivity.class);
                intent.putExtra("idPet", pet.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (petList != null) {
            return petList.size();
        }
        return 0;
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagePet;
        private TextView namePet;
        private ImageView imageGenderPet;
        private TextView agePet;
        private RelativeLayout relativeLayout;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePet = itemView.findViewById(R.id.image_pet);
            namePet = itemView.findViewById(R.id.name_pet);
            imageGenderPet = itemView.findViewById(R.id.image_gender);
            agePet = itemView.findViewById(R.id.age);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
