package com.example.rozrachunki;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import androidx.appcompat.app.AlertDialog;
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
    private ArrayList<User> friends;
    private ArrayAdapter arrayAdapter = null;
    private ArrayList<String> arrayList = new ArrayList<>();

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
                friends = response.body();

                if (friends != null) {

                    arrayList = new ArrayList<>();

                    for (User user : friends) {
                        arrayList.add(user.getUsername());
                    }

                    arrayAdapter = new ArrayAdapter(FriendsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                    listview.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call2, Throwable t) {
                Toast.makeText(FriendsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(FriendsActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer idFriend = 0;
                        for (User friend : friends) {
                            if (friend.getUsername().equals(arrayAdapter.getItem(position))) {
                                idFriend = friend.getId();
                            }
                        }
                        Call<Integer> call2 = friendshipService.delete(DataStorage.getUser().getId(), idFriend);
                        call2.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call2, Response<Integer> response) {
                                Integer resp = response.body();

                                if (resp != null) {
                                    arrayList.remove(positionToRemove);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onFailure(Call<Integer> call2, Throwable t) {
                                Toast.makeText(FriendsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }});
                adb.show();
            }
        });

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