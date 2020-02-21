package my.msagame;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by infocom24206 on 2018-06-08.
 */

public class TestState implements IState {
    private GraphicObject m_Image;
    private SpriteAnimation m_spr;
    @Override
    public void Init() {
        m_Image = new GraphicObject(AppManager.getInstance().getBitmap(R.mipmap.ic_launcher_round));
        m_spr = new SpriteAnimation(BitmapFactory.decodeResource(AppManager.getInstance().getResources(),R.mipmap.rider3)); //초기화로직에서 그래픽오브젝트를 인스턴스화했듯이 멤버 변수 m_spr도 인스턴스화
       // 이미지 파일의 애니메이션 정보를 입력해야 한다. 여기서 사용할 이미지는 좌우로 네개가 이어져 있고
        m_spr.InitSpriteData(125,167,5,4);//125x167크기 하나당 5fps, 4프레임.
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_spr.Update(GameTime);
    }

    @Override
    public void Render(Canvas canvas) {
        m_Image.Draw(canvas);
        m_spr.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
