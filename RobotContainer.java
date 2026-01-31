package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.JoystickDriveC;
import frc.robot.subsystems.DriveTrainSS;
import frc.robot.subsystems.ShooterSS;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveTrainSS driveTrainSS = new DriveTrainSS();
  private final ShooterSS shooterSS = new ShooterSS();

  public static CommandXboxController controller = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driveTrainSS.setDefaultCommand(new JoystickDriveC(driveTrainSS));
    // Run the fasterTurning command only while X is held
    controller.x().whileTrue(driveTrainSS.fasterTurning());
    // Run shooter/loader commands only while their respective buttons/triggers are held
    controller.rightTrigger().whileTrue(shooterSS.shootNormal());
    controller.b().whileTrue(shooterSS.load());
    controller.y().whileTrue(shooterSS.unload());
  }

  public Command getAutonomousCommand() {
    return Autos.simpleAuto(driveTrainSS);
  }
}
