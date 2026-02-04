/*
* This file is contains the autonomous commands.
* There should be multiple commands here for different autonomous routines.
*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DriveTrainSS;
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
}