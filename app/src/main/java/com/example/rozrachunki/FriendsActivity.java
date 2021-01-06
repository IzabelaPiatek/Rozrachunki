package com.example.rozrachunki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.Contact;
import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.classes.FriendsAdapter;
import com.example.rozrachunki.classes.RecyclerItemClickListener;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener  {

    public static Activity thisActivity;
    private RecyclerView recyclerView;
    private Button filterBTN, addFriendsBTN;
    private TextView filteredOption;
    private FriendshipService friendshipService;
    private ArrayList<Friend> friends;
    private FriendsAdapter friendsAdapter = null;
    private ArrayList<Friend> friendsList = new ArrayList<>();
    private ArrayList<Contact> contactList = new ArrayList<>();


    //list.stream().filter(p -> p.age >= 30).collect(Collectors.toCollection(() -> new ArrayList<Person>()))


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisActivity = this;
        setContentView(R.layout.activity_friends);
        friendshipService = ApiUtils.getFriendshipService();
        contactList = getContacts();

        recyclerView = findViewById(R.id.listviewF);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //friendsAdapter = new FriendsAdapter(FriendsActivity.this, friendsList);
        //recyclerView.setAdapter(friendsAdapter);

        Call<ArrayList<Friend>> call2 = friendshipService.getUserFriends(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(Call<ArrayList<Friend>> call2, Response<ArrayList<Friend>> response) {
                friends = response.body();
                if (friends != null) {
                    friendsList = new ArrayList<>();

                    for (Friend friend : friends) {
                        friendsList.add(friend);
                        /*if (friendship.isHasAccount()) {
                            friendsList.add(friendship.getUsername());
                        } else if (friendship.getEmail() != null) {
                            friendsList.add(friendship.getEmail());
                        } else if (friendship.getPhoneNumber() != null) {
                            friendsList.add(friendship.getPhoneNumber()); //contactList.forEach( (c) -> { if (c.getPhone().equals(friendship.getPhoneNumber())) return c; } )
                        }*/

                        //friendsAdapter.notifyDataSetChanged();
                    }

                    friendsAdapter = new FriendsAdapter(FriendsActivity.this, friendsList);
                    recyclerView.setAdapter(friendsAdapter);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<Friend>> call2, Throwable t) {
                Toast.makeText(FriendsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                       // FriendsActivity.thisActivity.finish();
                        Intent intent = new Intent(view.getContext(), FriendsBalanceActivity.class);
                        view.getContext().startActivity(intent);

                       // finish();

                    }

                    @Override public void onLongItemClick(View view, int position) { // delete item
                        AlertDialog.Builder adb=new AlertDialog.Builder(FriendsActivity.this);
                        adb.setTitle("Usuń");
                        adb.setMessage("Czy na pewno chcesz usunąć " + friendsList.get(position).getUsername() + " z listy znajomych?");
                        final int positionToRemove = position;
                        adb.setNegativeButton("Anuluj", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Integer friendshipId = 0;
                                    friendshipId = friendsList.get(position).getId();
                                Call<Integer> call2 = friendshipService.delete(friendshipId);
                                call2.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call2, Response<Integer> response) {
                                        Integer resp = response.body();

                                        if (resp != null) {
                                            friendsList.remove(positionToRemove);
                                            friendsAdapter.notifyDataSetChanged();
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
                })
        );
/*
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(FriendsActivity.this);
                adb.setTitle("Usuń");
                adb.setMessage("Czy na pewno chcesz usunąć " + arrayAdapter.getItem(position) + " z listy znajomych?");
                final int positionToRemove = position;
                adb.setNegativeButton("Anuluj", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Integer friendshipId = 0;
                        for (Friendship friend : friends) {

                            if (friend.getUsername() != null && friend.isHasAccount()) {
                                if (friend.getUsername().equals(arrayAdapter.getItem(position))) {
                                    friendshipId = friend.getId();
                                }
                            }

                            if (friend.getPhoneNumber() != null && !friend.isHasAccount()) {
                                if (friend.getPhoneNumber().equals(arrayAdapter.getItem(position))) {
                                    friendshipId = friend.getId();
                                }
                            }
                            if (friend.getEmail() != null && !friend.isHasAccount())
                            {
                                if (friend.getEmail().equals(arrayAdapter.getItem(position))) {
                                    friendshipId = friend.getId();
                                }
                            }
                        }
                        Call<Integer> call2 = friendshipService.delete(friendshipId);
                        call2.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call2, Response<Integer> response) {
                                Integer resp = response.body();

                                if (resp != null) {
                                    friendsList.remove(positionToRemove);
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
                return false;
            }
        });*/

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
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                view.getContext().startActivity(intent);
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
                        finish();
                        return true;
                    case R.id.nav_groups:
                        startActivity(new Intent(getApplicationContext(), GroupsActivity .class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.nav_friends:
                        return true;
                    default:
                        return onNavigationItemSelected(item);
                }
                //return false;
            }
        });
    }

    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> list = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Contact contact = new Contact(name, phoneNumber, photoUri);

            list.add(contact);
        }

        return list;
    }

    public void addFabFunction(View view) {

        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        filteredOption.setText("Wybrana opcja: " + list[position]);
        if (position == 0) {
            friendsList = friends;
            //friendsList.add(new Friend(4, "user", 20, 0, null));
        }
        if (position == 1) {
            friendsList = friends.stream().filter(f -> f.getYouOwe()!= 0).collect(Collectors.toCollection(() -> new ArrayList<Friend>()));
        }
        if (position == 2) {
            friendsList = friends.stream().filter(f -> f.getOwesYou() != 0).collect(Collectors.toCollection(() -> new ArrayList<Friend>()));
            //friendsList.add(new Friend(4, "user", 20, 0, null));
        }

        friendsAdapter = new FriendsAdapter(FriendsActivity.this, friendsList);
        recyclerView.setAdapter(friendsAdapter);
    }

    @Override
    public void onNegativeButtonClicked() {
        filteredOption.setText("Nie wybrano żadnej opcji");

    }
}