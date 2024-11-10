package com.codeus.winter.context;

import javax.annotation.Nullable;

public interface ApplicationContext {

    /**
     * Return the unique id of this application context.
     *
     * @return the unique id of the context, or {@code null} if none
     */
    @Nullable
    String getId();

    /**
     * Return a name for the deployed application that this context belongs to.
     *
     * @return a name for the deployed application, or the empty String by default
     */
    String getApplicationName();

    /**
     * Return a friendly name for this context.
     *
     * @return a display name for this context (never {@code null})
     */
    String getDisplayName();

    /**
     * Return the timestamp when this context was first loaded.
     *
     * @return the timestamp (ms) when this context was first loaded
     */
    long getStartupDate();
}
