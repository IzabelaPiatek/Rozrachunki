package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;

import java.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayGroupActivity extends AppCompatActivity {

    TextView displayGroupName;
    ImageView groupImageView;
    RecyclerView group_recyclerView;
    public static Activity thisActivity;
    private GroupService groupService;
    GroupJson group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_header);

        thisActivity = this;
        groupService = ApiUtils.getGroupService();

        Integer id = getIntent().getExtras().getInt("id");

        //getSupportActionBar().hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayGroupName = findViewById(R.id.group_name);
        groupImageView = findViewById(R.id.group_imageView);

        group_recyclerView = findViewById(R.id.group_RecyclerView);

        Call<GroupJson> call2 = groupService.getGroup(id);
        call2.enqueue(new Callback<GroupJson>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<GroupJson> call2, Response<GroupJson> response) {
                group = response.body();
                if (group != null) {

                    displayGroupName.setText(group.getName());

                    byte[] backToBytes = Base64.getDecoder().decode(group.getImage());

                    Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length));

                    if (image != null) {
                        groupImageView.setImageDrawable(image);
                    }
                }
            }
            @Override
            public void onFailure(Call<GroupJson> call2, Throwable t) {
                Toast.makeText(DisplayGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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