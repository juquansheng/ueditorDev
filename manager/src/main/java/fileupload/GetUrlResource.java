/**
 * Title: GetUrlResource.java
 * Description:  获取url资源
 * Copyright: Copyright (c) 2017
 * Company:
 *
 * @author cexy
 * @date 2017年6月23日--下午2:35:10
 * @version 1.0
 */
package fileupload;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetUrlResource {

    /**
     * 发送Get请求
     *
     * @param url      请求的地址
     * @param headers  请求的头部信息
     * @param params   请求的参数
     * @param encoding 字符编码
     * @return
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    public static Result sendHead(String url, Map<String, String> headers,
                                 Map<String, String> params, String encoding, boolean duan) {
//		DefaultHttpClient client = new DefaultHttpClient();

        CloseableHttpClient client = buildHttpClient();

        url = url + (null == params ? "" : assemblyParameter(params));
        HttpHead hp = new HttpHead(url);
        if (null != headers) {
            hp.setHeaders(assemblyHeader(headers));
        }
        HttpResponse response;
        Result result = null;
        try {
            response = client.execute(hp);
            if (duan)
                hp.abort();
            HttpEntity entity = response.getEntity();

            result = new Result();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            result.setHttpEntity(entity);
        } catch (IOException e) {
            e.printStackTrace();
            hp.abort();
        } finally {

        }
        return result;
    }

    /**
     * 发送Get请求
     *
     * @param url      请求的地址
     * @param headers  请求的头部信息
     * @param params   请求的参数
     * @param encoding 字符编码
     * @return
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    public static Result sendGet(String url, Map<String, String> headers,
                                  Map<String, String> params, String encoding, boolean duan) {
//		DefaultHttpClient client = new DefaultHttpClient();

        CloseableHttpClient client = buildHttpClient();

        url = url + (null == params ? "" : assemblyParameter(params));
        HttpGet hp = new HttpGet(url);
        if (null != headers) {
            hp.setHeaders(assemblyHeader(headers));
        }
        HttpResponse response;
        Result result = null;
        try {
            response = client.execute(hp);
            if (duan)
                hp.abort();
            HttpEntity entity = response.getEntity();

            result = new Result();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            result.setHttpEntity(entity);
        } catch (IOException e) {
            e.printStackTrace();
            hp.abort();
        } finally {

        }
        return result;
    }

    /**
     * 发送Delete请求
     *
     * @param url      请求的地址
     * @param headers  请求的头部信息
     * @param params   请求的参数
     * @param encoding 字符编码
     * @return
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    public static Result sendDelete(String url, Map<String, String> headers,
                                    Map<String, String> params, String encoding, boolean duan) {
//		DefaultHttpClient client = new DefaultHttpClient();

        CloseableHttpClient client = buildHttpClient();

        url = url + (null == params ? "" : assemblyParameter(params));
        HttpDelete hp = new HttpDelete(url);
        if (null != headers) {
            hp.setHeaders(assemblyHeader(headers));
        }
        HttpResponse response;
        Result result = null;
        try {
            response = client.execute(hp);
            if (duan)
                hp.abort();
            HttpEntity entity = response.getEntity();

            result = new Result();
//			result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            result.setHttpEntity(entity);
        } catch (IOException e) {
            e.printStackTrace();
            hp.abort();
        } finally {

        }
        return result;
    }

    static Result sendHead(String url, Map<String, String> headers,
                          Map<String, String> params, String encoding) {
        return sendGet(url, headers, params, encoding, false);
    }

    static Result sendGet(String url, Map<String, String> headers,
                          Map<String, String> params, String encoding) {
        return sendGet(url, headers, params, encoding, false);
    }

    static Result sendDelete(String url, Map<String, String> headers,
                             Map<String, String> params, String encoding)
            throws ClientProtocolException, IOException {
        return sendDelete(url, headers, params, encoding, false);
    }

    public static Result sendHead(String url, Map<String, String> params) {
        return sendGet(url, null, params, "UTF-8", false);
    }

    public static Result sendGet(String url, Map<String, String> params) {
        return sendGet(url, null, params, "UTF-8", false);
    }

    static Result sendPost(String url, Map<String, String> headers,
                           String params, String encoding) {

//		DefaultHttpClient client = new DefaultHttpClient();
        CloseableHttpClient client = buildHttpClient();

        HttpPost post = new HttpPost(url);

        Result result = null;
        try {
            post.setEntity(new StringEntity(params, encoding));
            if (null != headers) {
                post.setHeaders(assemblyHeader(headers));
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            result = new Result();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
//			result.setCookie(assemblyCookie(client.getCookieStore().getCookies()));
            result.setHttpEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            post.abort();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            post.abort();
        } catch (IOException e) {
            e.printStackTrace();
            post.abort();
        } finally {
        }
        return result;
    }


    /**
     * 发送Post请求
     *
     * @param url      请求的地址
     * @param headers  请求的头部信息
     * @param params   请求的参数
     * @param encoding 字符编码
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static Result sendPost(String url, Map<String, String> headers,
                                   Map<String, String> params, String encoding) {

//		DefaultHttpClient client = new DefaultHttpClient();
//		CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpClient client = buildHttpClient();

        HttpPost post = new HttpPost(url);

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String temp : params.keySet()) {
            list.add(new BasicNameValuePair(temp, params.get(temp)));
        }
        Result result = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(list, encoding));
            if (null != headers) {
                post.setHeaders(assemblyHeader(headers));
            }
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            result = new Result();
            result.setStatusCode(response.getStatusLine().getStatusCode());
            result.setHeaders(response.getAllHeaders());
            result.setHttpEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            post.abort();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            post.abort();
        } catch (IOException e) {
            e.printStackTrace();
            post.abort();
        } finally {
        }
        return result;
    }


    /**
     * 创建HttpClient
     *
     * @return
     */
    public static CloseableHttpClient buildHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)
                .setSocketTimeout(30000).build();
        PoolingHttpClientConnectionManager conMgr = new PoolingHttpClientConnectionManager();
        conMgr.setDefaultMaxPerRoute(conMgr.getMaxTotal());
        conMgr.setMaxTotal(200);
        CookieStore cookieStore = new BasicCookieStore();
//		return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(conMgr).setDefaultCookieStore(cookieStore).build();
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
    }

    /**
     * 组装Cookie
     *
     * @param cookies
     * @return
     */
    public static String assemblyCookie(List<Cookie> cookies) {
        StringBuffer sbu = new StringBuffer();
        for (Cookie cookie : cookies) {
            sbu.append(cookie.getName()).append("=").append(cookie.getValue())
                    .append(";");
        }
        if (sbu.length() > 0) {
            sbu.deleteCharAt(sbu.length() - 1);
        }
        return sbu.toString();
    }

    /**
     * 组装请求参数
     *
     * @param parameters
     * @return
     */
    public static String assemblyParameter(Map<String, String> parameters) {
        String para = "?";
        for (String str : parameters.keySet()) {
            try {
                para += str + "=" + URLEncoder.encode(parameters.get(str), "UTF-8") + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return para.substring(0, para.length() - 1);
    }

    /**
     * 组装头部信息
     *
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, String> headers) {
        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }


    /**
     * 接口调用 GET
     *
     * @return
     */
    public static String httpURLConectionGET(String urlStr) {
        /* 网络的url地址 */
        URL url = null;
        /* 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        return result;
    }

    /**
     * 接口调用 POST
     *
     * @return
     */
    public static String httpURLConnectionPOST(String urlStr, Map<String, Object> params) throws Exception {
        StringBuilder strParams = new StringBuilder();
        for (String key : params.keySet()) {
            strParams.append(key + "=" + params.get(key) + "&");
        }
        strParams.deleteCharAt(strParams.length() - 1);

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
        printWriter.write(strParams.toString());
        printWriter.flush();
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                conn.disconnect();
                if (in != null) {
                    in.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        String result = sb.toString();
        return result;
    }

}
