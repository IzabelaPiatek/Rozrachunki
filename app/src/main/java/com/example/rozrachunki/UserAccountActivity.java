package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;

import androidx.appcompat.app.AppCompatActivity;

public class UserAccountActivity extends AppCompatActivity {

    Button edit;
    UserService userService = ApiUtils.getUserService();
    //User user = DataStorage.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        getSupportActionBar().hide();

        TextView username = findViewById(R.id.username_TV_fill);
        TextView email = findViewById(R.id.email_TV_fill);
        TextView number = findViewById(R.id.number_TV_fill);

        //username.setText(user.getUsername());
        //email.setText(user.getEmail());
        //number.setText(user.getPhoneNumber());

        edit = findViewById(R.id.editDataBTN);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditDataActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}