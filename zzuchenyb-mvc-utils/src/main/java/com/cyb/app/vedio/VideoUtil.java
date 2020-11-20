package com.cyb.app.vedio;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * 视频工具
 * @author 
 *
 */
public class VideoUtil {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param file  源视频文件
     * @param framefile  截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchPic(File file, String framefile) throws Exception{
        @SuppressWarnings("resource")
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file); 
        ff.start();
        int lenght = ff.getLengthInFrames();
        File targetFile = new File(framefile);
        int i = 0;
        Frame frame = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            frame = ff.grabFrame();
            if ((i > 5) && (frame.image != null)) {
                break;
            }
            i++;
        }      

        String imgSuffix = "jpg";
        if(framefile.indexOf('.') != -1){
            String[] arr = framefile.split("\\.");
            if(arr.length>=2){
                imgSuffix = arr[1];
            }
        }

        Java2DFrameConverter converter =new Java2DFrameConverter();
        BufferedImage srcBi =converter.getBufferedImage(frame);
        int owidth = srcBi.getWidth();
        int oheight = srcBi.getHeight();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(srcBi.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
        try {
            ImageIO.write(bi, imgSuffix, targetFile);
        }catch (Exception e) {
            e.printStackTrace();
        }      
        ff.stop();
    }

    /**
     * 获取视频时长，单位为秒
     * @param file
     * @return 时长（s）
     */
    public static Long getVideoTime(File file){
        Long times = 0L;
        try {
            @SuppressWarnings("resource")
			FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file); 
            ff.start();
            times = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
    
    public static Long getVideoTime(InputStream file){
        Long times = 0L;
        try {
            @SuppressWarnings("resource")
			FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file); 
            ff.start();
            times = ff.getLengthInTime()/(1000*1000);
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }
    
    
    public static InputStream getDurationOfVedio(String url1){
		try {
			//创建请求
	    	URL url = new URL(url1);
	    	HttpURLConnection connection;
			connection = (HttpURLConnection)url.openConnection();
			//默认就是Get，可以采用post，大小写都行，因为源码里都toUpperCase了。
	    	connection.setRequestMethod("GET");
	    	//是否允许缓存，默认true。
	    	connection.setUseCaches(Boolean.FALSE);
	    	//是否开启输出输入，如果是post使用true。默认是false
	    	//connection.setDoOutput(Boolean.TRUE);
	    	//connection.setDoInput(Boolean.TRUE);
	    	//设置请求头信息
	    	connection.addRequestProperty("Connection", "close");
	    	//设置连接主机超时（单位：毫秒）  
	    	connection.setConnectTimeout(8000);  
	    	 //设置从主机读取数据超时（单位：毫秒）  
	    	connection.setReadTimeout(8000);    
	    	//设置Cookie
	    	connection.addRequestProperty("Cookie","你的Cookies" );
	    	return connection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    public static void main(String[] args) {
    	String filePath = "d:/data/vedio/f0.mp4";
    	String target="d:/data/vedio/1.jpg";
    	try {
			fetchPic(new File(filePath), target);
			System.out.println(getVideoTime(new File(filePath)));
			System.out.println(getVideoTime(getDurationOfVedio("http://1252879966.vod2.myqcloud.com/598cc6b9vodcq1252879966/7fa7f41c5285890800171331424/f0.mp4")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}