/*
* This file contains the Robot class
* It is the main entry point for the robot code.
* It controls the flow of modes and initializes the RobotContainer.
*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.io.OutputStream;

import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  // Declares autonomous command

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture();
  }
  // Constructer initializes RobotContainer

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }
  // Runs the CommandScheduler periodically

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    // Instantiate the autonomous command

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
    // Schedule the autonomous command
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
}
  // Cancels autonomous command when teleop starts