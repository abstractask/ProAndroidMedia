package abstractask.example.media.ch01;

import abstractask.example.media.util.ClzUtil;
import abstractask.example.media.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.File;

public class SizedCameraIntent extends AppCompatActivity {

    final static int CAMERA_RESULT = 0;

    ImageView imv;
    String imageFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch01_main);

        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myfavoritepicture.jpg";
        File imageFile = new File(imageFilePath);
        Uri imageFileUri = Uri.fromFile(imageFile);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(i, CAMERA_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK) {

            imv = ClzUtil.getCast(findViewById(R.id.ReturnedImageView));
            DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
            int dw = disp.widthPixels;
            int dh = disp.heightPixels;

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;

            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) dw);

            if(heightRatio > 1 && widthRatio > 1) {
                bmpFactoryOptions.inSampleSize = (heightRatio > widthRatio ? heightRatio : widthRatio);
            }

            bmpFactoryOptions.inJustDecodeBounds = false;
            Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
            imv.setImageBitmap(bmp);

        }
    }
}
