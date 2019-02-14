package frc.team1816.robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogThread extends Thread {
    private BadLog logger;

    public synchronized void initLog() {
        // Format timestamp according to ISO 8601 e.g. 2019-02-14T16.37
        var timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm").format(new Date());
        String path = "/home/lvuser/";
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
        logger.updateTopics();
        if (!DriverStation.getInstance().isDisabled()) {
            logger.log();
        }
        if (!Thread.currentThread().isInterrupted()) {
            run();
        }
    }
}
