package com.toishitech.android.ball;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements Runnable {
    Ball ball;
    Handler handler;
    int width, height;
    int dx = 10, dy = 10, time = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.GREEN);
        setContentView(relativeLayout);

        handler = new Handler();
        handler.postDelayed(this, time);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;
        ball = new Ball(this);
        ball.x = width / 2;
        ball.y = height / 2;
        relativeLayout.addView(ball);
    }

    @Override
    public void run() {
        ball.x += dx;
        ball.y += dy;

        if (ball.x <= ball.radius) {
            ball.x = ball.radius;
            dx = -dx;
        } else if (ball.x >= width - ball.radius) {
            ball.x = width - ball.radius;
            dx = -dx;
        }

        if (ball.y <= ball.radius) {
            ball.y = ball.radius;
            dy = -dy;
        } else if (ball.y >= height - ball.radius) {
            ball.y = height - ball.radius;
            dy = -dy;
        }

        ball.invalidate();
        handler.postDelayed(this, time);
    }

    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
    }
}
