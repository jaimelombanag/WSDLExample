package mrbrown.com.soap_webservicesample;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by ufours18 on 4/24/2017.
 */

class Webservice {

    Context context;

    public Webservice(Context con) {
        context = con;
    }

    public String getCountry() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            SoapObject request = new SoapObject(context.getResources()
                    .getString(R.string.namespace), context.getResources()
                    .getString(R.string.getCountry));




            DatosOperativoDTO datosOperativoDTO = new DatosOperativoDTO();
            datosOperativoDTO.setTipoProyecto("7");
            Gson gson = new Gson();
            String json = gson.toJson(datosOperativoDTO);


            Log.i("JAIME",  " Los parametros: " + json);

            request.addProperty("json", json);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);


            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Constant.Webservice_url);


            androidHttpTransport.call(
                    context.getResources().getString(R.string.soap_action)
                           , envelope);



            String result = envelope.getResponse().toString();

            Log.i("JAIME------------  ", result);

            return result;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.i("JAIME E------------  ", e.toString());

            return e.toString();
        }
    }
}
