package com.example.demo.utils.observer;

import com.example.demo.utils.event.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
