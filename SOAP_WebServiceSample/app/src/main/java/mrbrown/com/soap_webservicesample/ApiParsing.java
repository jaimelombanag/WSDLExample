package mrbrown.com.soap_webservicesample;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ufours18 on 4/24/2017.
 */

class ApiParsing {

    Context mContext;

    public ApiParsing(Context context) {
        mContext = context;

    }

    public ArrayList<SpinnerHelper> getCountry(String json) {

        ArrayList<SpinnerHelper> Locationdetails = new ArrayList<>();
        try {
            JSONArray states = new JSONArray(json);
            for (int i = 0; i < states.length(); i++) {
                JSONObject state = states.getJSONObject(i);
                String LocationId = state.optString(Constant.TAG_COUNTRYID);
                String LoccationName = state.optString(Constant.TAG_COUNTRYNAME);
                SpinnerHelper countrydetails = new SpinnerHelper(LocationId, LoccationName);
                Locationdetails.add(countrydetails);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Locationdetails;
    }

}
