package inzynierka.animalshelters.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.EventListener;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminEditUser;
import inzynierka.animalshelters.activities.administration.AdminUsers;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.interfaces.AdminListElementInterface;
import inzynierka.animalshelters.models.UserModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class UserListItemAdapter extends ArrayAdapter<UserModel> implements AdminListElementInterface {

    EventListener eventListener;
    private Context _context;
    public UserListItemAdapter(Context context, ArrayList<UserModel> users, EventListener eventListener)
    {
        super(context, R.layout.user_list_item, users);
        this._context = context;
        this.eventListener = eventListener;
    }

    public interface EventListener{
        void onDeleteUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        UserModel userModel = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);

            viewHolder.id = (TextView)convertView.findViewById(R.id.user_id);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userEmail = (TextView) convertView.findViewById(R.id.user_email);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.user_photo);
            viewHolder.editBtn = (ImageButton) convertView.findViewById(R.id.editUser);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.deleteUser);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String avatar = userModel.getAvatar();

        if(avatar != null && avatar != "")
        {
            Bitmap bitmap = ImageHelper.getImageBitmap(avatar);
            viewHolder.avatar.setImageBitmap(bitmap);
        }

        viewHolder.id.setText(String.valueOf(userModel.getId()));
        viewHolder.userName.setText(String.format("%s %s",userModel.getLastName(), userModel.getFirstName()));
        viewHolder.userEmail.setText(userModel.getEmail());

        EditBtn_onClick(viewHolder.editBtn, userModel.getId());
        DeleteBtn_onClick(viewHolder.deleteBtn, userModel.getId());

        return convertView;
    }

    @Override
    public void EditBtn_onClick(ImageButton btn, final int id)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, AdminEditUser.class);
                intent.putExtra("UserId", id);
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public void DeleteBtn_onClick(ImageButton btn, final int id)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = ConfirmDelete(id);
                alertDialog.show();
            }
        });
    }

    @Override
    public AlertDialog ConfirmDelete(final int idUser)
    {
        AlertDialog confirmDeleteAlertDialog = new AlertDialog.Builder(_context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this user?")
                .setIcon(R.drawable.ic_delete_black_18dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteUser(idUser);
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

    private void DeleteUser(int idUser)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.delete(getContext(), Api.USER_ID_URL, idUser, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        eventListener.onDeleteUser();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    private static class ViewHolder
    {
        TextView id;
        TextView userName;
        TextView userEmail;
        ImageView avatar;
        ImageButton editBtn;
        ImageButton deleteBtn;
    }
}
