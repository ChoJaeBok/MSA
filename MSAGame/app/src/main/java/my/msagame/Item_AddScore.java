package my.msagame;

/**
 * Created by infocom24206 on 2018-06-10.
 */

public class Item_AddScore extends Item {
    public Item_AddScore(int x, int y){
        super(AppManager.getInstance().getBitmap(R.drawable.life2));
        this.InitSpriteData(1000 ,200,3,4);
        m_x = x;
        m_y = y;
    }


}
