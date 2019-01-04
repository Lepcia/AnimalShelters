package inzynierka.animalshelters.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageHelper {
    public static Bitmap getImageBitmap(String avatar)
    {
        try {
            byte[] decodeString = Base64.decode(avatar, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            return bitmap;
        } catch (Exception e)
        {
            e.getMessage();
            return null;
        }

    }

    public static String encodeBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String encodedBitmap = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedBitmap;
    }
}
