package com.example.shietoo.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by shietoo on 2016/8/4.
 */
public class Myprinter extends View {
    private LinkedList<LinkedList<HashMap<String,Float>>> lines,recycle;
    private Paint paint;
    private Bitmap bitmap =null;
    private Canvas vcanvas = null;
    private int width,height;
    private Context context;
    private int stroke;





    public Myprinter(Context context, AttributeSet attrs){
        super(context,attrs);

        setBackgroundColor(Color.RED);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        lines = new LinkedList<>();
        recycle = new LinkedList<>();


    Myprinter.this.post(new Runnable() {
        @Override
        public void run() {
            try {
                width = getWidth();
                height  = getHeight();
                bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

            }catch (Exception e){
                Log.i("aaa",e.toString());
            }
        }
    });


    }


    @Override
    protected void onDraw(Canvas canvas) {


        for (LinkedList<HashMap<String,Float>> line : lines){
            for (int i=1;i<line.size();i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"),p0.get("y"),
                        p1.get("x"),p1.get("y"),paint);
                canvas.drawPoint(p1.get("x"),p1.get("y"),paint);
               // canvas.drawBitmap(bitmap,0,0,null);

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX();float ey = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            doDown(ex,ey);
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            doMove(ex,ey);

        }


        return true; //super.onTouchEvent(event);
    }
    private void doDown(float x,float y){
        recycle.clear();
        LinkedList<HashMap<String,Float>> line = new LinkedList<>();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x",x);point.put("y",y);
        line.add(point);
        lines.add(line);
    }
    private void doMove(float x,float y){
        HashMap<String,Float> point = new HashMap<>();
        point.put("x",x);point.put("y",y);
        lines.getLast().add(point);
        invalidate();
    }

    public void clear(){
        Log.i("aaa","clear"+String.valueOf(lines.size()));
        lines.clear();
        invalidate();
    }

    public void undo(){
        if (lines.size()>0){
            recycle.add(lines.removeLast());
            invalidate();
        }
    }
    public void redo(){
        if (recycle.size()>0){
            lines.add(recycle.removeLast());
            invalidate();
        }
    }
    public void save(){
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        try {
            File dir = new File("sdcard/DCIM/test/");
            File path = new File(dir,getDrawingTime()+"draw.png");
            if (!dir.exists()){
                dir.mkdirs();
            }else {
                FileOutputStream fos = new FileOutputStream(path);
                Log.i("aaa", String.valueOf(getDrawingTime()));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            }
        }catch (Exception e){}

    }

 public int getColor(){
     return paint.getColor();
 }
    public void setColor(int newcolor){
        paint.setColor(newcolor);
    }
public int getBGColor(){
    return getDrawingCacheBackgroundColor();
}
    public void setBGColor(int color){
        setBackgroundColor(color);
    }
public void getStoke(int stoke){
    stroke = stoke;
    paint.setStrokeWidth(stroke);
    Log.i("aaa",String.valueOf(stroke));
}
}
