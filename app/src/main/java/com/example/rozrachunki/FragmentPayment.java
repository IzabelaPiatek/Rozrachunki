package com.example.rozrachunki;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FragmentPayment extends Fragment {

    public static Activity thisActivity;
    private PaymentService paymentService;
    ArrayList<String> listToDisplay = new ArrayList<>();
    RecyclerView paymentList;
    ListView listView;
    ArrayList<PaymentJson> payments = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView textView;

    public FragmentPayment() {
    }

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
                payments = response.body();
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
                        textView.setLayoutParams(params);
                        textView.setTextSize(15);
                        textView.setPadding(50,50,50,50);
                        textView.setGravity(Gravity.CENTER);
                        llMain.addView(textView);
                    }
                    else if (listToDisplay.size() > 0)
                    {
                        textView.setVisibility(View.GONE);
                        listView = getView().findViewById(R.id.listviewFragment2);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
                        listView.setAdapter(adapter);
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
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //listView = view.findViewById(R.id.listviewFragment2);

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
        else if (listToDisplay.size() > 0)
        {
            listView = (ListView)view.findViewById(R.id.listviewFragment2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listToDisplay);
            listView.setAdapter(adapter);
        }

        listView = view.findViewById(R.id.listviewFragment2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getView().getContext(), DisplayPaymentActivity.class);
                intent.putExtra("id", payments.get(position).getId());

                startActivity(intent);
            }
        });

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
