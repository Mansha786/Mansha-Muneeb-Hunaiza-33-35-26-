package com.example.mansha.cab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity
    {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread;
        thread = new Thread()
{
    @Override
    public void run() {
        try {
            sleep(7000);

        }
        catch (InterruptedException e)
        {
            System.out.println("Exception occurred");

        }
        finally
        {
            Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);

        }
    }
};
        thread.start();
    }

        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }
    }
