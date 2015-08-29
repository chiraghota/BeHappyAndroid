package vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prateek on 29/08/15.
 */
public class FeedVO implements Parcelable {

    public String id;
    public String image_url;
    public String likes;
    public List<CommentVO> comments;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(image_url);
        out.writeString(likes);
        out.writeTypedList(comments);
    }

    public FeedVO(){

    }

    public FeedVO(Parcel in){
        id = in.readString();
        image_url = in.readString();
        likes = in.readString();
        if(comments == null){
            comments = new ArrayList<CommentVO>();
        }
        in.readTypedList(comments, CommentVO.CREATOR);
    }

    public static final Parcelable.Creator<FeedVO> CREATOR = new Creator<FeedVO>() {
        @Override
        public FeedVO createFromParcel(Parcel parcel) {
            return new FeedVO(parcel);
        }

        @Override
        public FeedVO[] newArray(int size) {
            return new FeedVO[size];
        }
    };
}
