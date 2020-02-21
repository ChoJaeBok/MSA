package my.msagame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Random;

import static android.support.v4.content.ContextCompat.createDeviceProtectedStorageContext;
import static android.support.v4.content.ContextCompat.getDrawable;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by infocom24303 on 2018-06-08.
 */

public class GameState implements IState {

    // 멤버 변수를 추가할 곳
    private Player m_player;
    private BackGround m_backgroud;
    private BackGround2 m_background2;
    private GameView m_gameView;


    Random ranEnem = new Random();
    Random ranmItem = new Random();
    public long m_score = 0;
    public int time = 0;
    private static GameState s_instance;
    ArrayList<Item>m_itemlist = new ArrayList<Item>();
    ArrayList<Enemy> m_enemlist = new ArrayList<Enemy>();



    long LastRegenEnemy = System.currentTimeMillis();// 시간이 조금씩 흐를 때마다 적이 등장
    long LastRegenItem = System.currentTimeMillis();//시간이 조금씩 흐를 때마다 적이 등장

    public static GameState getInstance(){
        if(s_instance == null){
            s_instance = new GameState();
        }
        return s_instance;
    }

    public void MakeEnemy(){
        if (time>=1200) {
            if (System.currentTimeMillis() - LastRegenEnemy >= 200) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
        else if(time>=500){
            if (System.currentTimeMillis() - LastRegenEnemy >= 500) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
        else if(time>=200){
            if (System.currentTimeMillis() - LastRegenEnemy >= 700) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
        else if(time>=120){
            if (System.currentTimeMillis() - LastRegenEnemy >= 1000) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
        else if(time>=60){
            if (System.currentTimeMillis() - LastRegenEnemy >= 1500) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
        else {
            if (System.currentTimeMillis() - LastRegenEnemy >= 2000) {
                LastRegenEnemy = System.currentTimeMillis();

//            Enemy enem = new Enemy_2();
//            enem.SetPosition(ranEnem.nextInt(50),-60);
//            //enem.SetPosition(0,-60);
//
//            enem.movetype = ranEnem.nextInt(3);

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }
//            else if(enemtype==3){
//                enem = new Enemy_3();
//            }
                enem.SetPosition(0, -60);
                if (m_player.getLife() > 5) {
                    enem.movetype = ranEnem.nextInt(3);
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                }
                m_enemlist.add(enem);
            }
        }
    }
    public void MakeItem(){
        if(System.currentTimeMillis()-LastRegenItem>=1500){
            LastRegenItem = System.currentTimeMillis();

            int itemtype = ranmItem.nextInt(2);
            Item item = null;

            if (itemtype == 0) {
                item = new Item_1();
            }
            else if(itemtype==1){
                item = new Item_2();
            }
            item.SetPosition(0,-60);
            item.movet =ranmItem.nextInt(12);
            m_itemlist.add(item);
        }

    }
    public void CheckCollision(){
        for(int i = m_enemlist.size()-1;i>=0;i--){
            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox,m_enemlist.get(i).m_BoundBox)){
                m_enemlist.remove(i);
                m_player.destroyPlayer();
                if (m_player.getLife()<=0) {

                  System.exit(0);
                }
                return;
            }
        }
        for(int i = m_itemlist.size()-1;i>=0;i--){
            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox,m_itemlist.get(i).m_BoundBox)){
                m_itemlist.remove(i);
                m_player.AddLife();;
                return;
            }
        }
        for (Enemy enem : m_enemlist){
            CollisionManager.CheckBoxToBox(m_player.m_BoundBox,enem.m_BoundBox);
        }

    }



    @Override //이 상태로 바뀌었을 때 실행할 것들
    public void Init() {
        m_player = new Player(AppManager.getInstance().getBitmap(R.drawable.riderplayer));
        m_backgroud = new BackGround();
        m_background2 = new BackGround2();


    }

    @Override //다른 상태로 바뀔 때 실행할 것들
    public void Destroy() {


    }



    @Override  //그려야 할것들
    public void Render(Canvas canvas) {

        if (time>=1000) {
            m_background2.Draw(canvas);
        }
        else if (time>=500){
            m_backgroud.Draw(canvas);
        }
        else if (time>= 200){
            m_background2.Draw(canvas);
        }
        else if(time<200||time>=1500){
            m_backgroud.Draw(canvas);
        }
        for(Enemy enem : m_enemlist){
            enem.Draw(canvas);
        }
        for(Item item:m_itemlist){
            item.Draw(canvas);
        }
        m_player.Draw(canvas);
        Paint p = new Paint();
        p.setTextSize(50);
        p.setColor(Color.RED);
        canvas.drawText("Life :" +String.valueOf(m_player.getLife()),740,50,p);
        p.setTextSize(50);
        p.setColor(Color.BLUE);
        canvas.drawText("Score :" +String.valueOf(time),0,50,p);



    }
    @Override  //지속적으로 수행할 것들
    public void Update() {
        long GameTime = System.currentTimeMillis();
        m_player.Update(GameTime);
        m_backgroud.Update(GameTime);
        m_background2.Update(GameTime);
        for(Enemy enem : m_enemlist) {
            enem.Update(GameTime);
        }
        for(Item item:m_itemlist){
            item.Update(GameTime);
        }
        m_score+=System.currentTimeMillis()%3600;
        time = (int)m_score/10000;
        MakeEnemy();
        MakeItem();
        CheckCollision();


    }

    @Override //키입력처리
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 키 입력에 따른 플레이어 이동
        int x = m_player.GetX();
        int y = m_player.GetY();

        if(keyCode ==KeyEvent.KEYCODE_DPAD_LEFT) //왼쪽
        {
            if (x<349){
                x = 50;
                m_player.SetPosition(x , y);
            }
            else {
                m_player.SetPosition(x - 350, y);
            }
            }

        if(keyCode ==KeyEvent.KEYCODE_DPAD_RIGHT) //오른쪽
        {
            if(x>700){
                x = 750;
                m_player.SetPosition(x , y);
            }
            else {
                m_player.SetPosition(x + 350, y);
            }
            }
//        if(keyCode ==KeyEvent.KEYCODE_DPAD_UP) //위쪽
//            m_player.SetPosition(x,y-3);
//        if(keyCode ==KeyEvent.KEYCODE_DPAD_DOWN) //아래
//            m_player.SetPosition(x,y+3);

        return false;
    }


    @Override //터치 입력 처리
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }


}
