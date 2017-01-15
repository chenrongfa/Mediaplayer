package yy.chen.mediaplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import yy.chen.mediaplay.R;
import yy.chen.mediaplay.bean.BaiSiBudeQiJie;

/**
 * Created by chenrongfa on 2016/12/27
 */

public class NetMessage extends BaseAdapter  {
   private List<BaiSiBudeQiJie.ListBean> videos;
    private Context context;
    //类型
   private final  int text=0;//"text";
    private final  int  video1=1 ;//"video";
    private final  int gif=2;//"gif";
    private final  int  image=3;//"image";

    public NetMessage(List<BaiSiBudeQiJie.ListBean> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        String category=videos.get(position).getType();
        int num=0;
        if("text".equals(category)){
            num=text;
        }else if("gif".equals(category)){
            num= gif;
        }else if("image".equals(category)){
            num=image;
        }else if("video".equals(category)){
            num=video1;
        }

        return num;
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
        int categoty = getItemViewType(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.netmessageitem, null);
            viewHolder = new ViewHolder();
            viewHolder.userHeader = (ImageView) convertView.findViewById(R.id.iv_userheader);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.message = (TextView) convertView.findViewById(R.id.tv_message);
            viewHolder.userPassTime = (TextView) convertView.findViewById(R.id.tv_usertime);
            viewHolder.showMes = (ImageView) convertView.findViewById(R.id.iv_showmes);
            viewHolder.btn_clickUp = (TextView) convertView.findViewById(R.id.btn_clickup);
            viewHolder.btn_clickDown = (TextView) convertView.findViewById(R.id.btn_clickdown);
            viewHolder.btn_clickShare = (TextView) convertView.findViewById(R.id.btn_clickshare);
            viewHolder.btn_clickDiscuss = (TextView) convertView.findViewById(R.id.btn_clickdiscuss);
            viewHolder.jcVideoPlayer = (JCVideoPlayer) convertView.findViewById(R.id.jc_player);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BaiSiBudeQiJie.ListBean listBean = videos.get(position);
       if(categoty==image){
                       viewHolder.showMes.setVisibility(View.VISIBLE);
            if (listBean.getImage().getThumbnail_small().get(0) != null) {
                Glide.with(context).load(listBean.getImage().getThumbnail_small().get(0)).error(R.drawable.login_icon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.boy).into(viewHolder.showMes);

            } else {
                Glide.with(context).load(listBean.getImage().getThumbnail_small().get(1)).error(R.drawable.login_icon).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.boy).into(viewHolder.showMes);
            }
       }
        else if (categoty == gif) {
            viewHolder.showMes.setVisibility(View.VISIBLE);
            if (listBean.getGif().getImages().get(0) != null) {

                Glide.with(context).load(listBean.getGif().getImages().get(0)).
                        centerCrop().
                        placeholder(R.drawable.boy).crossFade().into(viewHolder.showMes);
            } else {
                Glide.with(context).load(listBean.getGif().getImages().get(1)).
                        centerCrop().
                        placeholder(R.drawable.boy).crossFade().into(viewHolder.showMes);
            }

        } else if (categoty == video1) {
            viewHolder.showMes.setVisibility(View.GONE);
            viewHolder.jcVideoPlayer.setVisibility(View.VISIBLE);
            if (listBean.getVideo().getVideo().get(0) != null) {
                viewHolder.jcVideoPlayer.setUp(listBean.getVideo().getVideo().get(0),
                        listBean.getVideo().getThumbnail().get(0), "陈蓉发");
//                viewHolder.jcVideoPlayer.set


            } else {
                viewHolder.jcVideoPlayer.setUp(listBean.getVideo().getVideo().get(0),  listBean.getVideo().getThumbnail().get(0), "陈蓉发");

            }

        } else if (categoty == text) {
            viewHolder.showMes.setVisibility(View.GONE);
            viewHolder.message.setText(listBean.getText());
        }
        //点击事件
//           convertView.setTag(1,viewHolder.btn_clickUp);
//            convertView.setTag(2,viewHolder.isClickUped);
           clickEvent(viewHolder);
            commonFeature(viewHolder, listBean);
            return convertView;
        }

    private void clickEvent(final ViewHolder viewHolder) {
//        final boolean isup= (boolean) viewHolder.getTag(2);
//        Button up= (Button) viewHolder.getTag(1);
        viewHolder.btn_clickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.isClickUped){
                    showTip( viewHolder.btn_clickUp.getText().toString()+viewHolder.btn_clickUp);
                    int num= Integer.parseInt(viewHolder.btn_clickUp.getText()
                            .toString()
                    )+1;
                    viewHolder.btn_clickUp.setText(num+"");
                    viewHolder.btn_clickUp.setPressed(true);
//                    viewHolder.btn_clickUp.setCompoundDrawables(new dr,
//                            null,null,null);
                    viewHolder.isClickUped=true;
                }else{
                    int num= Integer.parseInt(viewHolder.btn_clickUp.getText()
                            .toString()
                    )-1;
                    viewHolder.btn_clickUp.setText(num+"");
//                    viewHolder.btn_clickUp.setBackgroundResource(R.drawable.tv_clickup_selector);
                    viewHolder.btn_clickUp.setPressed(false);
                    viewHolder.isClickUped=false;
                }
                ;
            }
        })
        ;
        viewHolder.btn_clickDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.isClickDowned){
                    int num= Integer.parseInt(viewHolder.btn_clickDown.getText()
                            .toString()
                    )+1;
                    viewHolder.btn_clickDown.setText(num+"");
                    viewHolder.btn_clickDown.setPressed(true);
                    viewHolder.isClickDowned=true;
                }else{
                    int num= Integer.parseInt(viewHolder.btn_clickDown.getText()
                            .toString()
                    )-1;
                    viewHolder.btn_clickDown.setText(num+"");
                    viewHolder.btn_clickDown.setPressed(false);
                    viewHolder.isClickDowned=false;
                }
                showTip(viewHolder.btn_clickDown.getText().toString());
            }
        });
        viewHolder.btn_clickShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.isClickShared){
                   int num= Integer.parseInt(viewHolder.btn_clickShare.getText()
                           .toString()
                    )+1;
                    viewHolder.btn_clickShare.setText(num+"");
                    viewHolder.btn_clickShare.setPressed(true);
                    viewHolder.isClickShared=true;
                }else{
                    int num= Integer.parseInt(viewHolder.btn_clickShare.getText()
                            .toString()
                    )-1;
                    viewHolder.btn_clickShare.setText(num+"");
                    viewHolder.btn_clickShare.setPressed(false);
                    viewHolder.isClickShared=false;
                }
                showTip(viewHolder.btn_clickShare.getText().toString());
            }
        });
        viewHolder.btn_clickDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.isClickDiscussed){
                    int num= Integer.parseInt(viewHolder.btn_clickDiscuss.getText()
                            .toString()
                    )+1;
                    viewHolder.btn_clickDiscuss.setText(num+"");
                    viewHolder.btn_clickDiscuss.setPressed(true);
                    viewHolder.isClickDiscussed=true;
                }else{
                    int num= Integer.parseInt(viewHolder.btn_clickDiscuss.getText()
                            .toString()
                    )-1;
                    viewHolder.btn_clickDiscuss.setText(num+"");
                    viewHolder.btn_clickDiscuss.setPressed(false);
                    viewHolder.isClickDiscussed=false;
                }
                showTip(viewHolder.btn_clickDiscuss.getText().toString());

            }
        });
    }

    private void showTip(String s) {
        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
    }

    private void commonFeature(ViewHolder viewHolder, BaiSiBudeQiJie.ListBean listBean) {
        viewHolder.userHeader.setVisibility(View.VISIBLE);
        Glide.with(context).load(listBean.getU().getHeader().get(0))
                .error(R.drawable.login_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.login_icon)
                .into(viewHolder.userHeader);
        viewHolder.userName.setText(listBean.getU().getName());
        viewHolder.userPassTime.setText(listBean.getPasstime());
        viewHolder.userPassTime.setText(listBean.getPasstime());
        viewHolder.message.setText(listBean.getText());
        viewHolder.btn_clickUp.setText(listBean.getUp()+"");
        viewHolder.btn_clickDown.setText(listBean.getDown()+"");
        viewHolder.btn_clickShare.setText(listBean.getForward()+"");
        viewHolder.btn_clickDiscuss.setText(listBean.getComment()+"");
        viewHolder.isClickUped=false;
        viewHolder.isClickDowned=false;
        viewHolder.isClickShared=false;
        viewHolder.isClickDiscussed=false;
    }




    static class ViewHolder{
        public ImageView userHeader;
        public ImageView showMes;
        public TextView userName;
        public  TextView userPassTime;
        public  TextView btn_clickUp;
        public TextView btn_clickDown;
        public TextView btn_clickShare;
        public TextView btn_clickDiscuss;
        public JCVideoPlayer jcVideoPlayer;
        public boolean isClickUped;
        public boolean isClickDowned;
        public boolean isClickShared;
        public boolean isClickDiscussed;
        public  TextView message;
    }
}
