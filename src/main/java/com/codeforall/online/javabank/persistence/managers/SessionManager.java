package com.codeforall.online.javabank.persistence.managers;

import jakarta.persistence.EntityManager;

/**
 * Common interface for a Session Manager
 * @param <T>
 */
public interface SessionManager<T> {

    /**
     * Starts a session
     */
    void startSession();

    /**
     * Stops a session
     */
    void stopSession();

    /**
     * Gets the current session
     * @return an entity manager to perform the requests to the database
     */
    EntityManager getCurrentSession();
}
