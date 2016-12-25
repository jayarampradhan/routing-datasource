package com.uimirror.framework.db.shard.service;


import com.uimirror.framework.db.shard.model.DefaultShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContext;
import com.uimirror.framework.db.shard.model.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * A default implementation of the {@link ShardDbContextLocator}
 * Which will scan all the argument to find if it has any context of type
 * {@link UserContext}, if found try to resolve the context.
 *
 * @author Jayaram
 *         3/26/16.
 */
public class DefaultShardDbContextLocator implements ShardDbContextLocator{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultShardDbContextLocator.class);
    @Override
    public ShardDbContext lookUpContext(ProceedingJoinPoint pjp, String dbName) {
        Assert.notNull(pjp, "Joint Point is invalid.");
        Object[] args = pjp.getArgs();
        Assert.notNull(args, "Joint Point doesn't have any valid argument to look up the context.");
        UserContext context = resolveRightContext(args);
        LOG.debug("[INTERIM]- Found context to use with sequence #{}",context.getShardNumber());
        return new DefaultShardDbContext.Builder(dbName)
                .withSequenceNum(context.getShardNumber())
                .build();
    }

    private UserContext resolveRightContext(Object[] args) {
        UserContext context = null;
        for(Object arg : args){
            if(arg instanceof UserContext){
                context = (UserContext) arg;
                break;
            }
        }
        Assert.notNull(context, "No Context found from the joint point");
        return context;
    }

}
