package com.move.serverprovider;

import java.io.Serializable;

/**
 * Created by cxj on 2017/6/2.
 * app的服务端的配置
 */
public class ServerConfigEntity implements Serializable {

    // 是否是系统的,如果是这样的话不可删除
    public boolean isSystem;

    public boolean isPro;

    public String baseUrl;

    public boolean isSelect;

    public ServerConfigEntity(boolean isPro, String baseUrl, boolean isSelect) {
        this.isPro = isPro;
        this.baseUrl = baseUrl;
        this.isSelect = isSelect;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public boolean isPro() {
        return isPro;
    }

    public void setPro(boolean pro) {
        isPro = pro;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

}
