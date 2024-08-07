package com.project.petmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.pet.PetDetailsActivity;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.utils.FormatDateUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetHomeAdapter extends RecyclerView.Adapter<PetHomeAdapter.PetHomeViewHolder> {
    private final Context context;
    private final List<Pet> petList;

    public PetHomeAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_home, parent, false);
        return new PetHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHomeViewHolder holder, int position) {
        final Pet pet = petList.get(position);
        Glide.with(context)
                .load(pet.getImage())
                .error(R.drawable.default_pet_no_image)
                .into(holder.image);
        holder.name.setText(pet.getFullName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.age.setText(FormatDateUtils.calculate(pet.getDateOfBirth()));
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
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
        if (!petList.isEmpty()) {
            return petList.size();
        }
        return 0;
    }

    public static class PetHomeViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout item;
        private final CircleImageView image;
        private final TextView name;
        private final TextView age;


        public PetHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            name = itemView.findViewById(R.id.pet_name);
            age = itemView.findViewById(R.id.pet_age);
            item = itemView.findViewById(R.id.pet_home_item);
        }
    }
}
