package com.crazy.petter.warehouse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import net.wequick.small.Small;

/**
 * An example launcher activity that setUp the Small bundles and launch the main plugin.
 */
public class LaunchActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        textView = (TextView) findViewById(R.id.text);
        if (Small.getIsNewHostApp()) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Small.setUp(this, new Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Small.openUri("login", LaunchActivity.this);
                        finish();
                    }
                }, 1000);
            }
        });
    }
}
