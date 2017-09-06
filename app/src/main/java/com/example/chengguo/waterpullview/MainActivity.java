package com.example.chengguo.waterpullview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final float TOUCH_MOVE_MAX_Y = 600;
    private float mTouchMoveStartX;
    private float mTouchMoveStartY = 0;
    private PullView pullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pullView = (PullView) findViewById(R.id.pullView);
        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = motionEvent.getY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float y = motionEvent.getY();
                        if (y > mTouchMoveStartY) {
                            float moveSize = y - mTouchMoveStartY;
                            float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : moveSize / TOUCH_MOVE_MAX_Y;
                            pullView.setProgress(progress);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        pullView.release();
                        break;
                }
                return false;
            }
        });
    }
}
