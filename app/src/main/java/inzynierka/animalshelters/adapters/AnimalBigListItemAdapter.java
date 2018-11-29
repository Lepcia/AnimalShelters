package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.models.AnimalModel;

public class AnimalBigListItemAdapter extends ArrayAdapter<AnimalModel> {

    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String DOG = "Dog";
    private static String CAT = "Cat";

    public AnimalBigListItemAdapter(Context context, ArrayList<AnimalModel> newAnimals)
    {
        super(context, R.layout.animal_big_list_item, newAnimals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AnimalModel animalModel = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.animal_big_list_item, parent,false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.animal_id);
            viewHolder.animalName = (TextView) convertView.findViewById(R.id.animal_name);
            viewHolder.animalBreed = (TextView) convertView.findViewById(R.id.animal_breed);
            viewHolder.animalAge = (TextView) convertView.findViewById(R.id.animal_age);
            viewHolder.animalSpecies = (ImageView) convertView.findViewById(R.id.animal_species);
            viewHolder.favoriteAnimal = (ImageButton) convertView.findViewById(R.id.addToFavorite);
            viewHolder.animalPhoto = (ImageView) convertView.findViewById(R.id.animal_photo);
            viewHolder.animalSex = (ImageView) convertView.findViewById(R.id.animal_sex);
            viewHolder.animalSize = (TextView) convertView.findViewById(R.id.animal_size);
            viewHolder.inShelterFrom = (TextView) convertView.findViewById(R.id.in_shelter_from);
            viewHolder.description = (TextView) convertView.findViewById(R.id.animal_description);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.id.setText(String.valueOf(animalModel.getId()));
        viewHolder.animalName.setText(animalModel.getName());
        viewHolder.animalBreed.setText(animalModel.getBreed());
        viewHolder.animalAge.setText(animalModel.getAgeString());
        viewHolder.animalSize.setText(animalModel.getSize());
        Date date = animalModel.getInShelterFrom();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.inShelterFrom.setText(formater.format(date));
        viewHolder.description.setText(animalModel.getDescription());

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
        ImageView animalPhoto;
        ImageView animalSex;
        TextView animalSize;
        TextView inShelterFrom;
        TextView description;
    }
}
