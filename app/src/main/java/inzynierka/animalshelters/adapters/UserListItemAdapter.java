package inzynierka.animalshelters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.UserModel;

public class UserListItemAdapter extends ArrayAdapter<UserModel> {


    public UserListItemAdapter(Context context, ArrayList<UserModel> users)
    {
        super(context, R.layout.user_list_item, users);
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
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.user_avatar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String avatar = userModel.getAvatar();

        if(avatar == null) {
            viewHolder.avatar.setImageResource(R.drawable.ic_account_circle_black_48dp);
        } else {
            viewHolder.avatar.setImageBitmap(ImageHelper.getImageBitmap(userModel.getAvatar()));
        }
        viewHolder.id.setText(String.valueOf(userModel.getId()));
        viewHolder.userName.setText(String.format("%s %s",userModel.getLastName(), userModel.getFirstName()));
        viewHolder.userEmail.setText(userModel.getEmail());

        return convertView;
    }

    private static class ViewHolder
    {
        TextView id;
        TextView userName;
        TextView userEmail;
        ImageView avatar;
    }
}
