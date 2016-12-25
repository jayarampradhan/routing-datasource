package com.uimirror.framework.db.shard.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <a href="https://commons.apache.org/proper/commons-dbcp/configuration.html">Config Details</a>
 * @author Jayaram
 *         3/11/16.
 */
@XmlRootElement
public class DBConnectionConfigMeta {

    @XmlElement(name = "max_active")
    private Integer maxActive;
    @XmlElement(name = "min_active")
    private Integer minActive;
    @XmlElement(name = "min_idle")
    private Integer minIdle;

    //The maximum number of connections that can remain idle in the pool, without extra ones being released, or negative for no limit.
    @XmlElement(name = "max_idle")
    private Integer maxIdle;

    @XmlElement(name = "validation_query")
    private String validationQuery;
    @XmlElement(name = "default_auto_commit")
    private Boolean defaultAutoCommit;

    //maximum number of milliseconds that the pool will wait for getConnection if has no connection
    @XmlElement(name = "max_wait")
    private Long maxWait = 3000l;

    //Number of seconds A query can run
    @XmlElement(name = "default_query_timeout")
    private Integer defaultQueryTimeout = 30;

    @XmlElement(name = "is_pool_pstatment")
    private Boolean pooledPrepareStatement = Boolean.TRUE;

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinActive() {
        return minActive;
    }

    public void setMinActive(Integer minActive) {
        this.minActive = minActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public Boolean getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getDefaultQueryTimeout() {
        return defaultQueryTimeout;
    }

    public void setDefaultQueryTimeout(Integer defaultQueryTimeout) {
        this.defaultQueryTimeout = defaultQueryTimeout;
    }

    public Boolean getPooledPrepareStatement() {
        return pooledPrepareStatement;
    }

    public void setPooledPrepareStatement(Boolean pooledPrepareStatement) {
        this.pooledPrepareStatement = pooledPrepareStatement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("maxActive", maxActive)
                .append("minActive", minActive)
                .append("minIdle", minIdle)
                .append("maxIdle", maxIdle)
                .append("validationQuery", validationQuery)
                .append("defaultAutoCommit", defaultAutoCommit)
                .append("maxWait", maxWait)
                .append("defaultQueryTimeout", defaultQueryTimeout)
                .toString();
    }
}
