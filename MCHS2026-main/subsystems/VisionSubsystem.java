package frc.robot.subsystems;
import java.util.List;
import java.io.IOException;
import java.util.Optional;

import edu.wpi.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// PhotonVision

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;



// Geometry
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Rotation2d;

// Apriltag field stuff
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;


public class VisionSubsystem extends SubsystemBase {

    private static final String CAMERA_NAME = "Main";

    private static final int[] GOAL_IDS = {25,24,21,20,19,18,27, 4,3 ,2, 11, 10, 9, 8, 5}; // GET GOAL IDS DO THIS LATER, UNDER HOOPS

    private static final double HOOP_HEIGHT = 1.8034; // GOAL HEIGHT OFF FLOOR

    private static final double CAMERA_HEIGHT_METERS = 0.508; // HOW HIGH CAMERA IS OFF FLOOR

    private static final double CAMERA_PITCH = 0.0; // CHECK LATER



    public static final Transform3d kRobotToCam =
                new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0, 0, 0));








    
    private final PhotonCamera camera;
    private final PhotonPoseEstimator poseEstimator;

    private final AprilTagFieldLayout fieldLayout;

    private PhotonPipelineResult latestResult;

    // Cached values from latestResult ^^
    private boolean hasTargets = false;
    private PhotonTrackedTarget bestTarget = null;
    private Optional<EstimatedRobotPose> estimatedRobotPose = Optional.empty();

    public VisionSubsystem() {

          


        try {
            // Load field layout 2026
            fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltAndymark);
            System.out.println("Loaded field layout successfully");
        }
        catch (IOException e) {
            System.err.println("Couldn't load field layout.");
            throw new RuntimeException("Vision system failed to init.");
        }


        // Create PhotonCamera
        camera = new PhotonCamera(CAMERA_NAME);
        System.out.println("Camera successfully connected to " + CAMERA_NAME);

        poseEstimator = new PhotonPoseEstimator(
    fieldLayout,
    PoseStrategy.MULTI_TAG_PNP,
    camera,
    kRobotToCam
);
        System.out.println("Estimator initialized.");

        latestResult = new PhotonPipelineResult();
        System.out.println("System is ready!");


    }


    @Override
    public void periodic() {
        latestResult = camera.getLatestResult();

        if (latestResult.hasTargets()) {
            PhotonTrackedTarget target = latestResult.getBestTarget();
        
            if (target != null && isValidTarget(target)) {
                hasTargets = true;
                bestTarget = target;
                estimatedRobotPose = poseEstimator.update();
            }
            else {
                hasTargets = false;
                bestTarget = null;
                estimatedRobotPose = Optional.empty();
            }

        else {
            // No targets
            hasTargets = false;
            bestTarget = null;
            estimatedRobotPose = Optional.empty();
        }
        updateDashboard();


            }


    
    
    
    
    
    
    
    // Target Detection methods

        // Check if we have any valid targets
    public boolean hasTarget() {
        return hasTargets && bestTarget != null;
    }


    private boolean isValidTarget(PhotonTrackedTarget target) {
    // Example: check if the target ID is in your GOAL_IDS array
    for (int id : GOAL_IDS) {
        if (target.getFiducialId() == id) {
            return true;
        }
    }
    return false;
}


    public List<PhotonTrackedTarget> getAllTargets() {
        if (!latestResult.hasTargets()) {
            return List.of();
        }
        return latestResult.getTargets();
    }
    
    public PhotonTrackedTarget getBestTarget() {
        return bestTarget;
    }
    public Optional<Double> getTargetYaw() {
        if (bestTarget != null) {
            return Optional.of(bestTarget.getYaw());
        }
        return Optional.empty();
    }




    // get targets currently avaliable
    public int getTargetCount() {
        return latestResult.hasTargets() ? latestResult.getTargets().size() : 0;
    }


    private void updateDashboard() {
        SmartDashboard.putBoolean("Has target: ", hasTarget());
        SmartDashboard.putNumber("Target count: ", getTargetCount());
        getTargetYaw().ifPresent(yaw -> SmartDashboard.putNumber("Target Yaw", yaw));
        
    }

    



    
    
    
    
    }









