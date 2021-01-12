package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="ParkByTime")
public class ParkByTime extends LinearOpMode {

    // Declare OpMode members.
    // private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topLeftDrive = null;
    private DcMotor topRightDrive = null;
    private DcMotor bottomLeftDrive = null;
    private DcMotor bottomRightDrive = null;

    // static final int MOTOR_TICK_COUNT = 1120;
    // static final int GEAR_RATIO = 80 / 40;
    // static final double CIRCUMFERENCE = 12.1208661;

    // static final int TICKS_PER_WHEEL_ROTATION = (int) (MOTOR_TICK_COUNT / GEAR_RATIO);
    // // static final double AVG_MARGIN_OF_ERROR = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables.
        topLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_motor");
        topRightDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
        bottomLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_motor");
        bottomRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        topLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        topRightDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Run
        // Sleeps for Half a Second to Account for Implicit Momentum due to Robot Placement
        sleep(500);
        // Moves Robot Forward by Powering All Wheels Positively for 900ms 
        topLeftDrive.setPower(1);
        topRightDrive.setPower(1);
        bottomLeftDrive.setPower(1);
        bottomRightDrive.setPower(1);
        sleep(900);
        // Stops Robot
        topLeftDrive.setPower(0);
        topRightDrive.setPower(0);
        bottomLeftDrive.setPower(0);
        bottomRightDrive.setPower(0);
    }
}
