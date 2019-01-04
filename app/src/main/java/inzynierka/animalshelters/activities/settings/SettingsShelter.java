package inzynierka.animalshelters.activities.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.AnimalShelterModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class SettingsShelter extends Fragment {
    private View rootView;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings_shelter, container, false);

        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        int shelterId = settingsActivity.GetShelterId();
        getShelter(shelterId);
        addListenerOnSave();
        addListenerOnUploadPhoto();
        requestStoragePermission();

        return rootView;
    }

    private void getShelter(int shelterId)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(getContext(), Api.ANIMAL_SHELTER_ID_URL, shelterId, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        setFormData(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        super.onFailure(statusCode, headers, res, t);
                        Log.e("Error", res);
                    }
                });
    }

    private void setFormData(JSONObject data)
    {
        try {
            if(data.has("id")) {
                TextView userId = rootView.findViewById(R.id.shelter_id);
                userId.setText(String.valueOf(data.getInt("id")));
            }

            if(data.has("name")) {
                EditText user_name = rootView.findViewById(R.id.edit_name);
                user_name.setText(data.getString("name"));
            }

            if(data.has("phone")) {
                EditText user_surname = rootView.findViewById(R.id.edit_phone);
                user_surname.setText(data.getString("phone"));
            }

            if(data.has("email")) {
                EditText user_email = rootView.findViewById(R.id.edit_email);
                user_email.setText(data.getString("email"));
            }

            if(data.has("street")) {
                EditText user_name = rootView.findViewById(R.id.edit_street);
                user_name.setText(data.getString("street"));
            }

            if(data.has("city")) {
                EditText user_surname = rootView.findViewById(R.id.edit_city);
                user_surname.setText(data.getString("city"));
            }

            if(data.has("number")) {
                EditText user_email = rootView.findViewById(R.id.edit_street_number);
                user_email.setText(data.getString("number"));
            }

            if(data.has("postalCode")) {
                EditText user_email = rootView.findViewById(R.id.edit_postal);
                user_email.setText(data.getString("postalCode"));
            }

            if(data.has("avatar")) {
                String encodedPhoto = data.getString("avatar");
                Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
                ImageView animal_avatar = rootView.findViewById(R.id.animal_avatar);
                animal_avatar.setImageBitmap(bitmap);
            }

        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    private void addListenerOnSave()
    {
        FloatingActionButton saveBtn = rootView.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveBtnClick();
            }
        });
    }

    private void onSaveBtnClick()
    {
        AnimalShelterModel shelter = new AnimalShelterModel();

        TextView idShelter = rootView.findViewById(R.id.shelter_id);
        String id = idShelter.getText().toString();
        if(id != "" && id != null ) {
            shelter.setId(Integer.parseInt(id));
        }

        EditText shelterName = rootView.findViewById(R.id.edit_name);
        shelter.setName(shelterName.getText().toString());

        EditText shelterEmail = rootView.findViewById(R.id.edit_email);
        shelter.setEmail(shelterEmail.getText().toString());

        EditText shelterPhone = rootView.findViewById(R.id.edit_phone);
        shelter.setPhone(shelterPhone.getText().toString());

        EditText shelterStreet = rootView.findViewById(R.id.edit_street);
        shelter.setStreet(shelterStreet.getText().toString());

        EditText shelterStreetNumber = rootView.findViewById(R.id.edit_street_number);
        shelter.setNumber(shelterStreetNumber.getText().toString());

        EditText shelterCity = rootView.findViewById(R.id.edit_city);
        shelter.setCity(shelterCity.getText().toString());

        EditText shelterPostal = rootView.findViewById(R.id.edit_postal);
        shelter.setPostalCode(shelterPostal.getText().toString());

        if(bitmap != null)
        {
            String encodedPhoto = ImageHelper.encodeBitmap(bitmap);
            shelter.setAvatar(encodedPhoto);
        }

        if(id != "" && id != null) {
            updateShelter(shelter);
        }

    }

    private void addListenerOnUploadPhoto()
    {
        Button uploadBtn = rootView.findViewById(R.id.addPhotoBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void requestStoragePermission()
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser()
    {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                if(bitmap != null) {
                    ImageView avatar = rootView.findViewById(R.id.animal_avatar);
                    avatar.setImageBitmap(bitmap);
                }
            } catch (IOException e)
            {
            }
        }
    }

    private  void updateShelter(AnimalShelterModel shelter)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(shelter);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.update(getContext(), Api.ANIMAL_SHELTER_ID_URL, stringEntity, shelter.getId(), headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });
    }

}
