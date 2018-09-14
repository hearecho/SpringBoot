package com.echo.springboot206security.bean;


import java.util.List;

public class UrlAuthBean {

    private String url;
    private List<String> rols;

    public UrlAuthBean() {
    }

    public UrlAuthBean(String url, List<String> rols) {
        this.url = url;
        this.rols = rols;
    }

    @Override
    public String toString() {
        return "UrlAuthBean{" +
                "url='" + url + '\'' +
                ", rols=" + rols.toArray().toString() +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getRols() {
        return rols;
    }

    public void setRols(List<String> rols) {
        this.rols = rols;
    }
}
