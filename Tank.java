/*
I am in pain.
This took unnecessarily long to do.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank", group="BaseOpModes")
public class Tank extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topLeftDrive = null;
    private DcMotor topRightDrive = null;
    private DcMotor bottomLeftDrive = null;
    private DcMotor bottomRightDrive = null;
    // static final double HDHEX_ULTRAPLANETARY_TICK_COUNT = 28 / 2.89;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

        // Initialize the hardware variables.
        topLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_motor");
        topRightDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
        bottomLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_motor");
        bottomRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");

        // Reverse the motors that face towards the back of the robot
        topLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        topRightDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomRightDrive.setDirection(DcMotor.Direction.REVERSE);

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

        // Tank Mode
        bottomLeftPower = topLeftPower  = -gamepad1.left_stick_y;
        bottomRightPower = topRightPower = -gamepad1.right_stick_y;

        // Send calculated power to wheels
        topLeftDrive.setPower(topLeftPower);
        topRightDrive.setPower(topRightPower);
        bottomLeftDrive.setPower(bottomLeftPower);
        bottomRightDrive.setPower(bottomRightPower);

        // Show the elapsed game time, wheel powers, and control award shit that ill add later
        telemetry.addData("Status", "Running, Run Time: " + runtime.toString());
        telemetry.addData("Motors", "top left (%.2f) top right (%.2f)\nbottom left (%.2f) bottom right (%.2f)", topLeftPower, topRightPower, bottomLeftPower, bottomRightPower);
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
