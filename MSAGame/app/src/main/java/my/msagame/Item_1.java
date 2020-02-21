package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by infocom24206 on 2018-06-11.
 */

public class Item_1 extends Item{
    public Item_1() {
        super(AppManager.getInstance().getBitmap(R.drawable.life2));
        this.InitSpriteData(1000,200,1,6);
        speedItem = 1.5f;
    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.set(m_x,m_y,m_x+62,m_y+104);
        Move1();
    }
}
