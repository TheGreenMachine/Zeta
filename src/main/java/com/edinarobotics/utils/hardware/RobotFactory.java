package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.IMotorController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.util.HashMap;
import java.util.Map;

public class RobotFactory {

    private YamlConfiguration config;

    public RobotFactory(String configName) {
        System.out.println("Loading Config for "+ configName);
        Yaml yaml = new Yaml(new Constructor(YamlConfiguration.class));
        yaml.setBeanAccess(BeanAccess.FIELD);
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

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration.SubsystemConfig getSubsystem(String subsystem) {
        return config.subsystems.get(subsystem);
    }

    // Since the Collections of configurations are injected by SnakeYaml,
    // IDEs will report that the collections are never updated.
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static class YamlConfiguration {
        Map<String, SubsystemConfig> subsystems;
        Map<String, Double> constants = new HashMap<>();
        int pcm;

        public static class SubsystemConfig {
            boolean implemented = false;
            Map<String, Integer> talons = new HashMap<>();
            Map<String, Integer> victors = new HashMap<>();
            Map<String, Integer> solenoids = new HashMap<>();
            Map<String, DoubleSolenoidConfig> doubleSolenoids = new HashMap<>();
            Map<String, Double> constants = new HashMap<>();
            Integer canifier;

            @Override
            public String toString() {
                return "Subsystem {\n" +
                        "  implemented = " + implemented + ",\n" +
                        "  talons = " + talons.toString() + ",\n" +
                        "  victors = " + victors.toString() + ",\n" +
                        "  solenoids = " + solenoids.toString() + ",\n" +
                        "  doubleSolenoids = " + doubleSolenoids.toString() + ",\n" +
                        "  canifier = " + canifier + ",\n" +
                        "  constants = " + constants.toString() + ",\n" +
                        "}";
            }
        }

        public static class DoubleSolenoidConfig {
            int forward;
            int reverse;

            @Override
            public String toString() {
                return String.format("{ forward = %d, reverse = %d }", forward, reverse);
            }
        }

        @Override
        public String toString() {
            return "YamlConfiguration {\n  subsystems = " + subsystems.toString() +
                    "\n  pcm = " + pcm + "\n  constants = " + constants.toString( )+ "\n}";
        }
    }
}
