package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

class RobotFactoryTest {
    private RobotFactory robotFactory = new RobotFactory("zeta");

    @Test
    void streamExists_zeta() throws IOException {
        try (InputStream is = RobotFactory.class
                .getClassLoader().getResourceAsStream("zeta.config.yml")) {
            assertNotNull(is);
            byte[] read = is.readAllBytes();
            System.out.println(Arrays.toString(read).substring(0, 100) + "...]");
            assertTrue(read.length > 0);
        }
    }

    @Test
    void configLoads_zeta() {
        System.out.println(robotFactory.getConfig());
        assertNotNull(robotFactory.getConfig());
    }

    @Test
    void implementedTrue_zetaDrivetrain() {
        assertTrue(robotFactory.isImplemented("drivetrain"));
    }

    @Test
    void talonsDefined_zetaDrivetrain() {
        assertTrue(
                robotFactory.getSubsystem("drivetrain").talons.values().size() > 0
        );
    }

    @Test
    @Ignore
    void definedTalonReturnsTalon_zetaDrivetrain() {
        assertTrue(
                "Talon defined in config does not return TalonSRX",
                robotFactory.getMotor("drivetrain", "leftMain") instanceof TalonSRX
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_undefinedSubsystem_zetaCargoShooter() {
        IMotorController talon = robotFactory.getMotor("CargoShooter", "arm");
        System.out.println(talon);
        assertTrue(
                "Talon undefined in undefined subsystem does not return GhostTalonSRX",
                talon instanceof GhostTalonSRX
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_definedSubsystem_zetaDrivetrain() {
        IMotorController talon = robotFactory.getMotor("drivetrain", "undefined");
        System.out.println(talon);
        assertTrue(
                "Talon undefined in defined subsystem does not return GhostTalonSRX",
                talon instanceof GhostTalonSRX
        );
    }

    @Test
    void definedTalonReturnsGhostTalon_unimplSubsystem_zetaClimber() {
        IMotorController talon = robotFactory.getMotor("climber", "one");
        System.out.println(talon);
        assertTrue(
                "Talon defined in unimplemented subsystem does not return GhostTalonSRX",
                talon instanceof GhostTalonSRX
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_unimplSubsystem_zetaClimber() {
        IMotorController talon = robotFactory.getMotor("climber", "two");
        System.out.println(talon);
        assertTrue(
                "Talon undefined in unimplemented subsystem does not return GhostTalonSRX",
                talon instanceof GhostTalonSRX
        );
    }

    @Test
    void getConstant_notNull_zetaTicksPerRev() {
        Double ticksPerRev = robotFactory.getConstant("ticksPerRev");
        System.out.println(ticksPerRev);
        assertNotNull(ticksPerRev);
    }
}
