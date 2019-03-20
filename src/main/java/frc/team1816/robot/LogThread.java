package frc.team1816.robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Threads;
import edu.wpi.first.wpilibj.Timer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogThread extends Thread {
    private BadLog logger;

    private static final int LOOP_TIME_S = 1;

    public LogThread() {
        super();
        // Reduce the priority of this thread

        // Threads.setCurrentThreadPriority(true, 30);
        setDaemon(true);
    }

    public synchronized void initLog() {
        // Format timestamp according to ISO 8601 e.g. 2019-02-14T16-37
        var timestamp = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH'-'mm").format(new Date());

        String path = "/home/lvuser/";
        // TODO: Fix issues with writing to USB
        // String usbPath = "/media/sda1/";
        // File f = new File(usbPath);
        // if (f.exists() && f.isDirectory()) {
        //     path = usbPath;
        // }

        logger = BadLog.init(path + System.getenv("ROBOT_NAME") + "_" + timestamp + ".bag");

        DriverStation ds = DriverStation.getInstance();
        BadLog.createValue("Match Type", ds.getMatchType().toString());
        BadLog.createValue("Match Number", String.valueOf(ds.getMatchNumber()));
        BadLog.createTopic("Match Time", "s", ds::getMatchTime);
    }

    public synchronized void finishInitialization() {
        logger.finishInitialization();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            logger.updateTopics();
            if (!DriverStation.getInstance().isDisabled()) {
                logger.log();
            }
            Timer.delay(LOOP_TIME_S);
        }
    }
}
