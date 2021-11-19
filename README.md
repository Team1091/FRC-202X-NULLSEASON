# FRC-202X-NULLSEASON
This is a project for use with a dummy robot, whether it be through a simulator, or an IRL robot. It uses the standard libraries controlled and developed by WPI.

The primary goal of this project is to create a highly abstracted interface for controlling a standard FRC (First Robotics Competition) Robot.
Derived from this we hope to be able to allow even users with a basic level of Java or Computer Science knowledge to interact with a RoboRio and similar hardware.

We intend to make it possible to control the robot through an interface similar to a typical [software turtle](https://en.wikipedia.org/wiki/Turtle_graphics). 

Should this project actually get off the ground, this project should include a blueprint / design that will allow you, or your team,
to build a simplistic robot that can fully use the capabilities described by this "program"

If your team found this helpful, shoot us an email and tell us! It would mean a lot :)

## Setup
Odd's are this will need some setup from a programming mentor, but the basics are as follows, change the preconfigured variables for our team, namely team number and base package. From there you should be able to use it through IntelliJ, or another IDE that can use Gradle.

## Example
Currently the most basic turtle commands exist. For example, you may find code like this in the "CleanRoom" class, which is where all of the simplistic code will probably live.

```
robot.drive(100); //Drive forwards at 0.75 speed.
robot.turn(200); //Turn clockwise at 0.75 speed.
robot.drive(-100); //Drive backwards at 0.75 speed.
robot.turn(-200); //Turn counter-clockwise at 0.75 speed.
robot.drive(200, 1.0); //Drive forward at 1.0 speed. (The maximum)
```
In addition, other commands will likely be implemented later for control of leds on the robot, or maybe a system to allow for drawing like most software turtles are capable of.
