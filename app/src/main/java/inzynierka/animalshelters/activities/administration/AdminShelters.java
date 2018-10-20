package inzynierka.animalshelters.activities.administration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.adapters.AnimalShelterAdminListItemAdapter;
import inzynierka.animalshelters.adapters.AnimalShelterListItemAdapter;
import inzynierka.animalshelters.models.AnimalShelterModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AdminShelters extends Fragment {

    private ListView shelterView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_shelters, container, false);
        getShelters();
        return rootView;
    }

    private void getShelters() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.ANIMAL_SHELTERS_ALL_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalShelterModel> sheltersArray = new ArrayList<>();
                        AnimalShelterAdminListItemAdapter shelterListItemAdapter =
                                new AnimalShelterAdminListItemAdapter(getContext(), sheltersArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                shelterListItemAdapter.add(new AnimalShelterModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        shelterView = (ListView) rootView.findViewById(R.id.sheltersList);
                        shelterView.setAdapter(shelterListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
