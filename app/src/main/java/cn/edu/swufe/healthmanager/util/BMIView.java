package cn.edu.swufe.healthmanager.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

import androidx.annotation.Nullable;

import cn.edu.swufe.healthmanager.R;

public class BMIView extends View {
    /** 可配置的控件属性*/
    private String title, normalText; // 标题和中间文字
    private int minVal, minNormal, maxNormal, maxVal; //三段线的从左到右每个点的数值，即最小值、正常值的最小正常值的最大值、最大值
    private float currVal; // 当前数值
    private int leftColor, midColor, rightColor, normalTextColor; // 左边线段颜色、中间线段颜色、右边线段颜色和中间线段的文字颜色
    private float titleSize, normalTextSize; // 标题文字大小、中间线段文字大小
    private Paint textPaint, linePaint; // 画文字和线段的paint
    private Bitmap bitmapUp, bitmapDown; // 指标箭头bitmap
    private int width, height, lineWidth, smallCircleRadius, circleStokeWidth; // 控件的宽度、高度、线段的宽度、圆环的半径、圆环的环的宽度
    public BMIView(Context context) {
        super(context);
        init(context, null);
    }

    public BMIView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BMIView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        // 读取自定义属性，没有设置的话，就设置为默认值。
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BMIView);
        String titleStr = array.getString(R.styleable.BMIView_title);
        if (titleStr != null) {
            title = titleStr;
        } else {
            title = "";
        }
        titleSize = array.getDimension(R.styleable.BMIView_normalTextSize, 50);
        smallCircleRadius = (int) array.getDimension(R.styleable.BMIView_circleRadius, 20);
        circleStokeWidth = (int) array.getDimension(R.styleable.BMIView_circleStokeWidth, 10);
        Drawable up = array.getDrawable(R.styleable.BMIView_upDrawable);
        Drawable down = array.getDrawable(R.styleable.BMIView_downDrawable);
        if (up != null) {
            bitmapUp = Bitmap.createBitmap(up.getIntrinsicWidth(),
                    up.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapUp);
            up.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            up.draw(canvas);
        } else {
            bitmapUp = getBitmap(context, R.drawable.ic_up);
        }

        if (down != null) {
            bitmapDown = Bitmap.createBitmap(down.getIntrinsicWidth(),
                    down.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapDown);
            down.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            down.draw(canvas);
        } else {
            bitmapDown = getBitmap(context, R.drawable.ic_down);
        }

        titleStr = array.getString(R.styleable.BMIView_normalText);
        if (titleStr != null) {
            normalText = titleStr;
        } else {
            normalText = "正常";
        }

        minNormal = array.getInteger(R.styleable.BMIView_minNormal, 316);
        maxNormal = array.getInteger(R.styleable.BMIView_maxNormal, 354);

        minVal = array.getInteger(R.styleable.BMIView_minVal, 0);
        maxVal = array.getInteger(R.styleable.BMIView_maxVal, 400);

        leftColor = array.getColor(R.styleable.BMIView_leftColor, Color.argb(255, 246, 174, 3));
        midColor = array.getColor(R.styleable.BMIView_midColor, Color.argb(255, 31, 156, 13));
        rightColor = array.getColor(R.styleable.BMIView_rightColor, Color.argb(255, 255, 63, 66));

        normalTextColor = array.getColor(R.styleable.BMIView_normalTextColor, Color.GRAY);

        lineWidth = (int) array.getDimension(R.styleable.BMIView_lineWidth, 14);

        normalTextSize = array.getDimension(R.styleable.BMIView_normalTextSize, 44);
        currVal = array.getFloat(R.styleable.BMIView_currentVal, 200);

        array.recycle();
        // 初始化画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GRAY);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(titleSize);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(leftColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
    }

    /**
     * 获取drawable的id转为bitmap
     */
    private Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap bitmap=null;
        Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
        if (vectorDrawable != null) {
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /** 以下是为所有可配置的属性提供get和set方法*/
    public String getTitle() {
        return title;
    }

    public String getNormalText() {
        return normalText;
    }

    public void setNormalText(String normalText) {
        this.normalText = normalText;
        invalidate();
    }

    public int getMinVal() {
        return minVal;
    }

    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    public int getMinNormal() {
        return minNormal;
    }

    public void setMinNormal(int minNormal) {
        this.minNormal = minNormal;
        invalidate();
    }

    public int getMaxNormal() {
        return maxNormal;
    }

    public void setMaxNormal(int maxNormal) {
        this.maxNormal = maxNormal;
        invalidate();
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
        invalidate();
    }

    public float getCurrVal() {
        return currVal;
    }

    public int getLeftColor() {
        return leftColor;
    }

    public int getMidColor() {
        return midColor;
    }

    public void setMidColor(int midColor) {
        this.midColor = midColor;
        invalidate();
    }

    public int getRightColor() {
        return rightColor;
    }


    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
        invalidate();

    }


    public Bitmap getBitmapUp() {
        return bitmapUp;
    }

    public void setBitmapUp(Bitmap bitmapUp) {
        this.bitmapUp = bitmapUp;
    }

    public Bitmap getBitmapDown() {
        return bitmapDown;
    }

    public void setBitmapDown(Bitmap bitmapDown) {
        this.bitmapDown = bitmapDown;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }

    public int getSmallCircleRadius() {
        return smallCircleRadius;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        invalidate();
    }

    public float getNormalTextSize() {
        return normalTextSize;
    }

    public void setNormalTextSize(float normalTextSize) {
        this.normalTextSize = normalTextSize;
        invalidate();
    }

    public void setSmallCircleRadius(int smallCircleRadius) {
        this.smallCircleRadius = smallCircleRadius;
        invalidate();
    }

    public int getCircleStokeWidth() {
        return circleStokeWidth;
    }

    public void setCircleStokeWidth(int circleStokeWidth) {
        this.circleStokeWidth = circleStokeWidth;
        invalidate();
    }

    public void setCurrVal(float currVal) {
        this.currVal = currVal;
        invalidate();
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public void setRightColor(int rightColor) {
        this.rightColor = rightColor;
        invalidate();
    }

    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
        invalidate();
    }
    /**
     * 为bitmap设置显示的颜色
     */
    private Bitmap tintBitmap(Bitmap inBitmap , int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap (inBitmap.getWidth(), inBitmap.getHeight() , inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter( new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN)) ;
        canvas.drawBitmap(inBitmap , 0, 0, paint) ;
        return outBitmap ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制标题
        canvas.drawText(title, 20, 100, textPaint);

        int offsetX = 0,offsetY = height / 3, window = width / 3;

        // 绘制线条
        linePaint.setColor(leftColor);
        canvas.drawLine(offsetX+30, offsetY + height / 3, offsetX += window, offsetY + height / 3, linePaint);
        linePaint.setColor(midColor);
        canvas.drawLine(offsetX, offsetY + height / 3, offsetX += window, offsetY + height / 3, linePaint);
        linePaint.setColor(rightColor);
        canvas.drawLine(offsetX, offsetY + height / 3, offsetX + window-30, offsetY  + height / 3, linePaint);

        // 绘制当前值
        if (currVal < minNormal) {
            // 在左边部分
            int x = (int) ((currVal / (double)minNormal) * width / 3);
            int y = offsetY + height / 3 - lineWidth / 2;
            if (x < smallCircleRadius + circleStokeWidth) {
                x = smallCircleRadius + circleStokeWidth;
            }
            linePaint.setColor(Color.WHITE);
            linePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, smallCircleRadius, linePaint);

            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(circleStokeWidth);
            linePaint.setColor(leftColor);
            canvas.drawCircle(x, y + lineWidth / 2, smallCircleRadius, linePaint);

            String currStr = String.format(Locale.getDefault(),"%.2f", currVal);
            textPaint.setTextSize(normalTextSize);
            textPaint.setColor(leftColor);
            bitmapDown = tintBitmap(bitmapDown, leftColor);
            float strWidth = textPaint.measureText(currStr) + bitmapDown.getWidth();
            x -= (int)strWidth / 2;
            if (x < 0) {
                x = 0;
            }
            canvas.drawBitmap(bitmapDown, x, y - bitmapDown.getHeight() - normalTextSize / 2, null);
            canvas.drawText(currStr, x + bitmapDown.getWidth(), y - normalTextSize, textPaint);
        } else if (currVal > maxNormal) {
            // 当前值在右边部分
            int x = width / 3 * 2 + (int) (((currVal - maxNormal) / (double)(maxVal - maxNormal)) * width / 3);
            int y = offsetY + height / 3 - lineWidth / 2;
            if (x + smallCircleRadius + circleStokeWidth > width) {
                x = width - smallCircleRadius - circleStokeWidth;
            }
            linePaint.setColor(Color.WHITE);
            linePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, smallCircleRadius, linePaint);

            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(circleStokeWidth);
            linePaint.setColor(rightColor);
            canvas.drawCircle(x, y + lineWidth / 2, smallCircleRadius, linePaint);

            String currStr = String.format(Locale.getDefault(),"%.2f", currVal);
            textPaint.setTextSize(normalTextSize);
            textPaint.setColor(rightColor);

            bitmapUp = tintBitmap(bitmapUp, rightColor);
            float strWidth = textPaint.measureText(currStr) + bitmapUp.getWidth();
            x -= (int)strWidth / 2;
            if (x + (int)strWidth / 2 > width) {
                x = width - (int)strWidth / 2;
            }
            canvas.drawBitmap(bitmapUp, x, y - bitmapUp.getHeight() - normalTextSize / 2, null);
            canvas.drawText(currStr, x + bitmapUp.getWidth(), y - normalTextSize, textPaint);
        } else {
            // 当前值在中间部分
            int x = width / 3 + (int) ((currVal - minNormal) / (double)(maxNormal - minNormal) * (width / 3)) - smallCircleRadius;
            int y = offsetY + height / 3 - lineWidth / 2;
            linePaint.setColor(Color.WHITE);
            linePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, smallCircleRadius, linePaint);

            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(circleStokeWidth);
            linePaint.setColor(midColor);
            canvas.drawCircle(x, y + lineWidth / 2, smallCircleRadius, linePaint);

            String currStr = String.format(Locale.getDefault(),"%.2f", currVal);
            textPaint.setTextSize(normalTextSize);
            textPaint.setColor(midColor);

            float strWidth = textPaint.measureText(currStr);
            x -= (int)strWidth / 2;
            canvas.drawText(currStr, x, y - normalTextSize, textPaint);
        }
        // 绘制坐标的正常、最小值和最大值
        textPaint.setTextSize(normalTextSize);
        textPaint.setColor(Color.GRAY);
        float textWidth = textPaint.measureText(normalText);
        canvas.drawText(normalText, (width - textWidth) / 2, offsetY + height / 3 + lineWidth * 4 + normalTextSize / 2, textPaint);

        textWidth = textPaint.measureText(String.valueOf(minNormal));
        canvas.drawText(String.valueOf(minNormal), width / 3 - textWidth / 2, offsetY + height / 3  + lineWidth * 4 + normalTextSize / 2, textPaint);

        textWidth = textPaint.measureText(String.valueOf(maxNormal));
        canvas.drawText(String.valueOf(maxNormal), width / 3 * 2 - textWidth / 2, offsetY + height / 3 + lineWidth * 4 + normalTextSize / 2, textPaint);
    }
}