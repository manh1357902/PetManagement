package com.project.petmanagement.activity.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.MyApplication;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.PaymentItemAdapter;
import com.project.petmanagement.models.entity.Cart;
import com.project.petmanagement.models.entity.CartItem;
import com.project.petmanagement.models.entity.User;
import com.project.petmanagement.models.enums.PaymentMethodEnum;
import com.project.petmanagement.payloads.requests.OrderRequest;
import com.project.petmanagement.payloads.responses.CartResponse;
import com.project.petmanagement.payloads.responses.OrderResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.services.StorageService;
import com.project.petmanagement.utils.FormatNumberUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private TextView fullName;
    private TextView phoneNumber;
    private TextView address;
    private RecyclerView listItemRecyclerView;
    private CharSequence items[] = new CharSequence[]{"Thanh toán khi nhận hàng", "Thẻ tín dụng"};
    private TextView totalPrice;
    private StorageService storageService = MyApplication.getStorageService();
    ActivityResultLauncher<Intent> infoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent intent = o.getData();
                if (intent != null) {
                    phoneNumber.setText(intent.getStringExtra("phoneNumber"));
                    address.setText(intent.getStringExtra("address"));
                }
            }
        }
    });

    private void getCardByUser() {
        APIService.apiService.getCart().enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse != null && cartResponse.getData() != null) {
                        Cart cart = cartResponse.getData();
                        if (cart != null) {
                            List<CartItem> cartItems = new ArrayList<>();
                            for (CartItem cartItem : cart.getCartItems()) {
                                if (cartItem.getSelected()) {
                                    cartItems.add(cartItem);
                                }
                            }
                            PaymentItemAdapter paymentItemAdapter = new PaymentItemAdapter(PaymentActivity.this, cartItems);
                            listItemRecyclerView.setAdapter(paymentItemAdapter);
                            listItemRecyclerView.setLayoutManager(new LinearLayoutManager(PaymentActivity.this, RecyclerView.VERTICAL, false));
                            String totalPrices = FormatNumberUtils.formatFloat(cart.getTotalPrice()) + " VND";
                            totalPrice.setText(totalPrices);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnCart = findViewById(R.id.btn_cart);
        fullName = findViewById(R.id.full_name);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        listItemRecyclerView = findViewById(R.id.list_item);
        Button btnConfirmPayment = findViewById(R.id.confirm_payment);
        totalPrice = findViewById(R.id.total_price);
        TextView textChangePaymentMethod = findViewById(R.id.change_payment_method);
        TextView paymentMethod = findViewById(R.id.payment_method);
        TextView textChangeInfo = findViewById(R.id.change_info);
        User user = storageService.getUser("user");
        fullName.setText(user.getFullName());
        phoneNumber.setText(user.getPhoneNumber());
        address.setText(user.getAddress());
        getCardByUser();
        btnBack.setOnClickListener(v -> finish());
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ShopActivity.class);
            intent.putExtra("key", "cart");
            startActivity(intent);
            finish();
        });
        textChangeInfo.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ChangeOrderInfoActivity.class);
            intent.putExtra("fullName", fullName.getText().toString());
            intent.putExtra("phoneNumber", phoneNumber.getText().toString());
            intent.putExtra("address", address.getText().toString());
            infoLauncher.launch(intent);
        });
        textChangePaymentMethod.setOnClickListener(v -> {
            final int[] checkedItem = {-1};
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
            builder.setSingleChoiceItems(items, checkedItem[0], (dialog, which) -> {
                checkedItem[0] = which;
                paymentMethod.setText(items[which]);
                dialog.dismiss();
            });
            builder.setNegativeButton("Thoát", null);
            builder.setTitle("Chọn phương thức thanh toán");
            builder.show();
        });
        btnConfirmPayment.setOnClickListener(v -> {
            PaymentMethodEnum paymentMethodEnum;
            if (paymentMethod.getText().toString().equals(items[0].toString())) {
                paymentMethodEnum = PaymentMethodEnum.CASH_ON_DELIVERY;
            } else {
                paymentMethodEnum = PaymentMethodEnum.CREDIT_CARD;

            }
            OrderRequest orderRequest = new OrderRequest(address.getText().toString(), phoneNumber.getText().toString(), paymentMethodEnum);
            APIService.apiService.createOrder(orderRequest).enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this, OrdersActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {

                }
            });
        });
    }

}