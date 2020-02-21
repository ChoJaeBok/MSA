package my.msagame;

import android.graphics.Bitmap;

/**
 * Created by infocom24206 on 2018-06-11.
 */

public class Item_2 extends Item{
    public Item_2() {
        super(AppManager.getInstance().getBitmap(R.drawable.riderplayer));
        this.InitSpriteData(1000,200,1,6);
        speedItem = 5F;
    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.set(m_x,m_y,m_x+62,m_y+104);
        Move1();
    }
}
