package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by infocom24206 on 2018-06-08.
 */

public class GraphicObject {
    protected Bitmap m_bitmap; //비트를 표현하기위한
    protected  int m_x, m_y; //좌표값

    //생성자
    public GraphicObject(Bitmap bitmap){
        m_bitmap = bitmap;
        m_x=0;
        m_y=0;
        //생성자에서 비트맵을 로드하고 좌표를 원점으로 초기화하는부분
    }
    //좌표를 설정하는 메소드들 좌표설정
    public  void SetPosition(int x, int y){
        m_x = x;
        m_y = y;
    }
    //이미지를 그림.
    public void Draw(Canvas canvas){//그림을 그리게하는 코드 (메소드)
        canvas.drawBitmap(m_bitmap,m_x,m_y,null);
    }
    //x좌표를 반환
    public int GetX(){
        return m_x;
    }
    //y좌표를 반환.
    public int GetY(){
        return  m_y;
    }
    ///여기까지


    /*
    GraphicObject obj;
    obj = new GraphicObject(비트맵데이터)
    //GraphicObject 사용방법
    obj.Draw(canvas);*/
}
