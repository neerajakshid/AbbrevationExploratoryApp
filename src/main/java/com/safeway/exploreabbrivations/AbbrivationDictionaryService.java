package com.safeway.exploreabbrivations;

import com.safeway.exploreabbrivations.models.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AbbrivationDictionaryService {
        @GET("software/acromine/dictionary.py")
        Call<List<Results>> getFullForm(
                @Query("sf") String shortForm);
}
