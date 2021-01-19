package com.example.rozrachunki.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rozrachunki.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.FriendViewHolder> {
    Context context;
    List<Friend> friendsList;
    ArrayList<Friend> friendsListFull;

    public AddFriendsAdapter(Context context, List<Friend> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
        this.friendsListFull = new ArrayList<Friend>();
        this.friendsListFull.addAll(friendsList);
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_listview_style_clickable, parent, false);
        return new FriendViewHolder(view);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {

        String text = "";
        Friend friend = friendsList.get(position);
        holder.name_friend.setText(friend.getUsername());
        holder.itemView.setBackgroundColor(friend.isSelected() ? Color.parseColor("#CAFFF0") : Color.WHITE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friend.setSelected(!friend.isSelected());
                holder.itemView.setBackgroundColor(friend.isSelected() ? Color.parseColor("#CAFFF0") : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return friendsList.size();
        return friendsList == null ? 0 : friendsList.size();
    }

    /*public class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView name_friend, owe_friend;
        CircleImageView img_friend;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            name_friend = itemView.findViewById(R.id.name_friend);
            owe_friend = itemView.findViewById(R.id.owe_friend);
            //img_friend = itemView.findViewById(R.id.img_friend);
        }
    }*/
    public class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView name_friend, owe_friend;
        //CircleImageView img_friend;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            name_friend = itemView.findViewById(R.id.friend_name);
            //owe_friend = itemView.findViewById(R.id.friend_owe);
            //img_friend = itemView.findViewById(R.id.img_friend);
        }
    }

    public void updateList(List<Friend> newList){
        friendsList = new ArrayList<>();
        friendsList.addAll(newList);
        notifyDataSetChanged();

    }
}
