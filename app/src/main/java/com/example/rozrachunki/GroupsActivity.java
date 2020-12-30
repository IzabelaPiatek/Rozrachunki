package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.model.Group;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsActivity extends AppCompatActivity {

    private ListView listView;
    private Button creategroup;
    private GroupService groupService;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Group> groups = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groupService = ApiUtils.getGroupService();

        listView = findViewById(R.id.listview);

        Call<ArrayList<Group>> call2 = groupService.getUserGroups(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<Group>>() {
            @Override
            public void onResponse(Call<ArrayList<Group>> call2, Response<ArrayList<Group>> response) {
                groups = response.body();

                //Toast.makeText(GroupsActivity.this, groups.toString(), Toast.LENGTH_LONG).show();

                if (groups != null) {

                    for (Group group : groups) {
                        arrayList.add(group.getName());
                    }

                    arrayAdapter = new ArrayAdapter(GroupsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);
                }

                /*arrayList.add("Zakopane 2020");
                arrayList.add("Mieszkanie");

                arrayAdapter = new ArrayAdapter(GroupsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);*/
            }
            @Override
            public void onFailure(Call<ArrayList<Group>> call2, Throwable t) {
                Toast.makeText(GroupsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        creategroup = findViewById(R.id.button);
        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));
                startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));

            }
        });


        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set groups selected
        bottomNavigationView.setSelectedItemId(R.id.nav_groups);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_friends:
                        startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_groups:
                        return true;
                }
                return false;
            }
        });
    }
}