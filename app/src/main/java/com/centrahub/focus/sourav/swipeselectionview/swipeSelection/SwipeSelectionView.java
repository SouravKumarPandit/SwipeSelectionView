package com.centrahub.focus.sourav.swipeselectionview.swipeSelection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.centrahub.focus.sourav.swipeselectionview.R;


/**
 * Created by sourav on 07-Nov-17.
 */

public class SwipeSelectionView extends ViewPager {
    private ItemListHolderDTO[] itemListHolderDTOS;
    private SelectionItem[] calenderItemViews;
    private int strokeColor;
    private float strokeWidth;
    private int sTitleTextColor;
    private float sTitleTextSize;
    private int titleBackground;
    private int sideMargin;
    private float roundedCorner;
    private String[] selectionList;
    private int currentValue;
    private int alphaTransparent;

    public SwipeSelectionView(Context context, String[] stringArray) {
        super(context);
        this.selectionList = stringArray;
        ListSwipeUtil.itemListString = stringArray;
        init(null);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initCalenderView();
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position = position % 3;
                SelectionItem calenderItemView = calenderItemViews[position];
                if (calenderItemView.getParent() != null) {
                    container.removeView(calenderItemView);
                }
                container.addView(calenderItemView);
                return calenderItemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, itemPosion, object);
            }
        });
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setPositionCalender(itemListHolderDTOS[position % 3], position);
                getAdapter().notifyDataSetChanged();
                if (onCalenderPageChangeListener != null) {
                    onCalenderPageChangeListener.onChange(getCurrentCalender().getItemPosion(), getCurrentCalender().getItemLabel());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        int currentPosition = Integer.MAX_VALUE / 2;
       /* if (currentPosition % 3 == 2) {
            currentPosition++;
        } else if (currentPosition % 3 == 1) {
            currentPosition--;
        }*/
        setCurrentItem(currentPosition);
    }
    @SuppressLint("CustomViewStyleable")
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeSelectionView);
        sTitleTextColor = typedArray.getColor(R.styleable.SwipeSelectionView_sTitleTextColor, Color.WHITE);
        sTitleTextSize = typedArray.getFloat(R.styleable.SwipeSelectionView_iTitleTextSize, 18);
        titleBackground = typedArray.getColor(R.styleable.SwipeSelectionView_titleBackgroundColor, getResources().getColor(R.color.colorPrimaryDark));
        strokeColor = typedArray.getColor(R.styleable.SwipeSelectionView_strokeColor, Color.GRAY);
        strokeWidth = typedArray.getFloat(R.styleable.SwipeSelectionView_strokeWidth, 2);
        roundedCorner = typedArray.getFloat(R.styleable.SwipeSelectionView_roundedCornner, 5);
        sideMargin = typedArray.getColor(R.styleable.SwipeSelectionView_sideSpacing, 1);
        alphaTransparent = typedArray.getColor(R.styleable.SwipeSelectionView_setAlpha, 0);
        typedArray.recycle();
    }

    private void initCalenderView() {

        ItemListHolderDTO listHolderDTO = ListSwipeUtil.getItemList(currentValue);
        listHolderDTO = ListSwipeUtil.getFirstItem(selectionList[0], currentValue, false, true);

        itemListHolderDTOS = new ItemListHolderDTO[]{listHolderDTO,
                ListSwipeUtil.nextUnit(currentValue),
                ListSwipeUtil.previousUnit(selectionList.length - 1)};
        calenderItemViews = new SelectionItem[itemListHolderDTOS.length];
        for (int i = 0; i < calenderItemViews.length; i++) {
            SelectionItem calenderItemView = calenderItemViews[i] == null ? new SelectionItem(getContext()) : calenderItemViews[i];
//            calenderItemView.setSideSpacing();
            calenderItemView.setTitalTextSize(sTitleTextSize);
            calenderItemView.setTopTitleColor(sTitleTextColor);
            calenderItemView.setLableBackgroundColor(titleBackground);
            calenderItemView.setRounded(roundedCorner,roundedCorner);
            calenderItemView.setStrokeWidth(strokeWidth);
            calenderItemView.setSideSpacing(sideMargin);
            calenderItemView.setLableAlpha(alphaTransparent);
            calenderItemViews[i] = calenderItemView;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (getAdapter() != null) {
            SelectionItem calenderItemView = (SelectionItem) getChildAt(0);
            if (calenderItemView != null) {
                height = calenderItemView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }


    /**
     * 设置日历数组数据
     *
     * @param calenderWeekBean
     * @param position
     */
    private void setPositionCalender(ItemListHolderDTO calenderWeekBean, int position) {
        int iPagerPosition = position;
        position = position % 3;
        // the current month

        itemListHolderDTOS[position] = calenderWeekBean;
        // itemPosion after the next month
        itemListHolderDTOS[(position + 1) % 3] = ListSwipeUtil.nextUnit(calenderWeekBean.getItemPosion());
        // the previous one is last month
        itemListHolderDTOS[(position - 1 + 3) % 3] = ListSwipeUtil.previousUnit(calenderWeekBean.getItemPosion());
        for (int i = 0; i < itemListHolderDTOS.length; i++) {
            calenderItemViews[i].setsTitleText(itemListHolderDTOS[i].getItemLabel());
            calenderItemViews[i].setTitalTextSize(sTitleTextSize);
            calenderItemViews[i].setTopTitleColor(sTitleTextColor);
            calenderItemViews[i].setLableBackgroundColor(titleBackground);
            calenderItemViews[i].setRounded(roundedCorner,roundedCorner);
            calenderItemViews[i].setStrokeWidth(strokeWidth);
            calenderItemViews[i].setSideSpacing(sideMargin);
            calenderItemViews[i].setLableAlpha(alphaTransparent);
            calenderItemViews[i].setsAtPositon(iPagerPosition);
        }
    }

    /**
     * @return
     */
    public ItemListHolderDTO getCurrentCalender() {
        return itemListHolderDTOS[getCurrentItem() % 3];
    }

    public void setCurrentCalender(ItemListHolderDTO listHolderDTO) {
        listHolderDTO = ListSwipeUtil.getItemList(currentValue);
        int result = listHolderDTO.compareTo(ListSwipeUtil.getItemList(currentValue));
        if (result != 0) {
            itemListHolderDTOS[(getCurrentItem() + result) % 3] = listHolderDTO;
            setPositionCalender(listHolderDTO, getCurrentItem() + result);
            setCurrentItem(getCurrentItem() + result);
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        if (currentValue < 0 || currentValue > selectionList.length)
            return;
        this.currentValue = currentValue;
        init(null);
        invalidate();
    }

    private OnCalenderPageChangeListener onCalenderPageChangeListener;

    public void setOnCalenderPageChangeListener(OnCalenderPageChangeListener onCalenderPageChangeListener) {
        this.onCalenderPageChangeListener = onCalenderPageChangeListener;
    }

    public void setSelectionList(String[] selectionList) {
        this.selectionList = selectionList;
        invalidate();
    }

    public interface OnCalenderPageChangeListener {
        void onChange(int itemPosition, String itemLabel);
    }


}
