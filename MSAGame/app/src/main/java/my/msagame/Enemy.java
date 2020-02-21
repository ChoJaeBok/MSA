package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Enemy extends SpriteAnimation {

    protected  int hp;
    protected  float speed;
    public static final int MOVE_PATTERN_1=0;
    public static final int MOVE_PATTERN_2=1;
    public static final int MOVE_PATTERN_3=2;
    public static final int MOVE_PATTERN_4=3;
    public static final int MOVE_PATTERN_5=4;
    public static final int MOVE_PATTERN_6=5;
    public static final int MOVE_PATTERN_7=6;
    public static final int MOVE_PATTERN_8=7;
    public static final int MOVE_PATTERN_9=8;
    public static final int MOVE_PATTERN_10=9;
    public static final int MOVE_PATTERN_11=10;
    public static final int MOVE_PATTERN_12=11;
    public static final int MOVE_PATTERN_13=12;


    protected int movetype;
    Rect m_BoundBox = new Rect();



    public Enemy(Bitmap bitmap){
        super(bitmap);
    }

    // 움직임
    void Move(){
        if(movetype == MOVE_PATTERN_1){
            //첫번째
            if(m_y<=200){
                m_x = 50;
                m_y += speed; //중간지점까지 기본속도로
            }
            else {
                m_y += speed*2;
            }
        }
        else if(movetype == MOVE_PATTERN_2){
            //두번째
            if(m_y<=200){
                m_x = 380;
                m_y += speed; //중간지점까지 기본속도로
            }
            else {
                m_y += speed*5;
            }
        }
        else if(movetype == MOVE_PATTERN_3){
            //세번째
            if(m_y<=200){
                m_x = 700;
                m_y += speed; //중간지점까지 기본속도로
            }
            else {
                m_y += speed*5;
            }
        }
        else if(movetype == MOVE_PATTERN_4){
            //세번째
            m_x = 700;
            m_y += speed*5 ; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_5){
            //세번째
            m_x = 50;
            m_y += speed*2; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_6){
            //세번째
            m_x = 380;
            m_y += speed*2; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_7){
            //세번째
            m_x = 700;
            m_y += speed*3; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_8){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_9){
            //세번째
            m_x = 380;
            m_y += speed*3; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_10){
            //세번째
            m_x = 50;
            m_y += speed*3; //중간지점까지 기본속도로
        }

        else if(movetype == MOVE_PATTERN_11){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_12){
            //세번째
            m_x = 700;
            m_y += speed*2; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_13){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }

    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        Move();
    }
    // 공격(필요없음)
}
