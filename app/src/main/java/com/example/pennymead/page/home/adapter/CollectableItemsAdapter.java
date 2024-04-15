package com.example.pennymead.page.home.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.model.CollectableItemsListData;
import com.example.pennymead.page.checkout.CheckOutPageActivity;
import com.example.pennymead.page.product_detail.GetSystemIdOfCollectableItems;
import com.example.pennymead.page.product_detail.ProductDetailActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CollectableItemsAdapter extends RecyclerView.Adapter<CollectableItemsAdapter.CollectablesItemsViewHolder> {

    ArrayList<CollectableItemsListData> collectableItemsList;
    GetSystemIdOfCollectableItems getSystemIdOfCollectableItems;

    List<String> categoriesName;
    List<String> categoriesNumber;
    View layout;

    @NonNull
    @Override
    public CollectableItemsAdapter.CollectablesItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_collectable_items, parent, false);
        return new CollectablesItemsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CollectableItemsAdapter.CollectablesItemsViewHolder holder, int position) {
        holder.tvAuthor.setText(collectableItemsList.get(position).getAuthor());
        holder.tvPrice.setText(collectableItemsList.get(position).getPrice());
        holder.tvTitle.setText(collectableItemsList.get(position).getTitle());
        holder.tvDescription.setText(collectableItemsList.get(position).getDescription());

        if (collectableItemsList.get(position).getImage() != null && collectableItemsList.get(position).getImage().size() != 0) {
            Picasso.get().load(collectableItemsList.get(position).getImage().get(0)).resize(125, 180).into(holder.ivItems);
        } else {
            holder.ivItems.setImageResource(R.drawable.not_found_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoriesName != null && categoriesNumber != null) {
                    Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                    intent.putExtra("System Id", collectableItemsList.get(position).getSysid());
                    intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
                    intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
                    v.getContext().startActivity(intent);
                }
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSystemIdOfCollectableItems.getSysId(collectableItemsList.get(position).getSysid(), 0);

                Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                layout = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_toast_layout, null);
                snackbarLayout.setBackground(null);
                snackbarLayout.setPadding(16, 0, 16, 100);
                layout.findViewById(R.id.click_to_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), CheckOutPageActivity.class);
                        intent.putStringArrayListExtra("Categories Name", (ArrayList<String>) categoriesName);
                        intent.putStringArrayListExtra("Categories Number", (ArrayList<String>) categoriesNumber);
                        v.getContext().startActivity(intent);
                    }
                });
                snackbarLayout.addView(layout, 0);
                snackbar.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (collectableItemsList == null) {
            return 0;
        }
        return collectableItemsList.size();
    }

    public void setCollectableItemsList(ArrayList<CollectableItemsListData> hmList, List<String> categoriesName, List<String> categoriesNumber, GetSystemIdOfCollectableItems getSystemIdOfCollectableItems) {
        collectableItemsList = hmList;
        this.categoriesName = categoriesName;
        this.categoriesNumber = categoriesNumber;
        this.getSystemIdOfCollectableItems = getSystemIdOfCollectableItems;
    }


    public class CollectablesItemsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItems;
        MaterialTextView tvAuthor;
        MaterialTextView tvPrice;
        MaterialTextView tvTitle;
        MaterialTextView tvDescription;
        Button btnAddToCart;

        public CollectablesItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItems = itemView.findViewById(R.id.iv_collectable_items);
            tvTitle = itemView.findViewById(R.id.tv_collectables_items_title);
            tvPrice = itemView.findViewById(R.id.tv_collectables_items_price);
            tvAuthor = itemView.findViewById(R.id.tv_collectables_items_author);
            tvDescription = itemView.findViewById(R.id.tv_collectables_items_description);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);

        }

    }
}
