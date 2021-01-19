package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rozrachunki.classes.PaymentJson;
import com.example.rozrachunki.remote.ApiUtils;
import com.example.rozrachunki.services.PaymentService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
 * Use the {@link FragmentPayment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FragmentPayment extends Fragment {

    public static Activity thisActivity;
    private PaymentService paymentService;
    ArrayList<String> listToDisplay = new ArrayList<>();
    RecyclerView paymentList;
    //final String[] items = new String[] { "Browarek", "Pizza Per Te", "Zakupy Biedronka",
     //       "Paliwko", "Ciastko w Anabilis", "Obiad w Alcali", "Bilety lotnicze" };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView textView;

    public FragmentPayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPayment newInstance(String param1, String param2) {
        FragmentPayment fragment = new FragmentPayment();
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

        paymentService = ApiUtils.getPaymentService();

        Call<ArrayList<PaymentJson>> call2 = paymentService.getAllForGroup(DisplayGroupActivity.groupId);
        call2.enqueue(new Callback<ArrayList<PaymentJson>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ArrayList<PaymentJson>> call2, Response<ArrayList<PaymentJson>> response) {
                ArrayList<PaymentJson> payments = response.body();
                if (payments != null) {

                    for (PaymentJson p : payments) {
                        listToDisplay.add(p.getDescription());
                    }

                    if (listToDisplay.size() == 0)
                    {
                        RelativeLayout llMain = getView().findViewById(R.id.relative2);
                        textView = new TextView(getView().getContext());
                        textView.setText("Nie dodano żadnej płatności");
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT
                        );
                        //textView.setId("");
                        textView.setLayoutParams(params);
                        textView.setTextSize(15);
                        textView.setPadding(50,50,50,50);
                        textView.setGravity(Gravity.CENTER);
                        llMain.addView(textView);
                    }
                    //Jeśli są płatności to wyświetl listview
                    else if (listToDisplay.size() > 0)
                    {
                        textView.setVisibility(View.GONE);
                        // nie wiem czy może zostać listview ale jego po prostu łatwiej mi było oprogramować
                        ListView list = (ListView) getView().findViewById(R.id.listviewFragment2);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
                        list.setAdapter(adapter);
                    }

                }
            }
            @Override
            public void onFailure(Call<ArrayList<PaymentJson>> call2, Throwable t) {
                Toast.makeText(FragmentPayment.thisActivity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*paymentList = view.findViewById(R.id.listviewFragment2);
        paymentList.setHasFixedSize(true);
        paymentList.setLayoutManager(new LinearLayoutManager(thisActivity));*/


        //Jeśli nie ma płatności to wyświetl tego TextView
        if (listToDisplay.size() == 0)
        {
            RelativeLayout llMain = view.findViewById(R.id.relative2);
            textView = new TextView(view.getContext());
            textView.setText("Nie dodano żadnej płatności");
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
        //Jeśli są płatności to wyświetl listview
        else if (listToDisplay.size() > 0)
        {
            // nie wiem czy może zostać listview ale jego po prostu łatwiej mi było oprogramować
            ListView list = (ListView)view.findViewById(R.id.listviewFragment2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
            list.setAdapter(adapter);
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                intent.putExtra("id", DisplayGroupActivity.groupId);
                intent.putExtra("name", DisplayGroupActivity.groupName);
                view.getContext().startActivity(intent);
            }
        });

    }
}
