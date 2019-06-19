package com.edinarobotics.utils.hardware;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// Since the Collections of configurations are injected by SnakeYaml,
// IDEs will report that the collections are never updated.
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class YamlConfig {
    Map<String, SubsystemConfig> subsystems;
    Map<String, Double> constants = new HashMap<>();
    int pcm;

    public static YamlConfig loadFrom(InputStream input) {
        Yaml yaml = new Yaml(new Constructor(YamlConfig.class));
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.load(input);
    }

    public static class SubsystemConfig {
        boolean implemented = false;
        Map<String, Integer> talons = new HashMap<>();
        Map<String, Integer> victors = new HashMap<>();
        Map<String, Integer> solenoids = new HashMap<>();
        Map<String, DoubleSolenoidConfig> doublesolenoids = new HashMap<>();
        Map<String, Double> constants = new HashMap<>();
        Integer canifier;

        @Override
        public String toString() {
            return "SubsystemConfig {\n" +
                    "  implemented = " + implemented + ",\n" +
                    "  talons = " + talons.toString() + ",\n" +
                    "  victors = " + victors.toString() + ",\n" +
                    "  solenoids = " + solenoids.toString() + ",\n" +
                    "  doublesolenoids = " + doublesolenoids.toString() + ",\n" +
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
        return "YamlConfig {\n  subsystems = " + subsystems.toString() +
                "\n  pcm = " + pcm + "\n  constants = " + constants.toString( )+ "\n}";
    }
}
