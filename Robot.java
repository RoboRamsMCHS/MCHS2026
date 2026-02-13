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
  // Cancels autonomous command when teleop starts

  @Override
  public void robotInit() {
    new Thread(() -> {
      // USB camera from the CameraServer
      
      UsbCamera camera = CameraServer.startAutomaticCapture();
      camera.setResolution(640, 480);
    
      CvSink cvSink = CameraServer.getVideo();

      /*  (Optional though) CV source to output process image to dashboard
      CvSource outputStream = CameraServer.putVideo("Processed", 640, 480)
      */

      // Setup AprilTag detector and estimator
      AprilTagDetector detector = new AprilTagDetector();
      detector.addFamily("tag36h11", 0);    

      // Pose estimator
      AprilTagPoseEstimator.Config poseEstConfig = new AprilTagPoseEstimator.Config();

      AprilTagPoseEstimator estimator = new AprilTagPoseEstimator(poseEstConfig);


      // Mat object for image process
      Mat image = new Mat();

      while(!Thread.interrupted()) {
        if (cvSink.grabFrame(image) == 0) {
          // If frame is bad, skip frame
          OutputStream.notifyError(cvSink.getError());
          continue;
        }
        // Detect AprilTags
        AprilTagDetection[] detections = detector.detect(image);

        // Process detections

        for (AprilTagDetection detection : detections) {
          int id = detection.getId();
          Transform3d pose = estimator.estimate(detection);
        
          NetworkTableInstance.getDefault()
          .getTable("Vision");
          .getEntry("tagID").setInteger(id);
        }
        OutputStream.putFrame(image); // Show "images" on dashboard
      }
    }).start();
  }
}