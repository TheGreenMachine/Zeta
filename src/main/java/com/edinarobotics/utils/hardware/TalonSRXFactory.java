package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonSRXFactory {

    public IMotorControllerEnhanced createTalon(int port) {
        TalonSRX talon = new TalonSRX(port);
        talon.set(ControlMode.PercentOutput, 0.0);
        talon.setNeutralMode(NeutralMode.Brake);
        return talon;
    }

    public IMotorControllerEnhanced createSlaveTalon(int port, int masterPort) {
        TalonSRX talon = new TalonSRX(port);
        talon.set(ControlMode.Follower, masterPort);
        talon.setNeutralMode(NeutralMode.Brake);
        return talon;
    }

    public IMotorControllerEnhanced createGhostTalon() {
        return new GhostTalonSRX();
    }
}
