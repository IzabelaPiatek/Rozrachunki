package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.classes.PageAdapter;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayGroupActivity extends AppCompatActivity {


    public static Activity thisActivity;
    GroupService groupService;
    GroupJson group;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1,tab2;
    public PagerAdapter pagerAdapter;
    TextView displayGroupName;
    ImageView groupImageView;
    RecyclerView group_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_group);

        thisActivity = this;
        groupService = ApiUtils.getGroupService();

        Integer id = getIntent().getExtras().getInt("id");

        //getSupportActionBar().hide();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayGroupName = findViewById(R.id.group_name);

        tabLayout = findViewById(R.id.table_layout);
        tab1 = findViewById(R.id.members_item);
        tab2 = findViewById(R.id.payments_item);
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                } else if(tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                    }
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Call<GroupJson> call2 = groupService.getGroup(id);
        call2.enqueue(new Callback<GroupJson>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<GroupJson> call2, Response<GroupJson> response) {
                group = response.body();
                if (group != null) {

                    displayGroupName.setText(group.getName());

                   /* byte[] backToBytes = Base64.getDecoder().decode(group.getImage());

                    Bitmap bm = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length);

                    Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length));

                    if (image != null) {
                        groupImageView.setImageDrawable(image);
                        //groupImageView.setImageBitmap(bm);
                    }*/

                }
            }
            @Override
            public void onFailure(Call<GroupJson> call2, Throwable t) {
                Toast.makeText(DisplayGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        /*
        FloatingActionButton fab = findViewById(R.id.fab1);
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
                //DisplayGroupActivity.thisActivity.finish();
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
    /*public void addFabFunction(View view) {

        Intent intent = new Intent(view.getContext(), PaymentActivity.class);
        view.getContext().startActivity(intent);
    }*/

}