package inzynierka.animalshelters.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminEditShelter;
import inzynierka.animalshelters.interfaces.AdminListElementInterface;
import inzynierka.animalshelters.models.AnimalShelterModel;

public class AnimalShelterAdminListItemAdapter extends ArrayAdapter<AnimalShelterModel> implements AdminListElementInterface {

    private Context _context;
    public AnimalShelterAdminListItemAdapter(Context context, ArrayList<AnimalShelterModel> shelters)
    {
        super(context, R.layout.animal_shelter_admin_list_item, shelters);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AnimalShelterModel shelterModel = getItem(position);
        AnimalShelterAdminListItemAdapter.ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new AnimalShelterAdminListItemAdapter.ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.animal_shelter_admin_list_item, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.shelter_id);
            viewHolder.shelterName = (TextView) convertView.findViewById(R.id.shelter_name);
            viewHolder.shelterAdres = (TextView) convertView.findViewById(R.id.shelter_adres);
            viewHolder.shelterEmail = (TextView) convertView.findViewById(R.id.shelter_email);
            viewHolder.shelterPhone = (TextView) convertView.findViewById(R.id.shelter_phone);
            viewHolder.shelterAvatar = (ImageView) convertView.findViewById(R.id.shelter_avatar);
            viewHolder.editShelter = (ImageButton) convertView.findViewById(R.id.editShelter);
            viewHolder.deleteShelter = (ImageButton) convertView.findViewById(R.id.deleteShelter);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AnimalShelterAdminListItemAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(String.valueOf(shelterModel.getId()));
        viewHolder.shelterName.setText(shelterModel.getName());
        viewHolder.shelterAdres.setText(shelterModel.getFullAdres());
        viewHolder.shelterEmail.setText(shelterModel.getEmail());
        viewHolder.shelterPhone.setText(shelterModel.getPhone());
        //viewHolder.shelterAvatar.setImageBitmap(ImageHelper.getImageBitmap(shelterModel.getAvatar()));

        EditBtn_onClick(viewHolder.editShelter);
        DeleteBtn_onClick(viewHolder.deleteShelter);

        return convertView;
    }

    @Override
    public void EditBtn_onClick(ImageButton btn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, AdminEditShelter.class);
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public void DeleteBtn_onClick(ImageButton btn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = ConfirmDelete();
                alertDialog.show();
            }
        });
    }

    @Override
    public AlertDialog ConfirmDelete()
    {
        AlertDialog confirmDeleteAlertDialog = new AlertDialog.Builder(_context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this shelter?")
                .setIcon(R.drawable.ic_delete_black_18dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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



    private static class ViewHolder
    {
        TextView id;
        TextView shelterName;
        TextView shelterAdres;
        TextView shelterEmail;
        TextView shelterPhone;
        ImageView shelterAvatar;
        ImageButton editShelter;
        ImageButton deleteShelter;
    }
}
