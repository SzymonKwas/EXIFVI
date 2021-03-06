package sk.exif_vi;

import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import sk.exif_vi.Image.BitmapConverter;
import sk.exif_vi.Entity.MyExif;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ImageFull extends AppCompatActivity {

    private BitmapConverter bitmapConverter = new BitmapConverter();
    private ImageView image;
    private TextView exifText;
    private Button returnBtn;
    private Button editBtn;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_image_full);
        exifText = findViewById(R.id.exifText);
        image = findViewById(R.id.imageViewFull);

        returnBtn = findViewById(R.id.returnEditBtn);
        returnBtn.setVisibility(INVISIBLE);
        editBtn = findViewById(R.id.editBtn);
        editBtn.setVisibility(INVISIBLE);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEditActivity(v);
            }
        });

        try {
            path = intent.getStringExtra("filePath");
            loadImage(path);
        } catch (IOException e) {
            Log.e("ERROR", "Could not load file " + path);
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            intentMain.putExtra("error", e.getMessage());
            intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentMain);
        }
    }

    private void loadImage(String path) throws IOException {

        image.setImageBitmap(bitmapConverter.getImageOriginalBitmap(path));
        ExifInterface exifInterface = new ExifInterface(path);
        MyExif exif = new MyExif(exifInterface,path);

        exifText.setText(exif.toString());
        exifText.setVisibility(INVISIBLE);
    }

    public void showExifInfo(View view) {
        int visibility = exifText.getVisibility() == VISIBLE ? INVISIBLE : VISIBLE;
        exifText.setVisibility(visibility);
        visibility = exifText.getVisibility() == VISIBLE ? VISIBLE : INVISIBLE;
        returnBtn.setVisibility(visibility);
        editBtn.setVisibility(visibility);
    }

    public void returnToMainActivity (View view){
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentMain);
    }

    public void toEditActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("filePath", path);
        startActivity(intent);
    }
}
