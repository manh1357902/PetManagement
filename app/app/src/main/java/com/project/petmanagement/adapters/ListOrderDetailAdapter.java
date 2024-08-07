package com.project.petmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.shop.ProductDetailsActivity;
import com.project.petmanagement.models.entity.OrderDetail;
import com.project.petmanagement.utils.FormatNumberUtils;
import com.project.petmanagement.utils.ImageUtils;

import java.util.List;

public class ListOrderDetailAdapter extends RecyclerView.Adapter<ListOrderDetailAdapter.ProductDetailViewHolder> {
    private Context context;
    private List<OrderDetail> orderDetails;

    public ListOrderDetailAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ProductDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailViewHolder holder, int position) {
        final OrderDetail orderDetail = orderDetails.get(position);
        holder.nameProduct.setText(orderDetail.getProduct().getName());
        String price = FormatNumberUtils.formatFloat(orderDetail.getPrice()) + " VNĐ";
        holder.priceProduct.setText(price);
        String quantity = "x" + String.valueOf(orderDetail.getQuantity());
        holder.quantity.setText(quantity);
        holder.imageProduct.setImageBitmap(ImageUtils.decodeBase64(orderDetail.getProduct().getImage()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product", orderDetail.getProduct());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderDetails != null) {
            return orderDetails.size();
        }
        return 0;
    }

    public static class ProductDetailViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageProduct;
        private final TextView nameProduct, priceProduct, quantity;
        private final RelativeLayout relativeLayout;

        public ProductDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.image_product);
            nameProduct = itemView.findViewById(R.id.name_product);
            priceProduct = itemView.findViewById(R.id.price_product);
            quantity = itemView.findViewById(R.id.quantity_product);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
