package sk.exif_vi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sk.exif_vi.Image.BitmapConverter;
import sk.exif_vi.Image.Image;
import sk.exif_vi.Image.ImageAdapter;
import sk.exif_vi.MyExif.DaoMaster;
import sk.exif_vi.MyExif.DaoSession;
import sk.exif_vi.MyExif.MyExif;
import sk.exif_vi.MyExif.MyExifDao;
import sk.exif_vi.Utils.FormUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    public ArrayList<Image> fileArray = new ArrayList<Image>();
    private BitmapConverter bitmapConverter = new BitmapConverter();
    private DaoSession daoSession;
    private MyExifDao myExifDao;
    private GridView gridview;

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
        while (!askPermission(REQUEST_ID_READ_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE));
        setContentView(R.layout.activity_main);

        Button filterButton = (Button) findViewById(R.id.button);
        filterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                QueryBuilder<MyExif> query = myExifDao.queryBuilder();
                String queryString = getQueryStringFromForm();
                WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition(queryString);
                loadFiltredImages(query.build().list());
            }
        });


        //set database
        setMyExifDao();
        myExifDao.deleteAll();
        //loadImagepaths(android.os.Environment.getExternalStorageDirectory());
        //loadImagepaths(new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCMI/jpg"));
        //        loadImagepaths(new File("/storage/emulated/0/DCIM/jpg"));
        loadImagepaths(new File("/storage/3334-AE98/DCIM/jpg"));

        loadImages();

        gridview = (GridView) findViewById(R.id.imageView);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toImageFullActivity(view, fileArray.get(position).getPath());
            }
        });
        gridview.setAdapter(new ImageAdapter(this, fileArray.toArray(new Image[fileArray.size()])));

    }

    public void loadImagepaths(File file) {
        for (File f : file.listFiles()) {
            if (fileArray.size() > 600) {
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
                        f.getAbsolutePath().endsWith(".jpeg")) {

                    try {
                        ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                        if (exifInterface != null) {
                            myExifDao.insert(new MyExif(exifInterface, f.getAbsolutePath()));
                        }

                    } catch (Exception e) {
                        Log.e("ERROR", "Could not load image path " + f.getAbsolutePath() + ", Message: " + e.getMessage());
                    }

                }
            }
        }
    }

    private void filterImages(View view) {

    }

    private String getQueryStringFromForm() {

        String queryString = "";

        TextView datetime = findViewById(R.id.datetimeeditText);
        Spinner datetimePredicateSpinner = findViewById(R.id.datetimePredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.DATETIME, datetimePredicateSpinner.getSelectedItem().toString(), datetime.getText().toString());


        Spinner flashLengthpredicate = findViewById(R.id.flashLengthpredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.FLASH, " = ", FormUtil.getSwitched(flashLengthpredicate.getSelectedItem()));

        TextView objectiveEditText = findViewById(R.id.objectiveEditText);
        Spinner objectivePredicateSpinner = findViewById(R.id.objectivePredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.FOCAL_LENGTH_OBJECTIVE, objectivePredicateSpinner.getSelectedItem().toString(), objectiveEditText.getText().toString());

        TextView ocularEditText = findViewById(R.id.ocularEditText);
        Spinner ocularPredicateSpinner = findViewById(R.id.ocularpredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.FOCAL_LENGTH_OCULAR, ocularPredicateSpinner.getSelectedItem().toString(), ocularEditText.getText().toString());

        TextView imageEditText = findViewById(R.id.imageEditText);
        Spinner imgLengthPredicateSpinner = findViewById(R.id.imgLengthpredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.IMAGE_LENGTH, imgLengthPredicateSpinner.getSelectedItem().toString(), imageEditText.getText().toString());

        TextView imageWidthEditText = findViewById(R.id.imageWidthEditText);
        Spinner imgWidthPredicateSpinner = findViewById(R.id.imgWidthpredicate);
        queryString = FormUtil.joinQuery(queryString, FormUtil.IMAGE_WIDTH, imgWidthPredicateSpinner.getSelectedItem().toString(), imageWidthEditText.getText().toString());

        return queryString;
    }

    private void setMyExifDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "exif_app_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        myExifDao = daoSession.getMyExifDao();
    }

    private void loadImages() {
        List<MyExif> myExifs = myExifDao.loadAll();
        for (MyExif myExif : myExifs) {
            Image image = new Image(myExif.getPathOfFile());
            image.setBitmap(bitmapConverter.getImageBitmap(myExif.getPathOfFile()));
            image.setExif(myExif);
            fileArray.add(image);
        }

    }

    private void loadFiltredImages(List<MyExif> myExifs) {
        for (MyExif myExif : myExifs) {
            Image image = new Image(myExif.getPathOfFile());
            image.setBitmap(bitmapConverter.getImageBitmap(myExif.getPathOfFile()));
            image.setExif(myExif);
            fileArray.add(image);
        }
        gridview.setAdapter(new ImageAdapter(this, fileArray.toArray(new Image[fileArray.size()])));
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

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public MyExifDao getMyExifDao() {
        return myExifDao;
    }


}
