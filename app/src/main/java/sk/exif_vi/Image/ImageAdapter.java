package sk.exif_vi.Image;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Image[] imageArray;

    public ImageAdapter(Context c, Image[] imageArray) {
        mContext = c;
        setImageArray(imageArray);
    }

    @Override
    public int getCount() {
        return getImageArray().length;
    }

    public Object getItem(int position) {
        return imageArray[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(imageArray[position].getBitmap());
        return imageView;
    }

    public Image[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(Image[] imageArray) {
        this.imageArray = imageArray;
    }
}