package vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tech on 29/08/2015.
 */
public class PhotosVO implements Parcelable{

    public String id;
    public String source;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(source);
    }

    public PhotosVO(){

    }

    public PhotosVO(Parcel parcel){
        id = parcel.readString();
        source = parcel.readString();
    }

    public static final Parcelable.Creator<PhotosVO> CREATOR = new Parcelable.Creator<PhotosVO>() {
        @Override
        public PhotosVO createFromParcel(Parcel parcel) {
            return new PhotosVO(parcel);
        }

        @Override
        public PhotosVO[] newArray(int size) {
            return new PhotosVO[size];
        }
    };
}
