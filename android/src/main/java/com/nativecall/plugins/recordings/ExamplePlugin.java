package com.nativecall.plugins.recordings;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.JSObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.pm.PackageManager;
import android.media.MediaRecorder;

@CapacitorPlugin(name = "Example")
public class ExamplePlugin extends Plugin {

       private MediaRecorder recorder;
    private String filePath;

    @PluginMethod
    public void initiateCall(JSObject callOptions) {
        String phoneNumber = callOptions.getString("phoneNumber");
        if (phoneNumber != null) {
            if (getContext().checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                getContext().startActivity(intent);
                startRecording();
            } else {
                requestPermission(Manifest.permission.CALL_PHONE, 1001);
            }
        }
    }

    private void startRecording() {
        try {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CallRecordings/";
            File dir = new File(dirPath);
            if (!dir.exists()) dir.mkdirs();

            String fileName = "CallRecording_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp3";
            filePath = dirPath + fileName;

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(filePath);
            recorder.prepare();
            recorder.start();

            // Register a receiver to stop recording when call ends
            getContext().registerReceiver(new CallReceiver(), new IntentFilter("android.intent.action.PHONE_STATE"));
        } catch (IOException e) {
            Log.e("CallRecorderPlugin", "Error starting recording", e);
        }
    }

    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                stopRecording();
                getContext().unregisterReceiver(this);
            }
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(getContext(), "Call recorded: " + filePath, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleOnRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can initiate the call now
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
