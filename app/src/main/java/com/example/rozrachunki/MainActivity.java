package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    UserService userService = ApiUtils.getUserService();
    User user;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        user = DataStorage.getUser();

        welcome = findViewById(R.id.welcomeTV);
        welcome.setText("Witaj, " + user.getUsername() + "!");


        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set groups selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_groups:
                        startActivity(new Intent(getApplicationContext(), GroupsActivity.class));
                        overridePendingTransition(0,0);
                        //finish();
                        return true;
                    case R.id.nav_friends:
                        startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                        overridePendingTransition(0,0);
                        //finish();
                        return true;
                    case R.id.nav_home:
                        //finish();
                         return true;
                }
                return false;
            }
        });

        dl = findViewById(R.id.dl);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        t.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch(id)
                {
                    case R.id.myaccount:
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Ustawienia",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        /*finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));*/
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                        Toast.makeText(MainActivity.this, "Wylogowano",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return t.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}