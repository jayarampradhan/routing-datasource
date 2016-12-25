package com.uimirror.framework.db.shard;


import com.uimirror.framework.db.shard.annotation.ShardConnection;
import com.uimirror.framework.db.shard.model.DBDetailsMeta;
import com.uimirror.framework.db.shard.model.DefaultShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContextHolder;
import com.uimirror.framework.db.shard.service.ShardDbContextLocatorHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

/**
 * Sometimes when you decided based on the transactional method args, to choose the {@link ShardDbContext}.
 * Make use {@link ShardConnection} and register this bean to locate, store and clear the context around the method.
 *
 * {@link DeclarePrecedence} Will make sure this annotation will be invoked first prior to {@link org.springframework.transaction.annotation.Transactional}
 *
 * @author Jayaram
 *         2/23/16.
 */
@Aspect
@DeclarePrecedence(value = "com.uimirror.framework.db.shard.ShardedConnectionInterceptor, org.springframework.transaction.aspectj.AnnotationTransactionAspect, org.springframework.transaction.aspectj.JtaAnnotationTransactionAspect, *")
@Order(1)
@Configurable(autowire = Autowire.BY_TYPE)
public class ShardedConnectionInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ShardedConnectionInterceptor.class);


    /**
     * Not Sure Why adding execution(* *(..)) fixed the advice calling twice
     * <a href="http://forum.spring.io/forum/spring-projects/aop/74520-advice-around-annotation-gets-executed-twice-aspectj-with-ltw">Advice Calling Twice</a>
     * @param pjp {@link ProceedingJoinPoint}
     * @param shardConnection {@link ShardConnection}
     * @return returned value of the execution point
     * @throws Throwable in case of any exception
     */
    @Around("execution(* *(..)) && @annotation(shardConnection)")
    public Object proceed(ProceedingJoinPoint pjp, ShardConnection shardConnection) throws Throwable {
        LOG.debug("[START]- Deciding the Shard DB Context to use.");
        String dbName = shardConnection.value();
        Assert.notNull(dbName, "Invalid Data Base Name.");
        try {
            ShardDbContext shardDbContext = lookUpContext(pjp, shardConnection);
            ShardDbContextHolder.addCurrentDbContext(shardDbContext);
            LOG.debug("[INTERIM]- Shard DB Context has been initialized.");
            return pjp.proceed();
        } finally {
            clearDbContext();
            LOG.debug("[INTERIM]- Cleared Shard DB Context.");
        }
    }

    private ShardDbContext lookUpContext(ProceedingJoinPoint pjp, ShardConnection shardConnection){
        DBDetailsMeta.ShardType shardType = shardConnection.shardType();
        ShardDbContext context;
        if(shardType == DBDetailsMeta.ShardType.MASTER_SLAVE){
            context = new DefaultShardDbContext.Builder(shardConnection.value())
                    .withDbType(shardConnection.use())
                    .build();

        }else {
            LOG.debug("[INTERIM]- Looking up for the context from the joint point");
            context = ShardDbContextLocatorHelper.lookUpContext(pjp, shardConnection.value());
            LOG.debug("[INTERIM]- Found context: {} from the joint point",context);
        }
        return context;
    }

    private void clearDbContext(){
        if(ShardDbContextHolder.isInSingleState()){
            ShardDbContextHolder.clearDbContext();
        }else{
            LOG.debug("[INTERIM]- Cleared All Shard DB Context from the context.");
            ShardDbContextHolder.removeCurrentDbContext();
        }
    }



}
