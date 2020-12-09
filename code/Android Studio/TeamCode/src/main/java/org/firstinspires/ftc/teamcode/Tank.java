package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank")
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
    private DcMotor shooter = null;
    private Servo leftIntakeServo = null;
    private Servo rightIntakeServo = null;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

        // Initializing hardware variables.
        topLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_motor");
        topRightDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
        bottomLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_motor");
        bottomRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");
        conveyor  = hardwareMap.get(DcMotor.class, "conveyor");
        leftIntake  = hardwareMap.get(DcMotor.class, "left_intake_rot");
        rightIntake  = hardwareMap.get(DcMotor.class, "right_intake_rot");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        leftIntakeServo  = hardwareMap.get(Servo.class, "left_intake");
        rightIntakeServo  = hardwareMap.get(Servo.class, "right_intake");

        // Reversing Flipped Motors
        topLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        topRightDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
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
        double shooterPower;
        
        // Gamepad 1 Controls
        // Drivetrain (Tank)
        bottomLeftPower = -gamepad1.left_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        topLeftPower  = -gamepad1.left_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        bottomRightPower = -gamepad1.right_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        topRightPower = -gamepad1.right_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        
        
        // Gamepad 1 Controls
        // Conveyor
        if (gamepad1.y) {
            conveyor.setPower(0.5);
            shooterPower = -1.0;
        } else if (gamepad1.a) {
            conveyor.setPower(-0.5);
            shooterPower = 1.0;
        } else {
            conveyor.setPower(0.0);
            shooterPower = 0.0;
        }
        
        // Intake
        if (gamepad1.left_bumper) {
            leftIntake.setPower(1.0);
            rightIntake.setPower(-1.0);
        } else if (gamepad1.right_bumper) {
            leftIntake.setPower(-1.0);
            rightIntake.setPower(1.0);
        } else {
            leftIntake.setPower(0.0);
            rightIntake.setPower(0.0);
        }
        
        // Intake Servos
        if (gamepad1.dpad_up) {
            leftIntakeServo.setPosition(1.0);
            rightIntakeServo.setPosition(0.0);
        } else if (gamepad1.dpad_down) {
            leftIntakeServo.setPosition(0.0);
            rightIntakeServo.setPosition(1.0);
        }

        // Caps Powers at 1.0 or -1.0
        if (topLeftPower > 1.0) topLeftPower = 1.0;
        if (topLeftPower < -1.0) topLeftPower = -1.0;
        if (bottomRightPower > 1.0) bottomRightPower = 1.0;
        if (bottomRightPower < -1.0) bottomRightPower = -1.0;
        if (bottomLeftPower > 1.0) bottomLeftPower = 1.0;
        if (bottomLeftPower < -1.0) bottomLeftPower = -1.0;
        if (topRightPower > 1.0) topRightPower = 1.0;
        if (topRightPower < -1.0) topRightPower = -1.0;

        // Send calculated power to motors
        topLeftDrive.setPower(topLeftPower);
        topRightDrive.setPower(topRightPower);
        bottomLeftDrive.setPower(bottomLeftPower);
        bottomRightDrive.setPower(bottomRightPower);
        shooter.setPower(shooterPower);
        

        // Show the elapsed game time, wheel powers, and control award shit that ill add later
        telemetry.addData("Status", "Running, Run Time: " + runtime.toString());
        telemetry.addData("Motors", "Top Left: (%.2f) Top Right: (%.2f)", topLeftPower, topRightPower);
        telemetry.addData("Motors", "Bottom Left: (%.2f) Bottom Right: (%.2f)", bottomLeftPower, bottomRightPower);
        telemetry.update();
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
