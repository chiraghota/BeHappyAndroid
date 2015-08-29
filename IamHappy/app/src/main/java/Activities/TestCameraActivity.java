package activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import iamhappy.in.iamhappy.R;

/**
 * Created by Prateek on 30/08/15.
 */
public class TestCameraActivity extends AppCompatActivity {

    private static final String TAG = "TestCameraActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
    Button button;
    ImageView mImageView;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);
        mImageView = (ImageView) findViewById(R.id.image_capture);
        button = (Button) findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    private void capturePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File pictureFile = getNextPictureFileName();
            if (pictureFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "Error while capturing picture", Toast.LENGTH_SHORT).show();
            }


        }
    }
    private File getNextPictureFileName() {
        String timeStampStr = timeStamp.format(new Date());
        String imageFileName = "jpeg_" + timeStampStr;
        File storageDir = new File(Environment.getExternalStorageDirectory(), "hello");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            mCurrentPhotoPath = imageFile.getAbsolutePath();
            Log.d(TAG, "PhotoPath :" + mCurrentPhotoPath);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
