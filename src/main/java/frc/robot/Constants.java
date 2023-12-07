package frc.robot;

public final class Constants {
    public static class JoystickConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    public static final int leftStick_X = 0;
    public static final int leftStick_Y = 1;
    public static final int rightStick_X = 4;
    public static final int rightStick_Y = 5;
    public static final int trigger_L = 2;
    public static final int trigger_R = 3;
    public static final int btn_A = 1;
    public static final int btn_B = 2;
    public static final int btn_X = 3;
    public static final int btn_Y = 4;
    public static final int btn_LB = 5;
    public static final int btn_RB = 6;
    public static final int btn_LS = 9;
    public static final int btn_RS = 10;
  }

  public static final class Chassis {
    public static final int LFMotor = 1;
    public static final int LRMotor = 2;
    public static final int RFMotor = 3;
    public static final int RRMotor = 4;
    public static final int Cancoder1 = 5;
    public static final int Cancoder2 = 6;
    public static final int Falcon = 7;//hmm
    public static final int Neo1 = 8;//hmm
    public static final int Neo2 = 9;//hmm
    public static final double wheelMeters = 0.1495;
    public static final double falconEncoderCounts = 2048;

    public static final double low = -90;
    public static final double mid = -45;
    public static final double high = -25;
    public static final double idle = 0;

    public static final double in = 0.45;
    public static final double shootLow = -0.15;
    public static final double shootMid = -0.56;
    public static final double shootHigh = -0.95;
    public static final double shootIdle = 0;
  }
}
