package com.example.rozrachunki;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.model.Group;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayGroupActivity extends AppCompatActivity {

    TextView displayGroupName;
    ImageView groupImageView;
    RecyclerView group_recyclerView;
    public static Activity thisActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_header);

        thisActivity = this;
        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayGroupName = findViewById(R.id.group_name);
        groupImageView = findViewById(R.id.group_imageView);

        group_recyclerView = findViewById(R.id.group_RecyclerView);



//Pobrac dane grup

        //welcome.setText("Witaj, " + user.getUsername() + " !");
        //displayGroupName.setText(group.getName());
        //groupImageView.setImageResource(intent.getIntExtra("image", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_group, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_edit_group:
                //startActivity(new Intent(getApplicationContext(), GroupsActivity.class));
                //overridePendingTransition(0,0);
                Toast.makeText(DisplayGroupActivity.this, "Edytuj grupę", Toast.LENGTH_LONG).show();
                DisplayGroupActivity.thisActivity.finish();
                Intent intent = new Intent(thisActivity, EditGroupActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_delete_group:
                //startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                //overridePendingTransition(0,0);
                Toast.makeText(DisplayGroupActivity.this, "Grupa została usunięta", Toast.LENGTH_LONG).show();
                DisplayGroupActivity.thisActivity.finish();
                Intent intent2 = new Intent(thisActivity, GroupsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return false;
    }
}