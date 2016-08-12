package com.jscheng.rssmvpapplication.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jscheng.rssmvpapplication.utils.Constants;
import com.jscheng.rssmvpapplication.utils.NetworkUtils;
import com.jscheng.rssmvpapplication.utils.XMLRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-7-29.
 * 负责获取和解析xml
 */
public class RssLoadModel implements RssLoad {
    private final static String TAG = "RssLoadModel";
    private final static String ITEM = "item";
    private final static String TITLE = "title";
    private final static String LINK = "link";
    private final static String DESCRIPTION = "description";
    private final static String CONTENT = "content:encoded";


    private List<RssInfo> rssInfos;
    private RequestQueue mQueue;
    private Context context;

    public RssLoadModel(Context context, String url){
        this.context = context;
        rssInfos = new ArrayList<RssInfo>();
        mQueue = Volley.newRequestQueue(context);
    }

    public void excute(final RssLoadListener loadListener){
        if(!NetworkUtils.isNetworkAvailable(context)){
            loadListener.onNetWorkFailed();
            return;
        }
        loadListener.onBeginLoad();
        XMLRequest xmlRequest = new XMLRequest(Constants.RSS_URL,
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        parserXML(response);
                        loadListener.onLoadSuccess(rssInfos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                loadListener.onLoadFailed();
            }
        });
        mQueue.add(xmlRequest);
    }

    //解析xml文件
    private void parserXML(XmlPullParser response){
        try {
            rssInfos.clear();
            RssInfo rssInfo = null;
            String content = null;
            String title = null;
            String link = null;
            String description = null;
            int eventType = response.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = response.getName();
                        //Log.d(TAG, "parserXML: "+nodeName);
                        switch (nodeName){
                            case ITEM:
                                rssInfo = new RssInfo();
                                break;
                            case TITLE:
                                title= response.nextText();
                                break;
                            case LINK:
                                link = response.nextText();
                                break;
                            case DESCRIPTION:
                                 description = response.nextText();
                                break;
                            case CONTENT:
                                content = response.nextText();
                                if(title != null) {
                                    rssInfo.setTitle(title);
                                    rssInfo.setLink(link);
                                    rssInfo.setDescription(description);
                                    rssInfo.setImg(getImgFromContent(content));
                                    Log.e(TAG, rssInfo.toString());
                                    rssInfos.add(rssInfo);
                                }
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        break;
                }
                eventType = response.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //从content中获取第一个图片的链接作为img的内容
    private String getImgFromContent(String content){
        String img = null;
        if(content.contains("img") && img == null) {
            img = content.split("src=\"")[1];
            img = img.split("\"")[0];
        }
        return img;
    }
}
