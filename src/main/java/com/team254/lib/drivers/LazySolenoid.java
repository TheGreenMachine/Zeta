package com.team254.lib.drivers;

import edu.wpi.first.wpilibj.Solenoid;

public class LazySolenoid extends Solenoid implements ISolenoid {

    public LazySolenoid(int module, int channel) {
        super(module, channel);
    }
}
