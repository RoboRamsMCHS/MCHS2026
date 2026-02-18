package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LoaderSS extends SubsystemBase {
  private final Talon loader = new Talon(Constants.ShooterConstants.loader);

  public void setLoaderSpeed(double speed) {
    loader.set(speed);
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
