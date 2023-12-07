package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.ChenryLib.MathUtility;
import frc.ChenryLib.PID;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {
  private final WPI_TalonSRX leftfront;
  private final WPI_TalonSRX leftrear;
  private final WPI_TalonSRX rightfront;
  private final WPI_TalonSRX rightrear;
  private final CANSparkMax neo1;// 不知道阿
  private final CANSparkMax neo2;// 隨便填一個啦
  private final WPI_TalonFX falcon;// 這才是對的

  // private final MotorControllerGroup intakemotor;

  private final MotorControllerGroup left;
  private final MotorControllerGroup right;

  private final DifferentialDrive drive;

  private final AHRS gyro;

  private final CANCoder drivecancoder1;
  private final CANCoder drivecancoder2;

  private final XboxController js1;

  public static enum InputStates {
    A,
    B,
    X,
    Y,
    idle
  }

  public static enum IntakeStates {
    low,
    mid,
    high,
    idle
  }

  private double currentPos;
  private double currentAngle;
  // private double goalPos;
  // private double goalAngle;
  private double intakeCurrentAngle;
  private double intakeGoalAngle;

  private InputStates state;
  private IntakeStates intakeState;
  // private PID forward1mPID;
  // private PID forward2mPID;
  // private PID turnPID;
  private PID tilterPID;

  SendableChooser<Command> m_Chooser = new SendableChooser<>();

  public Chassis() {
    leftfront = new WPI_TalonSRX(Constants.Chassis.LFMotor);
    leftrear = new WPI_TalonSRX(Constants.Chassis.LRMotor);
    rightfront = new WPI_TalonSRX(Constants.Chassis.RFMotor);
    rightrear = new WPI_TalonSRX(Constants.Chassis.RRMotor);
    neo1 = new CANSparkMax(Constants.Chassis.Neo1, MotorType.kBrushless);
    neo2 = new CANSparkMax(Constants.Chassis.Neo2, MotorType.kBrushless);
    falcon = new WPI_TalonFX(Constants.Chassis.Falcon);

    // neo1.setInverted(false);
    // neo2.setInverted(true);
    // intakemotor = new MotorControllerGroup(neo1, neo2);

    neo2.follow(neo1, true);

    left = new MotorControllerGroup(leftfront, leftrear);
    right = new MotorControllerGroup(rightfront, rightrear);

    drive = new DifferentialDrive(left, right);

    gyro = new AHRS(SPI.Port.kMXP);
    gyro.reset();

    drivecancoder1 = new CANCoder(Constants.Chassis.Cancoder1);
    drivecancoder1.setPosition(0);

    drivecancoder2 = new CANCoder(Constants.Chassis.Cancoder2);
    drivecancoder2.setPosition(0);

    // forward1mPID = new PID(1.1, 0.25, 0.01, 0, 0.3);
    // forward2mPID = new PID(0.75, 0.25, 0.01, 0, 0.3);
    // turnPID = new PID(0.01, 0.004, 0.0008, 0, 0.008);
    tilterPID = new PID(0.005, 0, 0.0004, 0, 0);

    js1 = new XboxController(Constants.JoystickConstants.kDriverControllerPort);

    state = InputStates.idle;
    intakeState = IntakeStates.idle;
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("Input State", state.toString());
    SmartDashboard.putNumber("Current Pos", currentPos);
    SmartDashboard.putNumber("Current Angle", currentAngle);
    SmartDashboard.putNumber("Intake Current Angle", intakeCurrentAngle);
    updateStates();
    currentPos = getDistance();
    currentAngle = getRotation2d().getDegrees();
    intakeCurrentAngle = getIntakeDegree() * 360 / Constants.Chassis.falconEncoderCounts;

    // setIntakeDegreeToZero();
  }

  public void setIntakeMotorDoingSomething(double speed) {
    neo1.set(speed);
  }

  // public void setIntakeDegreeToZero() {
  //   intakemotor.set(99999999);
  //   intakemotor.disable();
  // }

  public double getIntakeDegree() {
    return falcon.getSelectedSensorPosition();
  }

  public void resetGyro() {
    gyro.reset();
  }

  public Rotation2d getRotation2d() {
    return gyro.getRotation2d();
  }

  public void setEncoderToZero() {
    drivecancoder1.setPosition(0);
    drivecancoder2.setPosition(0);
  }

  public double getDistance() {
    return ((drivecancoder1.getPosition() + drivecancoder2.getPosition()) / 2) * (Constants.Chassis.wheelMeters * Math.PI / 360);
  }

  public void arcadeDrive(double x, double z) {
    drive.arcadeDrive(-x, -z);
  }

  public void setStates(InputStates state) {
    this.state = state;
  }

  public void updateStates() {
    switch (state) {
      case A:// hmm
        intakeGoalAngle = Constants.Chassis.low;
        intakeState = IntakeStates.low;
        falcon.set(MathUtility.clamp(tilterPID.calculate(intakeGoalAngle-intakeCurrentAngle), -0.3, 0.3));
        js1.setRumble(RumbleType.kBothRumble, 0);
        break;
      case B:// hmm
        break;
      case X:
        intakeGoalAngle = Constants.Chassis.mid;
        intakeState = IntakeStates.mid;
        falcon.set(MathUtility.clamp(tilterPID.calculate(intakeGoalAngle-intakeCurrentAngle), -0.3, 0.3));
        js1.setRumble(RumbleType.kBothRumble, 0);
        break;
      case Y:
        intakeGoalAngle = Constants.Chassis.high;
        intakeState = IntakeStates.high;
        falcon.set(MathUtility.clamp(tilterPID.calculate(intakeGoalAngle-intakeCurrentAngle), -0.3, 0.3));
        js1.setRumble(RumbleType.kBothRumble, 0);
        break;
      case idle:
        intakeGoalAngle = Constants.Chassis.idle;
        intakeState = IntakeStates.idle;
        falcon.set(MathUtility.clamp(tilterPID.calculate(intakeGoalAngle-intakeCurrentAngle), -0.3, 0.3));
        js1.setRumble(RumbleType.kBothRumble, 1);
        break;
    }
  }

  public void shootByState(){
    switch(intakeState){
      case low:
        setIntakeMotorDoingSomething(Constants.Chassis.shootLow);
      case mid:
        setIntakeMotorDoingSomething(Constants.Chassis.shootMid);
      case high:
        setIntakeMotorDoingSomething(Constants.Chassis.shootHigh);
      case idle:
        setIntakeMotorDoingSomething(Constants.Chassis.shootIdle);
    }
  }
}