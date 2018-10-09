package inzynierka.animalshelters.activities.administration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import inzynierka.animalshelters.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.adapters.UserAdapter;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;
import inzynierka.animalshelters.models.UserModel;

public class UserListActivity extends AppCompatActivity {

    private ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getUsers();
    }

    private void getUsers()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(UserListActivity.this, Api.USERS_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<UserModel> noteArray = new ArrayList<>();
                        UserAdapter userAdapter = new UserAdapter(UserListActivity.this, noteArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                userAdapter.add(new UserModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        userList = (ListView) findViewById(R.id.list_users);
                        userList.setAdapter(userAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
