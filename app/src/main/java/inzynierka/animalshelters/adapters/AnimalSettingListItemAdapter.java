package inzynierka.animalshelters.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animals.EditAnimalActivity;
import inzynierka.animalshelters.activities.settings.SettingsAnimals;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AnimalSettingListItemAdapter extends ArrayAdapter<AnimalDetailsModel> {

    SettingsAnimals fragment;

    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String DOG = "Dog";
    private static String CAT = "Cat";

    private Context _context;
    private String _activity;
    private ArrayList<AnimalDetailsModel> _animalModel;

    public AnimalSettingListItemAdapter(Context context, ArrayList<AnimalDetailsModel> animals, SettingsAnimals fragment)
    {
        super(context, R.layout.animal_settings_list_item, animals);
        this._context = context;
        this._activity = context.getClass().getSimpleName();
        this._animalModel = animals;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final AnimalDetailsModel animalModel = _animalModel.get(position);
        final ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.animal_settings_list_item, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.animal_id);
            viewHolder.animalName = (TextView) convertView.findViewById(R.id.animal_name);
            viewHolder.animalBreed = (TextView) convertView.findViewById(R.id.animal_breed);
            viewHolder.animalAge = (TextView) convertView.findViewById(R.id.animal_age);
            viewHolder.animalSpecies = (ImageView) convertView.findViewById(R.id.animal_species);
            viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.editBtn);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.deleteBtn);
            viewHolder.animalPhoto = (ImageView) convertView.findViewById(R.id.animal_photo);
            viewHolder.animalSex = (ImageView) convertView.findViewById(R.id.animal_sex);
            viewHolder.animalSize = (TextView) convertView.findViewById(R.id.animal_size);
            viewHolder.animalShelter = (TextView) convertView.findViewById(R.id.animal_shelter_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(animalModel.getId()));
        viewHolder.animalName.setText(animalModel.getName());
        viewHolder.animalBreed.setText(animalModel.getBreed());
        viewHolder.animalAge.setText(animalModel.getAgeString());
        viewHolder.animalSize.setText(animalModel.getSize());
        viewHolder.animalShelter.setText(animalModel.getAnimalShelter().getName());

        if(animalModel.getSex().equals(MALE)) {
            viewHolder.animalSex.setImageResource(R.drawable.male_brown);
        } else if (animalModel.getSex().equals(FEMALE)){
            viewHolder.animalSex.setImageResource(R.drawable.woman_brown);
        }

        if(animalModel.getSpecies().equals(DOG) ) {
            viewHolder.animalSpecies.setImageResource(R.drawable.dog_brown_big);
        } else if (animalModel.getSpecies().equals(CAT)) {
            viewHolder.animalSpecies.setImageResource(R.drawable.cat_brown_big);
        }

        viewHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int animalId = animalModel.getId();
                Intent intent = new Intent(_context, EditAnimalActivity.class);
                intent.putExtra("AnimalId", animalId);
                _context.startActivity(intent);
            }
        });

        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog alertDialog = ConfirmDelete(animalModel.getId());
                alertDialog.show();
            }

        });
        return convertView;
    }

    public AlertDialog ConfirmDelete(final int idAnimal)
    {
        AlertDialog confirmDeleteAlertDialog = new AlertDialog.Builder(_context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this user?")
                .setIcon(R.drawable.ic_delete_black_18dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteAnimal(idAnimal);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        return  confirmDeleteAlertDialog;
    }

    private void DeleteAnimal(int idAnimal)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.delete(getContext(), Api.ANIMAL_ID_URL, idAnimal, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        fragment.onDeleteAnimal();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    private static class ViewHolder
    {
        TextView id;
        TextView animalName;
        TextView animalBreed;
        TextView animalAge;
        ImageView animalSpecies;
        ImageButton editBtn;
        ImageButton deleteBtn;
        ImageView animalPhoto;
        ImageView animalSex;
        TextView animalSize;
        TextView animalShelter;
    }
}
