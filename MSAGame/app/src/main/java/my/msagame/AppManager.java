package my.msagame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by infocom24206 on 2018-06-08.
 */
//프레임워크를 관리하는 매니저 클래스(프레임워크를 사용하는 애플리케이션을 관리)
public class AppManager {
    private static AppManager s_instance;
    public static Object setActivity;
    private  GameView m_gameview;
    private Resources m_resources;
    private Activity activity;

    void setGameView(GameView _gameview){
        m_gameview = _gameview;
    }
    void  setResources(Resources _resources){
        m_resources = _resources;
    }
    public GameView getGameView(){
        return m_gameview;
    }
    public Resources getResources(){
        return m_resources;
    }
    public static AppManager getInstance(){
        if (s_instance ==null){
            s_instance = new AppManager();
        }
        return s_instance;
    }

    public Bitmap getBitmap(int r){
        return BitmapFactory.decodeResource(m_resources,r);
    }
}
// 게임뷰와 리소스 접근을 위해서만 사용, 나중에 상용 수준의 게임을 만든다면 여기에 애플리케이션 실행정보나 여러가지 정보를 관리하는 기능 추가가능