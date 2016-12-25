package com.uimirror.framework.db.shard.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jayaram
 *         3/11/16.
 */
@XmlRootElement
public class DBShardMeta {

    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "key_family")
    private String keyFamily;
    @XmlElement(name = "is_default")
    private boolean defaultConnection;
    @XmlElement(name = "type")
    private DBType connectionType;
    @XmlElement(name = "connection")
    private DBConnectionMeta connection;
    private String keyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyFamily() {
        return keyFamily;
    }

    public void setKeyFamily(String keyFamily) {
        this.keyFamily = keyFamily;
    }

    public boolean isDefaultConnection() {
        return defaultConnection;
    }

    public void setDefaultConnection(boolean defaultConnection) {
        this.defaultConnection = defaultConnection;
    }

    public DBConnectionMeta getConnection() {
        return connection;
    }

    public void setConnection(DBConnectionMeta connection) {
        this.connection = connection;
    }

    public DBType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(DBType connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("keyFamily", keyFamily)
                .append("defaultConnection", defaultConnection)
                .append("connectionType", connectionType)
                .append("connection", connection)
                .append("keyName", keyName)
                .toString();
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBShardMeta that = (DBShardMeta) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public enum DBType{
        MASTER,
        SLAVE
    }
}
