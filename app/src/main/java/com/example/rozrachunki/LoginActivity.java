package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static Activity thisActivity;
    UserService userService;
    EditText username, password;
    AwesomeValidation awesomeValidation;
    Button logowanie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        userService = ApiUtils.getUserService();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.loginAR, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        awesomeValidation.addValidation(this, R.id.passwordAR, RegexTemplate.NOT_EMPTY, R.string.invalid_password);

        logowanie = findViewById(R.id.loginbtn);
        logowanie.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String vUsername = username.getText().toString().trim();
                String vPassword = password.getText().toString().trim();

                if(!vUsername.isEmpty() && !vPassword.isEmpty()) {
                    Call<User> call2 = userService.login(username.getText().toString(), password.getText().toString());
                    call2.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call2, Response<User> response) {
                            User respUser = response.body();

                            if (respUser != null) {
                                DataStorage.setUser(respUser);
                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                view.getContext().startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Nieprawidłowe dane logowania", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call2, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    username.setError("Wprowadz nazwe uzytkownika!");
                    password.setError("Wprowadz haslo!");
                }
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


