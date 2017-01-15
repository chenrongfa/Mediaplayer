package yy.chen.mediaplay.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import yy.chen.mediaplay.bean.Lyric;

/**
 * Created by chenrongfa on 2017/1/5
 */

public class LyricUtil {
   private static ArrayList<Lyric> lyric;
    private static boolean isNmberformat;

    public static ArrayList<Lyric> getLyric(InputStream in){

            lyric=new ArrayList<>();
            BufferedReader br=null;
            try {
                br = new BufferedReader(new InputStreamReader(in,"utf-8"));
                String result="";

                    while((result=br.readLine())!=null) {
                        parceString(result);
                        if(isNmberformat)
                            return null;
                    }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                if(br!=null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }





        return lyric;
} public static ArrayList<Lyric> getLyricFromFile(File in){
if(in!=null&&in.length()>0&&!in.isDirectory()) {
    lyric = new ArrayList<>();
    BufferedReader br = null;
    try {
        br = new BufferedReader(new InputStreamReader(new FileInputStream(in), "utf-8"));
        String result = "";

        while ((result = br.readLine()) != null) {
            parceString(result);
            if (isNmberformat) return null;
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}else
   return null;
        return lyric;
}

    private static void parceString(String result) {
        String[] split=result.split("\\]");
        if(split.length==1){
            String content=split[0].replaceAll("\\[","").
                    replaceAll("\r","").replaceAll("\n","")
                    .trim();
            lyric.add(new Lyric(0,"0",content));
        }else if(split.length==2){
            String content=split[1].replaceAll("\\[","").
                    replaceAll("\r","").replaceAll("\n","")
                    .trim();
            String strTime=split[0].replaceAll("\\[","").
                    replaceAll("\r","").replaceAll("\n","")
                    .trim();
            lyric.add(new Lyric(parceTime(strTime),strTime,content));
        }else{
            int last=split.length;
            String content=split[last-1].replaceAll("\\[","").
                    replaceAll("\r","").replaceAll("\n","")
                    .trim();
            for(int i=0;i<last-2;i++){
                String strTime=split[i].replaceAll("\\[","").
                        replaceAll("\r","").replaceAll("\n","")
                        .trim();
                lyric.add(new Lyric(parceTime(strTime),strTime,content));
            }


        }

    }

    private static int parceTime(String strTime) {
        String[] split=strTime.split(":");
        String[] split1=split[1].split("\\.");
        int time= 0;
        int mills= 0;
        int minmills= 0;
        try {
            time = Integer.parseInt(split[0])*60*1000;
            mills = Integer.parseInt(split1[0])*1000;
            minmills = Integer.parseInt(split1[1])*10;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isNmberformat=true;
        }
        return time+mills+minmills;
    }
}
