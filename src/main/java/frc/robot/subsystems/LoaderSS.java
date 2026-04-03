package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
//import com.revrobotics.*;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class LoaderSS extends SubsystemBase {
   private final SparkMax loader = new SparkMax(5, MotorType.kBrushless);
  // private final TalonFX intakeMotor = new TalonFX(Constants.ShooterConstants.PLACEHOLDER);

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
