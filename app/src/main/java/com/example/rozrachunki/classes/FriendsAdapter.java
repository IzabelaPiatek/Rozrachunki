package com.example.rozrachunki.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rozrachunki.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
    Context context;
    List<Friend> friendsList;
    ArrayList<Friend> friendsListFull;

    public FriendsAdapter(Context context, List<Friend> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
        this.friendsListFull = new ArrayList<Friend>();
        this.friendsListFull.addAll(friendsList);
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {

        String text = "";
        Friend friend = friendsList.get(position);
        holder.name_friend.setText(friend.getUsername());
        if (friend.getOwesYou() != 0) {
            text = "Jest Ci dłużny/a " + friend.getOwesYou() + "zł\n";
        }
        if (friend.getYouOwe() != 0) {
            text += "Jesteś mu/jej dłużny " + friend.getYouOwe() + "zł.";
        }
        holder.owe_friend.setText(text);

        /*
        if(friend.getPhoto() != null){
            Picasso.get().load(friend.getPhoto()).into(holder.img_friend);
        } else{
            holder.img_friend.setImageResource(R.mipmap.ic_launcher);
        }*/
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView name_friend, owe_friend;
        CircleImageView img_friend;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            name_friend = itemView.findViewById(R.id.name_friend);
            owe_friend = itemView.findViewById(R.id.owe_friend);
            //img_friend = itemView.findViewById(R.id.img_friend);
        }
    }

    public void updateList(List<Friend> newList){
        friendsList = new ArrayList<>();
        friendsList.addAll(newList);
        notifyDataSetChanged();

    }
}
