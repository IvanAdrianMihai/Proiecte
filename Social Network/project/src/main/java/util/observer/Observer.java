package util.observer;

import util.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}