package yy.chen.mediaplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.NetVideo;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class NetVideoAdapter extends BaseAdapter {
   private ArrayList<NetVideo> videos;
    private Context context;

    public NetVideoAdapter(ArrayList<NetVideo> videos, Context context) {
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
            convertView=View.inflate(context, R.layout.netvideoitem,null);
            viewHolder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        NetVideo video=videos.get(position);
//        x.image().bind(viewHolder.iv_icon,video.getCoverImg());
        viewHolder.tv_name.setText(video.getMovieName());
        viewHolder.tv_title.setText(video.getVideoTitle());
        //glide
       /* Glide.with(context).load(video.getCoverImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.video_default)
                .placeholder(R.drawable.video_default)
                .override(195,136)
                .into(viewHolder.iv_icon);*/
        Picasso.with(context).load(video.getCoverImg())
                .error(R.drawable.video_default)
                .placeholder(R.drawable.video_default)
//                .resize(195,136)
                .into(viewHolder.iv_icon);

        return convertView;
    }
    class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_title;

    }
}
