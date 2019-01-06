package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animalShelters.ShelterActivity;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.AnimalShelterModel;

public class AnimalShelterListItemAdapter extends ArrayAdapter<AnimalShelterModel> {

    Context _context;

    public AnimalShelterListItemAdapter(Context context, ArrayList<AnimalShelterModel> shelters)
    {
        super(context, R.layout.animal_shelter_list_item, shelters);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final AnimalShelterModel shelterModel = getItem(position);
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
            viewHolder.shelterAvatar = (ImageView) convertView.findViewById(R.id.shelter_avatar);
            viewHolder.details = (ImageButton) convertView.findViewById(R.id.detailsBtn);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(shelterModel.getId()));
        viewHolder.shelterName.setText(shelterModel.getName());
        viewHolder.shelterAdres.setText(shelterModel.getFullAdres());
        viewHolder.shelterEmail.setText(shelterModel.getEmail());
        viewHolder.shelterPhone.setText(shelterModel.getPhone());
        if(shelterModel.getAvatar() != null && shelterModel.getAvatar() != "") {
            String encodedPhoto = shelterModel.getAvatar();
            Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
            viewHolder.shelterAvatar.setImageBitmap(bitmap);
        }
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shelterId = shelterModel.getId();
                Intent intent = new Intent(_context, ShelterActivity.class);
                intent.putExtra("ShelterId", shelterId);
                _context.startActivity(intent);
            }
        });
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
        ImageButton details;
    }
}
