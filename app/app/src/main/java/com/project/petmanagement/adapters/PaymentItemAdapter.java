package com.project.petmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.shop.ProductDetailsActivity;
import com.project.petmanagement.models.entity.CartItem;

import java.util.List;

public class PaymentItemAdapter extends RecyclerView.Adapter<PaymentItemAdapter.PaymentHolder> {
    private Context context;
    private List<CartItem> cartItems;


    public PaymentItemAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }


    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {
        final CartItem cartItem = cartItems.get(position);
        holder.nameItem.setText(cartItem.getProduct().getName());
        holder.quantity.setText("x" + String.valueOf(cartItem.getQuantity()));
        holder.nameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product", cartItem.getProduct());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }

    public static class PaymentHolder extends RecyclerView.ViewHolder {
        private TextView nameItem, quantity;

        public PaymentHolder(@NonNull View itemView) {
            super(itemView);
            nameItem = itemView.findViewById(R.id.name_item);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
