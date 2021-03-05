package abstractask.example.media.ch01;

import abstractask.example.media.R;
import abstractask.example.media.util.ClzUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;


public class CameraIntent extends AppCompatActivity {

    final static int CAMERA_RESULT = 0;

    ImageView imv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch01_main);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAMERA_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bmp = ClzUtil.getCast(extras.get("data"));
            imv = ClzUtil.getCast(findViewById(R.id.ReturnedImageView));
            imv.setImageBitmap(bmp);
        }
    }
}
