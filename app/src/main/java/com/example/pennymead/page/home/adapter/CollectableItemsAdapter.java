package com.example.pennymead.page.home.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.model.home.CollectableItemsListData;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CollectableItemsAdapter extends RecyclerView.Adapter<CollectableItemsAdapter.CollectablesItemsViewHolder> {

    ArrayList<CollectableItemsListData> collectableItemsList;

    @NonNull
    @Override
    public CollectableItemsAdapter.CollectablesItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_collectable_items,parent,false);
        return new CollectablesItemsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CollectableItemsAdapter.CollectablesItemsViewHolder holder, int position) {


        holder.tvAuthor.setText(collectableItemsList.get(position).getAuthor());
        holder.tvPrice.setText("$"+collectableItemsList.get(position).getPrice());
        holder.tvTitle.setText(collectableItemsList.get(position).getTitle());
        holder.tvDescription.setText(collectableItemsList.get(position).getDescription());

        if(collectableItemsList.get(position).getImage()!=null) {
            Picasso.get().load(collectableItemsList.get(position).getImage().get(0)).resize(320, 320).into(holder.ivItems);
        }
        else{
            holder.ivItems.setImageResource(R.drawable.not_found_img);
        }


    }

    @Override
    public int getItemCount() {
        if(collectableItemsList==null) {
            return 0;
        }
        return collectableItemsList.size();
    }
    public void setCollectableItemsList(ArrayList<CollectableItemsListData> hmList){
        collectableItemsList = hmList;
    }
    public class CollectablesItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItems;
        MaterialTextView tvAuthor;
        MaterialTextView tvPrice;
        MaterialTextView tvTitle;
        MaterialTextView tvDescription;
        public CollectablesItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItems = itemView.findViewById(R.id.iv_collectable_items);
            tvTitle = itemView.findViewById(R.id.tv_collectables_items_title);
            tvPrice =itemView.findViewById(R.id.tv_collectables_items_price);
            tvAuthor = itemView.findViewById(R.id.tv_collectables_items_author);
            tvDescription =itemView.findViewById(R.id.tv_collectables_items_description);
        }
    }
}
