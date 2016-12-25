package com.uimirror.framework.db.shard.model;


import java.io.Serializable;

/**
 * A context holder of the user current pod and shard number.
 * @author Jayaram
 *         2/20/16.
 */
public class UserContext implements Serializable{
    private static final long serialVersionUID = -432147849186941848L;
    private final Long userId;
    private final int shardNumber;

    private UserContext(ContextBuilder builder){
        this.userId = builder.userId;
        this.shardNumber = builder.shardNumber;
    }

    public static class ContextBuilder{
        private Long userId;
        private int shardNumber;

        public ContextBuilder(Long userId){
            this.userId = userId;
        }


        public ContextBuilder usingShard(int shardNo){
            this.shardNumber = shardNo;
            return this;
        }

        public UserContext build(){
            return new UserContext(this);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public int getShardNumber() {
        return shardNumber;
    }
}
