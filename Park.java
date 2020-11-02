package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Park", group="Linear Opmode")
public class Park extends LinearOpMode {

    // Declare OpMode members.
    // private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topLeftDrive = null;
    private DcMotor topRightDrive = null;
    private DcMotor bottomLeftDrive = null;
    private DcMotor bottomRightDrive = null;

    static final int MOTOR_TICK_COUNT = 1120;
    static final int GEAR_RATIO = 80 / 40;
    static final double CIRCUMFERENCE = 12.1208661;

    static final int TICKS_PER_WHEEL_ROTATION = (int) (MOTOR_TICK_COUNT / GEAR_RATIO);
    // static final double AVG_MARGIN_OF_ERROR = null;

    public String moveForward(double inches, double speed) {
        topLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double RawTarget = (1 / CIRCUMFERENCE * TICKS_PER_WHEEL_ROTATION) * inches;
        int TargetPosition = (int) ((1 / CIRCUMFERENCE * TICKS_PER_WHEEL_ROTATION) * inches);

        topLeftDrive.setTargetPosition(TargetPosition);
        topRightDrive.setTargetPosition(TargetPosition);
        bottomLeftDrive.setTargetPosition(TargetPosition);
        bottomRightDrive.setTargetPosition(TargetPosition);

        topLeftDrive.setPower(speed);
        topRightDrive.setPower(speed);
        bottomLeftDrive.setPower(speed);
        bottomRightDrive.setPower(speed);

        topLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return String.valueOf(TargetPosition) + " / " + String.valueOf(RawTarget);
    }

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
        // runtime.reset();

        // Run
        // INCHES VALUE DOESNT WORK YET
        String Moving = moveForward(12, 1.0);

        while (topLeftDrive.isBusy() || topRightDrive.isBusy() || bottomLeftDrive.isBusy() || bottomRightDrive.isBusy()) {
            // What the Robot Does While Its Running
            telemetry.addData("Status", "Moving... ");
            telemetry.addData("Moving To", "Target Position / Raw Target | " + Moving);
            telemetry.update();
        }

        topLeftDrive.setPower(0);
        topRightDrive.setPower(0);
        bottomLeftDrive.setPower(0);
        bottomRightDrive.setPower(0);
    }
}
