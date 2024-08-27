package com.app.restserver.persistance.queue;

public interface QueueDispatcherStrategy {
    void put(String job);
}
