// IAudioService.aidl
package yy.chen.mediaplay;

import yy.chen.mediaplay.bean.Video;
// Declare any non-default types here with import statements

interface IAudioService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
              void stop();
             void start();
               void pause();
              void release();
             void setPlayMode( int playMode);
               int getPlayMode ();
            void next();
            void openAudio(int position);
             /**
                 * 设置播放位置
                 * @param position
                 */
               void seekTo(int position);

                /**
                 *
                 * @return 反回播放最大值
                 */
                int getMaxDuretion();
                 /**
                     * 播放位置
                     * @return
                     */
                     int getCurrPosition();
                     boolean isPlaying();


                 //列表位置
                     int getPosition();
                     //设置位置
                     void setPosition(int position);
                     //列表大小
                     int getListSize();
                    //标识是否可以放下一个
                   boolean isCanNext();
                   //标识是否可以放下一个
                  void setCanNext(boolean can);
                   //反回当前播放一个对象
                   Video getVideo();


         int getAudioSessionId();
}