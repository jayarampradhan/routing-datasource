package com.uimirror.framework.db.shard;


import com.uimirror.framework.db.shard.model.ShardDbContext;
import com.uimirror.framework.db.shard.model.ShardDbContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * For Shard Databases this will act as store of the different datasource.
 * Example to use as shown below
 * <pre>
 *    {@code
 *       Bean
 *       Autowired
 *        public DataSource dataSource(){
 *               RoutingDataSource source = new RoutingDataSource();
 *               //Her Datasource method should return Map of different shard connections.
 *               Map&lt;Object, Object&gt; dataSources = dataSources();
 *               source.setTargetDataSources(dataSources);
 *               source.setDefaultTargetDataSource(dataSources.get(CreditUserShard.USER_SHARD_2.dbSeqNum()));
 *               return source;
 *        }
 *    }
 * </pre>
 *
 * @author Jayaram
 *         2/23/16.
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(RoutingDataSource.class);

    /**
     * Helps to identify the look up for choosing the right shard before opening the transaction
     * or doing database operation.
     * @return any key, which has been used as key for keeping the datasource map
     */
    @Override
    protected Object determineCurrentLookupKey() {
        LOG.info("[START]- Determining the current DB type/seq number to choose the right connection.");
        ShardDbContext context = ShardDbContextHolder.getCurrentDbContext();
        if(context == null){
            LOG.info("[END]- No DB Context is bounded falling back to the default connection");
            return null;
        }
        Object key = determineKey(context);
        LOG.info("[END]- DB type/seq number: {} found for the current transaction.", key);
        return key;
    }

    /**
     * Checks the {@link ShardDbContext#type()} if it has the key, if no key found in that
     * will fall back for the {@link ShardDbContext#dbSeqNum()}
     * @param context current context {@link ShardDbContext}
     * @return either dbType or sequence number to be used.
     */
    private Object determineKey(ShardDbContext context){
        if(context.type() != null){
            return context.type();
        }else {
            return context.dbSeqNum();
        }
    }
}
