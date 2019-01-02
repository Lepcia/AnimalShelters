package inzynierka.animalshelters.activities.administration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.adapters.UserListItemAdapter;
import inzynierka.animalshelters.models.UserModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AdminUsers extends Fragment implements UserListItemAdapter.EventListener {

    private ListView usersList;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin_users, container, false);
        getUsers();
        addUserListenerOnClick();
        return rootView;
    }

    private void addUserListenerOnClick()
    {
        FloatingActionButton addBtn = rootView.findViewById(R.id.addUserBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminEditUser.class);
                intent.putExtra("UserId", -1);
                getContext().startActivity(intent);
            }
        });
    }

    public void onDeleteUser()
    {
        this.getUsers();
    }

    private void getUsers()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.USERS_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<UserModel> noteArray = new ArrayList<>();
                        UserListItemAdapter userListItemAdapter = new UserListItemAdapter(getContext(), noteArray, AdminUsers.this);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                userListItemAdapter.add(new UserModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        usersList = (ListView) rootView.findViewById(R.id.users_list);
                        usersList.setAdapter(userListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
