package de.lernt.sebastian.soapwsdlclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.SoapFault;
/*
WSDL SOAP Client WSDL wird genutzt von http://predic8.de/soap/blz-webservice.htm

Bietet die Möglichkeit anhand einer BLZ Institut etc auszulesen.
Methode getBank muss aufgerufen werden.
WSDL: http://www.thomas-bayer.com/axis2/services/BLZService?wsdl

ksoap infos http://misha.beshkin.lv/android-wsdl-client-response/

Beispiel in PHP http://phpforum.de/forum/showthread.php?t=257851
*/


public class MainActivity extends ActionBarActivity {

    //Soap Variabeln
    private static final String NAMESPACE = "http://thomas-bayer.com/blz/";
    private static final String URL = "http://www.thomas-bayer.com/axis2/services/BLZService?wsdl";
    private static final String METHOD_NAME = "getBank";
    private static final String SOAP_ACTION = "http://thomas-bayer.com/blz/BLZService/getBankResponse";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateSoapRequest();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CreateSoapRequest()
    {
        SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
        PropertyInfo PropInfo = new PropertyInfo();

        PropInfo.name = "arg0";
        PropInfo.type = PropertyInfo.STRING_CLASS;

        Request.addProperty(PropInfo.toString(),"Sebastian");

        SoapSerializationEnvelope Envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        Envelope.setOutputSoapObject(Request);

        HttpTransportSE HTTPTransport = new HttpTransportSE(URL);

        try {
            HTTPTransport.call(SOAP_ACTION,Envelope);
            SoapObject Result = (SoapObject) Envelope.bodyIn;
        } catch (Exception e) {
            //App wird angehalten wenn es zu fehlern kommt.
            //TODO rausbekommen wie man das verhindern kann und den Fehler behandeln kann.
            Log.i("ERROR",e.getMessage());  //android.os.NetworkOnMainThreadException
        }
    }
}
