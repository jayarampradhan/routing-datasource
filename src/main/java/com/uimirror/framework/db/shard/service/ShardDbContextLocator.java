package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.ShardDbContext;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * An implementer should give logic of creating a ShardDbContext based on the shard db number or
 * the current joint point.
 *
 * @author Jayaram
 *         2/23/16.
 */
public interface ShardDbContextLocator {

    /**
     * Look Up for the DbContext from a dbSequence number
     * @param pjp Current JoinPoint to be used to get the exact db sequence number.
     * @param dbName DB name to be used for choosing the right context.
     * @return {@link ShardDbContext} to be used.
     * @throws IllegalStateException when implementation has been provided.
     */
    default ShardDbContext lookUpContext(ProceedingJoinPoint pjp, String dbName){
        throw new IllegalStateException("No Implementation has been provided yet!");
    }

}
