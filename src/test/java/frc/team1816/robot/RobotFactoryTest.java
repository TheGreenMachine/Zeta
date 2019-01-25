package frc.team1816.robot;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

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
}