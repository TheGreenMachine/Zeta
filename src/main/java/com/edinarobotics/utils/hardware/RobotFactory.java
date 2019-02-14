package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.HashMap;
import java.util.Map;

public class RobotFactory {

    private YamlConfiguration config;

    public RobotFactory(String configName) {
        Yaml yaml = new Yaml(new Constructor(YamlConfiguration.class));
        config = yaml.load(
            this.getClass()
                .getClassLoader()
                .getResourceAsStream(configName + ".config.yml")
        );
    }

    public boolean isImplemented(String subsystem) {
        return (getSubsystem(subsystem) != null) && (getSubsystem(subsystem).implemented);
    }

    public IMotorController getMotor(String subsystem, String name) {
        if (!isImplemented(subsystem)) return CtreMotorFactory.createGhostTalon();
        YamlConfiguration.SubsystemConfig subsystemConfig = getSubsystem(subsystem);
        if (
            subsystemConfig.talons.get(name) != null &&
            subsystemConfig.talons.get(name) > -1
        ) {
            return CtreMotorFactory.createDefaultTalon(subsystemConfig.talons.get(name));
        } else if (
            subsystemConfig.victors.get(name) != null &&
            subsystemConfig.victors.get(name) > -1
        ) {
            return CtreMotorFactory.createDefaultVictor(subsystemConfig.victors.get(name));
        }
        return CtreMotorFactory.createGhostTalon();
    }

    public IMotorController getMotor(String subsystem, String name, IMotorController master) {
        if (!isImplemented(subsystem)) return CtreMotorFactory.createGhostTalon();
        YamlConfiguration.SubsystemConfig subsystemConfig = getSubsystem(subsystem);
        if (
            subsystemConfig.talons.get(name) != null &&
            subsystemConfig.talons.get(name) > -1 &&
            master != null
        ) {
            // Talons must be following another Talon, cannot follow a Victor.
            return CtreMotorFactory.createPermanentSlaveTalon(
                    subsystemConfig.talons.get(name), master
            );
        } else if (
            subsystemConfig.victors.get(name) != null &&
            subsystemConfig.victors.get(name) > -1
        ) {
            // Victors can follow Talons or another Victor.
            if (master != null) {
                return CtreMotorFactory.createPermanentSlaveVictor(
                        subsystemConfig.victors.get(name), master
                );
            }
        }
        return CtreMotorFactory.createGhostTalon();
    }

    public Solenoid getSolenoid(String subsystem, String name) {
        Integer solenoidId = getSubsystem(subsystem).solenoids.get(name);
        if (solenoidId != null) {
            return new Solenoid(config.pcm, solenoidId);
        }
        return null;
    }

    public DoubleSolenoid getDoubleSolenoid(String subsystem, String name) {
        YamlConfiguration.DoubleSolenoidConfig solenoidConfig = getSubsystem(subsystem).doubleSolenoids.get(name);
        if (solenoidConfig != null) {
            return new DoubleSolenoid(config.pcm, solenoidConfig.forward, solenoidConfig.reverse);
        }
        return null;
    }

    public Double getConstant(String name) {
        return config.constants.get(name);
    }

    public Double getConstant(String subsystem, String name) {
        return getSubsystem(subsystem).constants.get(name);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration.SubsystemConfig getSubsystem(String subsystem) {
        return config.subsystems.get(subsystem);
    }

    public static class YamlConfiguration {
        public Map<String, SubsystemConfig> subsystems;
        public Map<String, Double> constants = new HashMap<>();
        public int pcm;

        public static class SubsystemConfig {
            public boolean implemented = false;
            public Map<String, Integer> talons = new HashMap<>();
            public Map<String, Integer> victors = new HashMap<>();
            public Map<String, Integer> solenoids = new HashMap<>();
            public Map<String, DoubleSolenoidConfig> doubleSolenoids = new HashMap<>();
            public Map<String, Double> constants = new HashMap<>();

            @Override
            public String toString() {
                return "Subsystem {\n" +
                        "  implemented = " + implemented + ",\n" +
                        "  talons = " + talons.toString() + ",\n" +
                        "  victors = " + victors.toString() + ",\n" +
                        "  solenoids = " + solenoids.toString() + ",\n" +
                        "  doubleSolenoids = " + doubleSolenoids.toString() + ",\n" +
                        "  constants = " + constants.toString() + ",\n" +
                        "}";
            }
        }

        public static class DoubleSolenoidConfig {
            public int forward;
            public int reverse;

            @Override
            public String toString() {
                return String.format("{ forward: %d, reverse: %d }", forward, reverse);
            }
        }

        @Override
        public String toString() {
            return "YamlConfiguration {\n  subsystems = " + subsystems.toString() +
                    "\n  pcm = " + pcm + "\n  constants = " + constants.toString( )+ "\n}";
        }
    }
}
