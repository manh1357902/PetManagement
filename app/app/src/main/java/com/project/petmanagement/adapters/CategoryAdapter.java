package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categories;
    private RecyclerView productRecyclerView;
    private TextView all;
    private TextInputEditText searchInput;
    private int indexRow = -1;

    public CategoryAdapter(Context context, List<Category> categories, RecyclerView productRecyclerView, TextView all, TextInputEditText searchInput) {
        this.context = context;
        this.categories = categories;
        this.productRecyclerView = productRecyclerView;
        this.all = all;
        this.searchInput = searchInput;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if (searchInput.length() != 0) {
                    searchInput.setText("");
                }
                indexRow = holder.getBindingAdapterPosition();
                ListProductAdapter listProductAdapter = new ListProductAdapter(context, category.getProducts());
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                productRecyclerView.setLayoutManager(layoutManager);
                productRecyclerView.setAdapter(listProductAdapter);
                notifyDataSetChanged();
            }
        });
        if (indexRow == position) {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.green));
            all.setTextColor(ContextCompat.getColor(context, R.color.text_default));
        } else {
            holder.name.setTextColor(ContextCompat.getColor(context, R.color.text_default));
        }
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_category);
        }
    }

    public void resetSelection() {
        indexRow = -1;
        notifyDataSetChanged();
    }

    public int getSelectedRows() {
        return indexRow;
    }
}
