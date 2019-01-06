package inzynierka.animalshelters.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.PhotoModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class PhotoListItemAdapter extends ArrayAdapter<PhotoModel> {
    private Context _context;
    private ArrayList<PhotoModel> _photos;

    public PhotoListItemAdapter(Context context, ArrayList<PhotoModel> photos)
    {
        super(context, R.layout.photo_item, photos);
        this._context = context;
        this._photos = photos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final PhotoModel photo = _photos.get(position);
        final ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.photo_item, parent, false);

            viewHolder.photo_id = convertView.findViewById(R.id.id_photo);
            viewHolder.photo = convertView.findViewById(R.id.photo);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.animal_id = convertView.findViewById(R.id.id_animal);
            viewHolder.deleteBtn = convertView.findViewById(R.id.deleteBtn);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.photo_id.setText(String.valueOf(photo.getId()));
        viewHolder.title.setText(photo.getTitle());
        viewHolder.animal_id.setText(String.valueOf(photo.getAnimalId()));

        String encodedPhoto = photo.getContent();
        if(encodedPhoto != null && encodedPhoto != "")
        {
            Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
            viewHolder.photo.setImageBitmap(bitmap);
        }

        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = ConfirmDelete(photo.getId(), position);
                alertDialog.show();
            }
        });

        return convertView;
    }

    public AlertDialog ConfirmDelete(final int idPhoto, final int position)
    {
        AlertDialog confirmDeleteAlertDialog = new AlertDialog.Builder(_context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this animal?")
                .setIcon(R.drawable.ic_delete_black_18dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeletePhoto(idPhoto, position);
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

    private void DeletePhoto(final int idPhoto, final int position)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.delete(getContext(), Api.PHOTOS_BY_ID, idPhoto, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        _photos.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    private static class ViewHolder
    {
        TextView photo_id;
        TextView animal_id;
        ImageView photo;
        TextView title;
        ImageButton deleteBtn;
    }
}
