package frc.team1816.robot;

import com.edinarobotics.utils.hardware.RobotFactory;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;

import frc.team1816.robot.subsystems.Birdbeak;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.Drivetrain;

/**
 * Contains all subsystems of the robot. Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Birdbeak birdbeak;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public CargoShooter shooter;

    VideoSink server;
    public UsbCamera camFront, camRear;
    private boolean isFrontCam = true;

    private Components() {
        RobotFactory factory = Robot.factory;

        if (factory.isImplemented(Birdbeak.NAME)) {
            birdbeak = new Birdbeak();
        }
        if (factory.isImplemented(Climber.NAME)) {
            climber = new Climber();
        }
        if (factory.isImplemented(CargoCollector.NAME)) {
            collector = new CargoCollector();
        }
        if (factory.isImplemented(Drivetrain.NAME)) {
            drivetrain = new Drivetrain();
        }
        if (factory.isImplemented(CargoShooter.NAME)) {
            shooter = new CargoShooter();
        }

        // camFront = CameraServer.getInstance().startAutomaticCapture(0);
        // camRear = CameraServer.getInstance().startAutomaticCapture(1);
        // server = CameraServer.getInstance().getServer();
    }

    // public void toggleCamera() {
    //     if(isFrontCam) {
    //         System.out.println("Activating Rear Cam");
    //         server.setSource(camRear);
    //         isFrontCam = false;
    //     } else {
    //         System.out.println("Activating Front Cam");
    //         server.setSource(camFront);
    //         isFrontCam = true;
    //     }
    // }

    /**
     * Returns the singleton instance of Components. Initializes it if there is no
     * current instance.
     * 
     * @return The current singleton instance of Components.
     */
    public static Components getInstance() {
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }
}
