package com.example.lpdam;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class AsyncSensorDataPush extends AsyncTask<JSONArray, String, Boolean> {

    private AsyncSensorListener listener;
    private String type = "Person";
    private String id;

    public AsyncSensorDataPush(AsyncSensorListener listener, String id){
        this.listener = listener;
        this.id=id;
    }

    @Override
    protected Boolean doInBackground(JSONArray... locations) {
        try {
            for (JSONArray arrayOfLocation: locations) {
                for(int i=0 ; i< arrayOfLocation.length(); i++){
                    JSONObject oneLocation = arrayOfLocation.getJSONObject(i);
                    String longitude = oneLocation.get("longitude").toString();
                    String latitude = oneLocation.get("longitude").toString();
                    FiwareEntityManager manager = new FiwareEntityManager("http://192.168.1.28:1026");
                    manager.registerEntity(id, type);
                    manager.addOrUpdateAttributes(id,type, longitude, latitude);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void onPostExecute(Boolean b){
        if (listener != null)
            listener.onUploaded(b);
    }
}
