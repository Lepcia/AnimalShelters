package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.PhotoModel;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.ViewHolder> {
    public ArrayList<PhotoModel> photos;
    public Context _context;

    public PhotoGalleryAdapter(Context context, ArrayList<PhotoModel> photos)
    {
        this._context = context;
        this.photos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoGalleryAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.id_photo.setText(photos.get(i).getTitle());
        viewHolder.title.setText(photos.get(i).getTitle());
        viewHolder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String encodedPhoto = photos.get(i).getContent();
        viewHolder.photo.setImageResource(R.drawable.cat_photo3);


        viewHolder.photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

     public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView id_photo;
        ImageView photo;
        TextView title;

        public ViewHolder(View view){
            super(view);
            id_photo = view.findViewById(R.id.id_photo);
            photo = view.findViewById(R.id.photo);
            title = view.findViewById(R.id.title);
        }

    }
}
