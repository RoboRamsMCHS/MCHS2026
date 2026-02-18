/*
* This command allows the robot to be driven using a controller.
* An arcade drive scheme is implemented.
*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSS;
import edu.wpi.first.wpilibj2.command.Command;

public class JoystickDriveC extends Command {
  private final DriveTrainSS driveTrainSS;
  // Instance of the DriveTrain subsystem

  public JoystickDriveC(DriveTrainSS subsystem) {
    this.driveTrainSS = subsystem;
    addRequirements(subsystem);
  }
  // Sets up the command with the required subsystem

  @Override
  public void initialize() {
    System.out.println("JoystickDriveC initialized");
  }
  // Called once when the command is initially scheduled

  @Override
  public void execute() {
    double forward = -RobotContainer.controller.getLeftY() * 1.0; // Max forward speed
    double rotation = RobotContainer.controller.getRightX() * 0.6; // 60% max rotation speed
    double leftSpeed = forward + rotation; // Arcade drive calculations
    double rightSpeed = forward - rotation;

    double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed)); // Normalize speeds if needed
    if (maxMagnitude > 1.0) {
      leftSpeed /= maxMagnitude;
      rightSpeed /= maxMagnitude;
    }

    driveTrainSS.setMotorSpeeds(leftSpeed, rightSpeed); // Set motor speeds
  }
  // Called while the command is scheduled
}