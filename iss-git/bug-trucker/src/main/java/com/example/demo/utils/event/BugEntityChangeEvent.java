package com.example.demo.utils.event;

import com.example.demo.model.Bug;

public class BugEntityChangeEvent implements Event{
    private ChangeEventType type;

    private Bug old_data;

    private Bug data;

    public BugEntityChangeEvent(ChangeEventType type, Bug data) {
        this.type = type;
        this.data = data;
    }

    public BugEntityChangeEvent(ChangeEventType type, Bug old_data, Bug data) {
        this.type = type;
        this.old_data = old_data;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Bug getOld_data() {
        return old_data;
    }

    public Bug getData() {
        return data;
    }
}
