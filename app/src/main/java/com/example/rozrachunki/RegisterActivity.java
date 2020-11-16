package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    UserService userService = ApiUtils.getUserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        EditText username = findViewById(R.id.loginAR);
        EditText password = findViewById(R.id.passwordAR);
        EditText email = findViewById(R.id.emailAR);
        EditText phone = findViewById(R.id.numberAR);

        TextView zaloguj = findViewById(R.id.zalogujsieAR);
        zaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        Button zarejestruj = findViewById(R.id.registerBtnAR);
        zarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<User> call2 = userService.register(new User(null, username.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString()));
                call2.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call2, Response<User> response) {
                        //Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                        //view.getContext().startActivity(intent);
                        if (response != null)
                        {
                            Toast.makeText(RegisterActivity.this,"Zarejestrowano",Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(view.getContext(), MainActivity.class);
                            //view.getContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Istnieje już taki użytkownik",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call2, Throwable t) {
                        Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }



}


