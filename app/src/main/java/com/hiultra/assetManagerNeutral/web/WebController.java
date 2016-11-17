package com.hiultra.assetManagerNeutral.web;

import com.hiultra.assetManagerNeutral.util.Util;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class WebController {

    /* 整改后 */

    public static int getRequestCount(ArrayList<String> tablenameList, String lastDate) {
        int count = 0;
        for (String tableName : tablenameList) {
            try {
                String result = WebUtil.getRequestCount(tableName, lastDate);
                count += Util.parseStr2int(result);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return count;
    }

    public static String getData(String tableName, String lastDate, int page) {
        try {
            return WebUtil.getBaseData(tableName, lastDate, page);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int uploadBaseData(String jsonArray, String tableName) throws IOException, XmlPullParserException {
        return WebUtil.uploadBaseData(jsonArray, tableName);
    }

    public static int uploadBaseData1(String jsonArray, String tableName) throws IOException, XmlPullParserException {
        return WebUtil.uploadBaseData1(jsonArray, tableName);
    }

    public static int uploadExtraData(String mainJson, String detailJson, String mainTable) throws IOException, XmlPullParserException {
        return WebUtil.uploadExtraData(mainJson, detailJson, mainTable);
    }

}
