/*
* This is the subsystem for the shooter.
* It contains methods to control the motors and commands for shooting.
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSS extends SubsystemBase {
  private final Talon shooter = new Talon(Constants.ShooterConstants.shooter);
  public void setShooterSpeed(double speed) {
    shooter.set(speed);
  }
  public Command shootNormal() {
    // Start shooter while held, stop when released
    return startEnd(() -> setShooterSpeed(1.0), () -> setShooterSpeed(0));
  }
}