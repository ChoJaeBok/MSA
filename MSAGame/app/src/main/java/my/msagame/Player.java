package my.msagame;
import  android.graphics.Bitmap;
import android.graphics.Rect;


/**
 * Created by infocom24303 on 2018-06-08.
 */

public class Player extends SpriteAnimation{
    Rect m_BoundBox = new Rect();
    int m_Life = 3;

    public Player(Bitmap bitmap) {
        super(bitmap);

        // 멤버 변수를 추가할 곳
        this.InitSpriteData(1000, 300, 10, 1);

        // 초기 위치 값 설정
        this.SetPosition(400, 1100);
    }
    public int getLife(){
        return m_Life;
    }

    public void AddLife(){
        m_Life++;
    }
    public void destroyPlayer(){
        m_Life--;
    }

    @Override
    public void Update(long GameTime) {
        m_BoundBox.left = m_x;
        m_BoundBox.top = m_y;
        m_BoundBox.right = m_x+62;
        m_BoundBox.bottom = m_y+104;
    }
}
