package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import inzynierka.animalshelters.R;

public class PhotoSliderAdapter extends PagerAdapter{

    private Context _context;
    private LayoutInflater layoutInflater;
    private Integer[] images =  {R.drawable.cat_photo, R.drawable.cat_photo2, R.drawable.cat_photo3};

    public PhotoSliderAdapter(Context context)
    {
        this._context = context;
    }

    @Override
    public int getCount() {
        return images.length;
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
        imageView.setImageResource(images[position]);

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
