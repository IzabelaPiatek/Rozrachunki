package com.example.rozrachunki;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.model.Group;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity {

    public static Activity thisActivity;
    ImageView imageView;
    Button chooseImage;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    Uri uri = null;
    GroupService groupService = ApiUtils.getGroupService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        thisActivity = this;

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.image_view_choose_image);
        chooseImage = findViewById(R.id.choose_imageBTN);

        chooseImage.setOnClickListener(new View.OnClickListener() {
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
            imageView.setImageURI(data.getData());
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

        /*if( id == R.id.){
            CreateGroupActivity.this.finish();
        }*/

        if( id == R.id.save){
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.typeRadioGroup);
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
            String selectedText = (String) radioButton.getText();

            EditText editTextName = findViewById(R.id.username_ET_fill);

            byte[] inputData = null;
            if (uri != null)
            {
                try {
                    Bitmap bitmap = decodeUri(CreateGroupActivity.this, uri, 500);
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

            Integer type = 0;

            if (selectedText.equals("Zakupy"))
            {
                type = 0;
            } else if (selectedText.equals("Wypad na miasto"))
            {
                type = 1;
            } else if (selectedText.equals("Inne"))
            {
                type = 2;
            }

           // int type = radioButton.getText()

            Call<GroupJson> call2 = groupService.add(new Group(null, editTextName.getText().toString(), type, false, inputData), DataStorage.getUser().getId());
            call2.enqueue(new Callback<GroupJson>() {
                @Override
                public void onResponse(Call<GroupJson> call2, Response<GroupJson> response) {
                    GroupJson resp = response.body();

                    if (resp != null)
                    {
                        Toast.makeText(CreateGroupActivity.this,"Zapisano pomyślnie", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(CreateGroupActivity.this, AddMemberToGroupActivity.class);
                        CreateGroupActivity.this.startActivity(intent);
                    }
                    else {
                        //friendship[0] = null;
                        Toast.makeText(CreateGroupActivity.this,"Błąd dodawania grupy", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GroupJson> call2, Throwable t) {
                    Toast.makeText(CreateGroupActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        return true;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }
}