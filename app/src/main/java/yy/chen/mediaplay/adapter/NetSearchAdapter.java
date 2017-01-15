package yy.chen.mediaplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.SearchTitle;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class NetSearchAdapter extends BaseAdapter {
   private List<SearchTitle.ItemsBean> trailer;
    private Context context;



    public NetSearchAdapter(List<SearchTitle.ItemsBean> trailer, Context context) {
        this.trailer = trailer;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trailer.size();
    }

    @Override
    public Object getItem(int position) {
        return trailer.get(position);
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
        SearchTitle.ItemsBean video=trailer.get(position);
//        x.image().bind(viewHolder.iv_icon,video.getCoverImg());
        viewHolder.tv_name.setText(video.getItemTitle());
        viewHolder.tv_title.setText(video.getKeywords());
        //glide
       /* Glide.with(context).load(video.getCoverImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.video_default)
                .placeholder(R.drawable.video_default)
                .override(195,136)
                .into(viewHolder.iv_icon);*/
        Picasso.with(context).load(video.getItemImage().getImgUrl1())
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
