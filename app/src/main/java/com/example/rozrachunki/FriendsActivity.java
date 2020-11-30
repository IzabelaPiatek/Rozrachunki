package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener  {

    private ListView listview;
    private Button filterBTN, addFriendsBTN;
    private TextView filteredOption;
    private FriendshipService friendshipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        friendshipService = ApiUtils.getFriendshipService();
        listview = findViewById(R.id.listviewF);

        Call<ArrayList<User>> call2 = friendshipService.getUserFriends(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call2, Response<ArrayList<User>> response) {
                ArrayList<User> resp = response.body();

                if (resp != null) {

                    ArrayList<String> arrayList = new ArrayList<>();

                    for (User user : resp) {
                        arrayList.add(user.getUsername());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(FriendsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                    listview.setAdapter(arrayAdapter);
                } else {
                    //Toast.makeText(FriendsActivity.this, "Nieprawidłowe dane logowania", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call2, Throwable t) {
                Toast.makeText(FriendsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //ArrayList<String> arrayList = new ArrayList<>();
/*
        arrayList.add("Grupa wsparcia 2.0");
        arrayList.add("Anonimowi");
        arrayList.add("Grupa wsparcia 2.0");
        arrayList.add("Anonimowi");
        arrayList.add("Grupa mocnych");
        arrayList.add("Grupa wsparcia 2.0");
        arrayList.add("Anonimowi");
        arrayList.add("Grupa mocnych");
*/
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arrayList);
        //listview.setAdapter(arrayAdapter);

        filterBTN = findViewById(R.id.filterBTN);
        filterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoice = new SingleChoiceDialogFragment();
                singleChoice.setCancelable(false);
                singleChoice.show(getSupportFragmentManager(), "Single choice dialog");
            }
        });

        addFriendsBTN = findViewById(R.id.add_friendsBTN);
        addFriendsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddFriendsActivity.class);
                view.getContext().startActivity(intent);
            }
        });


        filteredOption = findViewById(R.id.setFilterOption);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set groups selected
        bottomNavigationView.setSelectedItemId(R.id.nav_friends);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_groups:
                        startActivity(new Intent(getApplicationContext(), GroupsActivity .class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_friends:
                        return true;
                }
                return false;
            }
        });
    }

    public void addFabFunction(View view) {

        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        filteredOption.setText("Wybrana opcja: " + list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {
        filteredOption.setText("Nie wybrano żadnej opcji");

    }
}