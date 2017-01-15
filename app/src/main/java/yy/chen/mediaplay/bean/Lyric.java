package yy.chen.mediaplay.bean;

/**
 * Created by chenrongfa on 2017/1/5
 */

public class Lyric {
    private int time;
    private String stTime;
    private String content;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Lyric() {
    }

    public Lyric(int time, String stTime, String content) {
        this.time = time;
        this.stTime = stTime;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "time=" + time +
                ", stTime='" + stTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
