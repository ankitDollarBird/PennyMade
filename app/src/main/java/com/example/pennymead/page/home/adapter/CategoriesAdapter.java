package com.example.pennymead.page.home.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.model.CategoriesData;
import com.example.pennymead.page.catalogue.CatalogueListActivity;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ListCategoryViewHolder> {

    ArrayList<CategoriesData> listCategoriesDataList;
    Intent intent;
    ArrayList<String> categoriesName = new ArrayList<>();
    ArrayList<String> categoriesNumber = new ArrayList<>();

    @NonNull
    @Override
    public CategoriesAdapter.ListCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_categories_items,parent,false);
        return new ListCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ListCategoryViewHolder holder, int position) {
            categoriesName.add(listCategoriesDataList.get(position).getName());
            categoriesNumber.add(listCategoriesDataList.get(position).getCategory());
            holder.tvName.setText(listCategoriesDataList.get(position).getName());
            holder.tvTitle.setText(listCategoriesDataList.get(position).getTitle());
            if (listCategoriesDataList.get(position).getImage() != null && listCategoriesDataList.get(position).getImage().size() != 0) {
                Picasso.get().load(listCategoriesDataList.get(position).getImage().get(0)).resize(100, 120).into(holder.ivItems);
            } else {
                holder.ivItems.setImageResource(R.drawable.not_found_img);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(v.getContext(), CatalogueListActivity.class);
                    intent.putExtra("Search Term", (String) null);
                    intent.putExtra("Reference",-1);
                    intent.putExtra("Category Position", position);
                    intent.putStringArrayListExtra("Categories Name", categoriesName);
                    intent.putStringArrayListExtra("Categories Number", categoriesNumber);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    v.getContext().startActivity(intent);

                }
            });
    }

    @Override
    public int getItemCount() {
        if(listCategoriesDataList==null) {
            listCategoriesDataList = new ArrayList<>();
        }
        return listCategoriesDataList.size();
    }

    public void getListCategoriesList(ArrayList<CategoriesData> hmList){
        listCategoriesDataList = hmList;
    }

    public class ListCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItems;
        MaterialTextView tvTitle;
        MaterialTextView tvName;

        public ListCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItems = itemView.findViewById(R.id.img_list_categories);
            tvTitle = itemView.findViewById(R.id.tv_title_list_categories);
            tvName =itemView.findViewById(R.id.tv_name_list_categories);
        }
    }
}
