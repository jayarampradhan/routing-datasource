package com.uimirror.framework.db.shard.model;


/**
 * A default implementation of the {@link ShardDbContext}
 * which holds the current db context information.
 *
 * @author Jayaram
 *         3/26/16.
 */
public class DefaultShardDbContext implements ShardDbContext{

    private final Integer dbSeqNum;
    private final String dbName;
    private final DBShardMeta.DBType dbType;

    private DefaultShardDbContext(Builder bldr){
        this.dbName = bldr.dbName;
        this.dbSeqNum = bldr.dbSeqNum;
        this.dbType = bldr.dbType;
    }

    @Override
    public Integer dbSeqNum() {
        return dbSeqNum;
    }

    @Override
    public String name() {
        return dbName;
    }

    @Override
    public DBShardMeta.DBType type() {
        return dbType;
    }

    public static class Builder{
        private Integer dbSeqNum;
        private String dbName;
        private DBShardMeta.DBType dbType;

        public Builder(String name){
            this.dbName = name;
        }

        public Builder withSequenceNum(Integer sequenceNum){
            this.dbSeqNum = sequenceNum;
            return this;
        }

        public Builder withDbType(DBShardMeta.DBType dbType){
            this.dbType = dbType;
            return this;
        }

        public ShardDbContext build(){
            return new DefaultShardDbContext(this);
        }
    }
}
