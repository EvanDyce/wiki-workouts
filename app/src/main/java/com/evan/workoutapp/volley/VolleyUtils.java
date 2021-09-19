package com.evan.workoutapp.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class VolleyUtils {
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(Object response);
    }

    /**
     * Logic for making API Request with callbacks
     * @param context context to be used
     * @param url url for the api to query
     * @param listener callback instance. listener.onResponse and onError are callback functions
     */
    public static void makeJsonObjectRequest(Context context, String url, final VolleyUtils.VolleyResponseListener listener) {
        Log.d("REQUEST", "MAKING THE REquest");

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
