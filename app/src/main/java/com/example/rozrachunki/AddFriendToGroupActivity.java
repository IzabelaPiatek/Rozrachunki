package com.example.rozrachunki;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rozrachunki.classes.AddFriendsAdapter;
import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.classes.FriendsAdapter;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendToGroupActivity extends AppCompatActivity{

    public static Activity thisActivity;
    private FriendshipService friendshipService;
    private ArrayList<Friend> friends;
    private FriendsAdapter friendsAdapter = null;
    private ArrayList<Friend> friendsList = new ArrayList<>();
;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    //list.stream().filter(p -> p.age >= 30).collect(Collectors.toCollection(() -> new ArrayList<Person>()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisActivity = this;
        setContentView(R.layout.activity_add_friends_to_group);
        friendshipService = ApiUtils.getFriendshipService();

        recyclerView = (RecyclerView) findViewById(R.id.listviewADDF);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(AddFriendToGroupActivity.this);
        recyclerView.setLayoutManager(manager);

        Call<ArrayList<Friend>> call2 = friendshipService.getUserFriends(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(Call<ArrayList<Friend>> call2, Response<ArrayList<Friend>> response) {
                friends = response.body();
                if (friends != null) {
                    friendsList = new ArrayList<>();

                    for (Friend friend : friends) {
                        friendsList.add(friend);
                    }

                    adapter = new AddFriendsAdapter(AddFriendToGroupActivity.this, friendsList);
                    recyclerView.setAdapter(adapter);
                    //recyclerView.setAdapter(friendsAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Friend>> call2, Throwable t) {
                Toast.makeText(AddFriendToGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    /*private List<Model> getListData() {
        mModelList = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            mModelList.add(new Model("TextView " + i));
        }
        return mModelList;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.save){
            Toast.makeText(AddFriendToGroupActivity.this,"Dodano członków do grupy", Toast.LENGTH_LONG).show();

            //Intent intent = new Intent(AddFriendToGroupActivity.this, DisplayGroupActivity.class);
            //AddFriendToGroupActivity.this.startActivity(intent);

        }
       return true;

    }


}