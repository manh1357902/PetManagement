package com.project.petmanagement.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.adapters.ListOrderDetailAdapter;
import com.project.petmanagement.models.entity.Order;
import com.project.petmanagement.models.enums.OrderStatusEnum;
import com.project.petmanagement.models.enums.PaymentMethodEnum;
import com.project.petmanagement.payloads.responses.OrderResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;
import com.project.petmanagement.utils.FormatNumberUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        RecyclerView productRecyclerView = findViewById(R.id.product_recyclerview);
        TextView textIdOrder = findViewById(R.id.id_order);
        TextView textOrderDate = findViewById(R.id.date_order);
        TextView textAddress = findViewById(R.id.address);
        TextView textPhone = findViewById(R.id.phone_number);
        TextView textFullName = findViewById(R.id.full_name);
        TextView textPaymentMethod = findViewById(R.id.payment_method);
        TextView textTotalPrice = findViewById(R.id.total_price);
        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnCart = findViewById(R.id.btn_cart);
        TextView status = findViewById(R.id.status);
        Button btnCancelOrder = findViewById(R.id.btn_cancel);
        Order order = (Order) getIntent().getSerializableExtra("order");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, ShopActivity.class);
                intent.putExtra("key", "cart");
                startActivity(intent);
                finish();
            }
        });
        if (order != null) {
            textIdOrder.setText(String.valueOf(order.getId()));
            textOrderDate.setText(FormatDateUtils.DateToString2(order.getOrderDate()));
            textFullName.setText(order.getUser().getFullName());
            textPhone.setText(order.getPhone());
            String totalPrice1 = FormatNumberUtils.formatFloat(order.getTotalPrice()) + " VNĐ";
            textTotalPrice.setText(totalPrice1);
            textAddress.setText(order.getShippingAddress());
            if (order.getPaymentMethod() == PaymentMethodEnum.CASH_ON_DELIVERY) {
                textPaymentMethod.setText("Thanh toán khi nhận hàng");
            } else {
                textPaymentMethod.setText("Thẻ tín dụng");
            }
            if (order.getStatus() == OrderStatusEnum.CANCELLED || order.getStatus() == OrderStatusEnum.DELIVERED) {
                btnCancelOrder.setVisibility(View.GONE);
            } else {
                btnCancelOrder.setVisibility(View.VISIBLE);
            }
            if (order.getStatus() == OrderStatusEnum.PENDING) {
                status.setText("Đang xử lý");
                status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.orange1));
            } else if (order.getStatus() == OrderStatusEnum.PROCESSING) {
                status.setText("Chờ giao");
                status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.yellow));
            } else if (order.getStatus() == OrderStatusEnum.SHIPPED) {
                status.setText("Đang giao");
                status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.blue));
            } else if (order.getStatus() == OrderStatusEnum.DELIVERED) {
                status.setText("Đã nhận");
                status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.green));
            } else {
                status.setText("Đã hủy");
                status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.red));
            }
            ListOrderDetailAdapter orderDetailAdapter = new ListOrderDetailAdapter(this, order.getOrderDetails());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            productRecyclerView.setLayoutManager(layoutManager);
            productRecyclerView.setAdapter(orderDetailAdapter);
            btnCancelOrder.setOnClickListener(v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetailsActivity.this);
                alertDialog.setTitle("Hủy đơn hàng")
                        .setMessage("Bạn có chắc chắn muốn hủy đơn hàng này")
                        .setPositiveButton("Có", (dialog, which) -> {
                            APIService.apiService.cancelOrder(order.getId()).enqueue(new Callback<OrderResponse>() {
                                @Override
                                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(OrderDetailsActivity.this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                        status.setText("Đã hủy");
                                        status.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.red));
                                        btnCancelOrder.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderResponse> call, Throwable t) {

                                }
                            });
                        })
                        .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                        .show();
            });
        }

    }

}