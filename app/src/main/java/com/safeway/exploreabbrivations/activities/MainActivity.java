package com.safeway.exploreabbrivations.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.safeway.exploreabbrivations.AbbrivationAPI;
import com.safeway.exploreabbrivations.R;
import com.safeway.exploreabbrivations.adapters.AbbrevationAdapter;
import com.safeway.exploreabbrivations.models.Item;
import com.safeway.exploreabbrivations.models.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> abbreviationResults;
    private AbbrevationAdapter adapter;
    private RecyclerView rvFullForm;

    String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Lookup the recyclerview in activity layout
        rvFullForm = (RecyclerView) findViewById(R.id.rvFullForm);

        abbreviationResults = new ArrayList<Item>();
        adapter = new AbbrevationAdapter(abbreviationResults);
        // Attach the adapter to the recyclerview to populate items
        rvFullForm.setAdapter(adapter);
        // Set layout manager to position the items
        rvFullForm.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setupSearchView(menu);
        return true;
    }

    public void fetchResults(String q){
        /*Create handle for the RetrofitInstance interface*/
        Call<List<Results>> call;
        call = AbbrivationAPI.getInstance().getFullForm(q);
        Log.v("REQUEST url", String.valueOf(call.request().url()));
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                try {
                    Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");
                    String didItWork = String.valueOf(response.isSuccessful());
                    Log.v("SUCCESS?", didItWork);
                    Log.v("RESPONSE_CODE", String.valueOf(response.code()));
                    if (response.body() == null) {
                        Log.v("RESPONSE_BODY", "RESPONSE_BODY_IS_NULL");
                    }
                    if (response.body().isEmpty()) {
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.no_results), Toast.LENGTH_LONG);
                        abbreviationResults.clear();
                    } else {
                        ArrayList<Item> items = (ArrayList<Item>) response.body().get(0).getFullNameStrings();
                        if (!items.isEmpty())
                            abbreviationResults.addAll(items);

                    }
                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException e) {
                    Toast.makeText(MainActivity.this, "Query failed: " + e.getClass().getSimpleName(), Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Log.v("FAILURE", t.getStackTrace().toString());
                Log.v("FAILU", String.valueOf(t.getMessage()));
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSearchView(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Called when query is submitted (by pressing "Search" button on keyboard)
            // Note: empty search queries detected by the SearchView itself and ignored
            @Override
            public boolean onQueryTextSubmit(String query) {
                abbreviationResults.clear();
                mQuery = query;
                fetchResults(mQuery);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
