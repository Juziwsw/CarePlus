package cn.com.cjland.careplus.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Map;


/**
 * Created by Administrator on 2015/11/23.
 * 网络连接工具类，用于判断网络类型，网络是否可用，以及与web服务器交互
 * @author ztt
 */
public class HttpUtils {
    private static String[] sessionId;
    public HttpUtils() {

    }
    /**
     * 判断是否有网络连接
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        //获取网络管理对象
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            //获取所有代表联网状态的NetWorkInfo对象
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null){
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 判断当前是否连接上wifi网络
     * @param context
     * @return boolean
     */
    public static boolean isWifiConnected(Context context){
        //获取网络管理对象
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null != cm){
            //获取当前代表联网状态的NetWorkInfo对象
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if(null != netInfo){
                int netType = netInfo.getType();
                if(ConnectivityManager.TYPE_WIFI == netType){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断当前是否连接上2G/3G网络
     * @param context
     * @return boolean
     */
    public static boolean isMobileConnected(Context context){
        //获取网络管理对象
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null != cm){
            //获取当前代表联网状态的NetWorkInfo对象
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if(null != netInfo){
                int netType = netInfo.getType();
                if(ConnectivityManager.TYPE_MOBILE == netType){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 手机自带网络连接
     * @param url
     * @return
     */
    public static String PostString(Context context, String url,String parmas) throws MalformedURLException {
        if (!isNetworkAvailable(context)) {
            Intent intent = new Intent("cn.com.cjland.careplus.net");
            context.sendBroadcast(intent);
            return null;
        }
        URL mUrl = new URL(url);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            OutputStream out = conn.getOutputStream();//获取输入流
            String content = parmas;//需要传递的参数
            out.write(content.getBytes());//向服务器传递数据
            InputStream is = conn.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }
    /**
     * 手机自带网络连接   注册 保存session
     * @param url
     * @return
     */
    public static String RegistPostString(Context context, String url,String parmas) throws MalformedURLException {
        if (!isNetworkAvailable(context)) {
            Intent intent = new Intent("cn.com.cjland.careplus.net");
            context.sendBroadcast(intent);
            return null;
        }
        URL mUrl = new URL(url);
        HttpURLConnection conn = null;
        String session_value;
        try {
            conn = (HttpURLConnection) mUrl.openConnection();
            //保存session信息
            conn.setRequestProperty("Cookie", sessionId[0]);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
//            conn.connect();
            OutputStream out = conn.getOutputStream();//获取输入流
            String content = parmas;//需要传递的参数
            out.write(content.getBytes());//向服务器传递数据
            InputStream is = conn.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }
    /**
     * 手机自带网络连接 -- 注册 获取session
     * @param url
     * @return
     */
    public static String CodePostString(Context context, String url,String parmas) throws MalformedURLException {
        if (!isNetworkAvailable(context)) {
            Intent intent = new Intent("cn.com.cjland.careplus.net");
            context.sendBroadcast(intent);
            return null;
        }
        URL mUrl = new URL(url);
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
//            conn.connect();
            OutputStream out = conn.getOutputStream();//获取输入流
            String content = parmas;//需要传递的参数
            out.write(content.getBytes());//向服务器传递数据
            InputStream is = conn.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                //获得session信息
                String session_value = conn.getHeaderField("Set-Cookie");
                sessionId = session_value.split(";");
                conn.disconnect();
            }
        }
        return null;
    }
    //将JSON语句转换为我们需要的字符串
    /**
     * 通过is解析网页返回的数据
     * @param isresult
     * @return
     */
    public static String readStream(InputStream isresult){
        InputStreamReader ism;
        String result = "";
        String line = "";
        try {
            ism = new InputStreamReader(isresult,"utf-8");
            BufferedReader br = new BufferedReader(ism);
            while((line = br.readLine())!=null){
                result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 上传文件至Server的方法 */
    public String uploadFile (Context context, String mUrl,String filepath,String filename){
        if (!isNetworkAvailable(context)) {
            Intent intent = new Intent("cn.com.cjland.careplus.net");
            context.sendBroadcast(intent);
            return null;
        }
        String boundary = "---------------------------7de2c25201d48";
        String prex = "--";
        String end = "\r\n";
        URL url;
        try {
            url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//允许向服务器写入数据
            conn.setDoInput(true);//允许服务器输出数据
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(prex+boundary+end);
            //out.writeBytes("Content-Disposition:form-data;"+"name=\"filename\";filename=\""+filename+"\""+end+"Content-Type:image/jpeg"+end);
            out.writeBytes("Content-Disposition:form-data;"+"name=\"filename\";filename=\"" + "Header.jpg" + "\"" + end + "Content-Type:image/jpeg" + end);
            //out.writeBytes("Content-Disposition:form-data;" + "name=\""+filename+"+\";filename=\"" + "Header.jpg" + "\"" + end + "Content-Type:image/jpeg" + end);
            //fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
            out.writeBytes(end);
            FileInputStream fileinputstream = new FileInputStream(new File(filepath));
            byte[] b = new byte[1024*4];
            int len;
            while((len=fileinputstream.read(b))!=-1){
                out.write(b,0,len);
            }
//            out.write(parmas.getBytes());
            out.writeBytes(end);


            out.writeBytes(prex+boundary+prex+end);
            out.flush();
            /* 取得Response内容 */
            InputStream is = conn.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 上传文件至Server的方法 */
    public String uploadFile1 (Context context, String mUrl,String filepath,String filename){
        if (!isNetworkAvailable(context)) {
            Intent intent = new Intent("cn.com.cjland.careplus.net");
            context.sendBroadcast(intent);
            return null;
        }
        String boundary = "---------------------------7de2c25201d48";
        String prex = "--";
        String end = "\r\n";
        URL url;
        try {
            url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//允许向服务器写入数据
            conn.setDoInput(true);//允许服务器输出数据
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(prex+boundary+end);
            out.writeBytes("Content-Disposition:form-data;"+"name=\"filename\";filename=\""+filename+"\""+end+"Content-Type:image/jpeg"+end);
            //out.writeBytes("Content-Disposition:form-data;" + "name=\""+filename+"+\";filename=\"" + "Header.jpg" + "\"" + end + "Content-Type:image/jpeg" + end);
            out.writeBytes(end);
            FileInputStream fileinputstream = new FileInputStream(new File(filepath));
            byte[] b = new byte[1024*4];
            int len;
            while((len=fileinputstream.read(b))!=-1){
                out.write(b,0,len);
            }
//            out.write(parmas.getBytes());
            out.writeBytes(end);


            out.writeBytes(prex+boundary+prex+end);
            out.flush();
            /* 取得Response内容 */
            InputStream is = conn.getInputStream();
            return readStream(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
//    /**
//     *多文件上传
//     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:
//     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
//     <INPUT TYPE="text" NAME="name">
//     <INPUT TYPE="text" NAME="id">
//     <input type="file" name="imagefile"/>
//     <input type="file" name="zip"/>
//     </FORM>
//     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.iteye.cn或http://192.168.1.101:8083这样的路径测试)
//     * @param params 请求参数 key为参数名,value为参数值
//     * @param files 上传文件
//     */
//    public static boolean post(String path, Map<String, String> params, FormFile[] files) throws Exception{
//        final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
//        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志
//        Log.e("WU12","12121212");
//
//        int fileDataLength = 0;
//        for(FormFile uploadFile : files){//得到文件类型数据的总长度
//            StringBuilder fileExplain = new StringBuilder();
//            fileExplain.append("--");
//            fileExplain.append(BOUNDARY);
//            fileExplain.append("\r\n");
//            fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
//            fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
//            fileExplain.append("\r\n");
//            fileDataLength += fileExplain.length();
//            if(uploadFile.getInStream()!=null){
//                fileDataLength += uploadFile.getFile().length();
//            }else{
//                fileDataLength += uploadFile.getData().length;
//            }
//        }
//        StringBuilder textEntity = new StringBuilder();
//        for (Map.Entry<String, String> entry : params.entrySet()) {//构造文本类型参数的实体数据
//            textEntity.append("--");
//            textEntity.append(BOUNDARY);
//            textEntity.append("\r\n");
//            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
//            textEntity.append(entry.getValue());
//            textEntity.append("\r\n");
//        }
//        //计算传输给服务器的实体数据总长度
//        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
//
//
//        URL url = new URL(path);
//        int port = url.getPort()==-1 ? 80 : url.getPort();
//        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
//        OutputStream outStream = socket.getOutputStream();
//        //下面完成HTTP请求头的发送
//        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
//        outStream.write(requestmethod.getBytes());
//        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
//        outStream.write(accept.getBytes());
//        String language = "Accept-Language: zh-CN\r\n";
//        outStream.write(language.getBytes());
//        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
//        outStream.write(contenttype.getBytes());
//        String contentlength = "Content-Length: "+ dataLength + "\r\n";
//        outStream.write(contentlength.getBytes());
//        String alive = "Connection: Keep-Alive\r\n";
//        outStream.write(alive.getBytes());
//        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
//        outStream.write(host.getBytes());
//        //写完HTTP请求头后根据HTTP协议再写一个回车换行
//        outStream.write("\r\n".getBytes());
//        //把所有文本类型的实体数据发送出来
//        outStream.write(textEntity.toString().getBytes());
//        //把所有文件类型的实体数据发送出来
//        for(FormFile uploadFile : files){
//            StringBuilder fileEntity = new StringBuilder();
//            fileEntity.append("--");
//            fileEntity.append(BOUNDARY);
//            fileEntity.append("\r\n");
//            fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
//            fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
//            outStream.write(fileEntity.toString().getBytes());
//            if(uploadFile.getInStream()!=null){
//                byte[] buffer = new byte[1024];
//                int len = 0;
//                int count =0;
//                while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
//                    Log.e("wu123",++count+"");
//                    outStream.write(buffer, 0, len);
//                }
//                uploadFile.getInStream().close();
//            }else{
//                outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
//            }
//            outStream.write("\r\n".getBytes());
//        }
//        Log.e("WU12","23232323");
//       /* Log.e("WU12", "----= "+requestmethod.getBytes() + "  " +accept.getBytes()+ "  "+ language.getBytes()
//                                 +" "+contenttype.getBytes() +"  " + contentlength.getBytes() + " " + alive.getBytes()
//                                 + " "+host.getBytes()+"  "+textEntity.toString().getBytes() + "  " );*/
//        //下面发送数据结束标志，表示数据已经结束
//        outStream.write(endline.getBytes());
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        String result = "";
//        String line = "";
//        while((line = reader.readLine())!=null){
//            result+=line;
//        }
//        Log.e("wu123", "reader==" + result);
//        if(reader.readLine().indexOf("200")==-1){//读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
//            return false;
//        }
//        outStream.flush();
//        outStream.close();
//        reader.close();
//        socket.close();
//        return true;
//    }
    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }
        //DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5, xdpi=160.421, ydpi=159.497}
        //DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }
}
