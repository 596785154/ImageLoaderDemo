package com.nana.volleydemo;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.ErrorListener;

/**
 * Created by xpbuild on 2017/2/14.
 */
public class XMLRequest extends Request<XmlPullParser> {
    private final Response.Listener<XmlPullParser> mListener;

    public XMLRequest(int methods,String url,Response.Listener<XmlPullParser> listener,ErrorListener errorListener){
        super(methods,url, (Response.ErrorListener) errorListener);
        mListener = listener;
    }

    public XMLRequest(String url,Response.Listener<XmlPullParser> listener,ErrorListener errorListener){
        this(Method.GET, url, listener,errorListener);
    }
    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
        try {
            String xmlString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlPullParserException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(XmlPullParser response) {
        mListener.onResponse(response);
    }
}
