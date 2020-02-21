## Android Studio을 통한 이용한 게임(장애물 피하기 게임)
![title](https://user-images.githubusercontent.com/60215726/75008874-8e86b880-54bc-11ea-86e2-c39a727ce509.PNG)

* 자유로운 앱 제작의 과제 프로젝트로 통해서 처음으로 도전하며 3주의 시간정도 들어간 Android Studio 기반으로 제작한 게임 앱 입니다. 처음으로 게임 앱 제작에 도전하다보니 게임 코드 면에서 부족한 부분도 많고 디자인 또한 부족한 점이 많이 있습니다.   
* 특징으로는 main화면을 제외한 게임 중에 player 캐릭터의 움직임, 장애물들의 패턴, 회복시켜주는 아이템, 그리고 맵 모든 것들은 XML을 이용하지 않고 자바 코드로만 구현을 했다는 점입니다.
   
### 1. M.S.A 플로우 차트
![플로우차트](https://user-images.githubusercontent.com/60215726/75009070-0c4ac400-54bd-11ea-9721-e3e0589340f4.PNG)
   
### 2. 코드
#### 1)GameView.java
GameView는 프레임워크로서  게임 배경에 대한 그림을 비트맵으로 그리는 부분과 게임 플레이어의 조작, gameview의 surface에 대한 스레드를 관리해주는 역할을 합니다.
```java
//View를 연결하기 위한 surface생성,변경, 종료 이벤트 알려주는 인터페이스 surfaceholder: 실제 surface에 대한 작업자
//surface를 관리하는 surfaceHolder를 구현해야함//컨트롤하는 객체
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameViewThread m_thread;
    private  IState m_state;
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //작동 여부 확인용 그림
        Bitmap _scratch = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        canvas.drawColor(Color.RED);//배경
        //canvas.drawBitmap(_scratch,10,10,null);
        m_state.Render(canvas);
    }
    public GameView(Context context){
        super(context);

        setFocusable(true); // 키 입력을 지원하는 안드로이드 폰에서 주인공 캐릭터 이동 구현

        getHolder().addCallback(this);
//내가 만들 객체가 surfaceHolder롤백을 수신하고자 한다는것을 surfaceholder에게 알림.
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
```
   
#### 2) 게임진행
 여기서는 만들어진 장애물, 아이템, 플레이어의 움직임, 점수에 따른 맵 배경 변경 및 난이도 상승등을 컨트롤 할 수 있습니다.   
##### (1) 장애물의 패턴과 난이도 조절
* a) 장애물의 패턴   
프로젝트 내에서 장애물을 나타내는 코드는 패턴을 나타내는 Enemy와 세 가지의 장애물 그림을 나타내는 Enemy_1,Enemy_2,Enemy_3으로 구성되어있습니다. 
패턴에서는 등장하는 3가지 경로 중에 어느 곳으로 나오는 지 정해주는 m_x과 내려오는 속도를 정해주는 m_y로 존재하며 패턴은 이 두 가지로 섞어 패턴을 완성해주었습니다.
```java
//Enemy.java
 void Move(){
        if(movetype == MOVE_PATTERN_1){

            if(m_y<=200){
                m_x = 50;
                m_y += speed; //중간지점까지 기본속도로
            }
            else {
                m_y += speed*2;
            }
        }
        else if(movetype == MOVE_PATTERN_2){

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
 
            m_x = 700;
            m_y += speed*5 ; //중간지점까지 기본속도로
        }
        else if(movetype == MOVE_PATTERN_5){
  
            m_x = 50;
            m_y += speed*2; //중간지점까지 기본속도로
        }
}
```
패턴의 개수가 본 프로젝트에는 총 13가지로 정해놓았지만 깃허브에 저장되는 것이 너무 길어지는 것을 방지하기 위해 5패턴만 업로드하였습니다.
   
* b) 장애물의 난이도조절   
GameState.java에서 장애물을 점수(시간)이나 플레이어의 생명점수에 따라 저장된 장애물의 패턴을 조절을 해주는 역할을 합니다. 즉 난이도 조절을 해줍니다.
```java
//GameState.java 의 장애물을 만드는 코드
    Random ranEnem = new Random();
    Random ranmItem = new Random();
    ArrayList<Enemy> m_enemlist = new ArrayList<Enemy>();
    public void MakeEnemy(){
        if (time>=1200) {
            if (System.currentTimeMillis() - LastRegenEnemy >= 200) {
                LastRegenEnemy = System.currentTimeMillis();

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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

                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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


                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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



                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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


                int enemtype = ranEnem.nextInt(2);
                Enemy enem = null;
                if (enemtype == 0) {
                    enem = new Enemy_1();
                } else if (enemtype == 1) {
                    enem = new Enemy_2();
                }

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
```
