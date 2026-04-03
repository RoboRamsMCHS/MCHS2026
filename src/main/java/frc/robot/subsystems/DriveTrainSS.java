/*
* This is the subsystem for the drivetrain.
* It contains methods to control the motors and commands for driving.
* It contains a command for faster turning.
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
import frc.robot.RobotContainer;
//import com.revrobotics.*;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class DriveTrainSS extends SubsystemBase {
  private final SparkMax leftSide = new SparkMax(1, MotorType.kBrushed);
  private final SparkMax leftSide2 = new SparkMax(2, MotorType.kBrushed);

  private final SparkMax rightSide = new SparkMax(3, MotorType.kBrushed);
  private final SparkMax rightSide2 = new SparkMax(4, MotorType.kBrushed);

  
  // Instantiate motor controllers

  public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
    leftSide.set(-leftSpeed * 1.0);
    leftSide2.set(-leftSpeed * 1.0);
    rightSide.set(rightSpeed * 1.0);
    rightSide2.set(rightSpeed * 1.0);
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


  public Command turn180() {
    return run(() -> setMotorSpeeds(0.5, 0.5))
.withTimeout(0.0)
        .andThen(runOnce(() -> setMotorSpeeds(0, 0)));
}

  public void drive(double xSpeed, double ySpeed, double rotation) {
    double leftSpeed = xSpeed + rotation;
    double rightSpeed = xSpeed - rotation;

    double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
    if (maxMagnitude > 1.0) {
      leftSpeed /= maxMagnitude;
      rightSpeed /= maxMagnitude;
    }

    setMotorSpeeds(leftSpeed, rightSpeed);
  }
}