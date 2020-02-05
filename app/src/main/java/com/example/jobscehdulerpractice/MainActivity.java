package com.example.jobscehdulerpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = APIClient.getClient().create(APIInterface.class);




        new Util(new OnUpdateValueListner() {
            @Override
            public void onValudeUpdate() {


                Toast.makeText(getApplicationContext(), "updated ", Toast.LENGTH_LONG).show();

            }
        }).scheduleJob(getApplicationContext());


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
