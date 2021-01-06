package com.example.rozrachunki;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rozrachunki.classes.Contact;
import com.example.rozrachunki.classes.ContactAdapter;
import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.classes.RecyclerItemClickListener;
import com.example.rozrachunki.model.Friendship;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static Activity thisActivity;
    RecyclerView recyclerView;
    List<Contact> contactList;
    ContactAdapter contactAdapter;
    FriendshipService friendshipService = ApiUtils.getFriendshipService();
    AlertDialog alertDialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        thisActivity = this;

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();

        contactAdapter = new ContactAdapter(this, contactList);

        recyclerView.setAdapter(contactAdapter);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if(response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)){
                            getContacts();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(AddFriendsActivity.this, "Persmission should be granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).check();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(AddFriendsActivity.this);
                        dlgAlert.setMessage("Czy chcesz dodać " + contactList.get(position).getName() + " do listy znajomych? Na podany numer wysłana zostanie wiadomość SMS z powiadomieniem." );
                        dlgAlert.setTitle("Rozrachunki");
                        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                                Contact contact = contactList.get(position);

                                //jeżeli dodaję po username to trzeba będzie ustawiać hasAccount na true;
                                Friendship friendship = addFriend(view, null, contact.getPhone(), contact.getName(), false);

                                if (friendship != null)
                                {
                                    sendSMS(contact.getPhone());
                                    Toast.makeText(AddFriendsActivity.this,"Wysłano wiadomość SMS.", Toast.LENGTH_LONG).show();
                                    //alertDialog.dismiss();
                                    finishActivity();
                                }
                            }
                        });
                        dlgAlert.setCancelable(true);
                        AlertDialog alertDialog = dlgAlert.create();
                        alertDialog.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        //searchView = findViewById(R.id.action_search);

        //searchView.setOnQueryTextListener(this);
    }

    private void getContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photoUri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            Contact contact = new Contact(name, phoneNumber, photoUri);

            contactList.add(contact);

            contactAdapter.notifyDataSetChanged();
        }
    }

    private Friendship addFriend(View view, String email, String phoneNumber, String username, boolean hasAccount) {
        final Friendship[] friendship = new Friendship[1];
        //jeżeli dodaję po username to trzeba ustawić hasAccount na true;
        Call<Friendship> call2 = friendshipService.add(DataStorage.getUser().getId(), new User(null, username, email, null, phoneNumber, hasAccount));
        call2.enqueue(new Callback<Friendship>() {
            @Override
            public void onResponse(Call<Friendship> call2, Response<Friendship> response) {
                Friendship resp = response.body();

                if (resp != null)
                {
                    friendship[0] = resp;
                    Toast.makeText(AddFriendsActivity.this,"Dodano znajomego", Toast.LENGTH_LONG).show();

                    AddFriendsActivity.thisActivity.finish();
                    Intent intent = new Intent(view.getContext(), FriendsActivity.class);
                    view.getContext().startActivity(intent);

                    finish();
                }
                else {
                    friendship[0] = null;
                    Toast.makeText(AddFriendsActivity.this,"Użytkownik znajduje się już na twojej liście przyjaciół", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Friendship> call2, Throwable t) {
                Toast.makeText(AddFriendsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return friendship[0];
    }

    private void sendSMS(String phoneNumber) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, "" + DataStorage.getUser().getUsername() + " zaprosił(a) Cię do znajomych w aplikacji Rozrachunki. Aby zaakceptować zaproszenie kliknij w ten link: http://localhost:9090/friends/accept", null, null);
    }

    private void finishActivity() {
        FriendsActivity.thisActivity.finish();
        Intent intent = new Intent(AddFriendsActivity.this, FriendsActivity.class);
        AddFriendsActivity.this.startActivity(intent);
        AddFriendsActivity.this.finish();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        String userInput = text.toLowerCase(Locale.getDefault());
        List<Contact> newList = new ArrayList<>();

        for(Contact contact : contactList){
            if(contact.getName().toLowerCase(Locale.getDefault()).contains(userInput)){
                newList.add(contact);
            }
        }

        contactAdapter.updateList(newList);

        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_friends_search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }
}