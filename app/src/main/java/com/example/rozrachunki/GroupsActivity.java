package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.GroupJson;
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

    public static Activity thisActivity;
    private ListView listView;
    private Button creategroup;
    private GroupService groupService;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<GroupJson> groups = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groupService = ApiUtils.getGroupService();

        thisActivity = this;

        listView = findViewById(R.id.listview);

        Call<ArrayList<GroupJson>> call2 = groupService.getUserGroups(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<GroupJson>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupJson>> call2, Response<ArrayList<GroupJson>> response) {
                groups = response.body();

                if (groups != null) {

                    for (GroupJson group : groups) {
                        //Toast.makeText(GroupsActivity.this, group.getImage(), Toast.LENGTH_LONG).show();
                        //byte[] bytes = group.getImage().getBytes();
                        //Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        //ImageView image = (ImageView) findViewById(R.id.imageView1);
                        //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

                        //Blob blob = new SerialBlob(bytes);

                        arrayList.add(group.getName());
                    }

                    arrayAdapter = new ArrayAdapter(GroupsActivity.this, R.layout.group_listview_style, R.id.TextView_group, arrayList);
                    listView.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<GroupJson>> call2, Throwable t) {
                Toast.makeText(GroupsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        creategroup = findViewById(R.id.button);
        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(GroupsActivity.this, "I pick: " + arrayAdapter.getItem(position), Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(thisActivity, DisplayGroupNoMemberActivity.class);
                Intent intent = new Intent(thisActivity, DisplayGroupActivity.class);
                intent.putExtra("id", groups.get(position).getId());
                startActivity(intent);
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
                        finish();
                        return true;
                    case R.id.nav_friends:
                        startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.nav_groups:
                        return true;
                }
                return false;
            }
        });

    }
}