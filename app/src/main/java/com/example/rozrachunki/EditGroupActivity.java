package com.example.rozrachunki;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;

public class EditGroupActivity extends AppCompatActivity {


    public static Activity thisActivity;
    private GroupService groupService;
    GroupJson group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thisActivity = this;
        groupService = ApiUtils.getGroupService();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.save){
            Toast.makeText(EditGroupActivity.this, "Zapisz grupę", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}