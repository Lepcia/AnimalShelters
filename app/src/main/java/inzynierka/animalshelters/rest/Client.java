package inzynierka.animalshelters.rest;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.UserService;

public class Client {
    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setThreadPool(Executors.newSingleThreadExecutor());
        client.setConnectTimeout(10000);
    }

    public static void get(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(context, getAbsoluteUrl(url), addHeaders(), params, responseHandler);
    }

    public static void getById(Context context, String url, int idUser, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(context, getAbsoluteUrl(url, idUser), addHeaders(), params, responseHandler);
    }

    public static void addNotLogged(Context context, String url, StringEntity entity, Header[] headers, AsyncHttpResponseHandler responseHandler)
    {
        client.post(context, getAbsoluteUrl(url), headers, entity, "application/json", responseHandler);
    }

    public static void add(Context context, String url, StringEntity entity, Header[] headers, AsyncHttpResponseHandler responseHandler)
    {
        client.post(context, getAbsoluteUrl(url), addHeaders(), entity, "application/json", responseHandler);
    }

    public static void add(Context context, String url, StringEntity entity, int idUser, Header[] headers, AsyncHttpResponseHandler responseHandler)
    {
        client.post(context, getAbsoluteUrl(url, idUser), addHeaders(), entity, "application/json", responseHandler);
    }

    public static void update(Context context, String url, StringEntity entity, Header[] headers, AsyncHttpResponseHandler responseHandler)
    {
        client.put(context, getAbsoluteUrl(url), addHeaders(), entity, "aplication/json", responseHandler);
    }

    public static void update(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.put(context, getAbsoluteUrl(url), params, responseHandler);
    }

    public static void update(Context context, String url, StringEntity entity, int id, Header[] headers, AsyncHttpResponseHandler responseHandler)
    {
        client.put(context, getAbsoluteUrl(url, id), addHeaders(), entity, "aplication/json", responseHandler);
    }

    public static void delete(Context context, String url, int id, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.delete(context, getAbsoluteUrl(url, id), addHeaders(), responseHandler);
    }

    public static void delete(Context context, String url, int idUser, int id, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.delete(context, getAbsoluteUrl(url, idUser, id), addHeaders(), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl)
    {
        return Api.getBaseUrl() + relativeUrl;
    }

    private static String getAbsoluteUrl(String relativeUrl, int id)
    {
        relativeUrl = relativeUrl.replace("{id}", String.valueOf(id));
        return Api.getBaseUrl() + relativeUrl;
    }

    private static String getAbsoluteUrl(String relativeUrl, int idUser, int id)
    {
        relativeUrl = relativeUrl.replace("{idUser}", String.valueOf(idUser));
        relativeUrl = relativeUrl.replace("{id}", String.valueOf(id));
        return Api.getBaseUrl() + relativeUrl;
    }

    private static Header[] addHeaders()
    {
        String token = UserService.getInstance().getToken();

        List<Header> headersList = new ArrayList<>();
        headersList.add(new BasicHeader("Content-Type", "application/json"));
        headersList.add(new BasicHeader("Authorization", "Bearer " + token));

        Header[] headers = headersList.toArray(new Header[headersList.size()]);
        return headers;
    }
}
