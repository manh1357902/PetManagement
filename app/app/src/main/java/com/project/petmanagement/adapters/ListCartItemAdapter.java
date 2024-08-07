package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.shop.ProductDetailsActivity;
import com.project.petmanagement.models.entity.Cart;
import com.project.petmanagement.models.entity.CartItem;
import com.project.petmanagement.payloads.responses.CartResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatNumberUtils;
import com.project.petmanagement.utils.ImageUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCartItemAdapter extends RecyclerView.Adapter<ListCartItemAdapter.CartItemViewHolder> {
    private List<CartItem> cartItemList;
    private Context context;
    private TextView totalPrice;
    private TextView btnPayment;

    public ListCartItemAdapter(Context context, List<CartItem> cartItemList, TextView totalPrice, TextView btnPayment) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.totalPrice = totalPrice;
        this.btnPayment = btnPayment;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        final CartItem cartItem = cartItemList.get(position);
        holder.imageProduct.setImageBitmap(ImageUtils.decodeBase64(cartItem.getProduct().getImage()));
        holder.nameProduct.setText(cartItem.getProduct().getName());
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        String price1 = FormatNumberUtils.formatFloat(cartItem.getProduct().getPrice()) + "đ";
        holder.price.setText(price1);
        holder.checkBox.setChecked(cartItem.getSelected());
        holder.nameProduct.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("product", cartItem.getProduct());
            context.startActivity(intent);
        });
        holder.btnSub.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantity.getText().toString()) - 1;
            if (quantity > 0) {
                APIService.apiService.updateCart(cartItem.getId(), quantity, holder.checkBox.isChecked()).enqueue(new Callback<CartResponse>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        if (response.isSuccessful()) {
                            CartResponse cartResponse = response.body();
                            if (cartResponse != null) {
                                Cart cart = cartResponse.getData();
                                cartItemList = cart.getCartItems();
                                notifyDataSetChanged();
                                String totalPrice1 = FormatNumberUtils.formatFloat(cart.getTotalPrice()) + " VNĐ";
                                totalPrice.setText(totalPrice1);
                                if (cart.getTotalPrice() == 0) {
                                    btnPayment.setEnabled(false);
                                    btnPayment.setAlpha(0.4f);
                                } else {
                                    btnPayment.setEnabled(true);
                                    btnPayment.setAlpha(1f);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                    }
                });
            } else {
                quantity = Integer.parseInt(holder.quantity.getText().toString());
            }
        });
        holder.btnAdd.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantity.getText().toString()) + 1;
            APIService.apiService.updateCart(cartItem.getId(), quantity, holder.checkBox.isChecked()).enqueue(new Callback<CartResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if (response.isSuccessful()) {
                        CartResponse cartResponse = response.body();
                        if (cartResponse != null) {
                            Cart cart = cartResponse.getData();
                            cartItemList = cart.getCartItems();
                            notifyDataSetChanged();
                            String totalPrice1 = FormatNumberUtils.formatFloat(cart.getTotalPrice()) + " VNĐ";
                            totalPrice.setText(totalPrice1);
                            if (cart.getTotalPrice() == 0) {
                                btnPayment.setEnabled(false);
                                btnPayment.setAlpha(0.4f);
                            } else {
                                btnPayment.setEnabled(true);
                                btnPayment.setAlpha(1f);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                }
            });
        });
        holder.btnDelete.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Thông báo")
                    .setMessage("Bạn chắc chắn muốn xóa nhật kí này")
                    .setPositiveButton("Có", (dialog, which) -> {
                        APIService.apiService.deleteCartItem(cartItem.getId()).enqueue(new Callback<CartResponse>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                                if (response.isSuccessful()) {
                                    CartResponse cartResponse = response.body();
                                    if (cartResponse != null) {
                                        Cart cart = cartResponse.getData();
                                        cartItemList = cart.getCartItems();
                                        notifyDataSetChanged();
                                        String totalPrice1 = FormatNumberUtils.formatFloat(cartResponse.getData().getTotalPrice()) + " VNĐ";
                                        totalPrice.setText(totalPrice1);
                                        if (cart.getTotalPrice() == 0) {
                                            btnPayment.setEnabled(false);
                                            btnPayment.setAlpha(0.4f);
                                        } else {
                                            btnPayment.setEnabled(true);
                                            btnPayment.setAlpha(1f);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CartResponse> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                    .show();
        });
        holder.checkBox.setOnClickListener(v -> {
            APIService.apiService.updateCart(cartItem.getId(), Integer.parseInt(holder.quantity.getText().toString()), holder.checkBox.isChecked()).enqueue(new Callback<CartResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if (response.isSuccessful()) {
                        CartResponse cartResponse = response.body();
                        if (cartResponse != null) {
                            Cart cart = cartResponse.getData();
                            cartItemList = cart.getCartItems();
                            notifyDataSetChanged();
                            String totalPrice1 = FormatNumberUtils.formatFloat(cartResponse.getData().getTotalPrice()) + " VNĐ";
                            totalPrice.setText(totalPrice1);
                            if (cart.getTotalPrice() == 0) {
                                btnPayment.setEnabled(false);
                                btnPayment.setAlpha(0.4f);
                            } else {
                                btnPayment.setEnabled(true);
                                btnPayment.setAlpha(1f);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (cartItemList != null) {
            return cartItemList.size();
        }
        return 0;
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final ImageView imageProduct, btnAdd, btnSub;
        private final TextView nameProduct, price, quantity;
        private final ImageButton btnDelete;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.image_cart_item);
            nameProduct = itemView.findViewById(R.id.name_cart_item);
            price = itemView.findViewById(R.id.price_cart_item);
            btnAdd = itemView.findViewById(R.id.btn_add);
            btnSub = itemView.findViewById(R.id.btn_sub);
            quantity = itemView.findViewById(R.id.quantity);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            checkBox = itemView.findViewById(R.id.select_check_box);
        }
    }
}
