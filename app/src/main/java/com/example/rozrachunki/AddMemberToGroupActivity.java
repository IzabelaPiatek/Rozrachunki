package com.example.rozrachunki;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberToGroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addMember;
    ImageView imageView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> friendsList;
    private FriendshipService friendshipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendshipService = ApiUtils.getFriendshipService();

        setContentView(R.layout.activity_add_member_to_group);

        recyclerView = findViewById(R.id.LV_add_member_to_group);

        addMember = findViewById(R.id.add_memberBTN);

        imageView = findViewById(R.id.image_view_Add_member);


        Call<ArrayList<Friend>> call2 = friendshipService.getUserFriends(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(Call<ArrayList<Friend>> call2, Response<ArrayList<Friend>> response) {
                ArrayList<Friend> friends = response.body();
                if (friends != null) {
                    friendsList = new ArrayList<>();

                    for (Friend friend : friends) {
                        friendsList.add(friend.getUsername());
                    }

                    arrayAdapter = new ArrayAdapter(AddMemberToGroupActivity.this, R.layout.group_listview_style, R.id.TextView_group, friendsList);
                    //recyclerView.setAdapter(arrayAdapter);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Friend>> call2, Throwable t) {
                Toast.makeText(AddMemberToGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                view.getContext().startActivity(intent);
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_group, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.nav_edit_group){




            CreateGroupActivity.thisActivity.finish();
            GroupsActivity.thisActivity.finish();
            Intent intent = new Intent(AddMemberToGroupActivity.this, GroupsActivity.class);
            AddMemberToGroupActivity.this.startActivity(intent);
            AddMemberToGroupActivity.this.finish();

            //Toast.makeText(AddMemberToGroupActivity.this, "Edytuj grupę", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}