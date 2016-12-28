package com.jscheng.rssmvpapplication.model;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-12-28.
 */
public class RssParser {
    private final static String ITEM = "item";
    private final static String TITLE = "title";
    private final static String LINK = "link";
    private final static String DESCRIPTION = "description";
    private final static String CONTENT = "content:encoded";

    //解析xml文件
    public static List<RssInfo> parserXML(String xmlString){
        List<RssInfo> rssInfos = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));

            RssInfo rssInfo = null;
            String content = null;
            String title = null;
            String link = null;
            String description = null;
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = xmlPullParser.getName();
                        //Log.d(TAG, "parserXML: "+nodeName);
                        switch (nodeName){
                            case ITEM:
                                rssInfo = new RssInfo();
                                break;
                            case TITLE:
                                title= xmlPullParser.nextText();
                                break;
                            case LINK:
                                link = xmlPullParser.nextText();
                                break;
                            case DESCRIPTION:
                                description = xmlPullParser.nextText();
                                break;
                            case CONTENT:
                                content = xmlPullParser.nextText();
                                if(title != null) {
                                    rssInfo.setTitle(title);
                                    rssInfo.setLink(link);
                                    rssInfo.setDescription(description);
                                    rssInfo.setImg(getImgFromContent(content));
                                    Logger.e(rssInfo.toString());
                                    rssInfos.add(rssInfo);
                                }
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rssInfos;
    }
    //从content中获取第一个图片的链接作为img的内容
    private static String getImgFromContent(String content){
        String img = null;
        if(content.contains("img") && img == null) {
            img = content.split("src=\"")[1];
            img = img.split("\"")[0];
        }
        return img;
    }
}
