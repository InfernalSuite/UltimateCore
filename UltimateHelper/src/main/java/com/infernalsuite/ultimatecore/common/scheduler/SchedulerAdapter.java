package com.infernalsuite.ultimatecore.common.scheduler;

import java.util.concurrent.*;

/**
 * A scheduler for running tasks
 */
public interface SchedulerAdapter {

    /**
     * Gets an async executor service
     * @return an async executor instance
     */
    Executor async();

    /**
     * Gets a sync executor service
     * @return a sync executor instance
     */
    Executor sync();

    /**
     * Executes a task async
     * @param task the task
     */
    default void executeAsync(Runnable task) { async().execute(task); }

    /**
     * Executes a task sync
     * @param task the task
     */
    default void executeSync(Runnable task) { sync().execute(task); }

    /**
     * Executes the given task with a delay.
     * @param task the task
     * @param delay the delay
     * @param unit the unit of delay
     * @return the resultant task instance
     */
    SchedulerTask asyncLater(Runnable task, long delay, TimeUnit unit);

    /**
     * Executes the given task repeatedly at a given interval.
     * @param task the task
     * @param interval the interval
     * @param unit the unit of interval
     * @return the resultant task instance
     */
    SchedulerTask asyncRepeating(Runnable task, long interval, TimeUnit unit);

    /**
     * Shuts down the scheduler instance.
     * <p>{@link #asyncRepeating(Runnable, long, TimeUnit) asyncRepeating} and {@link #asyncLater(Runnable, long, TimeUnit) asyncLater}</p>
     */
    void shutdownScheduler();

    /**
     * Shuts down the executor instance.
     * <p>{@link #async()} and {@link #executeAsync(Runnable)}</p>
     */
    void shutdownExecutor();

}
