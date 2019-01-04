package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.helpers.AdministrationHelper;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.interfaces.AnimalListElementInterface;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AnimalListItemAdapter extends ArrayAdapter<AnimalDetailsModel> {

    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String DOG = "Dog";
    private static String CAT = "Cat";

    private Context _context;
    private String _activity;
    private ArrayList<AnimalDetailsModel> _animalModel;

    public AnimalListItemAdapter(Context context, ArrayList<AnimalDetailsModel> animals)
    {
        super(context, R.layout.animal_list_item, animals);
        this._context = context;
        this._activity = context.getClass().getSimpleName();
        this._animalModel = animals;
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
            convertView = inflater.inflate(R.layout.animal_list_item, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.animal_id);
            viewHolder.animalName = (TextView) convertView.findViewById(R.id.animal_name);
            viewHolder.animalBreed = (TextView) convertView.findViewById(R.id.animal_breed);
            viewHolder.animalAge = (TextView) convertView.findViewById(R.id.animal_age);
            viewHolder.animalSpecies = (ImageView) convertView.findViewById(R.id.animal_species);
            viewHolder.favoriteAnimal = (ImageButton) convertView.findViewById(R.id.addToFavorite);
            viewHolder.detailsAnimal = (ImageButton) convertView.findViewById(R.id.detailsBtn);
            viewHolder.animalPhoto = (ImageView) convertView.findViewById(R.id.animal_photo);
            viewHolder.animalSex = (ImageView) convertView.findViewById(R.id.animal_sex);
            viewHolder.animalSize = (TextView) convertView.findViewById(R.id.animal_size);
            viewHolder.animalShelter = (TextView) convertView.findViewById(R.id.animal_shelter_name);
            viewHolder.isFavorite = (TextView) convertView.findViewById(R.id.isFavorite);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(animalModel.getId()));
        viewHolder.animalName.setText(animalModel.getName());
        viewHolder.animalBreed.setText(animalModel.getBreed());
        viewHolder.animalAge.setText(animalModel.getAgeString());
        viewHolder.animalSize.setText(animalModel.getSize());
        if(animalModel.getAnimalShelter() != null) {
            viewHolder.animalShelter.setText(animalModel.getAnimalShelter().getName());
        }

        if(animalModel.getAvatar() != null && animalModel.getAvatar() != "")
        {
            String encodedPhoto = animalModel.getAvatar();
            Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
            viewHolder.animalPhoto.setImageBitmap(bitmap);
        }

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

        if(animalModel.isFavorite()){
            viewHolder.isFavorite.setText("true");
            viewHolder.favoriteAnimal.setImageResource(R.drawable.heart_red);
        } else {
            viewHolder.isFavorite.setText("false");
            viewHolder.favoriteAnimal.setImageResource(R.drawable.heart_outline_brown);
        }

        viewHolder.detailsAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int animalId = animalModel.getId();
                Intent intent = new Intent(_context, AnimalActivity.class);
                intent.putExtra("AnimalId", animalId);
                _context.startActivity(intent);
            }
        });

        viewHolder.favoriteAnimal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            int animalId = animalModel.getId();
            final boolean isFavorite = animalModel.isFavorite();

            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("Content-Type", "application/json"));

            RequestParams params = new RequestParams();
            params.put("animalId", animalId);

            AdministrationHelper administrationHelper = new AdministrationHelper();
            int userId = administrationHelper.GetLogedUserId();

            if(!isFavorite) {
                Client.update(_context, "users/" + userId + "/addFavoriteAnimal",
                        params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                                animalModel.setFavorite(true);
                                viewHolder.isFavorite.setText("true");
                                viewHolder.favoriteAnimal.setImageResource(R.drawable.heart_red);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                Log.e("Error", res);
                            }
                        });
            } else if (isFavorite){
                Client.delete(_context, Api.USER_ID_ANIMAL, userId , animalId, headers.toArray(new Header[headers.size()]),
                        new RequestParams(), new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                animalModel.setFavorite(false);
                                viewHolder.favoriteAnimal.setImageResource(R.drawable.heart_outline_brown);
                                viewHolder.isFavorite.setText("false");
                                if(_activity.equals("FavoriteAnimalsActivity"))
                                {
                                    _animalModel.remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                Log.e("Error", res);
                            }
                        });
            }
        }
    });
        return convertView;
    }

    private static class ViewHolder
    {
        TextView id;
        TextView animalName;
        TextView animalBreed;
        TextView animalAge;
        ImageView animalSpecies;
        ImageButton favoriteAnimal;
        ImageButton detailsAnimal;
        ImageView animalPhoto;
        ImageView animalSex;
        TextView animalSize;
        TextView animalShelter;
        TextView isFavorite;
    }
}
