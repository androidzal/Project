package com.fjnu.edu.helper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fjnu.edu.helper.R;
import com.fjnu.edu.helper.system.Commonality;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        new Thread() {
            public void run() {
                if (!fileIsExists()) {
                    copy();
                }
            }
        }.start();

        final Intent localIntent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                startActivity(localIntent);
                LoadActivity.this.finish();
            }
        };
        timer.schedule(tast, 3000);
    }

    public boolean fileIsExists() {
        try {
            File f = new File(getString(R.string.db_name));
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    public void copy() {
        String Filenames = getString(R.string.db_name);
        File dir = new File(getString(R.string.db_path));
        if (!dir.exists())
            dir.mkdir();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(Filenames);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStream is = LoadActivity.this.getResources().openRawResource(R.raw.helper);
        byte[] buffer = new byte[8192];
        int count = 0;
        try {

            while ((count = is.read(buffer)) > 0) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (IOException e) {

        }
        try {
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
