package com.project.petmanagement.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.Product;
import com.project.petmanagement.payloads.responses.CartResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatNumberUtils;
import com.project.petmanagement.utils.ImageUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView quantity;
    private ImageView btnSub, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ImageView btnBack = findViewById(R.id.btn_back);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);
        ImageView btnCart = findViewById(R.id.btn_cart);
        ImageView imageProduct = findViewById(R.id.image_product);
        TextView nameProduct = findViewById(R.id.name_product);
        TextView detailProduct = findViewById(R.id.product_detail);
        TextView priceProduct = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        btnSub = findViewById(R.id.btn_sub);
        btnAdd = findViewById(R.id.btn_add);
        Product product = (Product) getIntent().getSerializableExtra("product");
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantity.getText().toString()) > 1) {
                    int quantity1 = Integer.parseInt(quantity.getText().toString()) - 1;
                    quantity.setText(String.valueOf(quantity1));
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity1 = Integer.parseInt(quantity.getText().toString()) + 1;
                quantity.setText(String.valueOf(quantity1));
            }
        });
        if (product != null) {
            imageProduct.setImageBitmap(ImageUtils.decodeBase64(product.getImage()));
            nameProduct.setText(product.getName());
            detailProduct.setText(product.getDescription());
            String price = FormatNumberUtils.formatFloat(product.getPrice()) + "đ";
            priceProduct.setText(price);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, ShopActivity.class);
                intent.putExtra("key", "cart");
                startActivity(intent);
                finish();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity1 = Integer.parseInt(quantity.getText().toString());
                APIService.apiService.addToCart(product.getId(), quantity1).enqueue(new Callback<CartResponse>() {
                    @Override
                    public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductDetailsActivity.this, "Thêm sản phẩm vào giỏ hàng thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}