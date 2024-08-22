package com.enjoypartytime.testdemo.okhttp.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/22
 */
public class ConverterBean {


    /**
     * args : {}
     * data :
     * files : {}
     * form : {"password":"123","username":"aaa"}
     * headers : {"Accept-Encoding":"gzip","Content-Length":"25","Content-Type":"application/x-www-form-urlencoded","Host":"www.httpbin.org","User-Agent":"okhttp/4.12.0","X-Amzn-Trace-Id":"Root=1-66c6bdc5-209b3d4c17b874ba708038ac"}
     * json : null
     * origin : 47.129.13.191
     * url : https://www.httpbin.org/post
     */
    private String data;

    /**
     * password : 123
     * username : aaa
     */
    private FormBean form;

    /**
     * Accept-Encoding : gzip
     * Content-Length : 25
     * Content-Type : application/x-www-form-urlencoded
     * Host : www.httpbin.org
     * User-Agent : okhttp/4.12.0
     * X-Amzn-Trace-Id : Root=1-66c6bdc5-209b3d4c17b874ba708038ac
     */
    private HeadersBean headers;
    private Object json;
    private String origin;
    private String url;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FormBean getForm() {
        return form;
    }

    public void setForm(FormBean form) {
        this.form = form;
    }

    public HeadersBean getHeaders() {
        return headers;
    }

    public void setHeaders(HeadersBean headers) {
        this.headers = headers;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class FormBean {
        private String password;
        private String username;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class HeadersBean {
        @SerializedName("Accept-Encoding")
        private String AcceptEncoding;
        @SerializedName("Content-Length")
        private String ContentLength;
        @SerializedName("Content-Type")
        private String ContentType;
        private String Host;
        @SerializedName("User-Agent")
        private String UserAgent;
        @SerializedName("X-Amzn-Trace-Id")
        private String XAmznTraceId;

        public String getAcceptEncoding() {
            return AcceptEncoding;
        }

        public void setAcceptEncoding(String AcceptEncoding) {
            this.AcceptEncoding = AcceptEncoding;
        }

        public String getContentLength() {
            return ContentLength;
        }

        public void setContentLength(String ContentLength) {
            this.ContentLength = ContentLength;
        }

        public String getContentType() {
            return ContentType;
        }

        public void setContentType(String ContentType) {
            this.ContentType = ContentType;
        }

        public String getHost() {
            return Host;
        }

        public void setHost(String Host) {
            this.Host = Host;
        }

        public String getUserAgent() {
            return UserAgent;
        }

        public void setUserAgent(String UserAgent) {
            this.UserAgent = UserAgent;
        }

        public String getXAmznTraceId() {
            return XAmznTraceId;
        }

        public void setXAmznTraceId(String XAmznTraceId) {
            this.XAmznTraceId = XAmznTraceId;
        }
    }
}
