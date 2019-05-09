package com.safeway.exploreabbrivations.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.safeway.exploreabbrivations.AbbrivationAPI;
import com.safeway.exploreabbrivations.models.Item;
import com.safeway.exploreabbrivations.models.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbbrevationViewModel extends ViewModel {

    public MutableLiveData<Boolean> emptyListVisibility = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Item>> itemList = new MutableLiveData<>();


    public void fetchResults(String q){
        /*Create handle for the RetrofitInstance interface*/
        Call<List<Results>> call;
        call = AbbrivationAPI.getInstance().getFullForm(q);
        Log.v("REQUEST url", String.valueOf(call.request().url()));
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                    if (response.body() == null) {
                        Log.v("RESPONSE_BODY", "RESPONSE_BODY_IS_NULL");
                    }
                    if (response.body().isEmpty()) {
                        emptyListVisibility.postValue(true);
                    } else {
                        ArrayList<Item> items = (ArrayList<Item>) response.body().get(0).getFullNameStrings();
                        if (!items.isEmpty()) {
                            itemList.postValue(items);
                            emptyListVisibility.postValue(false);
                        } else {
                            emptyListVisibility.postValue(true);
                        }
                        }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Log.v("FAILURE", t.getStackTrace().toString());
                Log.v("FAILU", String.valueOf(t.getMessage()));
            }
        });
    }

    public MutableLiveData<Boolean> getEmptyListVisibility() {
        return emptyListVisibility;
    }

    public void setEmptyListVisibility(MutableLiveData<Boolean> emptyListVisibility) {
        this.emptyListVisibility = emptyListVisibility;
    }

    public MutableLiveData<ArrayList<Item>> getItemList() {
        return itemList;
    }

    public void setItemList(MutableLiveData<ArrayList<Item>> itemList) {
        this.itemList = itemList;
    }
}
