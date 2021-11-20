package frc.robot;

public class CleanRoom implements Runnable{
    private final robotInterface robot; //This is a robot instance, and that's how you control the robot.

    /**
     * @param robot Provides a reference that will be used by the thread that runs an instance of this class.
     */
    public CleanRoom(robotInterface robot) { //Simple constructor to provide the robot interface.
        this.robot = robot;
    }


    //You probably mostly care about this down here!
    @Override
    public void run() { //This function is where the bulk of your code will be, it is run one time, but can go on as
                        //long as you need.
        robot.forward(1000);
        robot.turnRight(5000);
        robot.reverse(1000);
    }

    public void repeater() { //This function is called every once in a while when the robot is running, however if this
                             //function gets too long, the robot *may* get mad.

        robot.drive(10000);
    }

}
