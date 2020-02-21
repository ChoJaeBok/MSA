package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by infocom24206 on 2018-06-08.
 */

public class IntroState implements IState {
    Bitmap icon;
    int x;
    int y;
    @Override
    public void Init() {
        icon = AppManager.getInstance().getBitmap(R.mipmap.ic_launcher_round);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        canvas.drawBitmap(icon,x,y,null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        AppManager.getInstance().getGameView().ChangeGameState(new CreditState());//터치시 Introstate에서 creditstate로
        return true;
    }
}
