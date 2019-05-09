package com.safeway.exploreabbrivations.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.safeway.exploreabbrivations.databinding.ActivityMainBinding;
import com.safeway.exploreabbrivations.models.Item;
import com.safeway.exploreabbrivations.models.Results;
import com.safeway.exploreabbrivations.viewmodels.AbbrevationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> abbreviationResults;
    private AbbrevationAdapter adapter;

    private AbbrevationViewModel viewModel;

    String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(AbbrevationViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setSupportActionBar(binding.toolbar);

        abbreviationResults = new ArrayList<Item>();
        adapter = new AbbrevationAdapter(abbreviationResults);

        // Attach the adapter to the recyclerview to populate items
        binding.content.rvFullForm.setAdapter(adapter);
        // Set layout manager to position the items
        binding.content.rvFullForm.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        viewModel.getItemList().observe(MainActivity.this, itemList -> {
            abbreviationResults.clear();
            abbreviationResults.addAll(itemList);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setupSearchView(menu);
        return true;
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
                viewModel.fetchResults(mQuery);
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
