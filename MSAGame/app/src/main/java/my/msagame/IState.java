package my.msagame;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by infocom24206 on 2018-06-08.
 */

public interface IState {
    public void Init();
    //이 상태로 바뀌었을 때 실행할 것들
    public void Destroy();
    //다른 상태로 바뀔 때 실행할 것들
    public void Update();
    //지속적으로 수행할 것들
    public void Render(Canvas canvas);
    //그려야 할것들
    public boolean onKeyDown(int keyCode, KeyEvent event);
    //키입력처리
    public boolean onTouchEvent(MotionEvent event);
    //터치 입력 처리

}
