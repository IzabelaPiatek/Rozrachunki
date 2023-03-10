package com.example.rozrachunki;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.classes.FriendsAdapter;
import com.example.rozrachunki.model.User;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.FriendshipService;
import com.example.rozrachunki.services.GroupService;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMembers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMembers extends Fragment {

    private FriendshipService friendshipService;
    private GroupService groupService;
    private ArrayList<Friend> friends;
    private FriendsAdapter friendsAdapter = null;
    private ArrayList<Friend> friendsList = new ArrayList<>();
    Button addMember;
    public static Activity thisActivity;
    ArrayList<String> listToDisplay = new ArrayList<>();
    TextView textView;
    //final String[] items = new String[] { "Jarek ", "Angelika ", "Kacper ",  "Kamila ", "Marek " };
    boolean[] checkedItems;
    ArrayList<Integer> userItems = new ArrayList<>();
    RecyclerView recyclerView;
    ListView list;
    ArrayAdapter<String> adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMembers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMembers.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMembers newInstance(String param1, String param2) {
        FragmentMembers fragment = new FragmentMembers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //list = (ListView) getView().findViewById(R.id.listviewFragment1);

        groupService = ApiUtils.getGroupService();

        Call<ArrayList<User>> call2 = groupService.getGroupMembers(DisplayGroupActivity.groupId);
        call2.enqueue(new Callback<ArrayList<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ArrayList<User>> call2, Response<ArrayList<User>> response) {
                ArrayList<User> members = response.body();
                if (members != null) {

                    for (User m : members) {
                        listToDisplay.add(m.getUsername());
                    }

                    if (listToDisplay.size() == 0)
                    {
                        RelativeLayout llMain = getView().findViewById(R.id.relative1);
                        textView = new TextView(getView().getContext());
                        textView.setText("Jeste?? tu sam! Dodaj nowego cz??onka, aby m??c tworzy?? rozliczenia!");
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        textView.setLayoutParams(params);
                        textView.setTextSize(15);
                        textView.setPadding(50,50,50,50);
                        textView.setGravity(Gravity.CENTER);
                        llMain.addView(textView);
                    }
                    //Je??li s?? p??atno??ci to wy??wietl listview
                    else if (listToDisplay.size() > 0)
                    {
                        textView.setVisibility(View.GONE);

                        ListView list = (ListView) getView().findViewById(R.id.listviewFragment1);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
                        list.setAdapter(adapter);
                    }

                }
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call2, Throwable t) {
                Toast.makeText(FragmentPayment.thisActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendshipService = ApiUtils.getFriendshipService();
       // recyclerView = view.findViewById(R.id.listviewFragment1);

        int count = 1;
        //Je??li nie ma cz??onk??w to wy??wietl tego TextView
        if (listToDisplay.size() == 0)
        {
            RelativeLayout llMain = view.findViewById(R.id.relative1);
            textView = new TextView(view.getContext());
            textView.setText("Jeste?? tu sam! Dodaj nowego cz??onka, aby m??c tworzy?? rozliczenia!");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );
            textView.setLayoutParams(params);
            textView.setTextSize(15);
            textView.setPadding(50,50,50,50);
            textView.setGravity(Gravity.CENTER);
            llMain.addView(textView);
        }
        //Je??li s?? cz??onkowie to wy??wietl listview
        else if (listToDisplay.size() > 0)
        {
         // nie wiem czy mo??e zosta?? listview ale jego po prostu ??atwiej mi by??o oprogramowa??
            list = (ListView)view.findViewById(R.id.listviewFragment1);
           adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
            list.setAdapter(adapter);
            /*list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                    adb.setTitle("Usu??");
                    adb.setMessage("Czy na pewno chcesz usun???? " + listToDisplay.get(position) + " z listy grup?");
                    final int positionToRemove = position;
                    adb.setNegativeButton("Anuluj", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            listToDisplay.remove(positionToRemove);
                            adapter.notifyDataSetChanged();

                        };
                    });
                    adb.show();

                    return false;
                }
            });*/
        }

        //checkedItems = new boolean[items.length];




        addMember = view.findViewById(R.id.add_memberBTN);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AddFriendToGroupActivity.class);
                intent.putExtra("id", DisplayGroupActivity.groupId);
                view.getContext().startActivity(intent);

                /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Wybierz cz??onk??w grupy");
                builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!userItems.contains(position)){
                                userItems.add(position);
                            } else{
                                userItems.remove(position);
                            }
                        }

                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for(int i = 0; i < userItems.size(); i++){
                            item = item + items[userItems.get(i)];
                            if(i != userItems.size() -1){
                                item = item + ", ";

                            }
                        }
                        //wyswietlanie na li??cie
                    }
                });
                builder.setNegativeButton("Powr??t", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Odznacz wszystkie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i = 0; i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            userItems.clear();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                */

            }

        });

    }

}