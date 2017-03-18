package yy.chen.mediaplay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
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

public class NetMessage extends BaseAdapter {
      //类型
      private final int text = 0;//"text";
      private final int video1 = 1;//"video";
      private final int gif = 2;//"gif";
      private final int image = 3;//"image";
      private List<BaiSiBudeQiJie.ListBean> videos;
      private Context context;
      private static final String TAG = "NetMessage";

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
            String category = videos.get(position).getType();
            int num = 0;
            if ("text".equals(category)) {
                  num = text;
            } else if ("gif".equals(category)) {
                  num = gif;
            } else if ("image".equals(category)) {
                  num = image;
            } else if ("video".equals(category)) {
                  num = video1;
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
            final ViewHolder viewHolder;
            int categoty = getItemViewType(position);
            if (convertView == null) {
                  convertView = View.inflate(context, R.layout.netmessageitem, null);
                  viewHolder = new ViewHolder();
                  viewHolder.userHeader = (ImageView) convertView.findViewById(R.id.iv_userheader);
                  viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_username);
                  viewHolder.message = (TextView) convertView.findViewById(R.id.tv_message);
                  viewHolder.userPassTime = (TextView) convertView.findViewById(R.id.tv_usertime);
                  viewHolder.showMes = (ImageView) convertView.findViewById(R.id.iv_showmes);
                  viewHolder.btn_clickUp = (CheckBox) convertView.findViewById(R.id.btn_clickup);
                  viewHolder.btn_clickDown = (CheckBox) convertView.findViewById(R.id.btn_clickdown);
                  viewHolder.btn_clickShare = (CheckBox) convertView.findViewById(R.id.btn_clickshare);
                  viewHolder.btn_clickDiscuss = (CheckBox) convertView.findViewById(R.id.btn_clickdiscuss);
                  viewHolder.jcVideoPlayer = (JCVideoPlayer) convertView.findViewById(R.id.jc_player);
                  viewHolder.sv_images= (ScrollView) convertView.findViewById(R.id.sv_images);
                  convertView.setTag(viewHolder);
                  viewHolder.btn_clickDown.setTag(null);

            } else {
                  viewHolder = (ViewHolder) convertView.getTag();
            }
            final BaiSiBudeQiJie.ListBean listBean = videos.get(position);
            if (categoty == image) {
                  viewHolder.sv_images.setVisibility(View.VISIBLE);
                  if (listBean.getImage().getThumbnail_small().get(0) != null) {
                        Log.e(TAG, "getView: "+listBean.getImage().getThumbnail_small().get(0) );
                        Glide.with(context).load(listBean.getImage().getThumbnail_small().get(0)).
                                error(R.drawable.login_icon).
                                diskCacheStrategy(DiskCacheStrategy.ALL).
                                placeholder(R.drawable.boy).into(viewHolder.showMes);
//                     new Thread(new Runnable() {
//                           @Override
//                           public void run() {
//                                 URL url= null;
//                                 try {
//                                       url = new URL(listBean.getImage().getThumbnail_small().get(0));
//                                       HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
//                                       httpURLConnection.setConnectTimeout(5000);
//                                       httpURLConnection.setDoInput(true);
//                                       int responseCode = httpURLConnection.getResponseCode();
//                                       if(responseCode==200){
//                                             InputStream inputStream = httpURLConnection.getInputStream();
////                                             Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
////                                             Log.e(TAG, "run: 1" );
////                                             Drawable drawable = new BitmapDrawable(context.getResources(), inputStream);
////                                             viewHolder.showMes.setBackground(drawable);
//                                             final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
////                                                      viewHolder.showMes.setImageDrawable(drawable);
//                                             inputStream.close();
//                                             viewHolder.showMes.post(new Runnable() {
//                                                   @Override
//                                                   public void run() {
//                                                         viewHolder.showMes.setImageBitmap(bitmap);
//                                                   }
//                                             });
//
//
//                                       }
//
//                                 } catch (Exception e) {
//                                       e.printStackTrace();
//                                 }
//
//
//                           }
//                     }).start();
                  } else {
                        Glide.with(context).
                                load(listBean.getImage().getThumbnail_small().get(1)).
                                error(R.drawable.login_icon).
                                diskCacheStrategy(DiskCacheStrategy.ALL).
                                placeholder(R.drawable.boy).
                                into(viewHolder.showMes);
                  }
            } else if (categoty == gif) {
                  viewHolder.sv_images.setVisibility(View.VISIBLE);
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
                  viewHolder.sv_images.setVisibility(View.GONE);
                  viewHolder.jcVideoPlayer.setVisibility(View.VISIBLE);
                  if (listBean.getVideo().getVideo().get(0) != null) {
                        viewHolder.jcVideoPlayer.setUp(listBean.getVideo().getVideo().get(0), listBean.getVideo().getThumbnail().get(0),
                                "陈蓉发");
//                viewHolder.jcVideoPlayer.set


                  } else {
                        viewHolder.jcVideoPlayer.setUp(listBean.getVideo().getVideo().get(0), listBean.getVideo().getThumbnail().get(0),
                                "陈蓉发");

                  }

            } else if (categoty == text) {
                  viewHolder.sv_images.setVisibility(View.GONE);
                  viewHolder.message.setText(listBean.getText());
            }
            commonFeature(viewHolder, listBean);
            clickEvent(viewHolder, listBean);

            return convertView;
      }

      private void clickEvent(final ViewHolder viewHolder, final BaiSiBudeQiJie.ListBean listBean) {

            viewHolder.btn_clickUp.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        int num = Integer.parseInt(listBean.getUp());
                        if (viewHolder.btn_clickUp.isChecked()) {
                              num++;
                              viewHolder.btn_clickUp.setText(num + "");
                              viewHolder.isClickUped = true;
                        } else {
                              num--;
                              viewHolder.btn_clickUp.setText(num + "");
                              viewHolder.isClickUped = false;
                        }
                        listBean.setUp(num + "");
                        listBean.setUp(num + "");


                  }
            });
            viewHolder.btn_clickDown.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        int num = listBean.getDown();
                        if (!viewHolder.btn_clickDown.isChecked()) {
                              num++;
                              viewHolder.btn_clickDown.setHovered(true);
                              viewHolder.isClickDowned = true;
                        } else {
                              num--;
                              viewHolder.btn_clickDown.setHovered(false);
                              viewHolder.isClickDowned = false;
                        }
                        viewHolder.btn_clickDown.setText(num + "");
                        listBean.setDown(num);

                  }
            });
            viewHolder.btn_clickShare.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        int num = listBean.getForward();
                        if (!viewHolder.btn_clickShare.isChecked()) {
                              num++;
                              viewHolder.btn_clickShare.setPressed(true);
                              viewHolder.isClickShared = true;
                        } else {
                              num--;
                              viewHolder.btn_clickShare.setPressed(false);
                              viewHolder.isClickShared = false;
                        }
                        viewHolder.btn_clickShare.setText(num + "");
                        listBean.setForward(num);

                  }
            });
            viewHolder.btn_clickDiscuss.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        int num = Integer.parseInt(listBean.getComment());
                        if (!viewHolder.btn_clickDiscuss.isChecked()) {
                              num++;
                              viewHolder.btn_clickDiscuss.setHovered(true);
                              viewHolder.isClickDiscussed = true;
                        } else {
                              num--;
                              viewHolder.btn_clickDiscuss.setHovered(false);
                              viewHolder.isClickDiscussed = false;
                        }
                        viewHolder.btn_clickDiscuss.setText(num + "");
                        listBean.setComment(num + "");


                  }
            });
      }

      private void showTip(String s) {
            Toast.makeText(context, "" + s, Toast.LENGTH_SHORT).show();
      }

      private void commonFeature(ViewHolder viewHolder, BaiSiBudeQiJie.ListBean listBean) {
            viewHolder.userHeader.setVisibility(View.VISIBLE);
            Glide.with(context).load(listBean.getU().getHeader().get(0)).error(R.drawable.login_icon).diskCacheStrategy(DiskCacheStrategy
                    .ALL).placeholder(R.drawable.login_icon).into(viewHolder.userHeader);
            viewHolder.userName.setText(listBean.getU().getName());
            viewHolder.userPassTime.setText(listBean.getPasstime());
            viewHolder.userPassTime.setText(listBean.getPasstime());
            viewHolder.message.setText(listBean.getText());
            viewHolder.btn_clickUp.setText(listBean.getUp() + "");
            viewHolder.btn_clickDown.setText(listBean.getDown() + "");
            viewHolder.btn_clickShare.setText(listBean.getForward() + "");
            viewHolder.btn_clickDiscuss.setText(listBean.getComment() + "");
//            viewHolder.isClickUped = false;
//            viewHolder.isClickDowned = false;
//            viewHolder.isClickShared = false;
//            viewHolder.isClickDiscussed = false;
      }


      static class ViewHolder {
            public ImageView userHeader;
            public ImageView showMes;
            public ScrollView sv_images;
            public TextView userName;
            public TextView userPassTime;
            public CheckBox btn_clickUp;
            public CheckBox btn_clickDown;
            public CheckBox btn_clickShare;
            public CheckBox btn_clickDiscuss;
            public JCVideoPlayer jcVideoPlayer;
            public boolean isClickUped;
            public boolean isClickDowned;
            public boolean isClickShared;
            public boolean isClickDiscussed;
            public TextView message;
      }
}
