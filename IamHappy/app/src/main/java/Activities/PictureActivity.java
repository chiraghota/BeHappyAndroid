package activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.lang.ref.WeakReference;

import iamhappy.in.iamhappy.R;
import interfaces.OnImageLoadListener;
import util.AppUtil;
import util.ConstantUtils;

/**
 * Created by Prateek on 29/08/15.
 */
public class PictureActivity extends AppCompatActivity implements OnImageLoadListener {

    private static final String TAG = "PictureActivity";
    private ImageView image;
    private TextView textSmileIndex;
    private View progressBar;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        image = (ImageView) findViewById(R.id.main_image);
        textSmileIndex = (TextView) findViewById(R.id.text_smile_index);
        progressBar = findViewById(R.id.progressLayout);
        progressBar.setVisibility(View.GONE);
        imagePath = getIntent().getStringExtra(ConstantUtils.IKEY_IMAGE_PATH);
        new ImageDecoderTask().setImageLoadListener(this).execute(new String[]{imagePath});
    }

    @Override
    public void beforeImageLoad() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterImageLoad(Bitmap bitmap) {
        if (bitmap != null) {
            int smileIndex = calculateSmileIndex(bitmap);

            image.setImageBitmap(bitmap);
            textSmileIndex.setText(smileIndex + " ");
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private int calculateSmileIndex(Bitmap bitmap) {

        float smileIndex = 0.0F;
        FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        if (faces != null && faces.size() > 0) {
            for (int i = 0; i < faces.size(); i++) {
                if (faces.get(i) != null) {
                    smileIndex = faces.get(i).getIsSmilingProbability();
                }
            }
            return (int) (smileIndex * 10);
        } else {
            AppUtil.showToast(this, "Say cheese again!");
        }
        return 0;
    }

    @Override
    public int getImageMaxWidth() {
        return 100;
    }

    @Override
    public int getImageMaxHeight() {
        return 200;
    }

    private static class ImageDecoderTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<OnImageLoadListener> onImageLoadListener;

        public ImageDecoderTask setImageLoadListener(OnImageLoadListener onImageLoadListener) {
            this.onImageLoadListener = new WeakReference<OnImageLoadListener>(onImageLoadListener);
            return this;
        }

        @Override
        protected void onPreExecute() {
            if (onImageLoadListener.get() != null) {
                onImageLoadListener.get().beforeImageLoad();
            }
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                if (onImageLoadListener.get() != null) {
                    int targetW = onImageLoadListener.get().getImageMaxWidth();
                    int targetH = onImageLoadListener.get().getImageMaxHeight();
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(strings[0], bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;
                    int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;

                    Bitmap bitmap = BitmapFactory.decodeFile(strings[0], bmOptions);
                    return bitmap;
                }
            } catch (Exception e) {
                Log.e(TAG,"Exception caught : "+e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (onImageLoadListener.get() != null) {
                onImageLoadListener.get().afterImageLoad(bitmap);
            }
        }
    }
}
