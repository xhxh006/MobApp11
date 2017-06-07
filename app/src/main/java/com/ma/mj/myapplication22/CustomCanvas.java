package com.ma.mj.myapplication22;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * Created by KimMinJung on 2017-06-07.
 */


public class CustomCanvas extends View {
    String operationType = "";
    Bitmap mBitmap;
    Canvas mCanvas;

    Paint mPaint = new Paint();

    public CustomCanvas(Context context) {
        super(context);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public CustomCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
    }

    private void drawStamp() {

        Bitmap img = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        Paint paint = new Paint();

        if (operationType.equals("rotate")) {
            Toast.makeText(getContext(), "회전", Toast.LENGTH_SHORT).show();

            Matrix matrixRotate = new Matrix();
            matrixRotate.postRotate(30);
            Bitmap imgRotate = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrixRotate, false);
            mCanvas.drawBitmap(imgRotate, x1 - imgRotate.getWidth() / 2, y1 - imgRotate.getHeight() / 2, paint);
            imgRotate.recycle();
        }
        else if (operationType.equals("move")) {
            Toast.makeText(getContext(), "move", Toast.LENGTH_SHORT).show();

            mCanvas.drawBitmap(img, (x1 - img.getWidth() / 2) + 100, (y1 - img.getHeight() / 2) + 100, mPaint);
            img.recycle();
        }
        else if (operationType.equals("scale")) {
            Toast.makeText(getContext(), "scale", Toast.LENGTH_SHORT).show();

            Bitmap imgBig = Bitmap.createScaledBitmap(img, img.getWidth() * 3 / 2, img.getHeight() * 3 / 2, false);
            mCanvas.drawBitmap(imgBig, x1 - imgBig.getWidth() / 2, y1 - imgBig.getHeight() / 2, mPaint);
            imgBig.recycle();
        }
        else if (operationType.equals("skew")) {
            Toast.makeText(getContext(), "skew", Toast.LENGTH_SHORT).show();

            Matrix matrixSkew = new Matrix();
            matrixSkew.postSkew(0.2f, 0);
            Bitmap imgSkew = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrixSkew, false);
            mCanvas.drawBitmap(imgSkew, x1 - imgSkew.getWidth() / 2, y2 - imgSkew.getHeight() / 2, mPaint);
            imgSkew.recycle();
        }
        else{
            mCanvas.drawBitmap(img, x1 - img.getWidth() / 2, y1 - img.getHeight() / 2, mPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        if(operationType.equals("eraser")){
            clear();
        }
        if(operationType.equals("save")){}
        if (operationType.equals("coloring")) {
            Toast.makeText(getContext(), "coloring", Toast.LENGTH_SHORT).show();
            float[] array = {
                    2, 0, 0, 0, -25f,
                    0, 2, 0, 0, -25f,
                    0, 0, 2, 0, -25f,
                    0, 0, 0, 2, 0
            };
            ColorMatrix matrix = new ColorMatrix(array);
            mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        }
        if (operationType.equals("nocoloring")) {
            Toast.makeText(getContext(), "nocoloring", Toast.LENGTH_SHORT).show();
            float[] array = {
                    1, 0, 0, 0, -25f,
                    0, 1, 0, 0, -25f,
                    0, 0, 1, 0, -25f,
                    0, 0, 0, 1, 0
            };
            ColorMatrix matrix = new ColorMatrix(array);
            mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        }


        if (operationType.equals("bluring")) {
            Toast.makeText(getContext(), "bluring", Toast.LENGTH_SHORT).show();
            BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.INNER);
            mPaint.setMaskFilter(blur);
        }

        if (operationType.equals("nobluring")) {
            Toast.makeText(getContext(), "nobluring", Toast.LENGTH_SHORT).show();
            BlurMaskFilter blur = new BlurMaskFilter(1, BlurMaskFilter.Blur.INNER);
            mPaint.setMaskFilter(blur);
        }
        if (operationType.equals("big")) {
            Toast.makeText(getContext(), "big", Toast.LENGTH_SHORT).show();
            mPaint.setStrokeWidth(5);
        }
        if (operationType.equals("nobig")) {
            Toast.makeText(getContext(), "nobig", Toast.LENGTH_SHORT).show();
            mPaint.setStrokeWidth(3);
        }
        if (operationType.equals("red")) {
            mPaint.setColor(Color.RED);
        }
        if (operationType.equals("blue")) {
            mPaint.setColor(Color.BLUE);
        }
    }

    float x1 = -1, y1 = -1;
    int x2 = -1;
    int y2 = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();

        if (MainActivity.check == true) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x2 = X;
                y2 = Y;
                drawStamp();
                invalidate();
            }
        }
        else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x2 = X;
                y2 = Y;
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (x2 != -1) {
                    mCanvas.drawLine(x2, y2, X, Y, mPaint);
                    invalidate();
                    x2 = X;
                    y2 = Y;
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (x2 != -1) {
                    mCanvas.drawLine(x2, y2, X, Y, mPaint);
                    invalidate();
                }
                x2 = -1;
                y2 = -1;
            }
            mPaint.setStrokeWidth(3);
        }
        return true;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
        invalidate();
    }

    public void clear() {
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }
}