package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.helpers.ImageHelper;

public class PhotoSliderAdapter extends PagerAdapter{

    private Context _context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images;

    public PhotoSliderAdapter(Context context, ArrayList<String> photos)
    {
        this._context = context;
        this.images = photos;
    }

    @Override
    public int getCount() {
        if(images != null) {

            return images.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.animal_photo_item, null);
        ImageView imageView = view.findViewById(R.id.animal_photo);
        if(images.get(position) != null) {
            Bitmap bitmap = ImageHelper.getImageBitmap(images.get(position));
            imageView.setImageBitmap(bitmap);
        } else
        {
            imageView.setImageBitmap(null);
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ViewPager vp = (ViewPager) container;
        View view = (View)object;
        vp.removeView(view);
    }
}
