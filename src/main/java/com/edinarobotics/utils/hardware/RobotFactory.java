package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.HashMap;
import java.util.Map;

public class RobotFactory {

    private Configuration config;

    public RobotFactory(String configName) {
        Yaml yaml = new Yaml(new Constructor(Configuration.class));
        config = yaml.load(
            this.getClass()
                .getClassLoader()
                .getResourceAsStream(configName + ".config.yml")
        );
    }

    public boolean isImplemented(String subsystem) {
        return (getSubsystem(subsystem) != null) && (getSubsystem(subsystem).implemented);
    }

    public IMotorControllerEnhanced getTalon(String subsystem, String name) {
        if (
            isImplemented(subsystem) &&
            getSubsystem(subsystem).talons.get(name) != null &&
            getSubsystem(subsystem).talons.get(name) > -1
        ) {
            return TalonSRXFactory.createDefaultTalon(getSubsystem(subsystem).talons.get(name));
        }
        return TalonSRXFactory.createGhostTalon();
    }

    public IMotorControllerEnhanced getTalon(String subsystem, String name, String master) {
        if (
            isImplemented(subsystem) &&
            getSubsystem(subsystem).talons.get(name) != null &&
            getSubsystem(subsystem).talons.get(name) > -1 &&
            getSubsystem(subsystem).talons.get(master) != null &&
            getSubsystem(subsystem).talons.get(master) > -1
        ) {
            return TalonSRXFactory.createPermanentSlaveTalon(getSubsystem(subsystem).talons.get(name), getSubsystem(subsystem).talons.get(master));
        }
        return TalonSRXFactory.createGhostTalon();
    }

    public Configuration getConfig() {
        return config;
    }

    public Configuration.SubsystemConfig getSubsystem(String subsystem) {
        return config.subsystems.get(subsystem);
    }

    public static class Configuration {
        public Map<String, SubsystemConfig> subsystems;

        public static class SubsystemConfig {
            public boolean implemented = false;
            public Map<String, Integer> talons = new HashMap<>();

            @Override
            public String toString() {
                return "Subsystem {\n" +
                        "  implemented = " + implemented + ",\n" +
                        "  talons = " + talons.toString() + ",\n" +
                        "}";
            }
        }

        @Override
        public String toString() {
            return "Configuration {\n  subsystems = " + subsystems.toString() + "\n}";
        }
    }
}
