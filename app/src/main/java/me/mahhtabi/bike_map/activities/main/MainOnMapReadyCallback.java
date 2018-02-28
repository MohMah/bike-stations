package me.mahhtabi.bike_map.activities.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import me.mahhtabi.bike_map.R;
import me.mahhtabi.bike_map.managers.BikeStationsManager;
import me.mahhtabi.bike_map.models.BikeStation;
import me.mahhtabi.bike_map.utils.Constants;
import me.mahhtabi.bike_map.utils.IntentUtils;

/**
 * Created by MohMah on 2/28/2018.
 */

class MainOnMapReadyCallback implements OnMapReadyCallback {
    private static final String TAG = "MainOnMapReadyCallback";

    private BikeStationsManager bikeStationsManager;
    private Context context;
    private GoogleMap googleMap;
    private Marker searchMarker;

    public MainOnMapReadyCallback(Context context, BikeStationsManager bikeStationsManager) {
        this.bikeStationsManager = bikeStationsManager;
        this.context = context;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        //style map
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        final LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (BikeStation bikeStation : bikeStationsManager.getBikeStations()) {
            LatLng latLng = new LatLng(bikeStation.getLatitude(), bikeStation.getLongitude());
            Circle circle = googleMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(bikeStation.getCircleRadius())
                    .strokeWidth(Constants.CIRCLE_STROKE_WIDTH)
                    .strokeColor(Color.WHITE)
                    .fillColor(Color.parseColor("#0099ff"))
                    .clickable(true));
            circle.setTag(bikeStation);
            boundsBuilder.include(latLng);
        }

        googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                BikeStation bikeStation = (BikeStation) circle.getTag();
                if (bikeStation == null)
                    return;
                IntentUtils.openMaps(context, bikeStation.getLatitude(), bikeStation.getLongitude(), bikeStation.getFeaturename());
            }
        });

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0));
                googleMap.animateCamera(CameraUpdateFactory.zoomBy(-0.5f));
            }
        });
    }

    void focusOnStation(BikeStation bikeStation) {
        if (googleMap == null)
            return;
        LatLng latLng = new LatLng(bikeStation.getLatitude(), bikeStation.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomBy(0.5f));
        if (searchMarker != null)
            searchMarker.remove();
        searchMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
    }
}
