package com.steegler.tmdb_java.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.steegler.tmdb_java.R;
import com.steegler.tmdb_java.model.Movie;
import com.steegler.tmdb_java.model.MovieAdapter;
import com.steegler.tmdb_java.utli.RecyclerTouchListener;
import com.steegler.tmdb_java.utli.RetrieveMovieListTask;

import java.util.List;

/**
 * Created by argi on 10/1/17.
 */

public class MainActivity extends AppCompatActivity implements RetrieveMovieListTask.MovieCallBack {

    RecyclerView recyclerView;
    MovieAdapter adapter;
    RetrieveMovieListTask retrieveTask;
    LinearLayoutManager layoutManager;

    boolean isLoading;
    int totalResults;
    int visibleThreshold = 2;
    String queryText = "";
    String route = "discover/movie?";
    String sort = "";
    Spinner routeSpinner;
    Spinner sortSpinner;

    private Menu mMenu;
    private MenuItem searchItem;
    private MenuItem routeItem;
    private MenuItem sortItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        recyclerView = findViewById(R.id.rvMain);
        adapter = MovieAdapter.getInstance();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount < totalResults) {
                    loadItems(route, queryText, (int) Math.floor(totalItemCount / 20) + 1);
                    isLoading = true;
                }
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this, recyclerView,
                        new RecyclerTouchListener.OnTouchActionListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Intent i = new Intent(getApplicationContext(), MovieDetailActivity.class);
                                i.putExtra("movie", adapter.getItemOnPosition(position));
                                startActivity(i);
                            }

                            @Override
                            public void onRightSwipe(View view, int position) {
                            }

                            @Override
                            public void onLeftSwipe(View view, int position) {
                            }
                        }));
        loadItems(route, null, 1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                route = (newText.length() == 0) ? "discover/movie?" :
                        getResources().getStringArray(R.array.route_value)[routeSpinner.getSelectedItemPosition()];
                loadItems(route, newText, 1);
                queryText = newText;
                return false;
            }
        });

        routeItem = menu.findItem(R.id.menuRoutes);
        routeSpinner = (Spinner) routeItem.getActionView();
        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                route = getResources().getStringArray(R.array.route_value)[position];
                loadItems(route, null, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sortItem = menu.findItem(R.id.menuSort);
        sortSpinner = (Spinner) sortItem.getActionView();
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                route = getResources().getStringArray(R.array.route_value)[routeSpinner.getSelectedItemPosition()];
                sort = getResources().getStringArray(R.array.sort_value)[position];
                loadItems(route, null, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        this.mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        searchItem.collapseActionView();
        routeItem.collapseActionView();
        sortItem.collapseActionView();
        item.expandActionView();
        return super.onOptionsItemSelected(item);
    }

    private void loadItems(String route, String query, int page) {
        if (retrieveTask != null)
            retrieveTask.cancel(true);

        retrieveTask = new RetrieveMovieListTask();
        retrieveTask.setCallBack(this);
        retrieveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, route, query, page, sort);
    }

    @Override
    public void updateData(List<Movie> movieList, boolean isNewData, int totalResults) {
        this.isLoading = false;
        this.totalResults = totalResults;
        if (adapter != null)
            adapter.updateData(movieList, isNewData);
    }
}
