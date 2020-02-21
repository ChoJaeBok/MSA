package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by infocom24206 on 2018-06-08.
 */

public class CreditState implements IState {
    Bitmap android;
    int x,y;
    @Override
    public void Init() {
        android = AppManager.getInstance().getBitmap(R.drawable.rider);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        canvas.drawBitmap(android,x,y,null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        AppManager.getInstance().getGameView().ChangeGameState(new IntroState());//터치시 Introstate에서 creditstate로
        return true;
    }
}
