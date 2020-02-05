package com.example.jobscehdulerpractice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();
    HandlerThread handlerThread = new HandlerThread("SomeOtherThread");
    @Override
    public boolean onStartJob(final JobParameters params) {


        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(getApplicationContext(),"running",Toast.LENGTH_LONG).show();

                Log.i("running","running here");
//                Toast.makeText(getApplicationContext(),"running",Toast.LENGTH_LONG).show();
                jobFinished(params, true);
                new Util().scheduleJob(getApplicationContext());

            }
        });


        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.i("stopiing","stopped here");

//        handlerThread.stop();
        return true;
    }
}
