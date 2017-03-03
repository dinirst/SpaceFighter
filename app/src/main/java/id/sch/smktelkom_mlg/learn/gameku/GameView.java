package id.sch.smktelkom_mlg.learn.gameku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by diniristanti on 3/3/2017.
 */
public class GameView extends SurfaceView implements Runnable {

    //variabel boolean
    volatile boolean main;

    //game thread
    private Thread gameThread = null;


    //menambahkan pemain
    private Player player;

    //digunakan untuk drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //konstruktor kelas
    public GameView(Context context) {
        super(context);
        player = new Player(context);

        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {
        while (main) {

            update();
            draw();
            control();


        }
    }

    private void update() {
        player.update();
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //ketika game dipause
        main = false;
        try {
            //menghentikan thread
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        //ketika game dimulai, memulai thread lagi
        main = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


}
