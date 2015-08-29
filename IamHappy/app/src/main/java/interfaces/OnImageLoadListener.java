package interfaces;

import android.graphics.Bitmap;

/**
 * Created by Prateek on 30/08/15.
 */
public interface OnImageLoadListener {

    void beforeImageLoad();
    void afterImageLoad(Bitmap bitmap);
    int getImageMaxWidth();
    int getImageMaxHeight();
}
