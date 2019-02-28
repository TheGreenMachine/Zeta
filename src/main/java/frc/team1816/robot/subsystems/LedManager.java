package frc.team1816.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class LedManager extends Subsystem implements Checkable {
    public static final String NAME = "ledmanager";

    private CANifier canifier;

    private boolean blinkMode;
    private boolean outputsChanged = true;

    private int ledR;
    private int ledG;
    private int ledB;

    private int ledBlinkR;
    private int ledBlinkG;
    private int ledBlinkB;

    public LedManager() {
        super(NAME);
        this.canifier = Robot.factory.getCanifier(NAME);
        this.ledR = 0;
        this.ledG = 0;
        this.ledB = 0;
    }

    public void setLedColor(int r, int g, int b) {
        if (this.ledR != r || this.ledG != g || this.ledB != b) {
            this.ledR = r;
            this.ledG = g;
            this.ledB = b;
            outputsChanged = true;
        }
    }

    public void setLedColorBlink(int r, int g, int b) {
        blinkMode = true;
        this.ledBlinkR = r;
        this.ledBlinkG = g;
        this.ledBlinkB = b;
    }

    public void indicateStatus(RobotStatus status) {
        blinkMode = false;
        setLedColor(status.getRed(), status.getGreen(), status.getBlue());
    }

    public void blinkStatus(RobotStatus status) {
        blinkMode = true;
        this.ledBlinkR = status.getRed();
        this.ledBlinkG = status.getGreen();
        this.ledBlinkB = status.getBlue();
    }

    public int[] getLedRgbBlink() {
        return new int[]{ledBlinkR, ledBlinkG, ledBlinkB};
    }

    public boolean getBlinkMode() {
        return blinkMode;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            canifier.setLEDOutput((double) (ledG / 255.0), CANifier.LEDChannel.LEDChannelA);
            canifier.setLEDOutput((double) (ledR / 255.0), CANifier.LEDChannel.LEDChannelB);
            canifier.setLEDOutput((double) (ledB / 255.0), CANifier.LEDChannel.LEDChannelC);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: checking LED systems");
        Timer.delay(3);

        setLedColor(255, 0, 0);
        periodic();
        Timer.delay(0.4);
        setLedColor(0, 255, 0);
        periodic();
        Timer.delay(0.4);
        setLedColor(0, 0, 255);
        periodic();
        Timer.delay(0.4);
        setLedColor(0, 0, 0);
        periodic();

        return true;
    }

    public enum RobotStatus {
        ENABLED(0, 255, 0), // green
        DISABLED(255, 103, 0), // orange
        ERROR(255, 0, 0), // red
        ENDGAME(0, 0, 255), // blue
        TARGET_SEEN(255, 0, 255), // magenta
        OFF(0, 0, 0); // off

        int red;
        int green;
        int blue;

        RobotStatus(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }
    }
}
