package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetail {
    @SerializedName("productdetail")
    CollectableItemsListData productDetail;
    @SerializedName("relateditems")
    List<CollectableItemsListData> collectableItemsListData;

    public CollectableItemsListData getProductDetail() {
        return productDetail;
    }

    public List<CollectableItemsListData> getCollectableItemsListData() {
        return collectableItemsListData;
    }
}
