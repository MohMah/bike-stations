package me.mahhtabi.bike_map.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by MohMah on 2/28/2018.
 */

public class IntentUtils {
    public static void openMaps(Context context, double lat, double lng, String name) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + lat
                            + "," + lng
                            + "?q=" + lat
                            + "," + lng
                            + "(" + name + ")"));
            intent.setComponent(new ComponentName(
                    "com.google.android.apps.maps",
                    "com.google.android.maps.MapsActivity"));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {

            try {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.google.android.apps.maps")));
            } catch (ActivityNotFoundException anfe) {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps")));
            }

            e.printStackTrace();
        }
    }


}
