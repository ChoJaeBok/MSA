package my.msagame;

import android.graphics.Canvas;

/**
 * Created by infocom24206 on 2018-06-11.
 */

public class BackGround2 extends GraphicObject {
    static final float SCROLL_SPEED = 30f;
    private float m_scroll = -2000 +480;

    public BackGround2() {
        super(AppManager.getInstance().getBitmap(R.drawable.rlroad2));
        //  m_layer2 = AppManager.getInstance().getBitmap(R.drawable.background_2);
        SetPosition(0,(int)m_scroll);
    }

    void Update(long GameTime){

        //배경스크롤
        m_scroll = m_scroll +SCROLL_SPEED;
        if(m_scroll >= 0 )
            m_scroll = -2000+480;
        SetPosition(0, (int)m_scroll);

        //구름 스크롤 (시차 스크롤링)
//        m_scroll_2 = m_scroll_2+SCROLL_SPEED_2;
//       if(m_scroll_2 >=0)
//         m_scroll_2=-2000+480;
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
        //   canvas.drawBitmap(m_layer2, m_x, m_scroll_2 ,null);
    }
}