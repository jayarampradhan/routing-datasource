package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.RoutingDataSource;
import com.uimirror.framework.db.shard.model.DBConnectionConfigMeta;
import com.uimirror.framework.db.shard.model.DBConnectionMeta;
import com.uimirror.framework.db.shard.model.DBDetailsMeta;
import com.uimirror.framework.db.shard.model.DBShardMeta;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jayaram
 *         6/19/16.
 */
public abstract class AbstractDatasourceConfig {

    protected BasicDataSource createNonShardDataSource(DBDetailsMeta db, String dbDriver) {
        DBConnectionMeta connection = db.getConnection();
        return buildBasicDataSource(connection, dbDriver);
    }

    private BasicDataSource createShardDataSource(DBShardMeta db, String dbDriver) {
        DBConnectionMeta connection = db.getConnection();
        return buildBasicDataSource(connection, dbDriver);
    }

    private BasicDataSource buildBasicDataSource(DBConnectionMeta connection, String dbDriver) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(dbDriver);
        source.setUsername(connection.getUserName());
        source.setPassword(connection.getPassword());
        source.setUrl(connection.getUrl());
        configureConnection(source, connection.getConnectionConfig());
        return source;
    }

    protected RoutingDataSource createShardDataSource(DBDetailsMeta db, DBDetailsMeta.ShardType shardType, String dbDriver) {
        Set<DBShardMeta> shardDetails = db.getShardDetails();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        RoutingDataSource source = new RoutingDataSource();
        for(DBShardMeta meta : shardDetails){
            Object key;
            if(shardType == DBDetailsMeta.ShardType.MASTER_SLAVE){
                key = meta.getConnectionType();
            }else {
                key = meta.getId();
            }
            dataSourceMap.put(key, createShardDataSource(meta, dbDriver));
            if(meta.isDefaultConnection()){
                if(meta.getConnectionType() != null){
                    source.setDefaultTargetDataSource(dataSourceMap.get(meta.getConnectionType()));
                }else {
                    source.setDefaultTargetDataSource(dataSourceMap.get(meta.getId()));
                }
            }
        }
        source.setTargetDataSources(dataSourceMap);
        return source;
    }

    private void configureConnection(BasicDataSource source, DBConnectionConfigMeta config) {
        if(config == null){
            return;
        }
        Integer maxActive;
        if((maxActive = config.getMaxActive()) != null )
            source.setMaxTotal(maxActive);
        Boolean default_auto_commit;
        if((default_auto_commit = config.getDefaultAutoCommit()) != null)
            source.setDefaultAutoCommit(default_auto_commit);
        String validationQuery;
        if((validationQuery = config.getValidationQuery()) != null)
            source.setValidationQuery(validationQuery);

        if(config.getMaxIdle() != null){
            source.setMaxIdle(config.getMaxIdle());
        }

        if(config.getDefaultQueryTimeout() != null){
            source.setDefaultQueryTimeout(config.getDefaultQueryTimeout());
        }

        if(config.getMaxWait() != null){
            source.setMaxWaitMillis(config.getMaxWait());
        }
        if(config.getPooledPrepareStatement() != null){
            source.setPoolPreparedStatements(config.getPooledPrepareStatement());
        }
    }
}
