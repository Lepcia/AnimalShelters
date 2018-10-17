package inzynierka.animalshelters.rest;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class Client {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public static void getById(Context context, String url, int idUser, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(context, getAbsoluteUrl(url, idUser), headers, params, responseHandler);
    }

    public static void add(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.post(context, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void update(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.put(context, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.delete(context, getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl)
    {
        return Api.getBaseUrl() + relativeUrl;
    }

    private static String getAbsoluteUrl(String relativeUrl, int id)
    {
        return Api.getBaseUrl() + relativeUrl + '/' +  String.valueOf(id);
    }
}
