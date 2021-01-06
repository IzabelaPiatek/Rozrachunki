package com.example.rozrachunki.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rozrachunki.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InsertAmountsAdapter extends RecyclerView.Adapter<InsertAmountsAdapter.InsertAmountsViewHolder> {
    Context context;
    List<UserAmountPOJO> usersDebtAmounts;
    ArrayList<UserAmountPOJO> usersDebtAmountsFull;

    public InsertAmountsAdapter(Context context, List<UserAmountPOJO> usersDebtAmounts) {
        this.context = context;
        this.usersDebtAmounts = usersDebtAmounts;
        this.usersDebtAmountsFull = new ArrayList<UserAmountPOJO>();
        this.usersDebtAmountsFull.addAll(usersDebtAmounts);
    }

    @NonNull
    @Override
    public InsertAmountsAdapter.InsertAmountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_insert_amount, parent, false);
        return new InsertAmountsAdapter.InsertAmountsViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull InsertAmountsViewHolder holder, int position) {

        UserAmountPOJO userDebtAmount = usersDebtAmounts.get(position);
        holder.username_useramount.setText(userDebtAmount.getUsername());
        holder.amount_useramount.setText(userDebtAmount.getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return usersDebtAmounts.size();
    }

    public class InsertAmountsViewHolder extends RecyclerView.ViewHolder {
        TextView username_useramount;
        EditText amount_useramount;
        public InsertAmountsViewHolder(@NonNull View itemView) {
            super(itemView);
            username_useramount = itemView.findViewById(R.id.username_TextView);
            amount_useramount = itemView.findViewById(R.id.amount_EditText);
        }
    }

    public void updateList(List<UserAmountPOJO> newList){
        usersDebtAmounts = new ArrayList<>();
        usersDebtAmounts.addAll(newList);
        notifyDataSetChanged();

    }
}