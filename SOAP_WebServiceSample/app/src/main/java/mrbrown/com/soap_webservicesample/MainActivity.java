package mrbrown.com.soap_webservicesample;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private Spinner spinCtry;
    ArrayList<String> locationIds = new ArrayList<>();
    List<SpinnerHelper> arrayCountryspinner;

    String Checkcommonspinner=Constant.COUNTRY, returnCountry="Error", countryId, countryName;



    String SOAP_ACTION = "";
    String METHOD_NAME = "cargarZonasPlacas";
    String NAMESPACE = "http://ws.fastcar.rwtec.com/";
    String URL = "http://ec2-18-223-82-5.us-east-2.compute.amazonaws.com:8180/SistemaAsignacionServicios/WSFuncionesSistemaAsignacion/WSFuncionesSistemaAsignacion";
    SoapPrimitive resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinCtry = (Spinner) findViewById(R.id.idSpinner);

        /*getLocationFunction*/
        getLocation();





        //WebService tarea = new WebService();
        //tarea.execute();

    }



    private class WebService extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ConsubeWeb();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("JAIME", "------La respuesta:  "  +  resultString.toString());
        }


    }


    private void ConsubeWeb(){
        try{
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("json", "{\"tipoProyecto\":\"7\"}");
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call("", soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i("JAIME",  "-----------    "   + (SoapPrimitive) soapEnvelope.getResponse());


        }catch (Exception e){
            e.printStackTrace();
        }

    }



    /*this_check_internet_connection&call_async_*/
    private void getLocation() {
        if (Constant.isConnectingToInternet(MainActivity.this)) {
            new getCountry(MainActivity.this)
                    .execute((Void[]) null);
        } else {
            Constant.alertbox(MainActivity.this,
                    getString(R.string.str_networkmessage),
                    getString(R.string.str_networktitlemessage));
        }
    }


    private class getCountry extends AsyncTask<Void, Void, String>{
        private ProgressDialog dialog;
        private Context context;

        public getCountry(MainActivity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Webservice webservice = new Webservice(context);

                if(Checkcommonspinner.equalsIgnoreCase(Constant.COUNTRY)) {
                    Log.i("JAIME", "-------Entra aca-----------");
                    returnCountry = webservice.getCountry();
                }
                Log.e("Data", returnCountry);
            } catch (Exception e) {
            }

            return returnCountry;
        }

        @Override
        protected void onPostExecute(String message) {
            dialog.dismiss();
            if (!message.equals("error")) {
                locationIds.clear();
                ApiParsing parsing =new ApiParsing(MainActivity.this);

                if(Checkcommonspinner.equalsIgnoreCase(Constant.COUNTRY)) {
                    //getting the values by parsing the response
                    arrayCountryspinner = parsing.getCountry(message);

                    for(int i=0;i<arrayCountryspinner.size();i++){
                        locationIds.add(arrayCountryspinner.get(i).getId());

                    }
                    //method to set the values in spinner
                    setSpinner();

                }else  {
                }
            } else {
                Constant.alertbox(MainActivity.this,
                        getString(R.string.error),
                        getString(R.string.error));
            }
        }
    }

    private void setSpinner() {

        ArrayAdapter<SpinnerHelper> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrayCountryspinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinCtry.setAdapter(spinnerAdapter);

        /*Spinner_onItemClick*/
        spinCtry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                SpinnerHelper locationid = (SpinnerHelper) adapterView.getSelectedItem();
                countryId = locationid.getId();

                countryName=locationid.getName();
                Checkcommonspinner=Constant.COUNTRY;
//                    getLocation();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
