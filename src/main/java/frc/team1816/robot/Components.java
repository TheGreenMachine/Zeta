package frc.team1816.robot;

public class Components {
    private static Components instance;

    private Components(){
        
    }

    public static Components getInstance(){
        if (instance == null){
            instance = new Components();
        }
        return instance;
    }


}
