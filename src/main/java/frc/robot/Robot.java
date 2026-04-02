/*
 * This file contains the Robot class.
 * It is the main entry point for the robot code.
 * It controls the flow of modes and initializes the RobotContainer.
 */

package frc.robot;

// Camera
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;

// WPILib
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.*;

// Geometry
import edu.wpi.first.math.geometry.Transform3d;

// NetworkTables
import edu.wpi.first.networktables.NetworkTableInstance;

// OpenCV
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import frc.robot.RobotContainer;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;
  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotInit() {
    // Start vision thread
    Thread visionThread = new Thread(() -> {
      // Start USB camera
      UsbCamera camera = CameraServer.startAutomaticCapture();
      camera.setResolution(640, 480);

      CvSink cvSink = CameraServer.getVideo();
      CvSource outputStream = CameraServer.putVideo("Processed", 640, 480);

      // Setup AprilTag detector
      AprilTagDetector detector = new AprilTagDetector();
      detector.addFamily("tag36h11", 0);

      // Setup pose estimator with camera intrinsics
      // IMPORTANT: Replace these values with your camera's actual calibration values
      AprilTagPoseEstimator.Config poseEstConfig = new AprilTagPoseEstimator.Config(
          0.1651,  // Tag size in meters (6.5 inch tag = 0.1651m) -- VERIFY THIS
          699.3,   // fx -- REPLACE WITH YOUR CALIBRATION VALUE
          677.7,   // fy -- REPLACE WITH YOUR CALIBRATION VALUE
          345.6,   // cx -- REPLACE WITH YOUR CALIBRATION VALUE
          207.1    // cy -- REPLACE WITH YOUR CALIBRATION VALUE
      );

      AprilTagPoseEstimator estimator = new AprilTagPoseEstimator(poseEstConfig);

      // Mat objects for image processing
      Mat image = new Mat();
      Mat grayImage = new Mat();

      while (!Thread.interrupted()) {
        // Grab frame from camera
        if (cvSink.grabFrame(image) == 0) {
          // Frame grab failed, report error and skip
          outputStream.notifyError(cvSink.getError());
          continue;
        }

        // Convert to grayscale (required for AprilTag detection)
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Detect AprilTags
        AprilTagDetection[] detections = detector.detect(grayImage);

        // Process each detection
        for (AprilTagDetection detection : detections) {
          int id = detection.getId();
          Transform3d pose = estimator.estimate(detection);

          // Publish tag ID and pose data to NetworkTables
          NetworkTableInstance.getDefault()
              .getTable("Vision")
              .getEntry("tagID")
              .setInteger(id);

          NetworkTableInstance.getDefault()
              .getTable("Vision")
              .getEntry("tagX")
              .setDouble(pose.getX());

          NetworkTableInstance.getDefault()
              .getTable("Vision")
              .getEntry("tagY")
              .setDouble(pose.getY());

          NetworkTableInstance.getDefault()
              .getTable("Vision")
              .getEntry("tagZ")
              .setDouble(pose.getZ());
        }

        // Push processed frame to dashboard
        outputStream.putFrame(image);
      }
    });

    visionThread.setDaemon(true); // Don't block JVM shutdown
    visionThread.start();
  }

  @Override
  public void robotPeriodic() {
    // Runs the CommandScheduler, which polls buttons and runs commands
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void teleopInit() {
    // Cancel autonomous command when teleop starts
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}