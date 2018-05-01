package sk.exif_vi.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    private static int QUALITY = 5;
    private BitmapFactory.Options options;

    public BitmapConverter() {

        this.options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
    }

    public Bitmap getImageBitmap(String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, byteArrayOutputStream);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    public Bitmap getImageOriginalBitmap(String path) {
        BitmapFactory.Options options;
        options = new BitmapFactory.Options();
        options.inSampleSize = 100;
        return BitmapFactory.decodeFile(path);
    }

}
