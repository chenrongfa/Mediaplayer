package yy.chen.mediaplay.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class Video implements Parcelable{
   private String name;
    private  String time;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static Creator<Video> getCREATOR() {
        return CREATOR;
    }

    private String author;


    public Video() {
    }

    public Video(String name, String time, String size, String data,String author) {
        this.name = name;
        this.time = time;
        this.author=author;
        this.size = size;
        this.data = data;
    }

    private String size;
    private String data;

    protected Video(Parcel in) {
        name = in.readString();
        time = in.readString();
        size = in.readString();
        data = in.readString();
        author = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(size);
        dest.writeString(data);
        dest.writeString(author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +

                ", size='" + size + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }





    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
