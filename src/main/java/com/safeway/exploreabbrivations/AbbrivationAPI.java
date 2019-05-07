package com.safeway.exploreabbrivations;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AbbrivationAPI {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://www.nactem.ac.uk/";

    private static AbbrivationDictionaryService mInstance = null;

    private AbbrivationAPI() {};

    public static AbbrivationDictionaryService getInstance() {
        if (mInstance == null) {
            mInstance = getRetrofitInstance().create(AbbrivationDictionaryService.class);
        }
        return mInstance;
    }

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}