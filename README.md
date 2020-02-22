## Android Studio을 통한 이용한 게임(장애물 피하기 게임)
![title](https://user-images.githubusercontent.com/60215726/75008874-8e86b880-54bc-11ea-86e2-c39a727ce509.PNG)

* 자유로운 앱 제작의 과제 프로젝트로 통해서 처음으로 도전하며 3주의 시간정도 들어간 Android Studio 기반으로 제작한 게임 앱 입니다. 처음으로 게임 앱 제작에 도전하다보니 게임 코드 면에서 부족한 부분도 많고 디자인 또한 부족한 점이 많이 있습니다.   
* 특징으로는 main화면을 제외한 게임 중에 player 캐릭터의 움직임, 장애물들의 패턴, 회복시켜주는 아이템, 그리고 맵 모든 것들은 XML을 이용하지 않고 자바 코드로만 구현을 했다는 점입니다.
* 이 프로젝트를 진행하면서 참고한 책으로는 만들면서 배우는 안드로이드 게임 프로그래밍(개정판)입니다.
   
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
   
#### 2) IState(인터페이스)
인터페이스로서 여러 클래스에서 공통적인 부분을 추상화한 것입니다. 
```java
public interface IState {
    public void Init();
    //이 상태로 바뀌었을 때 실행할 것들
    public void Destroy();
    //다른 상태로 바뀔 때 실행할 것들
    public void Update();
    //지속적으로 수행할 것들
    public void Render(Canvas canvas);
    //그려야 할것들
    public boolean onKeyDown(int keyCode, KeyEvent event);
    //키입력처리
    public boolean onTouchEvent(MotionEvent event);
    //터치 입력 처리

}
```
#### 3) GraphicObject.java
게임 실행에서 여러 가지 종류의 그래픽 객체에 대한 일반적인 속성을 제공하는 기본 클래스입니다. 
```java
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
}

```
   
#### 4) GameViewThread.java
Thread 클래스를 상속받는 클래스로써 GameView와 SurfaceHolder를 생성자 매개변수로 가지며, 스레드 실행을 위해 run 메서드를 재정의 합니다.
```java
/그림을 관리하는 클래스를 생성하는것  그림처리는 스레드로 이용
public class GameViewThread extends Thread {
    //접근을 위한 멤버 변수
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameview;
    //스레드 실행 상태 멤버 변수
    private boolean m_run =false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView){
        m_surfaceHolder = surfaceHolder;
        m_gameview = gameView;
    }
    public void setRunning(boolean run){
        m_run = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas _canvas;
        while(m_run){
            _canvas = null;
            try{
                m_gameview.Update();
                //SurfaceHolder를 통해 Surface에 접근해서 가져옴
                _canvas= m_surfaceHolder.lockCanvas(null);
                synchronized (m_surfaceHolder){
                    m_gameview.onDraw(_canvas);//그림을 그림
                }
            }finally {
                if (_canvas != null)//surface를 화면에 표시함
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
            }
        }

    }
}
```
   
#### 5) SpriteAnimation.java
게임에서 애니메이션을 담당하는 클래스입니다.
```java
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
            if(m_CurrentFrame >= m_iFrames){m_CurrentFrame = 0;}// 최대 프레임 수 초과하면 다시 첫번째로
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
```

#### 3) 게임진행
 여기서는 만들어진 장애물, 아이템, 플레이어의 움직임, 점수에 따른 맵 배경 변경 및 난이도 상승등을 컨트롤 할 수 있습니다.   

#####  (1) 장애물의 패턴과 난이도 조절   
* 장애물의 패턴   
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
   

* 장애물의 난이도조절   
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
                    //여기서는 장애물의 종류가 3가지이므로 3가지 중 랜덤으로 장애물이 나오도록 설정하는 곳입니다.
                } else {
                    enem.movetype = ranEnem.nextInt(13);
                    //여기서는 장애물의 패턴의 종류가 13가지 중 랜덤으로 패턴을 정하는 곳입니다.
                }
                m_enemlist.add(enem);
            }
        }
    }
```   
##### (2) 아이템    
아이템에서는 두 가지로 설정하였습니다. 아이템이라고 하면 먹을 시에 라이프의 개수가 하나 증가하는 역할을 합니다.
아이템은 장애물의 코드와 같습니다. 
   

##### (3) 맵   
맵은 정적인 이미지를 사용하여 스크롤이라는 효과를 줌으로서 동적인 것처럼 제작하였습니다. 
맵은 총 두 가지로 제작하였으며 테블릿으로 디자인을 그렸습니다. 
![map](https://user-images.githubusercontent.com/60215726/75035190-4a160f80-54f2-11ea-8451-c132204ec40a.PNG)
 * BackGround.java   
여기서는 스크롤의 효과와 스피드를 지정해주었습니다.
```java
public class BackGround extends  GraphicObject{
    static final float SCROLL_SPEED = 30f;
    private float m_scroll = -2000 +480;

    public BackGround() {
        super(AppManager.getInstance().getBitmap(R.drawable.rlroad));
   
        SetPosition(0,(int)m_scroll);
    }

    void Update(long GameTime){

        m_scroll = m_scroll +SCROLL_SPEED;
        if(m_scroll >= 0 )
            m_scroll = -2000+480;
        SetPosition(0, (int)m_scroll);

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
            canvas.drawBitmap(m_bitmap, m_x, m_y, null);
        
        }
    }
‘’‘
맵을 그려주고 효과를 주었으니 GameState.java에서 Update() 매소드를 이용하여 지속적으로 변화를 수행시켜줍니다.
```java
//GameState.java
 @Override  //지속적으로 수행할 것들
    public void Update() {
        m_backgroud.Update(GameTime);
        m_background2.Update(GameTime);
//...
}
```
   

##### (4) player   
플레이어는 좌,우로 만 움직일 수 있도록 설정하고 게임을 진행하는 방식으로 제작하였습니다.
플레이어가 장애물이나 아이템과의 코드가 다른점은 움직임에 따른 코드와 충돌시에 라이프 변화에 따른 코드가 있습니다.   
* Player.java   
여기서는 플레이어 캐릭터에 대한 그림과, 초기 위치값, 라이프등에 대한 것이 설정 되어있습니다.
라이프 회복을 해주는 AddLife()와 데미지를 받는 destroyPlayer() 메소드가 있습니다.
```java
Public class Player extends SpriteAnimation{
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
```   
   

##### (5) 충돌   
충돌이라는 것은 장애물과 플레이어, 라이프회복아이템과 플레이어 간에 발생하도록 해주는 것으로 캐릭터와 장애물, 아이템에 사각형이라는 안 보이는 틀을 그려 효과를 일으키도록 구현하였습니다.
*  CollisionManager.java   
이 클래스에서는 충돌 처리에 관한 로직을 담당하는 곳입니다.
```java
public class CollisionManager {
    public static  boolean CheckBoxToBox(Rect _rt1, Rect _rt2) {
        if (_rt1.right > _rt2.left && _rt1.left < _rt2.right && _rt1.top < _rt2.bottom && _rt1.bottom > _rt2.top){
            return true;}
            return false;
    }
}
```
* 장애물, 아이템, 플레이어에 추가.   
```java
Rect m_BoundBox = new Rect(); 
```
Enermy.java, Item.java, Player.java 각각에 추가하여 충돌 처리를 할 수 있도록 해줍니다.
```java
 @Override
    public void Update(long GameTime) {
        m_BoundBox.left = m_x;
        m_BoundBox.top = m_y;
        m_BoundBox.right = m_x+62;
        m_BoundBox.bottom = m_y+104;
    }
```
그리고 장애물, 아이템, 플레이어는 정적으로 있는 것이 아니므로 이와 같이 동적으로 움직여도 충돌 박스가 같이 갈 수 있도록 구현을 해주었습니다.

```java
    public void Update() {
     // GameState.java에서 충돌 관련성 있는 명령어입니다.
        for(Enemy enem : m_enemlist) {
            enem.Update(GameTime);
        }
        for(Item item:m_itemlist){
            item.Update(GameTime);
        }
        CheckCollision();
    }
```
for문을 이용해서 각각의 충돌을 하였을 때 사라지도록 구현하였습니다.   
   
### 3. 메인 및 게임 실행 화면
![ima](https://user-images.githubusercontent.com/60215726/75088558-581b6d00-5592-11ea-9433-52934e59f59e.PNG)
