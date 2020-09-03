package com.me.nageswar.sensors__AAAD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.ProximityDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;

public class MainActivity extends AppCompatActivity {

    Switch s1,s2,s3,s4;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.shake);
        s2 = findViewById(R.id.light);
        s3 = findViewById(R.id.touch);
        s4 = findViewById(R.id.proxi);
        tv = findViewById(R.id.mysensor);

        /*initialisation of sensy api*/
        Sensey.getInstance().init(MainActivity.this);

        /*This is shake sensor*/
        final ShakeDetector.ShakeListener shakeListener = new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {
                tv.setText("Shake Sensor Detected");
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String camID = null;

                try {
                    camID = manager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.setTorchMode(camID,true);//Flash light on
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onShakeStopped() {
                tv.setText("Shake Stopped");
                CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String camID = null;

                try {
                    camID = manager.getCameraIdList()[0];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        manager.setTorchMode(camID,false);//Flash light off
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        };
        //handling click event
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Sensey.getInstance().startShakeDetection(shakeListener);
                }
                else{
                    Sensey.getInstance().stopShakeDetection(shakeListener);
                }
            }
        });
        /*light sensor*/
        final LightDetector.LightListener lightListener = new LightDetector.LightListener() {
            @Override
            public void onDark() {
                tv.setText("In Dark Place");
            }

            @Override
            public void onLight() {
                tv.setText("In Bright Place");
            }
        };
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Sensey.getInstance().startLightDetection(lightListener);
                } else {
                    Sensey.getInstance().stopLightDetection(lightListener);
                }
            }
        });
        /*touch sensor*/
        final TouchTypeDetector.TouchTypListener touchTypListener = new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onDoubleTap() {
                tv.setText("Double Tap");
            }

            @Override
            public void onLongPress() {
                tv.setText("Long Press");
            }

            @Override
            public void onScroll(int i) {
                tv.setText("Scroll");
            }

            @Override
            public void onSingleTap() {
                tv.setText("Single Tap");
            }

            @Override
            public void onSwipe(int i) {
                tv.setText("Swipe");
            }

            @Override
            public void onThreeFingerSingleTap() {
                tv.setText("3 Finger Single Tap");
            }

            @Override
            public void onTwoFingerSingleTap() {
                tv.setText("2 Finger Single Tap");
            }
        };
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Sensey.getInstance().startTouchTypeDetection(MainActivity.this,touchTypListener);
                }
                else{
                    Sensey.getInstance().stopTouchTypeDetection();
                }
            }
        });
        final ProximityDetector.ProximityListener proximityListener = new ProximityDetector.ProximityListener() {
            @Override
            public void onFar() {
                tv.setText("Object Far Away");
            }

            @Override
            public void onNear() {
                tv.setText("Object is Close to the sensor");
            }
        };
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Sensey.getInstance().startProximityDetection(proximityListener);
                }
                else{
                    Sensey.getInstance().stopProximityDetection(proximityListener);
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /* Stopping Sensy api */
        Sensey.getInstance().stop();
    }
}