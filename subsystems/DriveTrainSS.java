/*
* This is the subsystem for the drivetrain.
* It contains methods to control the motors and commands for driving.
* It contains a command for faster turning.
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrainSS extends SubsystemBase {
  private final Talon leftSide = new Talon(Constants.DriveTrainConstants.leftSide);
  private final Talon rightSide = new Talon(Constants.DriveTrainConstants.rightSide);
  // Instantiate motor controllers

  public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
    leftSide.set(-leftSpeed * 1.0);
    rightSide.set(rightSpeed * 1.0);
  }
  // Method to set motor speeds

  public Command fasterTurning() {
    return run(
        () -> {
          double forward = -RobotContainer.controller.getLeftY() * 1.0; // Max forward speed
          double rotation = RobotContainer.controller.getRightX() * 1.0; // Max rotation speed
          double leftSpeed = forward + rotation; // Arcade drive calculation
          double rightSpeed = forward - rotation;

          double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed)); // Normalize speeds
          if (maxMagnitude > 1.0) {
            leftSpeed /= maxMagnitude;
            rightSpeed /= maxMagnitude;
          }

          setMotorSpeeds(leftSpeed, rightSpeed); // Set motor speeds
        });
  }
  // Command for driving with faster turning
}