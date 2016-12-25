package com.uimirror.framework.db.shard.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Jayaram
 *         3/11/16.
 */
@XmlRootElement
public class DBDetail {

    @XmlElement(name = "key_family")
    private String defaultKeyFamily;
    @XmlElement(name = "con_details")
    private List<DBDetailsMeta> dbDetails;

    public DBDetailsMeta getConnectionDetail(String name){
        Assert.hasText(name, "Not a Valid DB name");
        return getDbDetails().stream()
                .filter(dbDetailsMeta -> name.equals(dbDetailsMeta.getDbName()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("No Connection Detail Found for:"+name));
    }

    public String getDefaultKeyFamily() {
        return defaultKeyFamily;
    }

    public void setDefaultKeyFamily(String defaultKeyFamily) {
        this.defaultKeyFamily = defaultKeyFamily;
    }

    public List<DBDetailsMeta> getDbDetails() {
        return dbDetails;
    }

    public void setDbDetails(List<DBDetailsMeta> dbDetails) {
        this.dbDetails = dbDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("defaultKeyFamily", defaultKeyFamily)
                .append("dbDetails", dbDetails)
                .toString();
    }
}
