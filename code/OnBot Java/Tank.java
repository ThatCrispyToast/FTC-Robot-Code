package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank w/ Strafing")
public class Tank extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topLeftDrive = null;
    private DcMotor topRightDrive = null;
    private DcMotor bottomLeftDrive = null;
    private DcMotor bottomRightDrive = null;
    private DcMotor conveyor = null;
    private DcMotor leftIntake = null;
    private DcMotor rightIntake = null;
    private DcMotor Shooter = null;
    private Servo leftIntakeServo = null;
    private Servo rightIntakeServo = null;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

        // Initialize the Hardware Variables.
        topLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_motor");
        topRightDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
        bottomLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_motor");
        bottomRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");
        conveyor  = hardwareMap.get(DcMotor.class, "conveyor");
        leftIntake  = hardwareMap.get(DcMotor.class, "left_intake_rot");
        rightIntake  = hardwareMap.get(DcMotor.class, "right_intake_rot");
        leftIntakeServo  = hardwareMap.get(Servo.class, "left_intake");
        rightIntakeServo  = hardwareMap.get(Servo.class, "right_intake");
        Shooter = hardwareMap.get(DcMotor.class, "shooter");

        // Reverse the Motors that Face Towards the Back of the Robot
        topLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        topRightDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomRightDrive.setDirection(DcMotor.Direction.FORWARD);
        
        // Tell the Driver that Initialization is Complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    // ! Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY (Currently Unused)
    @Override
    public void init_loop() {
    }

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
        // Resets runtime Variable to 0
        runtime.reset();
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double topLeftPower;
        double topRightPower;
        double bottomLeftPower;
        double bottomRightPower;
        double conveyorPower;
        double leftIntakePower;
        double rightIntakePower;
        double shooterPower;
        
        // Gamepad 1 Controls
        // Drivetrain (Tank w/ Strafing)
        // Each Power is a Combination of the Y Value of it's Respective Stick and the Halved and Combined X Vales of the Two Sticks for Strafing
        // i.e wheelPower = -respectiveStick_y + left_stick_x/2 + right_stick_x/2;
        bottomLeftPower = -gamepad1.left_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        topLeftPower  = -gamepad1.left_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        bottomRightPower = -gamepad1.right_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        topRightPower = -gamepad1.right_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        
        // Conveyor Controls
        if (gamepad1.y) {
            // Moves Conveyor Backwards
            conveyorPower = 1;
        } else if (gamepad1.a) {
            // Moves Conveyor Forwards
            conveyorPower = -1;
        } else {
            // Stops Conveyor from Moving if Buttons Aren't Pressed
            conveyorPower = 0.0;
        }
        
        // Intake Controls
        if (gamepad1.left_bumper) {
            // Move Intake Inwards
            leftIntakePower = 1.0;
            rightIntakePower = -1.0;
        } else if (gamepad1.right_bumper) {
            // Move Intake Outwards
            leftIntakePower = -1.0;
            rightIntakePower = 1.0;
        } else {
            // Stops Intake from Moving if Buttons Aren't Pressed
            leftIntakePower = 0.0;
            rightIntakePower = 0.0;
        }
        
        
        // Gamepad 2 Controls
        // Shooter
        if (gamepad2.left_bumper) {
            // Move Shooter Inwards
            Shooter.setPower(-1.0);
        } else if (gamepad2.right_bumper) {
            // Move Shooter Outwards
            Shooter.setPower(1.0);
        } else {
            // Stops Shooter from Moving if Buttons Aren't Pressed
            Shooter.setPower(0);
        }

        // Intake Servos
        if (gamepad2.dpad_up) {
            // Moves Intake Servos Upwards
            leftIntakeServo.setPosition(1.0);
            rightIntakeServo.setPosition(0.0);
            
        } else if (gamepad2.dpad_down) {
            // Moves Intake Servos Downwards
            leftIntakeServo.setPosition(0.0);
            rightIntakeServo.setPosition(1.0);
        }

        // Limits Wheel Powers to a Range of 1.0 to -1.0
        if (topLeftPower > 1.0) topLeftPower = 1.0;
        if (topLeftPower < -1.0) topLeftPower = -1.0;
        if (bottomRightPower > 1.0) bottomRightPower = 1.0;
        if (bottomRightPower < -1.0) bottomRightPower = -1.0;
        if (bottomLeftPower > 1.0) bottomLeftPower = 1.0;
        if (bottomLeftPower < -1.0) bottomLeftPower = -1.0;
        if (topRightPower > 1.0) topRightPower = 1.0;
        if (topRightPower < -1.0) topRightPower = -1.0;

        // Send calculated power to wheels
        topLeftDrive.setPower(topLeftPower);
        topRightDrive.setPower(topRightPower);
        bottomLeftDrive.setPower(bottomLeftPower);
        bottomRightDrive.setPower(bottomRightPower);
        conveyor.setPower(conveyorPower);
        leftIntake.setPower(leftIntakePower);
        rightIntake.setPower(rightIntakePower);
        

        // Show the Elapsed Game Time, Wheel Powers, and Robot Status via Driver Station Telemetry
        telemetry.addData("Status", "Running, Run Time: " + runtime.toString());
        telemetry.addData("Motors", "Top Left (%.2f) Top Right (%.2f)\nBottom Left (%.2f) Bottom Right (%.2f)", topLeftPower, topRightPower, bottomLeftPower, bottomRightPower);
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        // Updates Robot Status
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
