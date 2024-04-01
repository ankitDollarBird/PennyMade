package com.example.pennymead.page.catalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennymead.R;
import com.example.pennymead.model.SubCategoryDropdownListData;
import com.example.pennymead.view.CustomDropdownMenu;
import com.example.pennymead.view.GetPosition;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> implements GetPosition {

    ArrayList<String> subCategoriesDropdownList;
    List<SubCategoryDropdownListData> subCatListData;
    Context context;
    ReferenceId referenceId;

    @NonNull
    @Override
    public SubCategoryAdapter.SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubCategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exposed_dropdown_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.SubCategoryViewHolder holder, int position) {
        holder.data.setText(subCatListData.get(position).getName());
        setDropdownList(position);
        holder.data.getDropdownList(subCategoriesDropdownList, this, position);
        holder.data.setDropDownBackgroundDrawable(context.getResources().getDrawable(R.drawable.filters_items_background));
        holder.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.data.onClick(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (subCatListData == null) {
            return 0;
        }
        return subCatListData.size();
    }

    public void setSubCategoriesList(List<SubCategoryDropdownListData> subCatListData, Context context, ReferenceId referenceId) {
        this.referenceId = referenceId;
        this.subCatListData = subCatListData;
        this.context = context;
    }

    public void setDropdownList(int i) {
        subCategoriesDropdownList = new ArrayList<>();
        for (int j = 0; j < subCatListData.get(i).getDropdownLists().size(); j++) {
            subCategoriesDropdownList.add(subCatListData.get(i).getDropdownLists().get(j).getName());
        }
    }

    @Override
    public void getPosition(int positionOfItems, int recyclerViewPosition) {
        referenceId.getReference(Integer.parseInt(subCatListData.get(recyclerViewPosition).getDropdownLists().get(positionOfItems).getReferenceId()));
    }


    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        CustomDropdownMenu data;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.customDropdownMenu);
        }
    }


}
