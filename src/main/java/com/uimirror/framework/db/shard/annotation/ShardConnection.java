package com.uimirror.framework.db.shard.annotation;


import com.uimirror.framework.db.shard.model.DBDetailsMeta;
import com.uimirror.framework.db.shard.model.DBShardMeta;

import java.lang.annotation.*;

/**
 *
 * When there are different data source with ach has its own sharding, then this annotation
 * helps to identify which shard datasource to use.
 *
 *  @author Jayaram
 *         2/23/16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface ShardConnection {

    /**
     * Specifies the connection name/data base name.
     * @return name of the connection.
     */
    String value();

    /**
     * Specifies the type of data base shard such as {@link DBDetailsMeta.ShardType#MASTER_SLAVE}
     * or {@link DBDetailsMeta.ShardType#OTHER}
     * in case of {@link DBDetailsMeta.ShardType#OTHER} it requires and context lookup, which should
     * return db number to use
     * @return {@link DBDetailsMeta.ShardType}
     */
    DBDetailsMeta.ShardType shardType() default DBDetailsMeta.ShardType.OTHER;

    /**
     * In case {@link #shardType()} is of type {@link DBDetailsMeta.ShardType#MASTER_SLAVE}
     * then it helps to identify either to use {@link DBShardMeta.DBType#MASTER}
     * or {@link DBShardMeta.DBType#SLAVE}
     *
     * @return {@link DBShardMeta.DBType}
     */
    DBShardMeta.DBType use() default DBShardMeta.DBType.MASTER;
}
