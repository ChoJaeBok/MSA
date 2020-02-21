package my.msagame;

/**
 * Created by infocom24206 on 2018-06-11.
 */

public class Enemy_3 extends Enemy {
    public Enemy_3(){
        super(AppManager.getInstance().getBitmap(R.drawable.riderener));
        this.InitSpriteData(1000,200,1,6);
        speed = 1.5f;
    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.set(m_x,m_y,m_x+62,m_y+104);
        Move();
    }
}
