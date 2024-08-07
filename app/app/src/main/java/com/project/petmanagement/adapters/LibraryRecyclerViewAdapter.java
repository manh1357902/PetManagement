package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.diseases.DiseaseDetailsActivity;
import com.project.petmanagement.models.entity.Disease;

import java.util.List;

public class LibraryRecyclerViewAdapter extends RecyclerView.Adapter<LibraryRecyclerViewAdapter.LibraryViewholder> {
    private List<Disease> listDiseases;
    private Context context;

    public LibraryRecyclerViewAdapter(Context context, List<Disease> listDiseases) {
        this.context = context;
        this.listDiseases = listDiseases;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Disease> listDiseases) {
        this.listDiseases = listDiseases;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LibraryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library, parent, false);
        return new LibraryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewholder holder, int position) {
        final Disease disease = listDiseases.get(position);
        holder.nameDiseases.setText(disease.getName());
        holder.linearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiseaseDetailsActivity.class);
                intent.putExtra("disease", disease);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listDiseases != null) {
            return listDiseases.size();
        }
        return 0;
    }

    public static class LibraryViewholder extends RecyclerView.ViewHolder {
        private TextView nameDiseases;
        private LinearLayout linearItem;

        public LibraryViewholder(@NonNull View itemView) {
            super(itemView);
            nameDiseases = itemView.findViewById(R.id.name_diseases);
            linearItem = itemView.findViewById(R.id.linear_item);
        }
    }
}