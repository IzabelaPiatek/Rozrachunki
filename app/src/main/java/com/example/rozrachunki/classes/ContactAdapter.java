package com.example.rozrachunki.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.rozrachunki.R;
import com.example.rozrachunki.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> /*implements Filterable*/ {
    Context context;
    List<Contact> contactList;
    ArrayList<Contact> contactListFull;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFull = new ArrayList<Contact>();
        this.contactListFull.addAll(contactList);

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Contact contact = contactList.get(position);
        holder.name_contact.setText(contact.getName());
        holder.phone_contact.setText(contact.getPhone());

        if(contact.getPhoto() != null){
            Picasso.get().load(contact.getPhoto()).into(holder.img_contact);
        } else{
            holder.img_contact.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name_contact, phone_contact;
        CircleImageView img_contact;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name_contact = itemView.findViewById(R.id.name_contact);
            phone_contact = itemView.findViewById(R.id.phone_contact);
            img_contact = itemView.findViewById(R.id.img_contact);
        }
    }

    public void updateList(List<Contact> newList){
        contactList = new ArrayList<>();
        contactList.addAll(newList);
        notifyDataSetChanged();

    }

}


