package com.example.itang.testlayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MyActivity extends ActionBarActivity {

    private View msgView;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button btn = (Button) this.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hello1");
            }
        });

        this.findViewById(R.id.rightButton).
                setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        System.out.print("111");
                        if (flag) {
                            showMsgView();
                        } else {
                            closeMsgView();
                        }
                        flag = !flag;
                    }
                });
    }

    private void showMsgView() {
        if (msgView != null) {
            msgView.setVisibility(View.VISIBLE);
            return;
        }
        ViewStub stub = (ViewStub) findViewById(R.id.msg_layout);
        msgView = stub.inflate();
    }

    private void closeMsgView() {
        if (msgView != null) {
            msgView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
