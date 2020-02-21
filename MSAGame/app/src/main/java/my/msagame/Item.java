package my.msagame;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by infocom24206 on 2018-06-10.
 */

public class Item extends SpriteAnimation{
    protected float speedItem;
    public static  final int MOVE_PAT_1 = 0;
    public static  final int MOVE_PAT_2 = 1;
    public static  final int MOVE_PAT_3 = 2;
    public static  final int MOVE_PAT_4 = 3;
    public static  final int MOVE_PAT_5 = 4;
    public static  final int MOVE_PAT_6 = 5;
    public static  final int MOVE_PAT_7 = 6;
    public static  final int MOVE_PAT_8 = 7;
    public static  final int MOVE_PAT_9 = 8;
    public static  final int MOVE_PAT_10 = 9;
    public static  final int MOVE_PAT_11= 10;
    public static  final int MOVE_PAT_12 = 11;

    protected int movet;
    Rect m_BoundBox = new Rect();


    public Item(Bitmap bitmap) {super(bitmap);    }

    void Move1(){
        if(movet == MOVE_PAT_1){
            //첫번째
                m_x = 50;
                m_y += speedItem*2; //중간지점까지 기본속도로

        }
        else if(movet == MOVE_PAT_2){
            //두번째
            m_x = 380;
            m_y = 10000;

        }
        else if(movet == MOVE_PAT_3){
            //세번째
                m_x = 700;
                m_y += speedItem*3; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_4){
            //세번째
            m_x = 50;
            m_y =10000; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_5){
            //세번째
            m_x = 380;
            m_y += speedItem*2; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_6){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_7){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }

        else if(movet == MOVE_PAT_8){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }

        else if(movet == MOVE_PAT_9){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_10){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_11){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }
        else if(movet == MOVE_PAT_12){
            //세번째
            m_x = 700;
            m_y = 10000; //중간지점까지 기본속도로
        }





    }
    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        Move1();
    }
}
