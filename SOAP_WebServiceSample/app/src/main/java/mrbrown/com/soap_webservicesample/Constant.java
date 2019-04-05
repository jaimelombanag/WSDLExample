package mrbrown.com.soap_webservicesample;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * Created by ufours18 on 4/24/2017.
 */

public class Constant {

    public static final String Webservice_url2 = "http://www.i2isoftwares.com/SSKSService/sskservices.asmx";
    public static final String Webservice_url = "http://ec2-18-223-82-5.us-east-2.compute.amazonaws.com:8180/SistemaAsignacionServicios/WSFuncionesSistemaAsignacion/WSFuncionesSistemaAsignacion";

    public static final String COUNTRY="Country";
    public static final String TAG_COUNTRYNAME= "CountryName";
    public static final String TAG_COUNTRYID= "CountryId";


    /*Create_Constant_Methods*/
    public static void alertbox(MainActivity _context, String message, String title) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(_context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /*check_for_Internet Connection*/
    public static boolean isConnectingToInternet(MainActivity _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null) {
            return false;
        } else {
            return true;
        }
    }
}
