package sk.exif_vi.Image;

import android.graphics.Bitmap;

import sk.exif_vi.MyExif.MyExif;

public class Image {

    public Image(Bitmap bitmap, String path) {
        this.bitmap = bitmap;
        this.path = path;
    }

    public Image(String path) {
        this.path = path;
    }

    private Bitmap bitmap;
    private String path;
    private MyExif exif;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MyExif getExif() {
        return exif;
    }

    public void setExif(MyExif exif) {
        this.exif = exif;
    }
}
