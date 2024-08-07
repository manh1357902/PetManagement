package com.project.petmanagement.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.shop.PaymentActivity;
import com.project.petmanagement.activity.shop.ShopActivity;
import com.project.petmanagement.adapters.ListCartItemAdapter;
import com.project.petmanagement.models.entity.Cart;
import com.project.petmanagement.models.entity.CartItem;
import com.project.petmanagement.payloads.responses.CartResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatNumberUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopCartFragment extends Fragment {

    private RecyclerView carItemRecyclerView;
    private ListCartItemAdapter cartItemAdapter;
    private TextView btnPayment;
    private TextView totalPrices;
    private Cart cart;

    public ShopCartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carItemRecyclerView = view.findViewById(R.id.cart_item_recyclerview);
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnPayment = view.findViewById(R.id.btn_payment);
        totalPrices = view.findViewById(R.id.total_price);
        getCart();
        btnBack.setOnClickListener(v -> {
            ShopActivity shopActivity = (ShopActivity) getActivity();
            shopActivity.getHomePage();
        });
        btnPayment.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), PaymentActivity.class);
            startActivity(intent);
        });
    }

    private void getCart() {
        APIService.apiService.getCart().enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse != null) {
                        cart = cartResponse.getData();
                        if (cart != null) {
                            List<CartItem> cartItems = cart.getCartItems();
                            if (cartItems != null) {
                                cartItemAdapter = new ListCartItemAdapter(getContext(), cartItems, totalPrices, btnPayment);
                                carItemRecyclerView.setAdapter(cartItemAdapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                carItemRecyclerView.setLayoutManager(layoutManager);
                            }
                            String totalPrice = FormatNumberUtils.formatFloat(cart.getTotalPrice()) + " VNĐ";
                            totalPrices.setText(totalPrice);
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
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCart();
    }
}