package com.uimirror.framework.db.shard.model;


/**
 * Any application which needs DB Connection i.e {@link javax.sql.DataSource} to be routed at runtime while
 * transaction begins, this works as key to store the datasource.
 * Ideal way of implementing this will be create an enum and implement this.
 * <p>
 * The key point to note here is {@link ShardDbContext#dbSeqNum()} which will help to determine the datasource
 * while doing a look up when {@link DBDetailsMeta.ShardType#OTHER}.
 *
 * when {@link DBDetailsMeta.ShardType#MASTER_SLAVE}, then {@link #type()}
 * helps to identify the datasource.
 * </p>
 *
 * @author Jayaram
 *         2/23/16.
 */
public interface ShardDbContext {

    /**
     * Defines the DB sequence number, represented by 1,2,3...N
     * @return an integer representation of the db sequence
     */
    default Integer dbSeqNum(){
        return null;
    }

    /**
     * Specifies the name of the data base.
     * @return name of the data base.
     */
    String name();

    /**
     * Specifies the type of the shard this context is, it can be any of the type
     * {@link DBShardMeta.DBType#MASTER} or {@link DBShardMeta.DBType#SLAVE}
     * @return {@link DBShardMeta.DBType}
     */
    default DBShardMeta.DBType type(){return null;}

}

