package inzynierka.animalshelters.activities.newsBoard;

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
import inzynierka.animalshelters.adapters.AnimalBigListItemAdapter;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.models.AnimalModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class NewsBoardNew extends Fragment {


    private ListView newAnimalsView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_board_new, container, false);
        getNewAnimals();

        return rootView;
    }

    private void getNewAnimals() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.ANIMALS_NEWEST_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalDetailsModel> newAnimalsArray = new ArrayList<>();
                        AnimalBigListItemAdapter animalListItemAdapter = new AnimalBigListItemAdapter(getContext(), newAnimalsArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                animalListItemAdapter.add(new AnimalDetailsModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        newAnimalsView = rootView.findViewById(R.id.newsBoardNewesList);
                        newAnimalsView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });

    }
}
