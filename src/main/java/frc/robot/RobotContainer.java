/*
* This file contains the RobotContainer class
* It is where the bulk of the robot should be declared.
* The RobotContainer is used to define subsystems, commands, and button mappings.
*/

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Agitate;
import frc.robot.commands.Autos;
import frc.robot.commands.JoystickDriveC;
import frc.robot.subsystems.DriveTrainSS;
import frc.robot.subsystems.ShooterSS;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LoaderSS;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ServoSS;
// Import other subsystems and commands as needed




// line 19, test commit message


public class RobotContainer {
  private final DriveTrainSS driveTrainSS = new DriveTrainSS();
  private final ShooterSS shooterSS = new ShooterSS();
  private final LoaderSS loaderSS = new LoaderSS();
  // Instantiate subsystems

  public static CommandXboxController controller = new CommandXboxController(0);
  public static CommandPS4Controller ps5 = new CommandPS4Controller(1);


  public static ServoSS servo = new ServoSS();

  public static Agitate agi = new Agitate(servo);

  // Instantiate the controller


  public RobotContainer() {
    configureBindings();




  }
  // Constructor to set up button bindings
  // Xbox
  private void configureBindings() {
    
    driveTrainSS.setDefaultCommand(new JoystickDriveC(driveTrainSS));
    // Set default command for driving
    controller.leftBumper().whileTrue(driveTrainSS.fasterTurning());
    // Run the fasterTurning command only while X is held
    controller.rightTrigger().whileTrue(shooterSS.shootNormal());
    // Run the shootNormal command only while right trigger is held
    controller.leftTrigger().whileTrue(shooterSS.reverseShooter());
    controller.b().whileTrue(loaderSS.load());
    // Run the load command only while B is held>
    controller.y().toggleOnTrue(loaderSS.unload());
    // Run the unload command when Y is pressed, stop when pressed again

    controller.rightBumper().toggleOnTrue(driveTrainSS.turn180());

    controller.pov(90).toggleOnTrue(agi);

    //                                                  END OF XBOX BINDINGS
    // -----------------------------------------------------------------------------------------------------------------------

    ps5.L1().whileTrue(driveTrainSS.fasterTurning());
    ps5.R2().whileTrue(shooterSS.shootNormal());
    ps5.L2().whileTrue(shooterSS.reverseShooter());
    ps5.circle().whileTrue(loaderSS.load());
    ps5.triangle().toggleOnTrue(loaderSS.unload());

    ps5.R1().toggleOnTrue(driveTrainSS.turn180());




    

  
  
  
  
  
  
  
  
  
  }

   public Command getAutonomousCommand() {
    return Autos.simpleAuto1(driveTrainSS);
  }   // Returns the command to run in autonomous
  // I don't know how this part works yet
}
