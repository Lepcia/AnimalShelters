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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.helpers.DateFormatHelper;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.UserModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class SettingsUser extends Fragment {
    private View rootView;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings_user, container, false);

        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        int userId = UserService.getInstance().getmUserId();
        getUser(userId);
        initBtn();
        addListenerOnUploadPhoto();
        requestStoragePermission();

        return rootView;
    }

    private void initBtn()
    {

       FloatingActionButton saveBtn = rootView.findViewById(R.id.saveBtn);
       saveBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onSaveBtnClick();
           }
       });
    }

    private void getUser(int userId)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(getContext(), Api.USER_ID_DETAILS, userId, headers.toArray(new Header[headers.size()]),
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
                TextView userId = rootView.findViewById(R.id.user_id);
                userId.setText(String.valueOf(data.getInt("id")));
            }

            if(data.has("firstName")) {
                EditText user_name = rootView.findViewById(R.id.edit_name);
                user_name.setText(data.getString("firstName"));
            }

            if(data.has("lastName")) {
                EditText user_surname = rootView.findViewById(R.id.edit_surname);
                user_surname.setText(data.getString("lastName"));
            }

            if(data.has("email")) {
                EditText user_email = rootView.findViewById(R.id.edit_email);
                user_email.setText(data.getString("email"));
            }

            if(data.has("dateOfBirth")) {
                Date dateOfBirth = DateFormatHelper.dateFromString(data.getString("dateOfBirth"), DateFormatHelper.FORMAT_POSTGRES_DATE);
                String dateString = DateFormatHelper.stringFromDate(dateOfBirth, DateFormatHelper.FORMAT_POSTGRES_DATE);

                EditText user_birthday = rootView.findViewById(R.id.edit_birthday);
                user_birthday.setText(dateString);
            }

            if(data.has("role"))
            {
                TextView role = rootView.findViewById(R.id.userType);
                role.setText(data.getString("role"));
            }

            if(data.has("avatar")) {
                String encodedPhoto = data.getString("avatar");
                Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
                ImageView user_avatar = rootView.findViewById(R.id.user_avatar);
                user_avatar.setImageBitmap(bitmap);
            }

            JSONObject animalShelter = data.getJSONObject("userToAnimalShelter");
            if(animalShelter.getInt("id") > 0) {
                String shelterName = animalShelter.getString("name");
                TextView usersShelter = rootView.findViewById(R.id.user_shelter_name);
                usersShelter.setText(shelterName);
            }
        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    //TODO:  save password
    private void onSaveBtnClick() {
        UserModel user = new UserModel();
        EditText userName = rootView.findViewById(R.id.edit_name);
        user.setFirstName(userName.getText().toString());

        EditText userSurname = rootView.findViewById(R.id.edit_surname);
        user.setLastName(userSurname.getText().toString());

        EditText userBirthday = rootView.findViewById(R.id.edit_phone);
        user.setDateOfBirth(DateFormatHelper.dateFromString(userBirthday.getText().toString(), DateFormatHelper.FORMAT_POSTGRES_DATE));

        EditText userEmail = rootView.findViewById(R.id.edit_email);
        user.setEmail(userEmail.getText().toString());

        //EditText userPassword = findViewById(R.id.edit_password);
        //user.setPassword(userPassword.getText().toString());

        TextView selectedShelter = rootView.findViewById(R.id.selected_shelter);
        user.setShelterName(selectedShelter.getText().toString());

        TextView idUser = rootView.findViewById(R.id.user_id);
        String id = idUser.getText().toString();

        TextView role = rootView.findViewById(R.id.userType);
        user.setRoleName(role.getText().toString());

        TextView shelterName = rootView.findViewById(R.id.user_shelter_name);
        user.setShelterName(shelterName.getText().toString());

        if(bitmap != null)
        {
            String encodedPhoto = ImageHelper.encodeBitmap(bitmap);
            user.setAvatar(encodedPhoto);
        }

        if (id != "" && id != null) {
            user.setId(Integer.parseInt(id));
        }
            updateUser(user);
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
                    ImageView avatar = rootView.findViewById(R.id.user_avatar);
                    avatar.setImageBitmap(bitmap);
                }
            } catch (IOException e)
            {
            }
        }
    }

    private void updateUser(UserModel user)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(user);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.update(getContext(), Api.USER_ID_URL, stringEntity, user.getId(), headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getContext(), "Updated succesfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });
    }
}
