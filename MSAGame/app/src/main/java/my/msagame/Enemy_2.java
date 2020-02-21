package my.msagame;

import android.graphics.Bitmap;

/**
 * Created by infocom24206 on 2018-06-10.
 */

public class Enemy_2 extends Enemy {
    public Enemy_2() {
        super(AppManager.getInstance().getBitmap(R.drawable.ridersuper));
        this.InitSpriteData(1000,200,1,6);
        speed = 3.5f;

    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.set(m_x,m_y,m_x+62,m_y+104);
        Move();
    }
}
