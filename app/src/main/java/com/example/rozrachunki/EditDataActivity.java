package com.example.rozrachunki;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    Button save;
    UserService userService = ApiUtils.getUserService();
    //User user = DataStorage.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        getSupportActionBar().hide();
        EditText username = findViewById(R.id.username_ET_fill);
        EditText email = findViewById(R.id.email_TV_fill);
        EditText number = findViewById(R.id.number_TV_fill);

        //username.setText(user.getUsername());
        //email.setText(user.getEmail());
        //number.setText(user.getPhoneNumber());

        save = findViewById(R.id.saveBTN);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Call<Integer> call2 = userService.updateUserData(new User(user.getId(), username.getText().toString(), email.getText().toString(), user.getPassword(), number.getText().toString()));
                call2.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call2, Response<Integer> response) {
                        Integer resp = response.body();

                        if (response.isSuccessful()) {
                            if (resp != 0)
                            {
                                Toast.makeText(EditDataActivity.this,"Zmiany zostały zapisane",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                                view.getContext().startActivity(intent);
                            }
                            else {
                                Toast.makeText(EditDataActivity.this,"Wystąpił błąd. Spróbuj ponownie.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call2, Throwable t) {
                        Toast.makeText(EditDataActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });*/

            }
        });

    }
}