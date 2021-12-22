package com.invariablestudio.nokiaringtone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class ActivitySplash extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(ActivitySplash.this, ActivityHome.class);
                startActivity(i);
                finish();
            }
        }, 1500);


    }


}
