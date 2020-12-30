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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rozrachunki.classes.Contact;
import com.example.rozrachunki.classes.ContactAdapter;
import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.RecyclerItemClickListener;
import com.example.rozrachunki.model.Friendship;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberToGroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addMember;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_to_group);

        recyclerView = findViewById(R.id.LV_add_member_to_group);

        addMember = findViewById(R.id.add_memberBTN);

        imageView = findViewById(R.id.image_view_Add_member);

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                view.getContext().startActivity(intent);
            }
        });

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
            Toast.makeText(AddMemberToGroupActivity.this, "Edytuj grupę", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}