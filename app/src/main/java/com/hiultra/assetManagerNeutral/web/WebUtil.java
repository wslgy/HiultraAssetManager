package com.hiultra.assetManagerNeutral.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.util.JsonUtil;
import com.hiultra.assetManagerNeutral.util.MD5Util;
import com.lidroid.xutils.util.LogUtils;

public class WebUtil {
    
    // 命名空间
    private static String nameSpace = "http://tempuri.org/";
    // Get请求的链接地址
    private static String urlGet = "http://%s:%s/GetService.asmx";
    // Set请求的链接地址
    private static String urlSet = "http://%s:%s/SetService.asmx";
    
    private interface Name {
        String LOGIN = "Login";
    }
    
    private static String initGet() {
        return String.format(urlGet, Varible.ip, Varible.port);
    }
    
    private static String initSet() {
        return String.format(urlSet, Varible.ip, Varible.port);
    }
    
    /**
     * 用户登录接口
     * 
     * @param loginname
     *            用户名
     * @param password
     *            密码
     * @return
     */
    public static UserInfo Login(String loginname, String password) {
        String url = initGet();
        LogUtils.e(url + " ; " + loginname + " : " + password);
        HttpTransportSE transport = new HttpTransportSE(url);
        transport.debug = true;
        SoapObject soapObject = new SoapObject(nameSpace, "Login");
        soapObject.addProperty("str1", loginname);
        soapObject.addProperty("str2", password);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.bodyOut = transport;
        envelope.setOutputSoapObject(soapObject);
        UserInfo userInfo = null;
        try {
            transport.call(nameSpace + Name.LOGIN, envelope);
            SoapObject object = (SoapObject) envelope.bodyIn;
            byte[] resultstream = Base64.decode(object.getProperty(0).toString());
            resultstream = MD5Util.unGZip(resultstream);
            // base64解码
            String strResult = new String(resultstream, "UTF-8");
            LogUtils.e(strResult);
            if (TextUtils.equals(strResult, "[]")) return null;
            @SuppressWarnings("unchecked")
            List<UserInfo> list = (List<UserInfo>) JsonUtil.parseJsonToList(strResult,
                    new TypeToken<List<UserInfo>>() {}.getType());
            userInfo = list.get(0);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
    
    /**
     * 访问WebService 返回值 0：正常处理结束 1：JSON解析异常 2：数据为空 3：调用WebService出错 4：Soap返回值异常
     * 5：日期格式错误
     */
    public static String getRequestnum(String tablename, String DateTime) throws IOException, XmlPullParserException {
        String strResult = "0";
        // 命名空间
        final String serviceNameSpace = "http://tempuri.org/";
        
        final String serviceURL = initGet();
        
        String tablename1 = tablename + "Num";
        // 实例化SoapObject对象,指定webService的命名空间以及调用方法的名称
        SoapObject request = new SoapObject(serviceNameSpace, tablename1);
        // example方法中有一个String的参数，这里将“android client”传递到example中
        request.addProperty("date", DateTime);
        
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            // 调用WebService
            transport.call(serviceNameSpace + tablename1, envelope);
            if (envelope.getResponse() != null) {
                SoapObject object = (SoapObject) envelope.bodyIn;
                strResult = object.getProperty(0).toString();
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e("materialmanagement", e.getMessage());
            }
            return "0";
        }
        return strResult;
    }
    
    /**
     * 获取基础数据
     * 
     * @param tableName
     * @param lastDate
     * @param page
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static String getBaseData(String tableName, String lastDate, int page) throws IOException, XmlPullParserException {
        String result = downloadData(tableName, lastDate, page);
        if (TextUtils.equals(result, "false") || TextUtils.isEmpty(result)) {
            return result;
        }
        return parse(result);
    }
    
    /**
     * 下载其他数据,包括盘点,巡检,处置等等
     * 
     * @param tableName
     *            表名
     * @param lastDate
     *            上次下载时间
     * @param page
     *            下载内容所在页
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static String getExtraData(String tableName, String lastDate, int page) throws IOException, XmlPullParserException {
        String result = downloadData(tableName, lastDate, page);
        if (TextUtils.equals(result, "false") || TextUtils.isEmpty(result)) {
            return result;
        }
        return parse(result);
    }
    
    // 获取BatchNumber
    public static String getBatchNumber(String batchnumbername) throws IOException, XmlPullParserException {
        String strResult = "0";
        // 命名空间
        final String serviceNameSpace = "http://tempuri.org/";
        
        final String serviceURL = initGet();
        String method = "GetBatchNumber";
        // 实例化SoapObject对象,指定webService的命名空间以及调用方法的名称
        SoapObject request = new SoapObject(serviceNameSpace, method);
        // example方法中有一个String的参数，这里将“android client”传递到example中
        request.addProperty("itemType", batchnumbername);
        
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceURL);
        transport.debug = true;
        try {
            // 调用WebService
            transport.call(serviceNameSpace + method, envelope);
            if (envelope.getResponse() != null) {
                SoapObject object = (SoapObject) envelope.bodyIn;
                strResult = object.getProperty(0).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return "0";
        }
        return strResult;
    }
    
    /* XXX 分割线 */
    
    /**
     * 获取下载数量
     * 
     * @param interfaceName
     *            表名
     * @param lastDate
     *            上次下载时间
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static String getRequestCount(String interfaceName, String lastDate) throws IOException, XmlPullParserException {
        String methodName = interfaceName + "Num";
        // 实例化SoapObject对象,指定webService的命名空间以及调用方法的名称
        SoapObject request = new SoapObject(nameSpace, methodName);
        request.addProperty("date", lastDate);
        
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(initGet());
        transport.debug = true;
        
        // 调用WebService
        transport.call(nameSpace + methodName, envelope);
        String strResult = "";
        if (envelope.getResponse() != null) {
            SoapObject object = (SoapObject) envelope.bodyIn;
            strResult = object.getProperty(0).toString();
        }
        return strResult;
    }
    
    public static String uploadBaseinfo(String json) throws IOException, XmlPullParserException {
        Map<String, String> map = newMap();
        map.put("dataList", json);
        return upload(Name.LOGIN, map);
    }
    
    public static String uploadDamageinfo(String maininfo, String detaialList) throws IOException, XmlPullParserException {
        Map<String, String> map = newMap();
        map.put("mainInfo", maininfo);
        map.put("detaialList", detaialList);
        return upload(Name.LOGIN, map);
    }
    
    public static String uploadCheckinfo(String maininfo, String detailList) throws IOException, XmlPullParserException {
        Map<String, String> map = newMap();
        map.put("mainInfo", maininfo);
        map.put("dataList", detailList);
        return upload(Name.LOGIN, map);
    }
    
    public static String uploadOutinfo(String detailList) throws IOException, XmlPullParserException {
        Map<String, String> map = newMap();
        map.put("detailList", detailList);
        return upload(Name.LOGIN, map);
    }
    
    public static int uploadBaseData(String json, String tableName) throws IOException, XmlPullParserException {
        // FIXME 这个地方,写死接口名?
        tableName = tableName.equals("AssetMaterialInfo") ? "UpdateAssetMaterial2" : String.format("insert%s", tableName);
        return uploadData(json, tableName);
    }
    
    public static int uploadBaseData1(String json, String tableName) throws IOException, XmlPullParserException {
        // FIXME 这个地方,写死接口名?
        tableName = tableName.substring(0, 1).equals("A") ? "UpdateAssetMaterial" : String.format("insert%s", tableName);
        return uploadData(json, tableName);
    }
    
    public static int uploadExtraData(String mainJson, String detailJson, String tableName) throws IOException,
            XmlPullParserException {
        String methodName = String.format("insert%s", tableName);
        return uploadData(mainJson, detailJson, methodName);
    }
    
    /**
     * 从服务器下载数据
     * 
     * @param interfaceName
     *            接口名称
     * @param lastDate
     *            上次下载时间
     * @param page
     *            下载内容所在页
     * @return 服务器返回的数据,注意:没有经过Base64解析
     * @throws XmlPullParserException
     * @throws IOException
     */
    private static String downloadData(String interfaceName, String lastDate, int page) throws IOException,
            XmlPullParserException {
        SoapObject request = new SoapObject(nameSpace, interfaceName);
        request.addProperty("date", lastDate);
        if (page > 0) {
            request.addProperty("page", page);
        }
        // 获得序列化的Envelope
        SoapSerializationEnvelope envelope;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        // Android传输对象
        HttpTransportSE transport = new HttpTransportSE(initGet());
        transport.debug = true;
        // 调用WebService
        transport.call(nameSpace + interfaceName, envelope);
        String result = null;
        if (envelope.getResponse() != null) {
            SoapObject object = (SoapObject) envelope.bodyIn;
            result = object.getProperty(0).toString();
        }
        return result;
    }
    
    private static String upload(String methodName, Map<String, String> map) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(nameSpace, methodName);
        if (map != null) {
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> entry = iterator.next();
                request.addProperty(entry.getKey(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.bodyOut = request;
        envelop.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(urlSet);
        transport.debug = true;
        transport.call(nameSpace + methodName, envelop);
        String result = null;
        if (envelop.getResponse() != null) {
            SoapObject object = (SoapObject) envelop.bodyIn;
            result = object.getProperty(0).toString();
        }
        return result;
    }
    
    /**
     * 上传数据到服务器
     * 
     * @param json
     *            json数据
     * @param methodName
     *            请求的接口名
     * @return 成功上传的条数
     * @throws IOException
     * @throws XmlPullParserException
     */
    private static int uploadData(String json, String methodName) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(nameSpace, methodName);
        request.addProperty("dataList", json);
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.bodyOut = request;
        envelop.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(initSet());
        transport.debug = true;
        transport.call(nameSpace + methodName, envelop);
        int result = 0;
        if (envelop.getResponse() != null) {
            SoapObject object = (SoapObject) envelop.bodyIn;
            result = Integer.parseInt(object.getProperty(0).toString());
        }
        return result;
    }
    
    /**
     * 上传数据到服务器
     * 
     * @return 成功上传的条数
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static int uploadResume(String key, String image) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(nameSpace, "UploadResume");
        request.addProperty("AttachmentKey", key);
        request.addProperty("Image", image);
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.bodyOut = request;
        envelop.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(initSet(), 60000);
        LogUtils.e(initSet() + " - " + "UploadResume");
        transport.debug = true;
        transport.call(nameSpace + "UploadResume", envelop);
        int result = 0;
        if (envelop.getResponse() != null) {
            SoapObject object = (SoapObject) envelop.bodyIn;
            result = Integer.parseInt(object.getProperty(0).toString());
        }
        return result;
    }
    
    /**
     * 上传数据到服务器
     * 
     * @param methodName
     *            请求的接口名
     * @return 成功上传的条数
     * @throws IOException
     * @throws XmlPullParserException
     */
    private static int uploadData(String mainJson, String detailJson, String methodName) throws IOException,
            XmlPullParserException {
        SoapObject request = new SoapObject(nameSpace, methodName);
        request.addProperty("mainInfo", mainJson);
        request.addProperty("dataList", detailJson);
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.bodyOut = request;
        envelop.dotNet = true;
        HttpTransportSE transport = new HttpTransportSE(initSet());
        LogUtils.e(initSet() + " - " + methodName);
        transport.debug = true;
        transport.call(nameSpace + methodName, envelop);
        int result = 0;
        if (envelop.getResponse() != null) {
            SoapObject object = (SoapObject) envelop.bodyIn;
            result = Integer.parseInt(object.getProperty(0).toString());
        }
        return result;
    }
    
    /**
     * 解析服务器的返回值
     * 
     * @param result
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String parse(String result) throws UnsupportedEncodingException {
        byte[] temp = Base64.decode(result);
        byte[] resultStream = MD5Util.unGZip(temp);
        return new String(resultStream, "UTF-8");
    }
    
    private static Map<String, String> newMap() {
        return new HashMap<>();
    }
    
}
