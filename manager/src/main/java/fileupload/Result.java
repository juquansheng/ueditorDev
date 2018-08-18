package fileupload;

import org.apache.http.*;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

 /**
 * test
 *
 * @author cexy
 * @description: 结果封装类 封装响应的头部信息、状态信息、Cookie信息、返回内容
 * @date 2018/7/16
 * @time 10:33
 */
public class Result {

    private String cookie;
    private int statusCode;
    private HashMap<String, Header> headerAll;
    private HttpEntity httpEntity;
    private String otherContent;

    /**
     * 获取Cookie信息
     *
     * @return
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * 设置Cookie信息
     *
     * @param cookie
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * 获取结果状态码
     *
     * @return
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 设置结果状态码
     *
     * @param statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 获取结果头部信息
     *
     * @return
     */
    public HashMap<String, Header> getHeaders() {
        return headerAll;
    }

    /**
     * 设置结果头部信息
     *
     * @param headers
     */
    public void setHeaders(Header[] headers) {
        headerAll = new HashMap<String, Header>();
        for (Header header : headers) {
            headerAll.put(header.getName(), header);
        }
    }

    /**
     * 获取响应结果
     *
     * @return
     */
    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    /**
     * 设置响应结果
     *
     * @param httpEntity
     */
    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    /**
     * 将服务器返回的结果HttpEntity流转换成String格式的内容
     *
     * @param encoding 指定的转换编码
     * @return
     */
    public String getHtmlContent(String encoding) {
        // HTML内容
        if (httpEntity != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                if (httpEntity.getContentEncoding() != null
                        && httpEntity.getContentEncoding().getValue().indexOf("gzip") != -1) {
                    // GZIP格式的流解压
                    is = new GZIPInputStream(new BufferedInputStream(httpEntity.getContent()));
                } else {
                    is = new BufferedInputStream(httpEntity.getContent());
                }
                String responseContent = "";
                if (is != null) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = is.read(buffer)) >= 0) {
                        output.write(buffer, 0, n);
                    }
                    responseContent = output.toString(encoding);
                    // responseContent=new
                    // String(responseContent.getBytes("utf-8"),"gbk");
                }
                return responseContent;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取请求中的内容
     */
    public String getHtml(Result result, String chart) {

        HttpEntity entity = result.getHttpEntity();
        String resultStr = "";
        try {
            resultStr = EntityUtils.toString(entity, chart);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultStr;
    }

    /**
     * 关闭HttpEntity流
     */
    public void consume(Result result) {
        try {
            HttpEntity entity = result.getHttpEntity();
            // EntityUtils.consume(entity);
            if (entity.isStreaming()) {
                InputStream instream = entity.getContent();
                if (instream != null) {
                    instream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContentString() {
        try {
            String string = getContentCharSet(httpEntity);
            String str = EntityUtils.toString(httpEntity, string);
            //System.out.println(string+"**"+str);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 默认编码utf -8
     * Obtains character set of the entity, if known.
     *
     * @param entity must not be null
     * @return the character set, or null if not found
     * @throws ParseException           if header elements cannot be parsed
     * @throws IllegalArgumentException if entity is null
     */
    public String getContentCharSet(final HttpEntity entity)
            throws ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        String charset = null;
        if (entity.getContentType() != null) {
            HeaderElement values[] = entity.getContentType().getElements();
            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");
                if (param != null) {
                    charset = param.getValue();
                }
            }
        }

        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }
        return charset;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }
}