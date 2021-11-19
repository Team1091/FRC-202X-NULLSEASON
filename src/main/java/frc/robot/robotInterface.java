package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class robotInterface {
    //TODO: Find out what we will be actually using for this
    private final SpeedController m_frontLeft;  //Describes the front left wheel.
    private final SpeedController m_rearLeft; //Describes the rear left wheel.
    private final SpeedControllerGroup m_left; //Describes the left wheel "group" used by DifferentialDrive

    private final SpeedController m_frontRight; //Describes the front right wheel.
    private final SpeedController m_rearRight; //Describes the rear right wheel
    private final SpeedControllerGroup m_right;//Describes the right wheel "group" used by DifferentialDrive

    private final DifferentialDrive m_drive;

    /**
     * @param chFrontLeft Describes the channel that the front left motor will use.
     * @param chRearLeft Describes the channel that the rear left motor will use.
     * @param chFrontRight Describes the channel that the front right motor will use.
     * @param chRearRight Describes the channel that the rear right motor will use.
     */
    public robotInterface(int chFrontLeft, int chRearLeft, int chFrontRight, int chRearRight) {
        m_frontLeft = new PWMVictorSPX(chFrontLeft); //Assign the front left wheel a channel and motor, which is how it
                                                     //communicates with the RoboRio electronically.
        m_rearLeft = new PWMVictorSPX(chRearLeft);   //Do the same with the rear left.
        m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft); //Tie the two left side motors together in a helper
                                                                    //class used by DifferentialDrive.

        m_frontRight = new PWMVictorSPX(chFrontRight); //Assign the front right wheel a channel and motor, which is how
                                                       //it communicates with the RoboRio electronically.
        m_rearRight = new PWMVictorSPX(chRearRight);   //Do the same with the rear right.
        m_right = new SpeedControllerGroup(m_frontRight, m_rearRight); //Tie the two right side motors together in a
                                                                       //helper class used by DifferentialDrive.

        m_drive = new DifferentialDrive(m_left, m_right); //Combine both "SpeedControllerGroups" into another helper
                                                          //which allows for further abstraction.

        Util.sleep(100);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     */
    public void drive(int units) {
        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0) dir = 1; //If units is more than one, we are going forwards
        if(units < 0) dir = -1; //If units are less than one, we are going backwards.
//      if(units == 0) dir = 0; Not needed, but shown for clarity. If units equals zero, we go nowhere.

        m_drive.tankDrive(0.75 * dir, 0.75 * dir); //Start the robot's motors on both the left and
        //right side. Multiply by dir to determine the
        //direction we want to travel.

        //TODO: Ease in and out these speed values so that the test bot can be used for a long time.
        //      Similar to what we do with the real robot.

        Util.sleep(1000L * Math.abs(units));//Wait for 1000 milliseconds, 'units' number of times.

        m_drive.tankDrive(0, 0); //We finished driving x amount of units. We can now stop the motors.

        Util.sleep(100);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     */
    public void drive(int units, double power) {
        assert (power <=  1) && (power >= -1);

        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0) dir = 1; //If units is more than one, we are going forwards
        if(units < 0) dir = -1; //If units are less than one, we are going backwards.
//      if(units == 0) dir = 0; Not needed, but shown for clarity. If units equals zero, we go nowhere.

        m_drive.tankDrive(power * dir, power * dir); //Start the robot's motors on both the left and
                                                                     //right side. Multiply by dir to determine the
                                                                     //direction we want to travel.

        //TODO: Ease in and out these speed values so that the test bot can be used for a long time.
        //      Similar to what we do with the real robot.

        Util.sleep(1000L * Math.abs(units));//Wait for 1000 milliseconds, 'units' number of times.

        m_drive.tankDrive(0, 0); //We finished driving x amount of units. We can now stop the motors.

        Util.sleep(100);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param powerFL Controls the power put behind the front left electric engine. 0.75 is the default.
     * @param powerRL Controls the power put behind the right left electric engine. 0.75 is the default.
     * @param powerFR Controls the power put behind the front right electric engine. 0.75 is the default.
     * @param powerRR Controls the power put behind the rear right electric engine. 0.75 is the default.
     */
    public void drive(int units, double powerFL, double powerRL, double powerFR, double powerRR) {
        assert (powerFL <=  1) && (powerFL >= -1);
        assert (powerRL <=  1) && (powerRL >= -1);
        assert (powerFR <=  1) && (powerFR >= -1);
        assert (powerRR <=  1) && (powerRR >= -1);

        boolean flDisable = false;
        boolean rlDisable = false;
        boolean frDisable = false;
        boolean rrDisable = false;

        if(powerFL == 0) flDisable = true;
        if(powerRL == 0) rlDisable = true;
        if(powerFR == 0) frDisable = true;
        if(powerRR == 0) rrDisable = true;

        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0)  dir =  1; //If units is more than one, we are going forwards
        if(units < 0)  dir = -1; //If units are less than one, we are going backwards.
//      if(units == 0) dir =  0; Not needed, but shown for clarity. If units equals zero, we go nowhere.

        if(flDisable) m_frontLeft.disable(); //Do I need to re-enable these? I can't figure out how...
        if(rlDisable) m_rearLeft.disable();
        if(frDisable) m_frontRight.disable();
        if(rrDisable) m_rearRight.disable();

        m_frontLeft.set(powerFL * dir);
        m_rearLeft.set(powerRL * dir);
        m_frontRight.set(powerFR * dir);
        m_rearRight.set(powerRR * dir);

        Util.sleep(1000L * Math.abs(units));

        m_drive.stopMotor();

        Util.sleep(100);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     */
    public void turn(int units, double power) {
        assert (power <=  1) && (power >= -1);

        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0) dir = 1; //If units is more than one, we are turning right (clockwise).
        if(units < 0) dir = -1; //If units are less than one, we are turning left (counterclockwise).
//      if(units == 0) dir = 0; Not needed, but shown for clarity. If units equals zero, we don't turn.

        m_drive.tankDrive(power * dir, -(power) * dir);

        try { //This is a try except block. It is needed because the function "Thread.sleep" can throw an exception.
            //We could choose to let the exception propagate, but we just want to know as soon as possible.

            Thread.sleep(1000L * Math.abs(units)); //Wait for 1000 milliseconds, 'units' number of times.
        } catch (InterruptedException e) {
            e.printStackTrace(); //This function prints a stacktrace. A stacktrace is a type of error message that
            //describes the path code took to reach a certain point.
        }

        m_drive.tankDrive(0, 0); //We finished driving x amount of units. We can now stop the motors.

        Util.sleep(100);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     */
    public void turn(int units) {
        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0) dir = 1; //If units is more than one, we are turning right (clockwise).
        if(units < 0) dir = -1; //If units are less than one, we are turning left (counterclockwise).
//      if(units == 0) dir = 0; Not needed, but shown for clarity. If units equals zero, we don't turn.

        m_drive.tankDrive(0.75 * dir, -(0.75) * dir);

        try { //This is a try except block. It is needed because the function "Thread.sleep" can throw an exception.
            //We could choose to let the exception propagate, but we just want to know as soon as possible.

            Thread.sleep(1000L * Math.abs(units)); //Wait for 1000 milliseconds, 'units' number of times.
        } catch (InterruptedException e) {
            e.printStackTrace(); //This function prints a stacktrace. A stacktrace is a type of error message that
            //describes the path code took to reach a certain point.
        }

        m_drive.tankDrive(0, 0); //We finished driving x amount of units. We can now stop the motors.

        Util.sleep(100);
    }
}