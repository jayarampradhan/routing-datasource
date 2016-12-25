package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.ShardDbContext;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * A hacky way of enabling the singleton and accessing the spring bean inside the aspectj.
 *
 * @author Jayaram
 *         2/23/16.
 */
public class ShardDbContextLocatorHelper {

    private static ShardDbContextLocator shardDbContextLocator;

    public ShardDbContextLocatorHelper(ShardDbContextLocator shardDbContextLocator){
        ShardDbContextLocatorHelper.shardDbContextLocator = shardDbContextLocator;
    }

    public static ShardDbContext lookUpContext(ProceedingJoinPoint pjp, String dbName){
        return getShardDbContextLocator().lookUpContext(pjp, dbName);
    }

    public static ShardDbContextLocator getShardDbContextLocator() {
        if(null == shardDbContextLocator){
            shardDbContextLocator = new DefaultShardDbContextLocator();
        }
        return shardDbContextLocator;
    }
}
