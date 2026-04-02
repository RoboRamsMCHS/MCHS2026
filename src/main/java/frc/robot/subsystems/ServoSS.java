package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class ServoSS extends SubsystemBase {


    private final Servo servo = new Servo(0);

    public void setAngle(double angle) {
        servo.setAngle(angle);
        
    }



   
}