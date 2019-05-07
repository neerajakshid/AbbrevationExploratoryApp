package com.safeway.exploreabbrivations.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safeway.exploreabbrivations.R;
import com.safeway.exploreabbrivations.models.Item;

import java.util.ArrayList;

public class AbbrevationAdapter extends RecyclerView.Adapter<AbbrevationAdapter.CustomViewHolder> {

    private ArrayList<Item> itemList;

    public AbbrevationAdapter(ArrayList<Item> itemList) {
        Log.v("AbbrevationAdapter size", String.valueOf(itemList.size()));
        this.itemList = itemList;
    }

    @Override
    public AbbrevationAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder myViewHolder = new CustomViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbbrevationAdapter.CustomViewHolder holder, int position) {
        Log.v("ADAPTER size", String.valueOf(itemList.size()));
        Log.v("ADAPTER pos", String.valueOf(position));

        Log.v("ADAPTER", String.valueOf(itemList.get(position).getFullName()));
        holder.item.setText(itemList.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // Static inner class to initialize the views of rows
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public CustomViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.row_item);
        }
    }
}
