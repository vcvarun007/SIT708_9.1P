package com.example.lostfoundmapapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_card, parent, false);
        return new ItemViewHolder(itemView, itemList, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.location.setText(itemList.get(position).getLocation());
        holder.date.setText(itemList.get(position).getDate());
        if (itemList.get(position).getPostType() == 1) {
            holder.postType.setText("Lost");
        } else {
            holder.postType.setText("Found");
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        List<Item> item;
        private Context mContext;
        TextView name, location, date, postType;

        public ItemViewHolder(@NonNull View itemView, List<Item> item, Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            postType = itemView.findViewById(R.id.itemType);
            this.mContext = context;
            this.item = item;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            String postType = item.get(position).getPostType() == 1 ? "Lost" : "Found";
            Intent intent = new Intent(mContext, ItemDetailActivity.class);
            intent.putExtra("id", item.get(position).getId());
            intent.putExtra("name", item.get(position).getName());
            intent.putExtra("post_type", postType);
            intent.putExtra("desc", item.get(position).getDescription());
            intent.putExtra("location", item.get(position).getLocation());
            intent.putExtra("phone", item.get(position).getPhone());
            intent.putExtra("date", item.get(position).getDate());
            mContext.startActivity(intent);
        }
    }
}
