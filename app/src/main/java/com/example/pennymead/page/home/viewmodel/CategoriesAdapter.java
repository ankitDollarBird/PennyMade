package com.example.pennymead.page.home.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pennymead.R;
import com.example.pennymead.model.home.CategoriesData;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ListCategoryViewHolder> {

    ArrayList<CategoriesData> listCategoriesDataList;
    @NonNull
    @Override
    public CategoriesAdapter.ListCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_categories_items,parent,false);
        return new ListCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ListCategoryViewHolder holder, int position) {
            holder.tvName.setText(listCategoriesDataList.get(position).getName());
            holder.tvTitle.setText(listCategoriesDataList.get(position).getTitle());
            if(listCategoriesDataList.get(position).getImage()!=null) {
                Picasso.get().load(listCategoriesDataList.get(position).getImage().get(0)).resize(100, 120).into(holder.ivItems);
            }
            else{
                holder.ivItems.setImageResource(R.drawable.not_found_img);
            }
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
