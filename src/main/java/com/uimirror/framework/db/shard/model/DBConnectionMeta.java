package com.uimirror.framework.db.shard.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jayaram
 *         3/11/16.
 */
@XmlRootElement
public class DBConnectionMeta {
    @XmlElement(name = "url")
    private String url;
    @XmlElement(name = "user_name")
    private String userName;
    @XmlElement(name = "password")
    private String password;
    @XmlElement(name = "config")
    private DBConnectionConfigMeta connectionConfig;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBConnectionConfigMeta getConnectionConfig() {
        return connectionConfig;
    }

    public void setConnectionConfig(DBConnectionConfigMeta connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("url", url)
                .append("userName", userName)
                .append("connectionConfig", connectionConfig)
                .toString();
    }
}
