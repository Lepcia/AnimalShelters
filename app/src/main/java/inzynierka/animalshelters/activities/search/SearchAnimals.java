package inzynierka.animalshelters.activities.search;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.UserListActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.adapters.AnimalListItemAdapter;
import inzynierka.animalshelters.adapters.UserListItemAdapter;
import inzynierka.animalshelters.models.AnimalModel;
import inzynierka.animalshelters.models.UserModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class SearchAnimals extends Fragment {

    private ListView animalView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_animals, container, false);
        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.searchToolbar);
        ((BasicActivity)getActivity()).setSupportActionBar(toolbar);
        getAnimals();
        setSearchPanel();

        return rootView;
    }

    private void setSearchPanel()
    {
        ImageButton expandBtn = (ImageButton)rootView.findViewById(R.id.expandBtn);

        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout searchPanel = (ConstraintLayout)rootView.findViewById(R.id.searchConteiner);
                if(searchPanel.getVisibility() == ConstraintLayout.VISIBLE)
                {
                    searchPanel.setVisibility(ConstraintLayout.GONE);
                } else if(searchPanel.getVisibility() == ConstraintLayout.GONE)
                {
                    searchPanel.setVisibility(ConstraintLayout.VISIBLE);
                }
            }
        });



    }

    private void getAnimals()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.ANIMALS_ALL_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalModel> animalsArray = new ArrayList<>();
                        AnimalListItemAdapter animalListItemAdapter = new AnimalListItemAdapter(getContext(), animalsArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        animalView = (ListView) rootView.findViewById(R.id.animalsList);
                        animalView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
