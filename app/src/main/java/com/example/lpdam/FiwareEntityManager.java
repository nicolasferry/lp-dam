package com.example.lpdam;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.EntitiesApi;
import io.swagger.client.model.CreateEntityRequest;
import io.swagger.client.model.UpdateOrAppendEntityAttributesRequest;

/**
 * This class offers the necessary support for publishing our data to FIWARE.
 */

public class FiwareEntityManager {

    private ApiClient apiClient;

    /**
     * Initialier the manager with the endpoint to communicate with FIWARE
     * @param path - URL of the FIWARE Context Orion Broker.
     */
    public FiwareEntityManager(String path){
        // We configure the endpoint to communicate with FIWARE
        this.apiClient= Configuration.getDefaultApiClient();
        apiClient.setBasePath(path); //"http://192.168.1.28:1026"
    }


    /**
     * To add and initialize new attributes of an entity. Or, if the attribute already exists, to update it.
     * @param id - Identifier of the entity
     * @param type - Type of the entity (in our case a person)
     * @param longitude - This can be changed later :)
     * @param latitude - This can be changed later :)
     */
    public void addOrUpdateAttributes(String id, String type, String longitude, String latitude){
        EntitiesApi ea = new EntitiesApi();
        UpdateOrAppendEntityAttributesRequest r = new UpdateOrAppendEntityAttributesRequest();
        Gson gson = new Gson();
        String s = "{\n" +
                "        \"type\": \"StructuredValue\",\n" +
                "        \"value\": {\n" +
                "             \"type\": \"String\",\n" +
                "             \"coordinates\": \""+ longitude +","+ latitude +"\"\n" +
                "        }\n" +
                "    }";
        Object staff = gson.fromJson(s, Object.class);
        r.setPayload(staff);
        try {
            ea.updateOrAppendEntityAttributesAsync(id, "application/json", r, type, null, new ApiCallback<Void>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    e.printStackTrace();
                    Log.v("LPDAM", "Error"+e.getMessage()+" "+e.getResponseBody()+ " ");
                }

                @Override
                public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                    //Nothing so far
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    //Nothing so far
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    //Nothing so far
                }
            });
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }

    /**
     * Provide a mean to register a new entity (in our case a person) into FIWARE. An entity has at least an id and a type. The couple id+type should be uniq.
     * @param id - the identifier of the entity
     * @param type - the type of the entity. In our case person
     * @throws JSONException
     */
    public void registerEntity(String id, String type) throws JSONException {
        EntitiesApi ea = new EntitiesApi();
        CreateEntityRequest c = new CreateEntityRequest();
        c.setId(id);
        c.setType(type);

        try {
            ea.createEntityAsync("application/json", c, "keyValues", new ApiCallback<Void>() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                    if(statusCode == 422){
                        Log.v("LPDAM", "Already created :)");
                    }else{
                        Log.v("LPDAM", "Error: "+e.getMessage()+" "+e.getResponseBody()+ " "+statusCode);
                    }
                }

                @Override
                public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                    Log.v("LPDAM", "Success: "+statusCode);
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    //Nothing so far
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    //Nothing so far
                }
            });
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
