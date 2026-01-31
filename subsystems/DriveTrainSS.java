package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrainSS extends SubsystemBase {
  private final Talon leftSide = new Talon(Constants.DriveTrainConstants.leftSide);
  private final Talon rightSide = new Talon(Constants.DriveTrainConstants.rightSide);

  public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
    leftSide.set(-leftSpeed * 1.0);
    rightSide.set(rightSpeed * 1.0);
  }

  public Command fasterTurning() {
    // Continuously read joystick and drive with faster turning while this command is scheduled.
    // When the trigger is released the command will be cancelled and the default command
    // (JoystickDriveC) will resume control of the drivetrain.
    return run(
        () -> {
          double forward = -RobotContainer.controller.getLeftY() * 1.0;
          double rotation = RobotContainer.controller.getRightX() * 1.0;
          double leftSpeed = forward + rotation;
          double rightSpeed = forward - rotation;

          double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
          if (maxMagnitude > 1.0) {
            leftSpeed /= maxMagnitude;
            rightSpeed /= maxMagnitude;
          }

          setMotorSpeeds(leftSpeed, rightSpeed);
        });
  }
}