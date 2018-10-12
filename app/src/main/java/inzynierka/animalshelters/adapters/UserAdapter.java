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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.models.UserModel;

public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(Context context, ArrayList<UserModel> users)
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

        if(avatar.isEmpty() || avatar == null) {
            viewHolder.avatar.setImageResource(R.drawable.ic_account_circle_black_48dp);
        } else {
            viewHolder.avatar.setImageBitmap(getImageBitmap(userModel.getAvatar()));
        }
        viewHolder.id.setText(String.valueOf(userModel.getId()));
        viewHolder.userName.setText(String.format("%s %s",userModel.getLastName(), userModel.getFirstName()));
        viewHolder.userEmail.setText(userModel.getEmail());

        return convertView;
    }

    private Bitmap getImageBitmap(String avatar)
    {
        byte[] decodeString = Base64.decode(avatar, Base64.NO_WRAP);
        InputStream inputStream = new ByteArrayInputStream(decodeString);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    private static class ViewHolder
    {
        TextView id;
        TextView userName;
        TextView userEmail;
        ImageView avatar;
    }
}
