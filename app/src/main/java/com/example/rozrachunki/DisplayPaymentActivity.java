package com.example.rozrachunki;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.BorrowerJson;
import com.example.rozrachunki.classes.PaymentWithOwnerJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.PaymentService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayPaymentActivity extends AppCompatActivity{

    ArrayList<BorrowerJson> borrowers = new ArrayList<>();
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

        Call<ArrayList<BorrowerJson>> call = paymentService.getBorrowers(paymentId);
        call.enqueue(new Callback<ArrayList<BorrowerJson>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ArrayList<BorrowerJson>> call, Response<ArrayList<BorrowerJson>> response) {
                borrowers = response.body();
                if (borrowers != null) {

                    ArrayList<String> breakdownsList = new ArrayList<>();

                    for (BorrowerJson borrower : borrowers) {
                        if (borrower.getPaidByHim() != 0 && !borrower.isSettled()) {
                            breakdownsList.add(borrower.getUsername() + " zapłacił/a " + borrower.getPaidByHim() + " zł i jest dłużny/a " + borrower.getOwe() + " zł");
                        } else if (borrower.getPaidByHim() != 0) {
                            breakdownsList.add(borrower.getUsername() + " zapłacił/a " + borrower.getPaidByHim() + " zł");
                        } else if (borrower.getOwe() != 0 && !borrower.isSettled()) {
                            breakdownsList.add(borrower.getUsername() + " jest dłużny/a " + borrower.getOwe() + " zł");
                        }
                    }

                    //TODO wypełnić listview

                    ListView listView = findViewById(R.id.listview);

                    ArrayAdapter arrayAdapter = new ArrayAdapter(DisplayPaymentActivity.this, android.R.layout.simple_list_item_1, breakdownsList);
                    listView.setAdapter(arrayAdapter);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<BorrowerJson>> call, Throwable t) {
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

