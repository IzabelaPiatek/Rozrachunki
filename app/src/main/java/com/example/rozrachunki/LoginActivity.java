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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        UserService userService = ApiUtils.getUserService();
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        Button logowanie = findViewById(R.id.loginbtn);
        logowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<User> call2 = userService.login(username.getText().toString(), password.getText().toString());
                call2.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call2, Response<User> response) {
                        if (response != null)
                        {
                            Toast.makeText(LoginActivity.this,"Zalogowano",Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(view.getContext(), MainActivity.class);
                            //view.getContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Nieprawidłowe dane",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call2, Throwable t) {
                        Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                //Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                //view.getContext().startActivity(intent);
            }
        });


        TextView zarejestruj = findViewById(R.id.zarejestrujsie);
        zarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                view.getContext().startActivity(intent);
            }
        });


    }

}


