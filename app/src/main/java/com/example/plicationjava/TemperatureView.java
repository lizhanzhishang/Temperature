package com.example.plicationjava;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class TemperatureView extends View {

    private int mLowColor = Color.parseColor("#444444");
    private int mHightColor = Color.parseColor("#20BF70");
    private int mBgColor = Color.parseColor("#000000");
    private int mPicState;
    private Paint mPaint=new Paint();
    private float mCircleBigWidth=0;
    private float mCircleBig=2;

    public String mTemperatAir="16.0°C";
    public float  mAirTp=16.0f;
    public String mTemperatHourse="室内:28.5°C";
    private int mTemperatAirCircle=1;// 16- 32


    public TemperatureView(Context context) {
        super(context);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedarray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView);
        mLowColor = typedarray.getColor(R.styleable.TemperatureView_low_color, mLowColor);
        mHightColor = typedarray.getColor(R.styleable.TemperatureView_high_color, mHightColor);
        mPicState = typedarray.getColor(R.styleable.TemperatureView_pic_flg, R.drawable.flag_leng);
        typedarray.recycle();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCircleBigWidth= TypedValue.applyDimension(COMPLEX_UNIT_DIP,26,getResources().getDisplayMetrics());
        mCircleBig= TypedValue.applyDimension(COMPLEX_UNIT_DIP,1,getResources().getDisplayMetrics());
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width=0;
        int height=-1;

        // MeasureSpec.EXACTLY  确定模式的值
        // MeasureSpec.AT_MOST  子组件包裹内容，约束尺寸值为父VIew的剩余大小
        // MeasureSpec.UNSPECIFIED    未指定大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else { // 如果是自适应的话。
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
//            float textWidth = mBounds.width();
//            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
//            width = desired;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitle, 0, mTitle.length(), mBounds);
//            float textHeight = mBounds.height();
//            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
//            height = desired;
        }
        width = widthSize;
        height = heightSize;
        if (width != height) {
            if (width > height) {
                width = height;
            } else {
                height = width;
            }
        }

        setMeasuredDimension(width, height); //保证他是个正方形
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getWidth();
        Log.e("onDraw", width + "  " + height +  " " + getPaddingLeft());
        canvas.drawColor(mBgColor);

        mCircleBigWidth=width/15;


        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF oval=new RectF(getPaddingLeft(), getPaddingLeft(), height-getPaddingLeft(), height-getPaddingLeft());
        mPaint.setColor(mHightColor);
        mPaint.setStrokeWidth(mCircleBigWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(mHightColor); // 17
        for (int i = 0; i < mTemperatAirCircle; i++) {
            canvas.drawArc(oval,-226+(i*16),15,false,mPaint);
        }
        mPaint.setColor(mLowColor);
        for (int i = mTemperatAirCircle; i < 17; i++) {
            canvas.drawArc(oval,-226+(i*16),15,false,mPaint);
        }

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(mCircleBig);
        int panding= (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());
        int radius= (int) ((width-getPaddingLeft()*2-  panding*2 - mCircleBigWidth )/2);
        canvas.drawCircle(width/2,width/2,radius, mPaint);

        int radiusBiamju= (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        int radiusInner=radius-radiusBiamju;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mHightColor);
        canvas.drawCircle(width/2,width/2,radiusInner, mPaint);


        int wc =width/2; //中心为止
        radiusInner= (int) (radiusInner-TypedValue.applyDimension(COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics()));
        // 最内侧绘制的圆
        RectF ovalInner=new RectF(wc-radiusInner, wc-radiusInner, wc+radiusInner, wc+radiusInner);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(TypedValue.applyDimension(COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics()));
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 180; i++) {
            canvas.drawArc(ovalInner,(i*2),1,false,mPaint);
        }
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),mPicState);
        Rect src=new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float lastRf=ovalInner.height()/10f; // 将剩余绘制区域分成10等分

        // 测试代码
//        mPaint.setColor(Color.RED);
//        canvas.drawCircle(width/2,width/2,ovalInner.width()/2,mPaint);

        RectF dst=new RectF(wc-lastRf/2,ovalInner.top + lastRf/2,wc+lastRf/2,ovalInner.top+lastRf/2*3);
        // 已经使用了 2份了，还剩余8份未使用  // 绘制图片UI
        canvas.drawBitmap(bitmap,src,dst,mPaint);
        Log.e("onDraw",dst.toString());
        Log.e("onDraw",ovalInner.toString());
//

        RectF dst2=new RectF(ovalInner.left,ovalInner.top+lastRf/2*3,ovalInner.right,ovalInner.top+lastRf/2*11);
        RectF dst3=new RectF(ovalInner.left,ovalInner.top+lastRf/2*11,ovalInner.right,ovalInner.top+lastRf/2*16);
        RectF dst4=new RectF(ovalInner.left,ovalInner.top+lastRf/2*16,ovalInner.right,ovalInner.top+lastRf/2*20);


        RectF dst2X=new RectF(dst2.left+lastRf,dst2.top ,dst2.right-lastRf,dst2.top);
        // 测试代码
//        mPaint.setStrokeWidth(1);
//        canvas.drawRect(dst,mPaint);

//        canvas.drawRect(dst2X,mPaint);
//        Log.e("onDraw dst2",dst2.toString());
//        Log.e("onDraw dst2X",dst2X.toString());
//        canvas.drawRect(dst3,mPaint);
//        canvas.drawRect(dst4,mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setTextSize(TypedValue.applyDimension(COMPLEX_UNIT_SP,56,getResources().getDisplayMetrics()));
        RectF dst2Text=new RectF(dst2X.left+lastRf/2f,dst2X.top,dst2X.right-lastRf/2f,dst2X.bottom);
        measureText(canvas,dst2Text,mTemperatAir); //测量温度值的最大尺寸
//        measureText(canvas,dst2X,mTemperatAir); //测量温度值的最大尺寸
        Paint.FontMetrics fontMetrics=mPaint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline=dst2.centerY()+distance;
        canvas.drawText(mTemperatAir,dst2.centerX(),baseline,mPaint);

        // startX, float startY, float stopX, float stopY,
        mPaint.setStrokeWidth(dst3.height());
        mPaint.setColor(Color.parseColor("#66ffffff"));
//        RectF dst3Other=new RectF(dst3);
        RectF dst3Other=new RectF(dst3.left+lastRf/2*3,dst3.top+lastRf/3,dst3.right-lastRf,dst3.bottom-lastRf/3);
        canvas.drawRoundRect(dst3Other, dst3.height()/2, dst3.height()/2, mPaint);//第二个参数是x半径，第三个参数是y半径

//        mPaint.setTextSize(TypedValue.applyDimension(COMPLEX_UNIT_SP,21,getResources().getDisplayMetrics()));

        RectF dst3Text=new RectF(dst3Other.left+lastRf/2f*3,dst3.top,dst3.right-lastRf/2f*3,dst3.bottom);
        measureText(canvas,dst3Text,mTemperatHourse);
        Paint.FontMetrics fontMetricsX=mPaint.getFontMetrics();
        float distancex = (fontMetricsX.bottom - fontMetricsX.top) / 2 - fontMetricsX.bottom;
        float baselinex=dst3.centerY()+distancex;
        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawText(mTemperatHourse,dst3Other.centerX(),baselinex,mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dst4.height()/3);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF ovalInnerLast=new RectF(ovalInner.left, ovalInner.top-lastRf, ovalInner.right, ovalInner.bottom-lastRf);
        mPaint.setStrokeJoin(Paint.Join.ROUND);


        canvas.drawArc(ovalInnerLast,97,15,false,mPaint);
        if(modelSpeed==ModelSpeed.LOW){
            mPaint.setAlpha(125);
        }
        canvas.drawArc(ovalInnerLast,81,15,false,mPaint);
        if(modelSpeed==ModelSpeed.Mid){
            mPaint.setAlpha(125);
        }
        canvas.drawArc(ovalInnerLast,65,15,false,mPaint);

    }



    private void measureText(Canvas canvas,RectF dst2,String needMeasure){
        if(TextUtils.isEmpty(needMeasure)){
            return;
        }
        boolean isMeasure=true; // 默认是需要测量的
        float mSize=1;
        while (isMeasure){
            mPaint.setTextSize(mSize);
            Paint.FontMetrics fontMetricsX=mPaint.getFontMetrics();
            float height= fontMetricsX.leading+fontMetricsX.ascent+fontMetricsX.descent;
            float wight= mPaint.measureText(needMeasure);
            if(height < dst2.height() && wight <dst2.width()){
                mSize++;
            }else {
                isMeasure=false;
            }
//            isMeasure=false;
        }
//        mPaint.setTextSize(TypedValue.applyDimension(COMPLEX_UNIT_SP,56,getResources().getDisplayMetrics()));
    }


    // 设置温度的正常值
    public void setAirConditionerText(float mAirTempera){
        try {
            if(mAirTempera>15 && mAirTempera<33){ //这个值才是正常值
                mAirTp = mAirTempera;
                mTemperatAir = mAirTp + "°C";
                mTemperatAirCircle= (int) (mAirTp-15);
                invalidate();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
//        mTemperatAir=mAirTempera+"°C";
    }
    // 设置温度的正常值
    public void setAirConditionerText(String mAirTemper){
        try {
            float mAirTempera=Float.valueOf(mAirTemper).floatValue();
            if(mAirTempera>15 && mAirTempera<33){ //这个值才是正常值
                mAirTp = mAirTempera;
                mTemperatAir = mAirTp + "°C";
                mTemperatAirCircle= (int) (mAirTp-15);
                invalidate();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
//        mTemperatAir=mAirTempera+"°C";
    }

    public float getmAirTp(){return mAirTp;}

    //增加空调温度
    public void addAirTip(){
        if(mAirTp>=32){
            // ???
        }else {
            mAirTp = mAirTp + 1;
            mTemperatAir = mAirTp + "°C";
            mTemperatAirCircle= (int) (mAirTp-15);
            invalidate();
        }
    }
    //减少空调温度
    public void subAirTip(){
        if(mAirTp<=16){
            // ???
        }else {
            mAirTp = mAirTp - 1;
            mTemperatAir = mAirTp + "°C";
            mTemperatAirCircle= (int) (mAirTp-15);
            invalidate();
        }
    }


    private  ModelSpeed modelSpeed=ModelSpeed.LOW;
    // 设置风速档位
    public void setModelSpeed(ModelSpeed modelSpeed){
//        if(this.modelSpeed==modelSpeed){  还是移除掉吧，防止UDP事件不生效
//            return;
//        }

        this.modelSpeed=modelSpeed;
        invalidate();
//        if(modelSpeed==ModelSpeed.LOW){
//            mPicState=R.drawable.flag_f1;
//            invalidate();
//        }else   if(modelSpeed==ModelSpeed.Mid){
//            mPicState=R.drawable.flag_leng;
//            invalidate();
//        }else   if(modelSpeed==ModelSpeed.Quick){
//            mPicState=R.drawable.flag_hot;
//            invalidate();
//        }
    }

    // 设置制冷制热送风模式
    public void setModelLHF(ModelHLF modelHLF){
        if(modelHLF==ModelHLF.SONGFENG){
            mPicState=R.drawable.flag_f1;
            invalidate();
        }else   if(modelHLF==ModelHLF.ZHILENG){
            mPicState=R.drawable.flag_leng;
            invalidate();
        }else   if(modelHLF==ModelHLF.ZHIRE){
            mPicState=R.drawable.flag_hot;
            invalidate();
        }

    }
//    public String mTemperatHourse="室内:28.5°C"; 设置室内温度
    public void setmTemperatHourse(String mTemperatHourse){
        this.mTemperatHourse="室内:"+mTemperatHourse;
        invalidate();
    }


}
