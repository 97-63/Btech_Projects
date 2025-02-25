package com.multiPingPong.pong;

import android.content.Context;
import android.os.Handler;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

public class GameTouchListener implements View.OnTouchListener {
    private Handler mHandler;
    private int x = 0;
    final private int delay = 1;
    private int bat;
    //private boolean isLeft;
    GestureDetector gestureDetector;
    DrawerLayout drawerLayout;

    public GameTouchListener(Context context, int bat, DrawerLayout drawer){
        this.bat = bat;
        gestureDetector = new GestureDetector(context, new GestureListener());
        drawerLayout = drawer;
    }

    @Override public boolean onTouch(View v, MotionEvent event) {
        x = (int) event.getX();

        if (event.getAction() ==  MotionEvent.ACTION_UP) {
            if (mHandler == null) return true;
            mHandler.removeCallbacks(action);
            mHandler = null;
            GameState.stopBat(bat);
        }
        return gestureDetector.onTouchEvent(event);
    }
    private Runnable action = new Runnable() {
        @Override public void run() {

            //GameState.mKeyPressed(isLeft, x);
            GameState.mKeyPressed(x, bat, GameState._batSpeed);
            mHandler.postDelayed(this, delay);

        }
    };

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            if (mHandler != null) return true;
            mHandler = new Handler();
            //isLeft = x <= positionBat;
            mHandler.postDelayed(action, delay);
            return false;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(MainActivity.pausedPlayer == 0 || MainActivity.pausedPlayer == MainActivity.playerNum) {
                if(!GameState.getIsPaused()) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                } else {
                    GameState.toggleGameState();
                }
            }
            return true;
        }
    }
}
