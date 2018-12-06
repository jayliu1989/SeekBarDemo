package com.jay.seekbardemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jay.seekbardemo.R;

public class CustomSeekbar extends View {
    private int[] resIds = {
            R.drawable.ic_face1,
            R.drawable.ic_face2,
            R.drawable.ic_face3,
            R.drawable.ic_face4,
            R.drawable.ic_face5
    };

    private int width;
    private int height;

    private int downX = 0;
    private int downY = 0;
    private int upX = 0;
    private int upY = 0;
    private int moveX = 0;
    private int moveY = 0;

    private Paint paintSlider;
    private Paint paintLine;
    private Paint paintImage;
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap bitmapSlider;
    private Bitmap bitmapLine;
    private Bitmap bitmapImage;

    private int spaceSlider = 2;
    private int spaceLine = 10;

    private int count = 0;
    // default 5, have a nice day today
    private int value = 5;

    private Context mContext;

    private OnItemSelectedClickLienter itemSelectedClickLienter;

    public CustomSeekbar(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView(attrs);
        initPaint();
        initCanvas();

        startCanvas();
    }

    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initView(attrs);
        initPaint();
        initCanvas();

        startCanvas();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;

        initView(attrs);
        initPaint();
        initCanvas();

        startCanvas();
    }

    private void initView(AttributeSet attributeSet) {
        count = resIds.length;
    }

    private void initPaint() {
        paintSlider = new Paint(Paint.DITHER_FLAG);
        paintSlider.setAntiAlias(true);
        paintSlider.setStrokeWidth(3);
        paintLine = new Paint(Paint.DITHER_FLAG);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setAntiAlias(true);
        paintImage = new Paint(Paint.DITHER_FLAG);
        paintImage.setAntiAlias(true);
        paintImage.setStyle(Paint.Style.FILL);
        paintImage.setAlpha(0);
    }

    private void initCanvas() {

    }

    private void startCanvas() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthSize;
        //控件的高度
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintImage.setColor(Color.WHITE);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.drawBitmap(bitmap, 0, 0, paintImage);

        bitmapSlider = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.slider),
                dip2px(10),
                dip2px(13),
                true
        );
        paintSlider.setColor(Color.WHITE);

        switch (value) {
            case 1:
                canvas.drawBitmap(bitmapSlider, (value - 1) * width / (resIds.length - 1)+dip2px(5), 0, paintSlider);
                break;
            case 5:
                canvas.drawBitmap(bitmapSlider, (value - 1) * width / (resIds.length - 1)-dip2px(18), 0, paintSlider);
                break;
            default:
                canvas.drawBitmap(bitmapSlider, (value - 1) * width / (resIds.length - 1)-dip2px(5), 0, paintSlider);
                break;
        }

        paintLine.setColor(Color.parseColor("#EEEEEE"));
        RectF oval3 = new RectF(0, dip2px(16), width, dip2px(26));
        canvas.drawRoundRect(oval3, 20, 25, paintLine);

        for (int i = 0; i < resIds.length; i++) {
            paintImage.setColor(Color.WHITE);
            Bitmap bitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), resIds[i]),
                    dip2px(23),
                    dip2px(23),
                    true
            );
            if (i == 0) {
                canvas.drawBitmap(bitmap, i * width / (resIds.length - 1), dip2px(36), paintImage);
            } else if (i < resIds.length - 1) {
                canvas.drawBitmap(bitmap, i * width / (resIds.length - 1) - dip2px(11.5f), dip2px(36), paintImage);
            } else {
                canvas.drawBitmap(bitmap, i * width / (resIds.length - 1) - dip2px(23), dip2px(36), paintImage);
            }


        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            itemSelected(x, y);
        }
        return super.onTouchEvent(event);
    }

    private void itemSelected(int x, int y) {
        if (y > dip2px(36) && y < dip2px(36 + 23)) {
            if (x > 0 && x < dip2px(23)) {
                setValue(1);
                if (itemSelectedClickLienter != null) {
                    itemSelectedClickLienter.onItemClick(1);
                }
            } else if (x > width / (resIds.length - 1) - dip2px(11.5f) && x < width / (resIds.length - 1) + dip2px(11.5f)) {
                setValue(2);
                if (itemSelectedClickLienter != null) {
                    itemSelectedClickLienter.onItemClick(2);
                }
            } else if (x > 2 * width / (resIds.length - 1) - dip2px(11.5f) && x < 2 * width / (resIds.length - 1) + dip2px(11.5f)) {
                setValue(3);
                if (itemSelectedClickLienter != null) {
                    itemSelectedClickLienter.onItemClick(3);
                }
            } else if (x > 3 * width / (resIds.length - 1) - dip2px(11.5f) && x < 3 * width / (resIds.length - 1) + dip2px(11.5f)) {
                setValue(4);
                if (itemSelectedClickLienter != null) {
                    itemSelectedClickLienter.onItemClick(4);
                }
            } else if (x > 4 * width / (resIds.length - 1) - dip2px(23) && x < 4 * width / (resIds.length - 1)) {
                setValue(5);
                if (itemSelectedClickLienter != null) {
                    itemSelectedClickLienter.onItemClick(5);
                }
            }

        }

        invalidate();
    }

    public void setOnItemSelectedClickLienter(OnItemSelectedClickLienter itemSelectedClickLienter) {
        this.itemSelectedClickLienter = itemSelectedClickLienter;
    }

    public void setResIds(int[] resIds) {
        this.resIds = resIds;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int dip2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnItemSelectedClickLienter {
        void onItemClick(int value);
    }
}
