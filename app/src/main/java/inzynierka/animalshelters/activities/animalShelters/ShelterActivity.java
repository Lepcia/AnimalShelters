package inzynierka.animalshelters.activities.animalShelters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.map.GeocodingLocation;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.adapters.PhotoSliderAdapter;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class ShelterActivity extends BasicActivity  implements OnMapReadyCallback {

    Context _context;
    ViewPager viewPager;
    private String Address;
    private String latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        onCreateDrawer();
        onCreateDrawerMenu();
        this._context = this;

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("ShelterId") > 0) {
            TextView animalIdTextView = findViewById(R.id.shelter_id);
            int shelterId = bundle.getInt("ShelterId");
            animalIdTextView.setText(String.valueOf(shelterId));

            getShelterById(shelterId);

            if(bundle.getString("FullAdres") != null) {
                this.Address = bundle.getString("FullAdres");
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        }
    }

    private void getShelterById(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(this, Api.ANIMAL_SHELTER_ID_DETAILS_URL, id, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        setFormData(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });

    }

    private void setFormData(JSONObject data)
    {
        try{
            if(data.has("fullAdres"))
            {
                Address = data.getString("fullAdres");
            }
            if(data.has("name")) {
                TextView animalShelter = findViewById(R.id.shelter_name);
                animalShelter.setText(data.getString("name"));
            }

            if(data.has("street")) {
                TextView street = findViewById(R.id.shelter_street);
                String streetS = data.getString("street");
                if(data.has("number"))
                {
                    streetS = streetS + " " + data.getString("number");
                }
                street.setText(streetS);
            }

            if(data.has("city")) {
                TextView city = findViewById(R.id.shelter_city);
                city.setText(data.getString("city"));
            }
            if(data.has("postalCode")) {
                TextView postalCode = findViewById(R.id.shelter_postal);
                postalCode.setText(data.getString("postalCode"));
            }
            if(data.has("email")) {
                TextView email = findViewById(R.id.shelter_email);
                email.setText(data.getString("email"));
            }
            if(data.has("phone")) {
                TextView phone = findViewById(R.id.shelter_phone);
                phone.setText(data.getString("phone"));
            }
            if(data.has("bankAccountNumber")) {
                TextView bank = findViewById(R.id.shelter_bank);
                bank.setText(data.getString("bankAccountNumber"));
            }
        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(ShelterActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(ShelterActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SettingsActivity.class);
        intent.putExtra("ShelterId", 1);
        intent.putExtra("UserId", 1);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(ShelterActivity.this, PhotosActivity.class);
        intent.putExtra("ShelterId", 1);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(Address,
                getApplicationContext(), new GeocoderHandler(googleMap));

    }

    private class GeocoderHandler extends Handler {
        private GoogleMap _googleMap;

        public GeocoderHandler(GoogleMap googleMap)
        {
            this._googleMap = googleMap;
        }
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            Double lat = 0.0;
            Double lon = 0.0;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat = bundle.getDouble("lat");
                    lon = bundle.getDouble("lon");
                    break;
                default:
                    locationAddress = null;
            }
            LatLng location;
            if(lat != null  && lon != null){
                location = new LatLng(lat, lon);
                _googleMap.addMarker(new MarkerOptions().position(location)
                        .title("Marker in Sydney"));
                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                _googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                _googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }
        }
    }
}


