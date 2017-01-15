package yy.chen.mediaplay.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenrongfa on 2017/1/3
 */

public class NetVideo implements Parcelable {
 private    String movieName;
 private    String videoTitle;
   private  String hightUrl;
   private  String coverImg;

    protected NetVideo(Parcel in) {
        movieName = in.readString();
        videoTitle = in.readString();
        hightUrl = in.readString();
        coverImg = in.readString();
    }

    public static final Creator<NetVideo> CREATOR = new Creator<NetVideo>() {
        @Override
        public NetVideo createFromParcel(Parcel in) {
            return new NetVideo(in);
        }

        @Override
        public NetVideo[] newArray(int size) {
            return new NetVideo[size];
        }
    };

    @Override
    public String toString() {
        return "NetVideo{" +
                "moveName='" + movieName + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", hightUrl='" + hightUrl + '\'' +
                ", coverImg='" + coverImg + '\'' +
                '}';
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getHightUrl() {
        return hightUrl;
    }

    public void setHightUrl(String hightUrl) {
        this.hightUrl = hightUrl;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public NetVideo(String movieName, String videoTitle, String hightUrl, String
            coverImg) {

        this.movieName = movieName;
        this.videoTitle = videoTitle;
        this.hightUrl = hightUrl;
        this.coverImg = coverImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(videoTitle);
        dest.writeString(hightUrl);
        dest.writeString(coverImg);
    }
}
