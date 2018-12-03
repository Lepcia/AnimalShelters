package inzynierka.animalshelters.activities.animals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.models.AnimalModel;
import inzynierka.animalshelters.models.PhotoModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

@SuppressLint("ValidFragment")
public class AnimalDetailsActivity extends Fragment {
    @SuppressLint("ValidFragment")
    AnimalDetailsActivity(int animalId) {
        getAnimal(animalId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getAnimal(int id) {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(getContext(), Api.ANIMAL_ID_URL, id, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            try {
                                new AnimalDetailsModel(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                        favoriteAnimalView = (ListView) findViewById(R.id.favorite_animals_list);
//                        favoriteAnimalView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    private class AnimalDetailsMainContext extends AppCompatActivity {
        ViewPager viewPager;
        AnimalDetailsViewAdjuster adjuster;

        AnimalDetailsMainContext(AnimalDetailsModel detailsModel) {
            viewPager = (ViewPager)findViewById(R.id.viewPager);
            adjuster = new AnimalDetailsViewAdjuster(AnimalDetailsMainContext.this, detailsModel);
            viewPager.setAdapter(adjuster );
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.animal_details);





        }
    }

    private class AnimalDetailsViewAdjuster extends PagerAdapter {
        Activity activity;
        List<String> images;
        LayoutInflater inflater;

        AnimalDetailsViewAdjuster(Activity activity, AnimalDetailsModel animal) {
            this.activity = activity;
            for (PhotoModel photo:animal.getPhotos()) {
                images.add(photo.getContent());
            }
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return false;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.animal_photo_item, container, false);

            ImageView imageView;
            imageView = (ImageView) itemView.findViewById(R.id.animal_photo);
            DisplayMetrics dis = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
            int height = dis.heightPixels;
            int width = dis.widthPixels;
            imageView.setMaxHeight(height);
            imageView.setMaxWidth(width);

            try {
                Picasso.with(activity.getApplicationContext())
                        .load(images.get(position))
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);
            } catch (Exception e) {

            }

            container.addView(itemView);
            return itemView;
        }
    }

}
