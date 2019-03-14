package frc.team1816.robot;

import com.edinarobotics.utils.hardware.Autowired;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import frc.team1816.robot.subsystems.*;

/**
 * Contains all subsystems of the robot. Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    @Autowired public Birdbeak birdbeak;
    @Autowired public LedManager ledManager;
    @Autowired public Climber climber;
    @Autowired public CargoCollector collector;
    @Autowired public Drivetrain drivetrain;
    @Autowired public CargoShooter shooter;

    VideoSink server;
    public UsbCamera camCargo, camHatch;
    private boolean isFrontCam = true;

    private Components() {
        RobotFactory factory = Robot.factory;
        factory.injectFields();
        /*if (factory.isImplemented(Birdbeak.NAME)) {
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
        if (factory.isImplemented(LedManager.NAME)) {
            ledManager = new LedManager();
        }*/
        
        camHatch = CameraServer.getInstance().startAutomaticCapture(0);
        // camCargo = CameraServer.getInstance().startAutomaticCapture(1);
        // server = CameraServer.getInstance().getServer();
    }

    public void toggleCamera() {
        if (isFrontCam) {
            System.out.println("Activating Rear Cam");
            server.setSource(camHatch);
            isFrontCam = false;
        } else {
            System.out.println("Activating Front Cam");
            server.setSource(camCargo);
            isFrontCam = true;
        }
    }

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
