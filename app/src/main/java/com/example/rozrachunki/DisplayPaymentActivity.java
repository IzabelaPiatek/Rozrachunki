package com.example.rozrachunki;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.PaymentWithOwnerJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.PaymentService;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayPaymentActivity extends AppCompatActivity{

    PaymentService paymentService;
    PaymentWithOwnerJson payment;
    TextView nameTV;
    TextView valueTV;
    TextView ownerTV;
    int paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentId = getIntent().getExtras().getInt("id");

        nameTV = findViewById(R.id.name_of_payment);
        valueTV = findViewById(R.id.value_of_payment);
        ownerTV = findViewById(R.id.name_of_owner);

        paymentService = ApiUtils.getPaymentService();

        Call<PaymentWithOwnerJson> call2 = paymentService.get(paymentId);
        call2.enqueue(new Callback<PaymentWithOwnerJson>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<PaymentWithOwnerJson> call2, Response<PaymentWithOwnerJson> response) {
                payment = response.body();
                if (payment != null) {

                    nameTV.setText(payment.getDescription());
                    valueTV.setText("Kwota: " + payment.getAmount());
                    ownerTV.setText("Zapłacone przez " + payment.getOwnerUsername());

                    //TODO wypełnić listview(?)

                }
            }
            @Override
            public void onFailure(Call<PaymentWithOwnerJson> call2, Throwable t) {
                Toast.makeText(FragmentPayment.thisActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.nav_delete_payments){

            Call<Integer> call2 = paymentService.delete(paymentId);
            call2.enqueue(new Callback<Integer>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<Integer> call2, Response<Integer> response) {
                    Integer resp = response.body();
                    if (resp != null) {
                        Toast.makeText(DisplayPaymentActivity.this,"Płatność usunięta", Toast.LENGTH_LONG).show();

                        DisplayGroupActivity.thisActivity.finish();
                        Intent intent = new Intent(DisplayPaymentActivity.this, DisplayGroupActivity.class);
                        intent.putExtra("id", DisplayGroupActivity.groupId);
                        intent.putExtra("name", DisplayGroupActivity.groupName);
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call2, Throwable t) {
                    Toast.makeText(FragmentPayment.thisActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return true;
    }

}

