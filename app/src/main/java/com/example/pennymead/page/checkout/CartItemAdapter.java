package com.example.pennymead.page.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.page.product_detail.GetSystemIdOfCollectableItems;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    ArrayList<CollectableItemsListData> collectableItemsListData;
    GetSystemIdOfCollectableItems getSystemIdOfCollectableItems;
    boolean isButtonVisible;

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        holder.tvAuthor.setText(collectableItemsListData.get(position).getAuthor());
        holder.tvTitle.setText(collectableItemsListData.get(position).getTitle());
        holder.tvDescription.setText(collectableItemsListData.get(position).getDescription());
        holder.tvPrice.setText(collectableItemsListData.get(position).getPrice());
        if (collectableItemsListData.get(position).getImage() != null && collectableItemsListData.get(position).getImage().size() != 0) {
            Picasso.get().load(collectableItemsListData.get(position).getImage().get(0)).into(holder.imageViewForCart);
        } else {
            holder.imageViewForCart.setImageResource(R.drawable.not_found_img);
        }
        if(isButtonVisible){
            holder.btnDelete.setVisibility(View.VISIBLE);
        }
        else{
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSystemIdOfCollectableItems.getSysId(collectableItemsListData.get(position).getSysid(), 1);
            }
        });


    }

    public void setCartItemList(ArrayList<CollectableItemsListData> collectableItemsListData, GetSystemIdOfCollectableItems getSystemIdOfCollectableItems,boolean isButtonVisible) {
        this.collectableItemsListData = collectableItemsListData;
        this.getSystemIdOfCollectableItems = getSystemIdOfCollectableItems;
        this.isButtonVisible = isButtonVisible;
    }

    @Override
    public int getItemCount() {
        if (collectableItemsListData == null) {
            return 0;
        }
        return collectableItemsListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewForCart;
        MaterialTextView tvAuthor;
        MaterialTextView tvDescription;
        MaterialTextView tvTitle;
        MaterialTextView tvPrice;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewForCart = itemView.findViewById(R.id.iv_collectable_items);
            tvAuthor = itemView.findViewById(R.id.tv_collectables_items_author);
            tvDescription = itemView.findViewById(R.id.tv_collectables_items_description);
            tvTitle = itemView.findViewById(R.id.tv_collectables_items_title);
            tvPrice = itemView.findViewById(R.id.tv_collectables_items_price);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
