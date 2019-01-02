package inzynierka.animalshelters.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import inzynierka.animalshelters.activities.animals.EditAnimalActivity;
import inzynierka.animalshelters.adapters.AnimalListItemAdapter;
import inzynierka.animalshelters.adapters.AnimalSettingListItemAdapter;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class SettingsAnimals extends Fragment {

    private ListView animalView;
    private View rootView;
    private int idShelter = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings_animals, container, false);

        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        int shelterId = settingsActivity.GetShelterId();
        idShelter = shelterId;
        getAnimals(shelterId);
        addAnimalListenerOnClick();

        return rootView;
    }

    public void onDeleteAnimal() {
        getAnimals(idShelter);
    }

    private void addAnimalListenerOnClick()
    {
        FloatingActionButton addBtn = rootView.findViewById(R.id.addAnimalBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditAnimalActivity.class);
                intent.putExtra("AnimalId", -1);
                getContext().startActivity(intent);
            }
        });
    }

    //TODO: get animals by shelter
    private void getAnimals(int idShelter)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(getContext(), Api.ANIMAL_SHELTER_ANIMALS, idShelter, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalDetailsModel> animalsArray = new ArrayList<>();
                        AnimalSettingListItemAdapter animalListItemAdapter = new AnimalSettingListItemAdapter(getContext(), animalsArray, SettingsAnimals.this);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                AnimalDetailsModel animal = new AnimalDetailsModel(response.getJSONObject(i));
                                animalListItemAdapter.add(animal);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        animalView = (ListView) rootView.findViewById(R.id.animals_list);
                        animalView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
