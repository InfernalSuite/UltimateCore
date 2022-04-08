package com.infernalsuite.ultimatecore.common.scheduler;

/**
 * Represents a scheduled task
 */
@FunctionalInterface
public interface SchedulerTask {

    /**
     * Cancels the task
     */
    void cancel();

}
