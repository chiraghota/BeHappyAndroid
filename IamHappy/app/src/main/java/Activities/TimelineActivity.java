package activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import VO.FeedVO;
import iamhappy.in.iamhappy.R;
import util.AppUtil;
import util.ConstantUtils;

/**
 * Created by Prateek on 29/08/15.
 */
public class TimelineActivity extends AppCompatActivity {

    private static final String TAG = "TimeLineActivity";
    static final int REQUEST_TAKE_PHOTO = 1;
    private ListView feedListView;
    private List<FeedVO> feedsList;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setTitle("Timeline");
        feedListView = (ListView) findViewById(R.id.timeline_list);
        feedsList = getIntent().getParcelableArrayListExtra(ConstantUtils.IKEY_FEED_LIST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_camera:
                dispatchTakePictureIntent();
                return true;
            case R.id.menu_action_upload:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.d(TAG, "currentPhotoPath : " + mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_TAKE_PHOTO :
                if(resultCode == RESULT_OK){
                    galleryAddPic();
                    Intent in = new Intent(this,PictureActivity.class);
                    Log.d(TAG, "currentPhotoPath : " + mCurrentPhotoPath);
                    in.putExtra(ConstantUtils.IKEY_IMAGE_PATH, mCurrentPhotoPath);
                    startActivity(in);
                }else{
                    AppUtil.showToast(this, "Error while capturing photo");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Log.d(TAG, "currentPhotoPath : " + mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
