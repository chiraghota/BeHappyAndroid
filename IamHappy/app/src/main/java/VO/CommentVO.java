package VO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prateek on 29/08/15.
 */
public class CommentVO implements Parcelable{

    public String id;
    public String text;
    public String feed_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(text);
        parcel.writeString(feed_id);
    }

    public CommentVO(){

    }

    public CommentVO(Parcel parcel){
        id = parcel.readString();
        text = parcel.readString();
        feed_id = parcel.readString();
    }

    public static final Parcelable.Creator<CommentVO> CREATOR = new Creator<CommentVO>() {
        @Override
        public CommentVO createFromParcel(Parcel parcel) {
            return new CommentVO(parcel);
        }

        @Override
        public CommentVO[] newArray(int size) {
            return new CommentVO[size];
        }
    };
}

