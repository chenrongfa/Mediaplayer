package yy.chen.mediaplay.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.Video;
import yy.chen.mediaplay.util.TimeUtil;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class VideoAdapter extends BaseAdapter {
   private List<Video> videos;
    private Context context;

    public VideoAdapter(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.videoitem,null);
            viewHolder.im_icon= (ImageView) convertView.findViewById(R.id.iv_video);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size= (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Video video=videos.get(position);
        viewHolder.im_icon.setImageResource(R.drawable.video_default_icon);
        viewHolder.tv_name.setText(video.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,Long.parseLong
                (video.getSize()))
                );
        viewHolder.tv_time.setText(TimeUtil.TimeToString(Integer.parseInt(video.getTime()
        )));
        return convertView;
    }
    class ViewHolder{
        ImageView im_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}
