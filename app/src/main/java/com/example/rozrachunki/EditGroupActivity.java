package com.example.rozrachunki;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.model.Group;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rozrachunki.CreateGroupActivity.decodeUri;

public class EditGroupActivity extends AppCompatActivity {


    public static Activity thisActivity;
    private GroupService groupService;
    GroupJson group;
    Button changeImageBTN;
    EditText groupName;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    ImageView changeImage;
    Integer id;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    Uri uri = null;
    Integer radioType;
    byte[] inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thisActivity = this;
        groupService = ApiUtils.getGroupService();

        groupName = findViewById(R.id.groupname_Edit);
        radioGroup = findViewById(R.id.typeRadioGroupE);
        radioButton1 = findViewById(R.id.zakupyRadioE);
        radioButton2 = findViewById(R.id.wypadRadioE);
        radioButton3 = findViewById(R.id.inneRadioE);


        changeImageBTN = findViewById(R.id.changeBTN);
        changeImage = findViewById(R.id.image_view_change_image);

        id = getIntent().getExtras().getInt("id");

        changeImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check runtime permission
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //permission not granted
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup
                        requestPermissions(permissions, PERMISSION_CODE);
                    }else{
                        //permission already granted
                        pickImageFromGallerry();

                    }
                }else{
                    //system os is less than m
                    pickImageFromGallerry();
                }
            }
        });

        Call<GroupJson> call2 = groupService.getGroup(id);
        call2.enqueue(new Callback<GroupJson>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<GroupJson> call2, Response<GroupJson> response) {
                group = response.body();
                if (group != null) {

                    groupName.setText(group.getName());
                    radioType = group.getType();
                    if (radioType == 0)
                    {
                        radioButton1.setChecked(true);
                    } else if (radioType == 1)
                    {
                        radioButton2.setChecked(true);
                    } else if (radioType == 2)
                    {
                        radioButton3.setChecked(true);
                    }

                    //Dorobić wyswietlanie zdjecia z bazy do changeImage
                    inputData = null;
                    if (uri != null)
                    {
                        try {
                            Bitmap bitmap = decodeUri(EditGroupActivity.this, uri, 500);
                            // int width = bitmap.getWidth();
                            // int height = bitmap.getHeight();

                            int size = bitmap.getRowBytes() * bitmap.getHeight();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                            bitmap.copyPixelsToBuffer(byteBuffer);
                            inputData = byteBuffer.array();

                            //InputStream iStream = getContentResolver().openInputStream(uri);
                            //inputData = getBytes(iStream);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Toast.makeText(CreateGroupActivity.this,new String(inputData, StandardCharsets.UTF_8), Toast.LENGTH_LONG).show();
                        //Toast.makeText(CreateGroupActivity.this, Base64.getEncoder().encodeToString(inputData), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<GroupJson> call2, Throwable t) {
                Toast.makeText(EditGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void pickImageFromGallerry() {
        //intent to pick image
        Intent intent = new Intent((Intent.ACTION_PICK));
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallerry();
                }else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            changeImage.setImageURI(data.getData());
            uri = data.getData();
        }
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

            // Powinno działać??
            /*Group savedGroup = new Group(group.getId(), groupName.getText().toString(), radioType, false, inputData );
            Call<Integer> call2 = groupService.updateGroup(savedGroup);
            call2.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call2, Response<Integer> response) {
                    Integer resp = response.body();

                        if (resp > 0)
                        {
                            //DataStorage.setUser(savedUser);
                            Toast.makeText(EditGroupActivity.this,"Zmiany zostały zapisane",Toast.LENGTH_LONG).show();
                            EditGroupActivity.thisActivity.finish();
                            Intent intent = new Intent(thisActivity, DisplayGroupActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            if (resp == -1)
                                Toast.makeText(EditGroupActivity.this,"Istnieje już grupa o takiej nazwie.",Toast.LENGTH_LONG).show();

                            //if (resp == -2)
                            //Toast.makeText(EditDataActivity.this,"Istnieje już  o takim adresie email.",Toast.LENGTH_LONG).show();
                        }

                }
                @Override
                public void onFailure(Call<Integer> call2, Throwable t) {
                    Toast.makeText(EditGroupActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
*/
            Toast.makeText(EditGroupActivity.this, "NIC", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}