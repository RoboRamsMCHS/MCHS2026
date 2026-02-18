/*
* This file contains robot-wide constants.
* It is a good place to hold numerical or boolean constants that are used
* Constants are organized by subsystem or purpose.
*/

package frc.robot;

public final class Constants {
  public static class DriveTrainConstants {
    public static final int leftSide = 0; // PWM port
    public static final int rightSide = 1; // PWM port
  }

  public static class ShooterConstants {
    public static final int shooter = 2; // PWM port
    public static final int loader = 3; // PWM port
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0; // USB port
  }
}