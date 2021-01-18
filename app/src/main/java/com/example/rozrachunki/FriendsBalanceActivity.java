package com.example.rozrachunki;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.PaymentService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsBalanceActivity extends AppCompatActivity  {

    //public static Activity thisActivity;
    PaymentService paymentService = ApiUtils.getPaymentService();
    Button settle_up;
    TextView owesYouTextView;
    TextView youOweTextView;
    TextView usernameTextView;
    String username;
    int owesYou, youOwe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //thisActivity = this;
        setContentView(R.layout.friend_header);

        owesYouTextView = findViewById(R.id.user_owes_you);
        youOweTextView = findViewById(R.id.you_owe_user);
        usernameTextView = findViewById(R.id.user_number);

        username = getIntent().getExtras().getString("username");
        owesYou = getIntent().getExtras().getInt("owesYou");
        youOwe = getIntent().getExtras().getInt("youOwe");

        usernameTextView.setText(username);

        if (owesYou - youOwe > 0) {
            owesYouTextView.setText(username + " jest Ci dluzny " + owesYou + " zł.");
        } else if (owesYou - youOwe < 0) {
            youOweTextView.setText("Ty jestes dluzna " + username + " " + youOwe + " zł");
        } else {
            owesYouTextView.setText("Jesteście rozliczeni.");
            youOweTextView.setVisibility(View.GONE);
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settle_up = findViewById(R.id.settle_upBTN);
        settle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<Integer> call2 = paymentService.settleUpUser(DataStorage.getUser().getId(), username);
                call2.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call2, Response<Integer> response) {
                        Integer resp = response.body();

                        if (resp >= 0)
                        {
                            owesYouTextView.setText("Jesteście rozliczeni.");
                            youOweTextView.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(FriendsBalanceActivity.this,"Błąd zapisu",Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Integer> call2, Throwable t) {
                        Toast.makeText(FriendsBalanceActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

                //Intent intent = new Intent(view.getContext(), SettleUpActivity.class);
                //view.getContext().startActivity(intent);

            }
        });

        //friendsAdapter = new FriendsAdapter(FriendsActivity.this, friendsList);
        //recyclerView.setAdapter(friendsAdapter);
    }
}