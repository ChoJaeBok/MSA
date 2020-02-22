package my.msagame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by infocom24206 on 2018-06-08.
 */
//View를 연결하기 위한 surface생성,변경, 종료 이벤트 알려주는 인터페이스 surfaceholder: 실제 surface에 대한 작업자
//surface를 관리하는 surfaceHolder를 구현해야함//컨트롤하는 객체
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameViewThread m_thread;
    private  IState m_state;



    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //작동 여부 확인용 그림
        Bitmap _scratch = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);//Jpeg 사진데이터를 가지고 Bitmap  으로 만들어 줄 때 많이 사용한다.
//        canvas.drawColor(Color.RED);//배경
        //canvas.drawBitmap(_scratch,10,10,null);
        m_state.Render(canvas);
    }
    public GameView(Context context){
        super(context);

        setFocusable(true); // 키 입력을 지원하는 안드로이드 폰에서 주인공 캐릭터 이동 구현

        getHolder().addCallback(this);//내가 만들 객체가 surfaceHolder롤백을 수신하고자 한다는것을 surfaceholder에게 알림.
        m_thread = new GameViewThread(getHolder(),this);

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());
        ChangeGameState(new GameState());


    }
    public void Update(){
        //프레임 워크에서 사용자의 입력이나 안드로이드 내외부의 신호를 받지 않더라도 데이터를 자동으로 갱신
        //updata메서드를 스레드에서 지속적으로 실행해야만 갱신이 수행되므로 gameviewthread의 run메서드에서 update메서드를 실행하도록
        m_state.Update();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        m_state.onKeyDown(keyCode,event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        m_state.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //뷰크기
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//뷰메모리내 만들다
        //스레드를 실행 상태로 만듬
        m_thread.setRunning(true);
        //스레드 실행
        m_thread.start();
    }
// gameview의 surface가 생성될 때 스레드를 실행하고, surface가 파괴될 때 스레드를 종료시키는 루틴을 구현
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //메모리에서 사라지면 호출
        boolean retry = true;
        m_thread.setRunning(false);
        while (retry) {
            try{
                //스레드를 중지
                m_thread.join();
                retry = false;
            }catch(InterruptedException e){
                //스레드가 종료되도록 계속 시도.
            }
        }
    }
  public void ChangeGameState(IState _state){//게임뷰에서 상태를 변경하기위한 메소드
      if (m_state!=null) m_state.Destroy();
      _state.Init();
      m_state=_state;
    }


}
