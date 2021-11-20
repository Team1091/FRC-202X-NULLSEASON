package frc.robot;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotComponent.EasingPWM;

public class robotInterface {
    private SpeedController m_frontLeft;  //Describes the front left wheel.
    private SpeedController m_rearLeft; //Describes the rear left wheel.
    private SpeedController m_left; //Describes the left wheel "group" used by DifferentialDrive

    private SpeedController m_frontRight; //Describes the front right wheel.
    private SpeedController m_rearRight; //Describes the rear right wheel
    private SpeedController m_right;//Describes the right wheel "group" used by DifferentialDrive

    private final DifferentialDrive m_drive;

    /**
     * @param chFrontLeft Describes the channel that the front left motor will use.
     * @param chRearLeft Describes the channel that the rear left motor will use.
     * @param chFrontRight Describes the channel that the front right motor will use.
     * @param chRearRight Describes the channel that the rear right motor will use.
     *
     * Use -1 to indicate that you only use two motors. IE: robotInterface(1, -1, 2, -1);
     * If any channel is assigned as -1, it will attempt to figure out the best way to control your system.
     */
    public robotInterface(int chFrontLeft, int chRearLeft, int chFrontRight, int chRearRight) {
        boolean flReal = false;
        boolean rlReal = false;
        boolean frReal = false;
        boolean rrReal = false;
        if(chFrontLeft > 0)  flReal = true; //Determine which motors exist. True if present, false if not.
        if(chRearLeft > 0)   rlReal = true;
        if(chFrontRight > 0) frReal = true;
        if(chRearRight > 0)  rrReal = true;

        if(!(flReal || rlReal)) throw new IllegalArgumentException("There must be at least one left motor...");
        if(!(frReal || rrReal)) throw new IllegalArgumentException("There must be at least one right motor...");


        int numLeftMotors = (flReal ? 1 : 0) + (rlReal ? 1 : 0);
        int numRightMotors = (frReal ? 1 : 0) + (rrReal ? 1 : 0);

        if(numLeftMotors == 1) { //Configure if there is one right motor.
            if(flReal) { //Use the channel for the "real" motor.
                m_left = new EasingPWM(chFrontLeft);
            }
            if(rlReal) {
                m_left = new EasingPWM(chFrontRight);
            }
        }
        if(numLeftMotors == 2) { //Configure if there is two right motors.
            m_frontLeft = new EasingPWM(chFrontLeft);
            //Assign the front left wheel a channel and motor, which is how it communicates with the RoboRio
            //electronically.
            m_rearLeft = new EasingPWM(chRearLeft);
            //Do the same with the rear left.
            m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
            //Tie the two left side motors together in a helper class used by DifferentialDrive.
        }

        if(numRightMotors == 1) { //Configure if there is one right motor.
            if(frReal) { //Use the channel for the "real" motor.
                m_right = new EasingPWM(chFrontRight);
            }
            if(rrReal) {
                m_right = new EasingPWM(chRearRight);
            }
        }
        if(numRightMotors == 2) { //Configure if there is two right motors.
            m_frontRight = new EasingPWM(chFrontRight);
            //Assign the front right wheel a channel and motor, which is how it communicates with the RoboRio
            //electronically.
            m_rearRight = new EasingPWM(chRearRight);
            //Do the same with the rear right.
            m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
            //Tie the two right side motors together in a helper class used by DifferentialDrive.
        }

        m_drive = new DifferentialDrive(m_left, m_right); //Combine both "SpeedControllerGroups" into another helper
                                                          //which allows for further abstraction.
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
        int dir = 0; //1 == forwards, -1 is backwards.

        if(units > 0) dir = 1; //If units is more than one, we are going forwards
        if(units < 0) dir = -1; //If units are less than one, we are going backwards.
//      if(units == 0) dir = 0; Not needed, but shown for clarity. If units equals zero, we go nowhere.

        m_drive.tankDrive(power * dir, power * dir); //Start the robot's motors on both the left and
                                                                     //right side. Multiply by dir to determine the
                                                                     //direction we want to travel.

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
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     *
     * Identical to the standard drive function;
     */
    public void forward(int units) {
        drive(units * -1);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     *
     * Identical to the standard drive function.
     */
    public void forward(int units, double power) {
        drive(units * -1, power);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param powerFL Controls the power put behind the front left electric engine. 0.75 is the default.
     * @param powerRL Controls the power put behind the right left electric engine. 0.75 is the default.
     * @param powerFR Controls the power put behind the front right electric engine. 0.75 is the default.
     * @param powerRR Controls the power put behind the rear right electric engine. 0.75 is the default.
     *
     * Identical to the standard drive function.
     */
    public void forward(int units, double powerFL, double powerRL, double powerFR, double powerRR) {
        drive(units * -1, powerFL, powerRL, powerFR, powerRR);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     *
     * Identical to the standard drive function, except reversed.
     */
    public void reverse(int units) {
        drive(units * -1);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     *
     * Identical to the standard drive function, except reversed.
     */
    public void reverse(int units, double power) {
        drive(units * -1, power);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of length. Use it to tell how far the robot should move.
     *              One unit is however far the robot can move in 1 second. IE: Can vary a lot.
     * @param powerFL Controls the power put behind the front left electric engine. 0.75 is the default.
     * @param powerRL Controls the power put behind the right left electric engine. 0.75 is the default.
     * @param powerFR Controls the power put behind the front right electric engine. 0.75 is the default.
     * @param powerRR Controls the power put behind the rear right electric engine. 0.75 is the default.
     *
     * Identical to the standard drive function, except reversed.
     */
    public void reverse(int units, double powerFL, double powerRL, double powerFR, double powerRR) {
        drive(units * -1, powerFL, powerRL, powerFR, powerRR);
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

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     */
    public void turn(int units, double power) {
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
     *
     * Identical to the standard turn function.
     */
    public void turnRight(int units) {
        turn(units);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     *
     * Identical to the standard turn function.
     */
    public void turnRight(int units, double power) {
        turn(units, power);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     *
     * Identical to the standard turn function, except reversed.
     */
    public void turnLeft(int units) {
        turn(units * -1);
    }

    /**
     * @param units Units is an entirely arbitrary descriptor of rotation. Use it to tell how far the robot should move.
     *              One unit is however far the robot can turn in 1 second. IE: Can vary a lot.
     * @param power Controls the power put behind the electric engine. 0.75 is the default.
     *
     * Identical to the standard turn function, except reversed.
     */
    public void turnLeft(int units, double power) {
        turn(units * -1, power);
    }
}
