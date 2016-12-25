package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.DBDetail;
import com.uimirror.framework.db.shard.model.DBDetailsMeta;
import com.uimirror.framework.db.shard.model.DBShardMeta;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jayaram
 *         3/11/16.
 */
public class DBDetailConfigurator {

    private final String shardPrefix;
    private final String keySeparator;

    public DBDetailConfigurator(String shardPrefix, String keySeparator) {
        this.shardPrefix = shardPrefix;
        this.keySeparator = keySeparator;
    }

    public DBDetail configure(DBDetail dbDetail){
        Assert.notNull(dbDetail, "DB Details are Invalid.");
        Assert.notNull(dbDetail.getDefaultKeyFamily(), "Default Key family is missing.");
        //Assigning the default key name
        assignKeyFamilyAndKeyNameToDetailMeta(dbDetail);
        //Assigning the default key Family name to shard connection
        assignKeyFamilyToShard(dbDetail);
        assignKeyNameToShard(dbDetail, shardPrefix, keySeparator);
        return dbDetail;
    }

    private void assignKeyNameToShard(DBDetail dbDetail, String shardPrefix, String keySeperator) {
        dbDetail.getDbDetails().parallelStream()
                .filter(DBDetailsMeta::isShard)
                .map(dbDetailsMeta -> configureKeyNameForShard(shardPrefix, keySeperator, dbDetailsMeta))
                .collect(Collectors.toList());
    }

    private void assignKeyFamilyToShard(DBDetail dbDetail) {
        dbDetail.getDbDetails().parallelStream()
                .filter(DBDetailsMeta::isShard)
                .map(this::assignKeyFamilyToShardDB)
                .collect(Collectors.toList());
    }

    private DBDetailsMeta configureKeyNameForShard(String shardPrefix, String keySeperator, DBDetailsMeta dbDetailsMeta) {
        dbDetailsMeta.getShardDetails().parallelStream()
                .map(dbShardMeta -> {
                    dbShardMeta.setKeyName(dbDetailsMeta.getDbName() + keySeperator + shardPrefix + dbShardMeta.getId() + keySeperator + dbDetailsMeta.getKeyFamily());
                    return dbShardMeta;
                })
                .collect(Collectors.toSet());
        return dbDetailsMeta;
    }

    private Set<DBShardMeta> assignKeyFamilyToShardDB(DBDetailsMeta dbDetailsMeta) {
        return dbDetailsMeta.getShardDetails().parallelStream()
                .filter(dbShardMeta -> StringUtils.isEmpty(dbShardMeta.getKeyFamily()))
                .map(dbShardMeta -> {
                    if (!StringUtils.isEmpty(dbDetailsMeta.getKeyFamily())) {
                        dbShardMeta.setKeyFamily(dbDetailsMeta.getKeyFamily());
                    }
                    return dbShardMeta;
                })
                .collect(Collectors.toSet());
    }

    private void assignKeyFamilyAndKeyNameToDetailMeta(DBDetail dbDetail) {
        dbDetail.getDbDetails().parallelStream()
                .filter(dbDetailsMeta -> StringUtils.isEmpty(dbDetailsMeta.getKeyFamily()))
                .map(dbDetailsMeta -> {
                    dbDetailsMeta.setKeyFamily(dbDetail.getDefaultKeyFamily());
                    return dbDetailsMeta;
                })
                .collect(Collectors.toList());
        dbDetail.getDbDetails().parallelStream()
                .filter(dbDetailsMeta -> !dbDetailsMeta.isShard())
                .map(dbDetailsMeta -> {
                    dbDetailsMeta.setKeyName(dbDetailsMeta.getDbName()+"."+dbDetailsMeta.getKeyFamily());
                    return dbDetailsMeta;
                })
                .collect(Collectors.toList());
    }

}
