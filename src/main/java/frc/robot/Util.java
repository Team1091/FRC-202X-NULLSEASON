package frc.robot;

public class Util {
    /**
     * @param milliseconds Number of milliseconds to sleep for.
     */
    public static void sleep(long milliseconds) {
        try {//This is a try except block. It is needed because the function "Thread.sleep" can throw an exception.
             //We could choose to let the exception propagate, but we just want to know as soon as possible.
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //This function prints a stacktrace. A stacktrace is a type of error message that describes the path code
            //took to reach a certain point.
        }
    }
}
