package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSS;
import edu.wpi.first.wpilibj2.command.Command;

public class JoystickDriveC extends Command {
  private final DriveTrainSS driveTrainSS;

  public JoystickDriveC(DriveTrainSS subsystem) {
    this.driveTrainSS = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    System.out.println("JoystickDriveC initialized");
  }

  @Override
  public void execute() {
    double forward = -RobotContainer.controller.getLeftY() * 1.0;
    double rotation = RobotContainer.controller.getRightX() * 0.6;
    double leftSpeed = forward + rotation;
    double rightSpeed = forward - rotation;

    double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
    if (maxMagnitude > 1.0) {
      leftSpeed /= maxMagnitude;
      rightSpeed /= maxMagnitude;
    }

    driveTrainSS.setMotorSpeeds(leftSpeed, rightSpeed);
  }
}
