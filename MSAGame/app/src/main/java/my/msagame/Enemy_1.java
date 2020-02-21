package my.msagame;

public class Enemy_1 extends  Enemy {

    public  Enemy_1(){
        super(AppManager.getInstance().getBitmap(R.drawable.riderener));
        this.InitSpriteData(1000,200,1,6);
        speed = 2.5f;

    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.set(m_x,m_y,m_x+62,m_y+104);
        Move();
    }
}
