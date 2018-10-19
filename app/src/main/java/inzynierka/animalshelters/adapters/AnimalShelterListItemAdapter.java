package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.AnimalShelterModel;

public class AnimalShelterListItemAdapter extends ArrayAdapter<AnimalShelterModel> {

    public AnimalShelterListItemAdapter(Context context, ArrayList<AnimalShelterModel> shelters)
    {
        super(context, R.layout.animal_shelter_list_item, shelters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AnimalShelterModel shelterModel = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.animal_shelter_list_item, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.shelter_id);
            viewHolder.shelterName = (TextView) convertView.findViewById(R.id.shelter_name);
            viewHolder.shelterAdres = (TextView) convertView.findViewById(R.id.shelter_adres);
            viewHolder.shelterEmail = (TextView) convertView.findViewById(R.id.shelter_email);
            viewHolder.shelterPhone = (TextView) convertView.findViewById(R.id.shelter_phone);
            viewHolder.shelterAvatar = (ImageView) convertView.findViewById(R.id.shelter_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(shelterModel.getId()));
        viewHolder.shelterName.setText(shelterModel.getName());
        viewHolder.shelterAdres.setText(shelterModel.getFullAdres());
        viewHolder.shelterEmail.setText(shelterModel.getEmail());
        viewHolder.shelterPhone.setText(shelterModel.getPhone());
        //viewHolder.shelterAvatar.setImageBitmap(ImageHelper.getImageBitmap(shelterModel.getAvatar()));

        return convertView;
    }

    private static class ViewHolder
    {
        TextView id;
        TextView shelterName;
        TextView shelterAdres;
        TextView shelterEmail;
        TextView shelterPhone;
        ImageView shelterAvatar;
    }
}
