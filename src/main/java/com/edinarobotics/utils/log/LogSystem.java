package com.edinarobotics.utils.log;

import java.util.StringTokenizer;

/**
 * The LogSystem class provides clients with a method to access the log system
 */
public final class LogSystem {
    
    private LogSystem(){
        //Hide constructor
    }
    
    private static final Logger rootLogger = new Logger("_root_", null);
    
    /**
     * This static method returns the root Logger in the Logger hierarchy.
     * @return The top, root Logger in the Logger hierarchy.
     */
    public static Logger getRootLogger(){
        return rootLogger;
    }
    
    /**
     * Returns a Logger with the given full name.
     * 
     * This method implicitly adds the root Logger's name to the provided name.
     * The provided name should omit the name of the root Logger.
     * If the requested Logger does not exist, it will be created.
     * @param fullNameWitoutRoot The name of the Logger to return.
     * @return The Logger with the requested full name.
     * @see Logger#getFullNameWithoutRoot()
     */
    public static Logger getLogger(String fullNameWitoutRoot){
        if(fullNameWitoutRoot.indexOf(" ") >= 0){
            throw new IllegalArgumentException("Logger names may not contain \" \" (received: "+fullNameWitoutRoot+")");
        }
        StringTokenizer tokenizer = new StringTokenizer(fullNameWitoutRoot, ".");
        String[] loggerNames = new String[tokenizer.countTokens()];
        int i = 0;
        while(tokenizer.hasMoreTokens()){
            loggerNames[i] = tokenizer.nextToken();
            i++;
        }
        Logger lastLogger = getRootLogger();
        for(int a = 0; a < loggerNames.length; a++){
            lastLogger = lastLogger.getChild(loggerNames[a]);
        }
        return lastLogger;
    }
}
