/*
* This file contains the RobotContainer class
* It is where the bulk of the robot should be declared.
* The RobotContainer is used to define subsystems, commands, and button mappings.
*/

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.JoystickDriveC;
import frc.robot.subsystems.DriveTrainSS;
import frc.robot.subsystems.ShooterSS;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.LoaderSS;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// Import other subsystems and commands as needed

public class RobotContainer {
  private final DriveTrainSS driveTrainSS = new DriveTrainSS();
  private final ShooterSS shooterSS = new ShooterSS();
  private final LoaderSS loaderSS = new LoaderSS();
  // Instantiate subsystems

  public static CommandXboxController controller = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  // Instantiate the controller

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  // Chooser for autonomous commands

  public RobotContainer() {
    configureBindings();
    configureAutonomousOptions();
  }
  // Constructor to set up button bindings

  private void configureBindings() {
    driveTrainSS.setDefaultCommand(new JoystickDriveC(driveTrainSS));
    // Set default command for driving
    controller.x().whileTrue(driveTrainSS.fasterTurning());
    // Run the fasterTurning command only while X is held
    controller.rightTrigger().whileTrue(shooterSS.shootNormal());
    // Run the shootNormal command only while right trigger is held
    controller.b().whileTrue(loaderSS.load());
    // Run the load command only while B is held
    controller.y().toggleOnTrue(loaderSS.unload());
    // Run the unload command when Y is pressed, stop when pressed again
    controller.a().whileTrue(aimCommand);
    // When A is pressed, shift bot aiming over to apriltag
  }

  public Command getAutonomousCommand() {
    Command selected = autoChooser.getSelected();
    if (selected != null) {
      return selected;
    }
    return Autos.simpleAuto1(driveTrainSS);
    // Fallback default
  }
  // Returns the command to run in autonomous

  private void configureAutonomousOptions() {
    // Register available autonomous routines in the chooser shown on the driver station
    autoChooser.setDefaultOption("Simple Auto 1 (forward 2s)", Autos.simpleAuto1(driveTrainSS));
    autoChooser.addOption("Simple Auto 2 (forward 1s, turn right)", Autos.simpleAuto2(driveTrainSS));
    autoChooser.addOption("Simple Auto 3 (forward 1s, turn left)", Autos.simpleAuto3(driveTrainSS));

    SmartDashboard.putData("Autonomous Mode", autoChooser);
  }
}
