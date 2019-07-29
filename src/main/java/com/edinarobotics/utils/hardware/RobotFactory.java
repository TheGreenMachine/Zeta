package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotFactory {

    private YamlConfig config;

    public RobotFactory(String configName) {
        System.out.println("Loading Config for " + configName);
        config = YamlConfig.loadFrom(
            this.getClass()
                .getClassLoader()
                .getResourceAsStream(configName + ".config.yml")
        );
    }

    public boolean isImplemented(String subsystem) {
        return (getSubsystem(subsystem) != null) && (getSubsystem(subsystem).implemented);
    }

    public IMotorControllerEnhanced getMotor(String subsystemName, String name) {
        if (isImplemented(subsystemName)) {
            YamlConfig.SubsystemConfig subsystem = getSubsystem(subsystemName);
            if (isHardwareValid(subsystem.talons.get(name))) {
                return CtreMotorFactory.createDefaultTalon(subsystem.talons.get(name));
            } //Never make the victor a master
        }
        return CtreMotorFactory.createGhostTalon();
    }

    public IMotorController getMotor(String subsystemName, String name, IMotorController master) {
        if (isImplemented(subsystemName) && master != null) {
            YamlConfig.SubsystemConfig subsystem = getSubsystem(subsystemName);
            if (isHardwareValid(subsystem.talons.get(name))) {
                // Talons must be following another Talon, cannot follow a Victor.
                return CtreMotorFactory.createPermanentSlaveTalon(
                    subsystem.talons.get(name), master
                );
            } else if (isHardwareValid(subsystem.victors.get(name))) {
                // Victors can follow Talons or another Victor.
                return CtreMotorFactory.createPermanentSlaveVictor(
                    subsystem.victors.get(name), master
                );
            }
        }
        return CtreMotorFactory.createGhostTalon();
    }

    private boolean isHardwareValid(Integer hardwareId) {
        return hardwareId != null && hardwareId > -1;
    }

    public Solenoid getSolenoid(String subsystem, String name) {
        Integer solenoidId = getSubsystem(subsystem).solenoids.get(name);
        if (isHardwareValid(solenoidId)) {
            return new Solenoid(config.pcm, solenoidId);
        }
        return null;
    }

    public DoubleSolenoid getDoubleSolenoid(String subsystem, String name) {
        YamlConfig.DoubleSolenoidConfig solenoidConfig = getSubsystem(subsystem).doublesolenoids.get(name);
        if (
            solenoidConfig != null
            && isHardwareValid(solenoidConfig.forward)
            && isHardwareValid(solenoidConfig.reverse)
        ) {
            return new DoubleSolenoid(config.pcm, solenoidConfig.forward, solenoidConfig.reverse);
        }
        return null;
    }

    public CANifier getCanifier(String subsystem) {
        if (isImplemented(subsystem) && getSubsystem(subsystem).canifier != null) {
            return new CANifier(getSubsystem(subsystem).canifier);
        }
        return null;
    }

    public Double getConstant(String name) {
        return config.constants.get(name);
    }

    public Double getConstant(String subsystem, String name) {
        return getSubsystem(subsystem).constants.get(name);
    }

    public YamlConfig getConfig() {
        return config;
    }

    public YamlConfig.SubsystemConfig getSubsystem(String subsystem) {
        return config.subsystems.get(subsystem);
    }

}
