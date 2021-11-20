package frc.robot.RobotComponent;

import edu.wpi.first.wpilibj.PWMVictorSPX;

import java.util.ArrayList;

/**
 * You can change what PWM Speed Controller this extends, and it should work no matter what.
 * This is a type of speed controller that linearly adjusts its speed at a pace you can assign.
 */

public class EasingPWM extends PWMVictorSPX {
    private int stepCount;
    private double currentStep = 0;
    private double targetPower = 0;
    private double currentPower = 0;
    private boolean targetUpdated = false;

    private static final ArrayList<EasingPWM> easingPWMS = new ArrayList<>();

    /**
     * Constructor for a Victor SPX connected via PWM.
     *
     * @param channel The PWM channel that the PWMVictorSPX is attached to. 0-9 are on-board, 10-19
     *                are on the MXP port
     */
    public EasingPWM(int channel) {
        super(channel);
        stepCount = 5;

        easingPWMS.add(this);
    }

    /**
     * Constructor for a Victor SPX connected via PWM.
     *
     * @param channel The PWM channel that the PWMVictorSPX is attached to. 0-9 are on-board, 10-19
     *                are on the MXP port
     * @param stepCount Number of updates (minus one) it takes to get to the target power.
     */
    public EasingPWM(int channel, int stepCount) {
        super(channel);
        this.stepCount = stepCount;

        easingPWMS.add(this);
    }

    public void update() {
        if(targetUpdated) {
            currentStep = (targetPower - currentPower) / stepCount;
        }

        currentPower += currentStep;
        super.set(currentPower);

        targetUpdated = false;
    }

    @Override
    public void set(double speed) {
        if(speed != targetPower) { //We only want to set the target updated flag if the value actually changed.
            //If we didn't we would re-eval the currentStep, which would slow down how long it would take.
            //Needless to say that would *not* be expected behavior.
            targetPower = speed;
            targetUpdated = true;
        }

        update();
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public static ArrayList<EasingPWM> getEasingPWMS() {
        return easingPWMS;
    }
}
