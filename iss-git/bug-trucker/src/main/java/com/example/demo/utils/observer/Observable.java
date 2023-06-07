package com.example.demo.utils.observer;

import com.example.demo.utils.event.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> observer);

    void removeObserver(Observer<E> observer);

    void notifyObservers(E t);
}
