package io.github.sshnakamoto.permissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.sshnakamoto.permissions.R;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA_CODE = 314;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOrAskPermissions();
            }
        });
    }

    private void checkOrAskPermissions() {
        /**
         * Check Android Version Before.
         * If device version is lower that Android M, ask for permissions, else continue.
         *
         * */
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1){
            Log.d(TAG, "checkOrAskPermissions: version");
            /**
             * Check:
             * If permission was already given, execute.
             * */
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "checkOrAskPermissions: checkSelfPermission");
                showCamera();
            } else {
                /**
                 * If permission wasn't given, ask permissions.
                 * But if permissions was already shown and denied one time, explain why before.
                 * */

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){

                    Log.d(TAG, "checkOrAskPermissions: shouldShowRequestPermissionRationale");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("In order to use camera. You must allow this permission first.")
                            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (dialog != null){
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .create()
                            .show();
                }
                /**
                 * If permission wasn't given, ask permissions.
                 * */
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);

                Log.d(TAG, "checkOrAskPermissions: requestPermissions");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showCamera();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showCamera() {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
