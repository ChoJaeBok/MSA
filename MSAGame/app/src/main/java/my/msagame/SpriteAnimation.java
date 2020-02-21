package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by infocom24206 on 2018-06-08.
 */
//움직이게 하는 클래스
public class SpriteAnimation extends GraphicObject{

    private Rect m_Rect;//그릴 범위정보, 그려줄 사각영역
    private int m_fps;//초당프레임
    private int m_iFrames;// 프레임 개수

    //애니매이션이 얼마나 진행되엇는지를 알려주는 용도(현재 프레임을 담는 멤버 변수)
    //이변수는 시간이 지남에 따라 값이 증가하고, 그에 따라 그려지는 이미지도 바뀌도록 구현, 나머지는 개별 프레임의 높이와 넓이를 담아줄 멤버 변수.
    private  int m_CurrentFrame;//최근 프레임
    private  int m_SpriteWidth;
    private  int m_SpriteHeight;
    private long m_FrameTimer;
    public SpriteAnimation(Bitmap bitmap){
        super(bitmap);
        //멤버 변수 초기화
        m_Rect = new Rect(0,0,0,0);//x,y,w,h
        m_CurrentFrame = 0;
        m_FrameTimer = 0;
    }
    //인스턴스에 스프라이트 애니메이션 정보를 대입하는 InitSpriteData메소드
    public void InitSpriteData(int _width, int _height, int _fps, int _iFrames){
        m_SpriteWidth = _width;
        m_SpriteHeight = _height;
        m_Rect.top = 0;
        m_Rect.bottom = m_SpriteHeight;
        m_Rect.left = 0;
        m_Rect.right = m_SpriteWidth;
        m_fps = 1000/_fps;//밀리초 단위로 프레임 수행(1초)
        m_iFrames = _iFrames;
    }
    public void Update(long GameTime){//게임시간 프레임변환주기 위한 시간.
        if(GameTime>m_FrameTimer+m_fps){
            m_FrameTimer = GameTime;
            m_CurrentFrame+=1;
            if(m_CurrentFrame >= m_iFrames){m_CurrentFrame = 0;}// 최대 프레임 수 초과하면 다시 첫번쨰로
        }
        m_Rect.left = m_CurrentFrame*m_SpriteWidth;
        m_Rect.right = m_Rect.left +m_Rect.right;
    }
    @Override
    public void Draw(Canvas canvas) {
        Rect dest = new Rect(m_x,m_y,m_x+m_SpriteWidth,m_y+m_SpriteHeight);
        canvas.drawBitmap(m_bitmap,m_Rect,dest,null);//bitmap,rect,st,paint
    }
}
