/*
* This file is contains the autonomous commands.
* There should be multiple commands here for different autonomous routines.
*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DriveTrainSS;
import frc.robot.subsystem.ShooterSS;
import frc.robot.subsystem.LoaderSS;
import frc.robot.subsystem.VisionSubsystem;

// Import other subsystems as needed

public final class Autos {
  public static Command simpleAuto1(DriveTrainSS driveTrainSS) {
    return Commands.sequence(
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.5, 0.5)),
        Commands.waitSeconds(2),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.0, 0.0))
    );
  }
  // Move forward for 2 seconds

  public static Command simpleAuto2(DriveTrainSS driveTrainSS) {
    return Commands.sequence(
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.5, 0.5)),
        Commands.waitSeconds(1),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.0, 0.0)),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(-0.5, 0.5)),
        Commands.waitSeconds(1),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.0, 0.0))
    );
  }
  // Move forward for 1 second, then turn right for 1 second

  public static Command simpleAuto3(DriveTrainSS driveTrainSS) {
    return Commands.sequence(
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.5, 0.5)),
        Commands.waitSeconds(1),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.0, 0.0)),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.5, -0.5)),
        Commands.waitSeconds(1),
        Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.0, 0.0))
    );
  }
  // Move forward for 1 second, then turn left for 1 second

  public static Command simpleAuto(DriveTrainSS driveTrainSS, double speedDefault)
  {
    return Commands.sequence(Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(0.5, 0.5)), Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(1, 1))
    , Commands.runOnce(() -> driveTrainSS.setMotorSpeeds(-0.5, -0.5)));
  }
  //Moves at a regular pace, maxes out speed, then goes backward at a regular pace

  public static Command shooterAuto(ShooterSS shooter, double speedDefault)
  {
    return Commands.sequence(Commands.runOnce(() -> shooter.setShooterSpeed(0.5)),
    Commands.runOnce(() -> shooter.shootNormal()));
  }
  //Sets shooter speed and then sets to default

  public static Command intakeAuto(LoaderSS loader, double speedDefault)
  {
    return Commands.sequence(Commands.runOnce(() -> loader.load()),Commands.waitSeconds(2), Commands.runOnce(loader.setLoaderSpeed(0.5)), Commands.runOnce(loader.unload()));
  }
  //Loads balls up, sets speed and then unloads them

  public static Command visionCheck(DriveTrainSS driveTrain, VisionSubsystem vision)
  {
    return Commands.sequence(Commands.runOnce(() -> VisionSubsystem.periodic()), Commands.waitSeconds(2), Commands.runOnce(() -> DriveTrainSS.canVisionAutomate()));
  }
  //Currently unfinished, but should move towards or away from a april tag if unread
}