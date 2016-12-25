package com.uimirror.framework.db.shard.model;


import org.springframework.util.Assert;

import java.util.Stack;

/**
 * A singleton pattern to provide thread local context to the data source look up while
 * choosing the connection object.
 *
 * The algorithim has been desgined as follows:
 * Lets say we have a paragraph
 * This (is so ( easy), so that) people ignores.
 * every opening braces has a enclosing close braces, similarly in the code flow one begining of the thread,
 * a method can call to the other thread and so on, each call chain has a different context.
 *
 * So it make sures, each method execution has its own DB context.
 *
 * Note **
 * The user of this library should focus on clearing out the threadloacl value, else it will cause in memory lekage.
 * As a best practice its necessary to clear the memory as an clean up activity, before current thread going to dead.
 *
 * @author Jayaram
 *         2/23/16.
 */
public class ShardDbContextHolder {

    /**
     * Stores the {@link ShardDbContext} in the current thread.
     */
    private static final ThreadLocal<Stack<ShardDbContext>> contextHolder = ThreadLocal.withInitial(Stack::new);

    /**
     * Stores the {@link ShardDbContext} for the current thread.
     * @param dbContext to be stored.
     */
    public static void addCurrentDbContext(ShardDbContext dbContext) {
        Assert.notNull(dbContext, "Not a valid DB Context.");
        Stack<ShardDbContext> shardDbContexts = contextHolder.get();
        //Assume a scenario, when single state transaction has removed everything from this
        //then its safe to initiate a new one.
        shardDbContexts = shardDbContexts==null ? new Stack<>() : shardDbContexts;
        shardDbContexts.push(dbContext);
        contextHolder.set(shardDbContexts);
    }

    /**
     * Returns the stored {@link ShardDbContext} for this thread.
     * @return {@link ShardDbContext} for the current thread.
     */
    public static ShardDbContext getCurrentDbContext() {
        Stack<ShardDbContext> shardDbContexts = contextHolder.get();
        return shardDbContexts.isEmpty()? null : shardDbContexts.peek();
    }

    /**
     * Will remove the current Db Context from the stack.
     */
    public static void removeCurrentDbContext(){
        Stack<ShardDbContext> shardDbContexts = contextHolder.get();
        shardDbContexts.pop();
        contextHolder.set(shardDbContexts);
    }

    /**
     * Clears the {@link ShardDbContext} stored for the current thread.
     */
    public static void clearDbContext() {
        contextHolder.remove();
    }

    /**
     * Checks if the current thread context is holding only one state.
     * @return <code>true</code> if it has one state only else <code>false</code>
     */
    public static boolean isInSingleState(){
        return contextHolder.get().size() == 1;
    }

}
