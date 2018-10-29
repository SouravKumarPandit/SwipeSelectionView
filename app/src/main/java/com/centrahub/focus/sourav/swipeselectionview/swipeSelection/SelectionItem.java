package com.centrahub.focus.sourav.swipeselectionview.swipeSelection;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by sourav on 24-Nov-2018.
 */
/*So far, the earliest finds of modern Homo sapiens skeletons come from Africa. They date to nearly 200,000 years ago on that continent. They appear in Southwest Asia around 100,000 years ago and elsewhere in the Old World by 60,000-40,000 years ago.*/

public class SelectionItem extends View /*implements View.OnClickListener*/ {
    private float sideSpacing = 0;
    private RectF titleRectTop;
    private Paint backgroundTopPaint;
    private Paint backgroundDetailsPaint;
    private TextPaint titalpaint;
    TextPaint detailPaint = new TextPaint();
    Rect detailPaintBound = new Rect();

    Paint strokePaint = new Paint();

    private String sTitleText = "description";


    private int itemWidth;
    private int itemHeight;
    private int rows = 7;
    private int lableAlpha;
    private int text_height = 0;
    private int text_width = 0;
    private float roundedX = 5;
    private float roundedY = 5;
    private float detailTextSize = 14;


    private float titalTextSize = 16;
    private int lableBackgroundColor;

    private float strokeWidth = 1;
    private int fix_text_height;
    private boolean strokeEnable;

    public SelectionItem(Context context) {
        this(context, null);
    }

    public SelectionItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initAttrs();
        initTools(context);
    }

    private void initAttrs() {
        lableBackgroundColor = Color.WHITE;
        topTitleColor = Color.BLACK;
        strokeColor = Color.WHITE;
        detialColorBackground = Color.WHITE;
    }

    private void initTools(Context context) {
        titleRectTop = new RectF();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeWidth);

        backgroundTopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundTopPaint.setColor(lableBackgroundColor);

        backgroundDetailsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundDetailsPaint.setColor(detialColorBackground);
//        backgroundDetailsPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
        if (lableAlpha > 0)
            backgroundTopPaint.setAlpha(lableAlpha);

        titalpaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        titalpaint.setTextSize(sp2px(titalTextSize));//bottomTextSize * 1.2f
        titalpaint.setColor(topTitleColor);


        detailPaint.setTypeface(Typeface.DEFAULT);// your preference here
        detailPaint.setTextSize(sp2px(detailTextSize));// have this the same as your sDetailsText size
        detailPaint.setColor(strokeColor);
        if (sTitleText != null && !sTitleText.isEmpty())
            titalpaint.getTextBounds(sTitleText, 0, sTitleText.length(), detailPaintBound);
        if (sTitleText != null && !sTitleText.isEmpty())
            titalpaint.getTextBounds(sTitleText, 0, sTitleText.length(), detailPaintBound);

        text_height = (new StaticLayout(sTitleText, titalpaint, detailPaintBound.width(), Layout.Alignment.ALIGN_NORMAL, 0.8f, 0.0f, false)).getHeight();

        this.fix_text_height = detailPaintBound.height();

        text_width = detailPaintBound.width();

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        itemWidth = width / rows;
        itemHeight = text_height;
        setMeasuredDimension(widthMeasureSpec, itemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (sTitleText != null && !sTitleText.isEmpty())
            drawTitleTop(canvas);
    }

    private void drawTitleTop(Canvas canvas) {
//        backgroundRect.set(0, 0, itemWidth * rows, itemHeight / 2);
        titleRectTop.set(sideSpacing, 0, canvas.getWidth() - sideSpacing, itemHeight);
        canvas.drawRoundRect(titleRectTop, roundedX, roundedY, backgroundTopPaint);
        if (strokeWidth>0)
        canvas.drawRoundRect(titleRectTop, roundedX, roundedX, strokePaint);
//        canvas.drawRect(titleRectTop, backgroundTopPaint);
        String truncatedText = sTitleText;
        byte textSize = 30;
        if (checkIsTab(getContext())) {
            textSize = 40;
        }
        ;
        if (sTitleText.length() > textSize) {
            truncatedText = sTitleText.substring(0, Math.max(0, 25));
            if (truncatedText.length() < sTitleText.length()) {
                truncatedText = truncatedText.substring(0, Math.max(0, truncatedText.length() - 3));
                truncatedText += "...";
            }
        }
        int xPos = canvas.getWidth() / 2 - (int) (titalpaint.measureText(sTitleText) / 2);
        canvas.drawText(truncatedText, xPos, canvas.getHeight() / 2 + fix_text_height / 2, titalpaint);
    }

    public boolean checkIsTab(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /*private PointF startPoint;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startPoint = new PointF(event.getX(), event.getY());
//            Log.i(TAG,startPoint.toString());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
//            Log.i(TAG,x+","+y);
            //选中日期
            if (Math.abs(startPoint.x - x) < 20 && Math.abs(startPoint.y - y) < 20) {

                    for (int j = 0; j < rows; j++) {
                        if (x > itemWidth * j && x < itemWidth * (j + 1) && y > itemHeight * (1) && y < itemHeight * (2)) {

                            if (onItemSelectListener != null) {
                                onItemSelectListener.onSelect(ListSwipeUtil.getItemList(5));
                            }
                            invalidate();
                        }

                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
*/
    public int sp2px(float sp) {
        Resources r = Resources.getSystem();
        final float scale = r.getDisplayMetrics().density;
        return (int) (sp * scale + 0.5f);
    }

    public void setRounded(float roundedX, float roundedY) {
        this.roundedX = roundedX;
        this.roundedY = roundedY;
        invalidate();
    }

    public void setsAtPositon(int sAtPositon) {
        this.sAtPositon = sAtPositon;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        initTools(getContext());
        invalidate();
    }

    public int getTopTitleColor() {
        return topTitleColor;
    }

    private int topTitleColor;
    private int strokeColor;

    private int detialColorBackground;

    public int getsAtPositon() {
        return sAtPositon;
    }

    private int sAtPositon;

    public float getTitalTextSize() {
        return titalTextSize;

    }

    public void setTitalTextSize(float titalTextSize) {
        this.titalTextSize = titalTextSize;
        initTools(getContext());
        invalidate();
    }

    public void setLableAlpha(int lableAlpha) {

        this.lableAlpha = lableAlpha;
        initTools(getContext());
        invalidate();
    }


    public void setLableBackgroundColor(int lableBackgroundColor) {
        this.lableBackgroundColor = lableBackgroundColor;
        initTools(getContext());
        invalidate();
    }

    public void setTopTitleColor(int topTitleColor) {
        this.topTitleColor = topTitleColor;
        initTools(getContext());
        invalidate();
    }
    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        initTools(getContext());
        invalidate();
    }
    public float getSideSpacing() {
        return sideSpacing;
    }

    public String getsTitleText() {
        return sTitleText;
    }

    public void setsTitleText(String sTitleText) {
        this.sTitleText = sTitleText;
        initTools(getContext());

        invalidate();
    }

    public void setStrokeEnable(boolean strokeEnable) {
        this.strokeEnable = strokeEnable;
        initTools(getContext());
        invalidate();
    }

    public void setSideSpacing(float sideSpacing) {
        this.sideSpacing = sideSpacing;
        initTools(getContext());
        invalidate();

    }
    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }
    public interface OnItemSelectListener {
        void onSelect(ItemListHolderDTO calenderBean);
    }


}
