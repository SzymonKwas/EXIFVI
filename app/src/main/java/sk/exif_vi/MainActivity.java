package sk.exif_vi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sk.exif_vi.Entity.DaoMaster;
import sk.exif_vi.Entity.DaoSession;
import sk.exif_vi.Entity.Make;
import sk.exif_vi.Entity.MakeDao;
import sk.exif_vi.Entity.Model;
import sk.exif_vi.Entity.ModelDao;
import sk.exif_vi.Entity.MyExif;
import sk.exif_vi.Entity.MyExifDao;
import sk.exif_vi.Image.BitmapConverter;
import sk.exif_vi.Image.Image;
import sk.exif_vi.Image.ImageAdapter;
import sk.exif_vi.Utils.FormUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    public ArrayList<Image> fileArray = new ArrayList<Image>();
    ArrayList<String> makeList = new ArrayList<String>();
    ArrayList<String> modelList = new ArrayList<String>();

    private BitmapConverter bitmapConverter = new BitmapConverter();
    private DaoSession daoSession;
    private MyExifDao myExifDao;
    private MakeDao makeDao;
    private ModelDao modelDao;


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
        setDao();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        while (!askPermission(REQUEST_ID_READ_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE))
            ;
        setContentView(R.layout.activity_main);

        Button filterButton = findViewById(R.id.buttonSearch);
        filterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                QueryBuilder<MyExif> query = myExifDao.queryBuilder();
                String queryString = getQueryStringFromForm();
                WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition(queryString);
                if (StringUtils.isNotEmpty(queryString)) {
                    query.where(stringCondition);
                }
                loadFiltredImages(query.build().list());
            }
        });

        Button resetButton = findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetForm();
            }
        });


        //set database

        //loadImagepaths(android.os.Environment.getExternalStorageDirectory());
        //loadImagepaths(new File (Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCMI/jpg"));
        //        loadImagepaths(new File("/storage/emulated/0/DCIM/jpg"));
        //loadImagepaths(new File("/Pamięć wewnętrzna/DCMI/jpg"));


        if (myExifDao.loadAll().size() == 0) {
            resetAll();
            loadImagepaths(new File("/storage/3334-AE98/DCIM/jpg"));
        } else {
            if (makeList.size() == 0 || modelList.size() == 0) {
                makeList.add("");
                modelList.add("");
                for (Model model : modelDao.loadAll()) {
                    modelList.add((model.getName()));
                }
                for (Make make : makeDao.loadAll()) {
                    makeList.add(make.getName());
                }
            }
        }

        setSpinners();
        loadImages();


        GridView gridview = (GridView) findViewById(R.id.imageView);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toImageFullActivity(view, fileArray.get(position).getPath());
            }
        });
        gridview.setAdapter(new ImageAdapter(this, fileArray.toArray(new Image[fileArray.size()])));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void loadImagepaths(File file) {

        if (file == null) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        }
        for (File f : file.listFiles()) {
            if (fileArray.size() > 300) {
                break;
            }
            if (f.isDirectory()) {
                if (f.getAbsolutePath().endsWith(".android_secure")) {
                    break;
                } else if (f.getAbsolutePath().contains("DCIM")) {
                    loadImagepaths(f);
                }
            } else {
                if (f.getAbsolutePath().endsWith(".jpg") ||
                        f.getAbsolutePath().endsWith(".jpeg")) {

                    try {
                        ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                        if (exifInterface != null) {
                            MyExif myExif = new MyExif(exifInterface, f.getAbsolutePath());
                            myExifDao.insert(myExif);
                            if (StringUtils.isNotEmpty(myExif.getMake()) && !makeList.contains(myExif.getMake())) {
                                makeDao.insert(new Make(myExif.getMake()));
                                makeList.add(myExif.getMake());
                            }
                            if (StringUtils.isNotEmpty(myExif.getModel()) && !modelList.contains(myExif.getModel())) {
                                modelDao.insert(new Model(myExif.getModel()));
                                modelList.add(myExif.getModel());
                            }

                        }

                    } catch (Exception e) {
                        Log.e("ERROR", "Could not load image path " + f.getAbsolutePath() + ", Message: " + e.getMessage());
                    }

                }
            }
        }
    }

    private void filterImages(View view) {
        myExifDao.deleteAll();
    }

    private void setSpinners() {
        Spinner modelSpinner = findViewById(R.id.modelPredicate);
        Spinner makeSpinner = findViewById(R.id.makePredicate);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, modelList);
        ArrayAdapter<String> makeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, makeList);

        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setSelection(0);

        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setSelection(0);
    }

    private void resetForm() {
        TextView datetime = findViewById(R.id.datetimeeditText);
        datetime.setText("");

        Spinner datetimePredicateSpinner = findViewById(R.id.datetimePredicate);
        datetimePredicateSpinner.setSelection(0);

        Spinner flashLengthPredicate = findViewById(R.id.flashLengthPredicate);
        flashLengthPredicate.setSelection(0);

        TextView objectiveEditText = findViewById(R.id.objectiveEditText);
        objectiveEditText.setText("");

        Spinner objectivePredicateSpinner = findViewById(R.id.objectivePredicate);
        objectivePredicateSpinner.setSelection(0);

        TextView ocularEditText = findViewById(R.id.ocularEditText);
        ocularEditText.setText("");

        Spinner ocularPredicateSpinner = findViewById(R.id.ocularPredicate);
        ocularPredicateSpinner.setSelection(0);

        TextView imageEditText = findViewById(R.id.imageLengthEditText);
        imageEditText.setText("");

        Spinner imgLengthPredicateSpinner = findViewById(R.id.imgLengthPredicate);
        imgLengthPredicateSpinner.setSelection(0);

        TextView imageWidthEditText = findViewById(R.id.imageWidthEditText);
        imageWidthEditText.setText("");

        Spinner imgWidthPredicateSpinner = findViewById(R.id.imgWidthPredicate);
        imgWidthPredicateSpinner.setSelection(0);

        Spinner whiteBalancePredicate = findViewById(R.id.whiteBalancePredicate);
        whiteBalancePredicate.setSelection(0);

        Spinner orientationPredicate = findViewById(R.id.orientationPredicate);
        orientationPredicate.setSelection(0);

        Spinner modelPredicate = findViewById(R.id.modelPredicate);
        modelPredicate.setSelection(0);

        Spinner makePredicate = findViewById(R.id.makePredicate);
        makePredicate.setSelection(0);

    }

    private String getQueryStringFromForm() {

        String queryString = "";

        TextView datetime = findViewById(R.id.datetimeeditText);
        Spinner datetimePredicateSpinner = findViewById(R.id.datetimePredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.DATETIME,
                FormUtil.getPredicationSign(datetimePredicateSpinner.getSelectedItem().toString()),
                datetime.getText().toString()
        );

        Spinner flashLengthPredicate = findViewById(R.id.flashLengthPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.FLASH,
                " = ",
                FormUtil.getSwitched(flashLengthPredicate.getSelectedItem())
        );

        TextView objectiveEditText = findViewById(R.id.objectiveEditText);
        Spinner objectivePredicateSpinner = findViewById(R.id.objectivePredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.FOCAL_LENGTH_OBJECTIVE,
                FormUtil.getPredicationSign(objectivePredicateSpinner.getSelectedItem().toString()),
                objectiveEditText.getText().toString()
        );

        TextView ocularEditText = findViewById(R.id.ocularEditText);
        Spinner ocularPredicateSpinner = findViewById(R.id.ocularPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.FOCAL_LENGTH_OCULAR,
                FormUtil.getPredicationSign(ocularPredicateSpinner.getSelectedItem().toString()),
                ocularEditText.getText().toString()
        );

        TextView imageEditText = findViewById(R.id.imageLengthEditText);
        Spinner imgLengthPredicateSpinner = findViewById(R.id.imgLengthPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.IMAGE_LENGTH,
                FormUtil.getPredicationSign(imgLengthPredicateSpinner.getSelectedItem().toString()),
                imageEditText.getText().toString()
        );

        TextView imageWidthEditText = findViewById(R.id.imageWidthEditText);
        Spinner imgWidthPredicateSpinner = findViewById(R.id.imgWidthPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.IMAGE_WIDTH,
                FormUtil.getPredicationSign(imgWidthPredicateSpinner.getSelectedItem().toString()),
                imageWidthEditText.getText().toString()
        );
        Spinner whiteBalancePredicate = findViewById(R.id.whiteBalancePredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.WHITE_BALANCE,
                " = ",
                FormUtil.getSwitched(whiteBalancePredicate.getSelectedItem())
        );
        Spinner orientationPredicate = findViewById(R.id.orientationPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.ORIENTATION,
                " = ",
                orientationPredicate.getSelectedItem().toString()
        );
        Spinner modelPredicate = findViewById(R.id.modelPredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.MODEL,
                " = ",
                FormUtil.getSqlStringQuery(modelPredicate.getSelectedItem().toString())
        );
        Spinner makePredicate = findViewById(R.id.makePredicate);
        queryString = FormUtil.joinQuery(
                queryString,
                FormUtil.MAKE,
                " = ",
                FormUtil.getSqlStringQuery(makePredicate.getSelectedItem().toString())
        );

        return queryString;
    }

    private void setDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "exif_app_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        myExifDao = daoSession.getMyExifDao();
        makeDao = daoSession.getMakeDao();
        modelDao = daoSession.getModelDao();
    }

    private void loadImages() {
        List<MyExif> myExifs = myExifDao.loadAll();
        for (MyExif myExif : myExifs) {
            Image image = new Image(myExif.getPathOfFile());
            image.setBitmap(bitmapConverter.getImageBitmap(myExif.getPathOfFile()));
            image.setExif(myExif);
            fileArray.add(image);
        }
        Toast.makeText(MainActivity.this, "Found " + fileArray.size() + "images!",
                Toast.LENGTH_LONG).show();
    }

    private void resetAll() {
        myExifDao.deleteAll();
        modelDao.deleteAll();
        makeDao.deleteAll();
        makeList.clear();
        fileArray.clear();
        modelList.clear();
        makeList.add("");
        modelList.add("");

    }

    private void loadFiltredImages(List<MyExif> myExifs) {
        GridView gridview = (GridView) findViewById(R.id.imageView);
        fileArray.clear();
        for (MyExif myExif : myExifs) {
            Image image = new Image(myExif.getPathOfFile());
            image.setBitmap(bitmapConverter.getImageBitmap(myExif.getPathOfFile()));
            image.setExif(myExif);
            fileArray.add(image);
        }
        AppBarLayout appbar = findViewById(R.id.appbar);
        appbar.setExpanded(false);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Toast.makeText(MainActivity.this, "Found " + fileArray.size() + "images!",
                Toast.LENGTH_LONG).show();
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

    private void resetAllImagesPath() {
        resetAll();
        resetForm();

        setSpinners();
        loadImages();

    }
}
