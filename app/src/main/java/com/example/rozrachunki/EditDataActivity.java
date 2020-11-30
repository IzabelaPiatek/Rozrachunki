package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataActivity extends AppCompatActivity {

    Button save;
    UserService userService = ApiUtils.getUserService();
    User user = DataStorage.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        getSupportActionBar().hide();
        EditText username = findViewById(R.id.username_ET_fill);
        EditText email = findViewById(R.id.email_TV_fill);
        EditText number = findViewById(R.id.number_TV_fill);

        username.setText(user.getUsername());
        email.setText(user.getEmail());
        number.setText(user.getPhoneNumber());

        save = findViewById(R.id.saveBTN);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User savedUser = new User(user.getId(), username.getText().toString(), email.getText().toString(), user.getPassword(), number.getText().toString());
                Call<Integer> call2 = userService.updateUserData(savedUser);
                call2.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call2, Response<Integer> response) {
                        Integer resp = response.body();

                        if (response.isSuccessful()) {
                            if (resp > 0)
                            {
                                DataStorage.setUser(savedUser);
                                Toast.makeText(EditDataActivity.this,"Zmiany zostały zapisane",Toast.LENGTH_LONG).show();
                                UserAccountActivity.thisActivity.finish();
                                Intent intent = new Intent(view.getContext(), UserAccountActivity.class);
                                view.getContext().startActivity(intent);
                                finish();
                            }
                            else {
                                if (resp == -1)
                                    Toast.makeText(EditDataActivity.this,"Istnieje już użytkownik o takiej nazwie.",Toast.LENGTH_LONG).show();

                                if (resp == -2)
                                    Toast.makeText(EditDataActivity.this,"Istnieje już użytkownik o takim adresie email.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Integer> call2, Throwable t) {
                        Toast.makeText(EditDataActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}