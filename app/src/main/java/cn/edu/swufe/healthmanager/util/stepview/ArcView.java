package cn.edu.swufe.healthmanager.util.stepview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ArcView extends View {

    //热量圆弧宽度
    private float hotStrokeWidth = 40f;
    //热量圆弧当前进度颜色65c8b3 586FFB
    private int hotStrokeProgressColor = Color.parseColor("#65c8b3");
    //默认的圆弧颜色
    private int defaultStrokeColor = Color.parseColor("#ECECEC");
    //默认开始角度
    private int startAngle = 180;
    //默认扫过的弧度
    private int defaultSweepAngle = 180;
    //当前热量长度
    private int currentHotLength = 80;//160
    //文字大小
    private int stepTextSize = 50;
    private float stepTextHeight = 0;
    //每日需求热量
    private int targetHotNum = 2500;
    private int dairlyHotNum = 1000;

    public int getHotStrokeProgressColor() {
        return hotStrokeProgressColor;
    }

    public void setHotStrokeProgressColor(int hotStrokeProgressColor) {
        this.hotStrokeProgressColor = hotStrokeProgressColor;
    }

    public int getCurrentHotLength() {
        return currentHotLength;
    }

    public void setCurrentHotLength(int currentHotLength) {
        this.currentHotLength = currentHotLength;
    }

    public int getTargetHotNum() {
        return targetHotNum;
    }

    public void setTargetHotNum(int targetHotNum) {
        this.targetHotNum = targetHotNum;
        judge();
    }

    public int getDairlyHotNum() {
        return dairlyHotNum;
    }

    public void setDairlyHotNum(int dairlyHotNum) {
        this.dairlyHotNum = dairlyHotNum;
        judge();

    }

    public ArcView(Context context) {
        super(context);
    }

    public void judge() {
        double a=this.getDairlyHotNum();
        double b=this.getTargetHotNum();
        if(a/ b<=1){
            this.setCurrentHotLength((int)((a/b)*180.0));

        }else {
            this.setHotStrokeProgressColor(Color.parseColor("#FF4500"));//红色
            this.setCurrentHotLength(180);

        }
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //中心点坐标
        float centerX = getWidth() / 2;
        //热量外矩形区域
        RectF hotRectF = new RectF();
        float hotL = hotStrokeWidth / 2;
        float hotT = hotStrokeWidth / 2;
        float hotR = centerX * 2 - hotStrokeWidth / 2;
        float hotB = hotR;
        hotRectF.set(hotL, hotT, hotR, hotB);

        //绘制热量圆弧
        drawHotStroke(canvas, centerX, hotRectF);

        //绘制文字
        drawText(canvas, centerX);
        drawText2(canvas, centerX);
        drawText3(canvas, centerX);

    }



    private void drawText(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(stepTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        String textTop = "每日需求热量 " + targetHotNum;
        Rect textF = new Rect();
        paint.getTextBounds(textTop, 0, textTop.length(), textF);
        //文字高度
        stepTextHeight = textF.height();
        float textY = textF.height() / 2 + hotStrokeWidth / 2 +310;
        canvas.drawText(textTop, centerX, textY, paint);

    }

    private void drawText2(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(stepTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        String textBottom = "当前热量 " ;
        Rect textF2 = new Rect();
        paint.getTextBounds(textBottom, 0, textBottom.length(), textF2);
        //文字高度
        stepTextHeight = textF2.height();
        float textY = textF2.height() / 2 + hotStrokeWidth / 2 +70;
        canvas.drawText(textBottom, centerX, textY, paint);
        canvas.drawText(textBottom, centerX, textY, paint);
    }

    private void drawText3(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(stepTextSize+60);
        paint.setTextAlign(Paint.Align.CENTER);

        String textBottom = ""+ dairlyHotNum;
        Rect textF2 = new Rect();
        paint.getTextBounds(textBottom, 0, textBottom.length(), textF2);
        //文字高度
        stepTextHeight = textF2.height();
        float textY = textF2.height() / 2 + hotStrokeWidth / 2 +180;
        canvas.drawText(textBottom, centerX, textY, paint);
        canvas.drawText(textBottom, centerX, textY, paint);
    }

    //热量所有的绘制
    private void drawHotStroke(Canvas canvas, float x, RectF f) {
        //绘制热量默认的圆弧
        drawDefaultHotStroke(canvas, f, hotStrokeWidth);
        //绘制当前进度
        drawProgressHotStroke(canvas, f);
    }

    private void drawProgressHotStroke(Canvas canvas, RectF f) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(hotStrokeProgressColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(hotStrokeWidth);
        canvas.drawArc(f, startAngle, currentHotLength, false, paint);
    }




    private void drawDefaultHotStroke(Canvas canvas, RectF f, float strokeWidth) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(defaultStrokeColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawArc(f, startAngle, defaultSweepAngle, false, paint);
    }



}
