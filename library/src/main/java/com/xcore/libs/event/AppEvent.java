package com.xcore.libs.event;

public class AppEvent {

    private int state;

    public AppEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
