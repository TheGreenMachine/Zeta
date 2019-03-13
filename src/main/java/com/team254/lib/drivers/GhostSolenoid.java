package com.team254.lib.drivers;

public class GhostSolenoid implements ISolenoid {

    private boolean mState;
    @Override
    public boolean get() {
        return mState;
    }

    @Override
    public void set(boolean on) {
        mState = on;
    }
}
