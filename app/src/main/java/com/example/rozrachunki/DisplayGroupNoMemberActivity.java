package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayGroupNoMemberActivity extends AppCompatActivity {

    TextView displayGroupName;
    ImageView groupImageView;
    RecyclerView group_recyclerView;
    public static Activity thisActivity;
    private GroupService groupService;
    GroupJson group;
    Button addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_group_no_member);

        thisActivity = this;
        groupService = ApiUtils.getGroupService();

        Integer id = getIntent().getExtras().getInt("id");

        //getSupportActionBar().hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayGroupName = findViewById(R.id.group_name);
        groupImageView = findViewById(R.id.group_imageView);

        group_recyclerView = findViewById(R.id.group_RecyclerView);

        addMember = findViewById(R.id.add_memberBTN);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddFriendsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        Call<GroupJson> call2 = groupService.getGroup(id);
        call2.enqueue(new Callback<GroupJson>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<GroupJson> call2, Response<GroupJson> response) {
                group = response.body();
                if (group != null) {

                    displayGroupName.setText(group.getName());

                   /* byte[] backToBytes = Base64.getDecoder().decode(group.getImage());

                    Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length));

                    if (image != null) {
                        //groupImageView.setImageDrawable(image);
                    }*/
                }
            }
            @Override
            public void onFailure(Call<GroupJson> call2, Throwable t) {
                Toast.makeText(DisplayGroupNoMemberActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                view.getContext().startActivity(intent);
            }
        });
*/

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
                Toast.makeText(DisplayGroupNoMemberActivity.this, "Edytuj grupę", Toast.LENGTH_LONG).show();
                DisplayGroupNoMemberActivity.thisActivity.finish();
                Intent intent = new Intent(thisActivity, EditGroupActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.nav_delete_group:
                //startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
                //overridePendingTransition(0,0);
                Toast.makeText(DisplayGroupNoMemberActivity.this, "Grupa została usunięta", Toast.LENGTH_LONG).show();
                DisplayGroupNoMemberActivity.thisActivity.finish();
                Intent intent2 = new Intent(thisActivity, GroupsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return false;
    }
    public void addFabFunction(View view) {

        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
        view.getContext().startActivity(intent);
    }

}