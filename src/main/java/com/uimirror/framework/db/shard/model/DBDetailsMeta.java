package com.uimirror.framework.db.shard.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * @author Jayaram
 *         3/11/16.
 */
@XmlRootElement
public class DBDetailsMeta {

    @XmlElement(name = "name")
    private String dbName;
    @XmlElement(name = "key_family")
    private String keyFamily;
    private String keyName;
    @XmlElement(name = "is_shard")
    private boolean shard;
    @XmlElement(name = "shard_type")
    private ShardType shardType;
    @XmlElement(name = "connection")
    private DBConnectionMeta connection;
    @XmlElement(name = "shard_details")
    private Set<DBShardMeta> shardDetails;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getKeyFamily() {
        return keyFamily;
    }

    public void setKeyFamily(String keyFamily) {
        this.keyFamily = keyFamily;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public boolean isShard() {
        return shard;
    }

    public void setShard(boolean shard) {
        this.shard = shard;
    }

    public DBConnectionMeta getConnection() {
        return connection;
    }

    public void setConnection(DBConnectionMeta connection) {
        this.connection = connection;
    }

    public Set<DBShardMeta> getShardDetails() {
        return shardDetails;
    }

    public void setShardDetails(Set<DBShardMeta> shardDetails) {
        this.shardDetails = shardDetails;
    }

    public ShardType getShardType() {
        return shardType;
    }

    public void setShardType(ShardType shardType) {
        this.shardType = shardType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dbName", dbName)
                .append("keyFamily", keyFamily)
                .append("keyName", keyName)
                .append("shard", shard)
                .append("connection", connection)
                .append("shardDetails", shardDetails)
                .toString();
    }

    public enum ShardType{
        MASTER_SLAVE,
        OTHER
    }
}
