package frc.robot;

//TODO: Write some example stuff here, I expect we end up using the physical robot almost like a
//      software turtle, however that is yet to be seen.
//      I would also like to see how we can further abstract away some of the fine control to make things
//      *really* simple. IE: robot.moveFwd(int units);
// -
//      Any ideas?

public class CleanRoom implements Runnable{
    private final robotInterface robot; //This is a robot instance, and that's how you control the robot.

    /**
     * @param milliseconds Number of milliseconds to sleep for.
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
        robot.turn(10000);
    }

    public void repeater() { //This function is called every once in a while when the robot is running, however if this
                             //function gets too long, the robot *may* get mad.
    }

}
