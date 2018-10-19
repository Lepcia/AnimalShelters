package inzynierka.animalshelters.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageHelper {
    public static Bitmap getImageBitmap(String avatar)
    {
        byte[] decodeString = Base64.decode(avatar, Base64.NO_WRAP);
        InputStream inputStream = new ByteArrayInputStream(decodeString);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
