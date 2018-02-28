package me.mahhtabi.bike_map.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.SupportMapFragment;

import me.mahhtabi.bike_map.R;
import me.mahhtabi.bike_map.controllers.SearchController;
import me.mahhtabi.bike_map.controllers.Searchable;
import me.mahhtabi.bike_map.managers.BikeStationsManager;
import me.mahhtabi.bike_map.models.BikeStation;
import me.mahhtabi.bike_map.utils.Constants;

import static me.mahhtabi.bike_map.utils.JsonUtils.readFromAssets;

public class MainActivity extends AppCompatActivity implements Searchable {
    private static final String TAG = "MainActivity";

    private MainOnMapReadyCallback mainOnMapReadyCallback;
    private BikeStationsManager bikeStationsManager;
    private FloatingSearchView floatingSearchView;
    private SearchController searchController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        floatingSearchView = findViewById(R.id.floating_search_view);
        bikeStationsManager = new BikeStationsManager(readFromAssets(getAssets(), Constants.JSON_DATA_PATH));
        mainOnMapReadyCallback = new MainOnMapReadyCallback(this, bikeStationsManager);
        searchController = new SearchController(floatingSearchView, this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mainOnMapReadyCallback);
    }

    @Override
    public void onSearchTexChanged(String newQuery) {
        floatingSearchView.swapSuggestions(bikeStationsManager.getSuggestions(newQuery));
    }

    @Override
    public void performSearch(String query) {
        BikeStation bikeStation = bikeStationsManager.getBikeStation(query);
        if (bikeStation != null) {
            mainOnMapReadyCallback.focusOnStation(bikeStation);
            floatingSearchView.clearSearchFocus();
            floatingSearchView.setSearchText(bikeStation.getFeaturename());
        }
    }
}
