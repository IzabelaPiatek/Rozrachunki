package com.example.rozrachunki;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.DataStorage;
import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.classes.InsertAmountsAdapter;
import com.example.rozrachunki.classes.PaymentJson;
import com.example.rozrachunki.classes.UserAmountPOJO;
import com.example.rozrachunki.model.Breakdown;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.GroupService;
import com.example.rozrachunki.services.PaymentService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements SingleChoiceDialogFragment2.SingleChoiceListener, DatePickerDialog.OnDateSetListener{
    ImageView imageView;
    Button chooseImage, chooseOptions, chooseDate;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private TextView filteredOption, displayDate;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    PaymentService paymentService = ApiUtils.getPaymentService();
    GroupService groupService = ApiUtils.getGroupService();
    String date;
    Uri uri;
    int paymentOption = 0;
    ArrayList<User> groupMembers = new ArrayList<>();
    ArrayList<User> selectedUsers = new ArrayList<>();
    ArrayList<GroupJson> groups = new ArrayList<GroupJson>();
    ArrayList<UserAmountPOJO> usersDebtsAmounts = new ArrayList<UserAmountPOJO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView = findViewById(R.id.image_view_choose_image_payment);
        chooseImage = findViewById(R.id.choose_image_paymentBTN);

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

        Call<ArrayList<GroupJson>> call2 = groupService.getUserGroups(DataStorage.getUser().getId());
        call2.enqueue(new Callback<ArrayList<GroupJson>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupJson>> call2, Response<ArrayList<GroupJson>> response) {
                groups = response.body();

                //Toast.makeText(GroupsActivity.this, groups.toString(), Toast.LENGTH_LONG).show();

                if (groups != null) {

                    for (GroupJson group : groups) {

                        //arrayList.add(group.getName());
                    }

                    //arrayAdapter = new ArrayAdapter(GroupsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                    //listView.setAdapter(arrayAdapter);
                }

                /*arrayList.add("Zakopane 2020");
                arrayList.add("Mieszkanie");

                arrayAdapter = new ArrayAdapter(GroupsActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);*/
            }
            @Override
            public void onFailure(Call<ArrayList<GroupJson>> call2, Throwable t) {
                Toast.makeText(PaymentActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        chooseOptions = findViewById(R.id.choose_paymentBTN);
        chooseOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment singleChoice = new SingleChoiceDialogFragment2();
                singleChoice.setCancelable(false);
                singleChoice.show(getSupportFragmentManager(), "Single choice dialog");
                //
            }
        });

        filteredOption = findViewById(R.id.payment_TV_fill);


        displayDate = findViewById(R.id.date_TV_fill);

        /*calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        displayDate.setText(currentDate);
*/
        chooseDate = findViewById(R.id.choose_dataBTN);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(PaymentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        displayDate.setText(mDay + "." + (mMonth + 1) + "." + mYear);
                        //date = Date.from(new GregorianCalendar(mYear, mMonth, mDay).toZonedDateTime().toInstant());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        date = format.format( new Date(mYear, mMonth, mDay) );
                        //date = new Date(mYear, mMonth, mDay);
                    }
                }, day, month, year);
                datePickerDialog.show();*/

                showDatePickerDialog();

            }
        });

    }
    private void showDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
        String date = year + "/" + (month + 1)+ "/" + day ;

        displayDate.setText(date);

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //this.date = format.format( new Calendar.Builder().setDate(year, month, day) );

        this.date =  year + "-" + new DecimalFormat("00").format(month + 1)+ "-" + new DecimalFormat("00").format(day);
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

        if( id == R.id.save){

            byte[] inputData = null;
            ArrayList<Breakdown> breakdowns = new ArrayList<>();

            EditText description = findViewById(R.id.opis_ET_fill);
            EditText amount = findViewById(R.id.kwota_ET_fill);
            TextView option = findViewById(R.id.payment_TV_fill);
            EditText note = findViewById(R.id.notatka_ET_fill);


            if (uri != null)
            {
                try {
                    InputStream iStream = getContentResolver().openInputStream(uri);
                    inputData = getBytes(iStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (User gm : selectedUsers) {
                if (paymentOption == 0 || paymentOption == 1) {
                    //po równo
                    breakdowns.add(new Breakdown(null, gm.getId(), Integer.parseInt(amount.getText().toString()) / selectedUsers.size(), null, false));
                }
                if (paymentOption == 2 || paymentOption == 3) {
                    //na kwoty
                    Integer kwota = usersDebtsAmounts.stream().filter(d -> d.getId() == gm.getId()).collect(Collectors.toCollection(() -> new ArrayList<UserAmountPOJO>())).get(0).getAmount();
                    breakdowns.add(new Breakdown(null, gm.getId(), kwota, null, false));
                }
            }


                    //TODO id group na sztywnoooooo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            Call<PaymentJson> call2 = paymentService.add(new PaymentJson(null, 11, DataStorage.getUser().getId(), Integer.parseInt(amount.getText().toString()), description.getText().toString(), date, inputData, note.getText().toString(), false, paymentOption, breakdowns));
            call2.enqueue(new Callback<PaymentJson>() {
                @Override
                public void onResponse(Call<PaymentJson> call2, Response<PaymentJson> response) {
                    PaymentJson resp = response.body();

                    if (resp != null)
                    {
                        Toast.makeText(PaymentActivity.this,"Zapisano pomyślnie", Toast.LENGTH_LONG).show();
                        //LoginActivity.thisActivity.finish();
                        //Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        //view.getContext().startActivity(intent);
                        //finish();
                    }
                    else {
                        Toast.makeText(PaymentActivity.this,"Błąd dodawania płatności", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PaymentJson> call2, Throwable t) {
                    Toast.makeText(PaymentActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }
        return true;
    }

    public void onPositiveButtonClicked(String[] list, int position) {
        filteredOption.setText("" + list[position]);
        String selectedText = list[position];

        if (selectedText.equals("Dzielone po równo między wszystkich"))
        {
            paymentOption = 0;
        } else if (selectedText.equals("Dzielone po równo pomiędzy wybranych użytkowników"))
        {
            paymentOption = 1;
        } else if (selectedText.equals("Dzielone między wszystkich z wprowadzeniem kwot"))
        {
            paymentOption = 2;
        } else if (selectedText.equals("Dzielone między wybranych użytkowników z wprowadzeniem kwot"))
        {
            paymentOption = 3;
        }

        Call<ArrayList<User>> call2 = groupService.getGroupMembers(11);    //id grupy ustawione na sztywno do testow !
        call2.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call2, Response<ArrayList<User>> response) {
                ArrayList<User> resp = response.body();

                if (resp != null)
                {
                    groupMembers = resp;

                    checkPaymentOptionAndSetUsers();
                }
                else {
                    Toast.makeText(PaymentActivity.this,"Błąd pobierania użytkowników", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call2, Throwable t) {
                Toast.makeText(PaymentActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
    public void onNegativeButtonClicked() {
        filteredOption.setText("Nie wybrano żadnej opcji");

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

    public void checkPaymentOptionAndSetUsers() {
        if (paymentOption == 1 || paymentOption == 3) {
            showMultiChoiceDialogWithUsers();
        } else if (paymentOption == 2) {
            showAmountsChoiceDialog(groupMembers);
            selectedUsers = groupMembers;
        } else {
            selectedUsers = groupMembers;
        }
    }

    public void showMultiChoiceDialogWithUsers() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

        String[] membersArray = new String[groupMembers.size()];
        boolean[] checkedMembersArray = new boolean[groupMembers.size()];

        int i = 0;
        for (User user : groupMembers) {
            // String array for alert dialog multi choice items
            membersArray[i] = user.getUsername();
            // Boolean array for initial selected items
            checkedMembersArray[i] = false;
            if (selectedUsers != new ArrayList<User>()) {
                for (User u : selectedUsers) {
                    if ( u.getId() == user.getId()) {
                        checkedMembersArray[i] = true;
                    }
                }
            }
            checkedMembersArray[i] = false;

            i++;
        }

        final List<String> membersList = Arrays.asList(membersArray);
        builder.setTitle("Wybierz użytkoników");
        builder.setMultiChoiceItems(membersArray, checkedMembersArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedMembersArray[which] = isChecked;
                // Get the current focused item
                String currentItem = membersList.get(which);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if acceptBtn selectedUsers = lista wybranych

                for (int i = 0; i<checkedMembersArray.length; i++){
                    boolean checked = checkedMembersArray[i];
                    if (checked) {
                        for (User u : groupMembers) {
                            if (u.getUsername().equals(membersList.get(i))) {
                                selectedUsers.add(u);
                            }
                        }
                    }
                }
                dialog.dismiss();

                if (paymentOption == 3) {
                    showAmountsChoiceDialog(selectedUsers);
                }
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAmountsChoiceDialog(ArrayList<User> usersList) {
        for (User u : usersList) {
            usersDebtsAmounts.add(new UserAmountPOJO(u.getId(), u.getUsername(), 0));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.dialog_insert_users_amounts, null);

        builder.setTitle("Wprowadź kwoty");

        builder.setView(dialogView);

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.recyclerviewDialog);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < usersDebtsAmounts.size(); i++) {
                    View view = rv.getChildAt(i);
                    EditText nameEditText = (EditText) view.findViewById(R.id.amount_EditText);
                    usersDebtsAmounts.get(i).setAmount(Integer.parseInt(nameEditText.getText().toString()));
                }
            }
        });

        usersDebtsAmounts.add(new UserAmountPOJO(1, "ada", 0));

        InsertAmountsAdapter adapter = new InsertAmountsAdapter(PaymentActivity.this, usersDebtsAmounts);
        rv.setAdapter(adapter);

        AlertDialog dialog1 = builder.create();

        dialog1.show();
    }


}

