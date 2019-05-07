package com.safeway.exploreabbrivations.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Results {
    @SerializedName("lfs")
    @Expose
    public ArrayList<Item> fullNameStrings;

    public ArrayList<Item> getFullNameStrings() {
        return fullNameStrings;
    }

    public void setFullNameStrings(ArrayList<Item> fullNameStrings) {
        this.fullNameStrings = fullNameStrings;
    }
}
