package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email, phone;
    UserService userService = ApiUtils.getUserService();
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        username = findViewById(R.id.loginAR);
        password = findViewById(R.id.passwordAR);
        email = findViewById(R.id.emailAR);
        phone = findViewById(R.id.numberAR);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.loginAR, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        awesomeValidation.addValidation(this, R.id.passwordAR, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.emailAR, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.numberAR, "[1-9]{1}[0-9]{8}$", R.string.invalid_phonenumber);


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
                if(awesomeValidation.validate())
                {
                    Call<User> call2 = userService.register(new User(null, username.getText().toString(), email.getText().toString(), password.getText().toString(), phone.getText().toString(), true));
                    call2.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call2, Response<User> response) {
                            User resp = response.body();

                                if (resp != null)
                                {
                                    Toast.makeText(RegisterActivity.this,"Zarejestrowano pomy??lnie",Toast.LENGTH_LONG).show();
                                    LoginActivity.thisActivity.finish();
                                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                                    view.getContext().startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this,"Istnieje ju?? taki u??ytkownik",Toast.LENGTH_LONG).show();
                                }
                        }

                        @Override
                        public void onFailure(Call<User> call2, Throwable t) {
                            Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Niepoprawne dane!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}


