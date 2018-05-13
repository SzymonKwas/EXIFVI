package sk.exif_vi;

import android.content.Intent;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

import sk.exif_vi.Entity.MyExif;
import sk.exif_vi.Utils.FormUtil;

public class EditActivity extends AppCompatActivity {

    private String path;
    private ExifInterface exifInterface;
    private MyExif exif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        try {
            path = intent.getStringExtra("filePath");
            exifInterface = new ExifInterface(path);
            exif = new MyExif(exifInterface, path);

        } catch (IOException e) {
            Log.e("ERROR", "Could not load file " + path);
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            intentMain.putExtra("error", e.getMessage());
            intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentMain);
        }

        getImageExifFields();

        Button returnBtn = findViewById(R.id.returnEditBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToImageFull();
            }
        });

        Button acceptBtn = findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editImage();
                returnToImageFull();
            }
        });
    }

    private void returnToImageFull() {
        Intent intent = new Intent(getApplicationContext(), ImageFull.class);
        intent.putExtra("filePath", path);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void editImage() {

    }

    private void getImageExifFields() {
        Adapter adapter;

        Spinner flashLengthSetPredicate = findViewById(R.id.flashLengthSetPredicate);
        adapter = flashLengthSetPredicate.getAdapter();
        flashLengthSetPredicate.setSelection(FormUtil.getPositionFromSpinner(adapter, String.valueOf(exif.getFlash())));

        EditText objectiveEditText = findViewById(R.id.objectiveEditText);
        objectiveEditText.setText(FormUtil.getExifValue(exif.getFocalLengthObjective()));


        EditText ocularEditText = findViewById(R.id.ocularEditText);
        ocularEditText.setText(FormUtil.getExifValue(exif.getFocalLengthOcular()));


        EditText imageLengthEditText = findViewById(R.id.imageLengthEditText);
        imageLengthEditText.setText(FormUtil.getExifValue(exif.getImageLength()));


        EditText imageWidthEditText = findViewById(R.id.imageWidthEditText);
        imageWidthEditText.setText(FormUtil.getExifValue(exif.getImageWidth()));

        Spinner whiteBalanceSetPredicate = findViewById(R.id.whiteBalanceSetPredicate);
        whiteBalanceSetPredicate.setSelection(FormUtil.getPositionFromSpinner(adapter, FormUtil.getExifValue(exif.getWhiteBalance())));

        Spinner orientationSetPredicate = findViewById(R.id.orientationSetPredicate);
        orientationSetPredicate.setSelection(FormUtil.getPositionFromSpinner(adapter, FormUtil.getExifValue(exif.getOrientation())));

        EditText modelText = findViewById(R.id.modelText);
        modelText.setText(exif.getModel());

        EditText makeText = findViewById(R.id.makeText);
        makeText.setText(exif.getMake());


    }
}
