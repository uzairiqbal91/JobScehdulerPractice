package com.example.jobscehdulerpractice;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Util {

    // schedule the start of the service every 10 - 30 seconds





   public OnUpdateValueListner onUpdateValueListner;
    APIInterface apiInterface;


    public Util(OnUpdateValueListner onUpdateValueListner){
        this.apiInterface = APIClient.getClient().create(APIInterface.class);
        this.onUpdateValueListner = onUpdateValueListner;

    }

    public Util(){
        this.apiInterface = APIClient.getClient().create(APIInterface.class);
    }
    @SuppressLint("NewApi")
    public void scheduleJob(Context context) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(
                Context.JOB_SCHEDULER_SERVICE);

        // The JobService that we want to run
        final ComponentName name = new ComponentName(context, MyJobService.class);

        // Schedule the job
        final int result = jobScheduler.schedule(getJobInfo(123, 1, name));

        // If successfully scheduled, log this thing
        if (result == JobScheduler.RESULT_SUCCESS) {

            if(onUpdateValueListner != null)
            {
                this.onUpdateValueListner.onValudeUpdate();
            }

            Log.d("success", "Scheduled job successfully!");
            getList();





        }
    }



    @SuppressLint("MissingPermission")
    private JobInfo getJobInfo(final int id, final long hour, final ComponentName name) {
        final long interval = 100; // run every second
        final boolean isPersistent = true; // persist through boot
        final int networkType = JobInfo.NETWORK_TYPE_ANY; // Requires some sort of connectivity

        final JobInfo jobInfo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(id, name)
                    .setMinimumLatency(interval)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(id, name)
                    .setPeriodic(interval)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        }

        return jobInfo;
    }


    public void getList(){

        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (MultipleResource.Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";


                    Log.i("data rsponse",datum.name);

                }



            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });


    }

}
