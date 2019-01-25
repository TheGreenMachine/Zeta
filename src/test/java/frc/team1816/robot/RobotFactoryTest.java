package frc.team1816.robot;

import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.edinarobotics.utils.hardware.GhostTalonSRX;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotFactoryTest {
    private RobotFactory robotFactory = RobotFactory.getInstance();

    @Test
    void streamExists_zeta() throws IOException {
        InputStream is = RobotFactory.class.getClassLoader().getResourceAsStream("zeta.config.yml");
        byte[] read = is.readAllBytes();
        System.out.println(read);
        assertTrue(read.length > 0);
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
                robotFactory.getConfig().subsystems
                        .get("drivetrain").talons.values().size() > 0
        );
    }

    @Test
    @Disabled
    void definedTalonReturnsTalon_zetaDrivetrain() {
        assertTrue(
                robotFactory.getTalon("drivetrain", "leftMain") instanceof TalonSRX,
                "Talon defined in config does not return TalonSRX"
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_undefinedSubsystem_zetaCargoShooter() {
        IMotorControllerEnhanced talon = robotFactory.getTalon("CargoShooter", "arm");
        System.out.println(talon);
        assertTrue(
                talon instanceof GhostTalonSRX,
                "Talon undefined in undefined subsystem does not return GhostTalonSRX"
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_definedSubsystem_zetaDrivetrain() {
        IMotorControllerEnhanced talon = robotFactory.getTalon("drivetrain", "undefined");
        System.out.println(talon);
        assertTrue(
                talon instanceof GhostTalonSRX,
                "Talon undefined in defined subsystem does not return GhostTalonSRX"
        );
    }

    @Test
    void definedTalonReturnsGhostTalon_unimplSubsystem_zetaClimber() {
        IMotorControllerEnhanced talon = robotFactory.getTalon("climber", "one");
        System.out.println(talon);
        assertTrue(
                talon instanceof GhostTalonSRX,
                "Talon defined in unimplemented subsystem does not return GhostTalonSRX"
        );
    }

    @Test
    void undefinedTalonReturnsGhostTalon_unimplSubsystem_zetaClimber() {
        IMotorControllerEnhanced talon = robotFactory.getTalon("climber", "two");
        System.out.println(talon);
        assertTrue(
                talon instanceof GhostTalonSRX,
                "Talon undefined in unimplemented subsystem does not return GhostTalonSRX"
        );
    }
}
