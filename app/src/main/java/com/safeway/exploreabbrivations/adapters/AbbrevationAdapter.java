package com.safeway.exploreabbrivations.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safeway.exploreabbrivations.R;
import com.safeway.exploreabbrivations.databinding.ListItemBinding;
import com.safeway.exploreabbrivations.models.Item;

import java.util.ArrayList;

public class AbbrevationAdapter extends RecyclerView.Adapter<AbbrevationAdapter.CustomViewHolder> {

    private ArrayList<Item> itemList;
    private LayoutInflater layoutInflater;

    public AbbrevationAdapter(ArrayList<Item> itemList) {
        Log.v("AbbrevationAdapter size", String.valueOf(itemList.size()));
        this.itemList = itemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ListItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Log.v("ADAPTER size", String.valueOf(itemList.size()));
        Log.v("ADAPTER pos", String.valueOf(position));

        Log.v("ADAPTER", String.valueOf(itemList.get(position).getFullName()));
        holder.binding.setItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

            private final ListItemBinding binding;

            public CustomViewHolder(final ListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.binding = itemBinding;
            }
    }
}
