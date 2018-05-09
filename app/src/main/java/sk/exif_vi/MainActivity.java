package sk.exif_vi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import sk.exif_vi.Image.BitmapConverter;
import sk.exif_vi.Image.Image;
import sk.exif_vi.Image.ImageAdapter;
import sk.exif_vi.Image.MyExif;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private ArrayList<Image> fileArray = new ArrayList<Image>();
    private BitmapConverter bitmapConverter = new BitmapConverter();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        while (!askPermission(REQUEST_ID_READ_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE))
            ;
        setContentView(R.layout.activity_main);

        //loadImagepaths(android.os.Environment.getExternalStorageDirectory());
        //loadImagepaths(new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCMI/jpg"));
//        loadImagepaths(new File("/storage/3334-AE98/DCIM/jpg"));
        loadImagepaths(new File("/storage/emulated/0/DCIM/jpg"));
        GridView gridview = (GridView) findViewById(R.id.imageView);
        gridview.setAdapter(new ImageAdapter(this, fileArray.toArray(new Image[fileArray.size()])));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toImageFullActivity(view, fileArray.get(position).getPath());
            }
        });
    }

    public void loadImagepaths(File file) {
        for (File f : file.listFiles()) {
            if (fileArray.size() > 40) {
                break;
            }
            if (f.isDirectory()) {
                if (f.getAbsolutePath().endsWith(".android_secure")) {
                    break;
                }
//                if (f.getAbsolutePath().endsWith("DCIM")) {
//                    continue;
//                }
                else if (f.getAbsolutePath().endsWith("jpg")) {
                    continue;
                }
                loadImagepaths(f);
            } else {
                if (f.getAbsolutePath().endsWith(".jpg") ||
//                        f.getAbsolutePath().endsWith(".gif") ||
//                        f.getAbsolutePath().endsWith(".png") ||
                        f.getAbsolutePath().endsWith(".jpeg")) {

                    try {
                        ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                        if (exifInterface != null) {
                            Image image = new Image(f.getAbsolutePath());
                            image.setBitmap(bitmapConverter.getImageBitmap(f.getAbsolutePath()));
                            image.setExif(new MyExif(exifInterface));
                            fileArray.add(image);
                        }

                    } catch (Exception e) {
                        Log.e("ERROR", "Could not load image path " + f.getAbsolutePath());
                    }

                }
            }
        }
    }

    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    public void toImageFullActivity(View view, String path) {
        Intent intent = new Intent(getApplicationContext(), ImageFull.class);
        intent.putExtra("filePath", path);
        startActivity(intent);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
