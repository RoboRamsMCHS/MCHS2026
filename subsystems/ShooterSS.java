/*
* This is the subsystem for the shooter.
* It contains methods to control the motors and commands for shooting and loading.
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSS extends SubsystemBase {
  private final Talon shooter = new Talon(Constants.ShooterConstants.shooter);
  private final Talon loader = new Talon(Constants.ShooterConstants.loader);
  // Instantiate motor controllers

  public void setShooterSpeed(double speed) {
    shooter.set(speed);
  }

  public void setLoaderSpeed(double speed) {
    loader.set(speed);
  }

  public Command shootNormal() {
    // Start shooter while held, stop when released
    return startEnd(() -> setShooterSpeed(1.0), () -> setShooterSpeed(0));
  }

  public Command load() {
    // Run loader while held, stop when released
    return startEnd(() -> setLoaderSpeed(1.0), () -> setLoaderSpeed(0));
  }

  public Command unload() {
    // Run loader in reverse while held, stop when released
    return startEnd(() -> setLoaderSpeed(-1.0), () -> setLoaderSpeed(0));
  }
}