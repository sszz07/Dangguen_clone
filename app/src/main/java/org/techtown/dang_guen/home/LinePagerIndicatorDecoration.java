package org.techtown.dang_guen.home;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration{

    /*
    private int colorActive = 0xFFFFFFFF;
    private int colorInactive = 0x66FFFFFF;
* 역할? 인트형으로 되어있는 색상이다
* 책임? 인트형 색상으로 색을 넣어준다
* 사용하는 이유-????
협력?mPaint.setColor(0xFFFFFFFF);
1.dot에 색상을 넣어준다
* */
    private int colorActive = 0xFFFFFFFF;
    private int colorInactive = 0x66FFFFFF;


    /*
DP = Resources.getSystem().getDisplayMetrics().density;
* 역할?
Resources-현재 클래스에서 리소스를 접근할수가 있다
getSystem()-리소스를 객체로 변환을 해준다
getDisplayMetrics()-화면을 계량으로 가져온다는 의미
density-float 소수점이 있는 수
* 책임? 현재 클래스에서 리소스를 객체로 변환을 하는데 getDisplayMetrics을 이용해서 수로 변환을 해준다
* 사용하는 이유-???
협력?    private final int mIndicatorHeight = (int) (DP * 16);, private final float mIndicatorStrokeWidth = DP * 1/2;,private final float mIndicatorItemLength = DP * 16;,  private final float mIndicatorItemPadding = DP * 4;
1.
* */
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     * 뷰 맨 아래에서 표시기가 차지하는 공간의 높이입니다
     */

               /*
        private final int mIndicatorHeight = (int) (DP * 16);
        * 역할?
        (int)-인트형으로 변환
        (DP * 16) 16을곱한다
        * 책임?사이즈를 바꿔주게 해준다
        * 사용하는 이유- 사용자 마음
          협력?
          1.
        * */
    private final int mIndicatorHeight = (int) (DP * 16);

    /**
     * Indicator stroke width.
     */

    private final float mIndicatorStrokeWidth = DP * 1/2;

    /**
     * Indicator width.
     */


    private final float mIndicatorItemLength = DP * 16;
    /**
     * Padding between indicators.
     */


    private final float mIndicatorItemPadding = DP * 4;

    /**
     * Some more natural animation interpolation
     */



               /*
        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
        * 역할?
        Interpolator(지워서 고침)
        AccelerateDecelerateInterpolator()-???
        * 책임?
        * 사용하는 이유-
          협력?
          1.
        * */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();


    /*
    private final Paint mPaint = new Paint();
* 역할?
Paint- 도형,텍스트,비트맵 스타일 색상 정보를 보유하고 있는 클래스이다
* 책임?
스타일을 꾸밀수 있게 해준다
* 사용하는 이유-스타일을 꾸밀수 있게 하려고
협력?
     mPaint.setStrokeCap(Paint.Cap.ROUND);

     //폭을 설정 해준다
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);

//페인트 스타일을 설정을 해준다
        mPaint.setStyle(Paint.Style.STROKE);



        mPaint.setAntiAlias(true);
1.
* */
    private final Paint mPaint = new Paint();
    public LinePagerIndicatorDecoration() {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }





    /*
 public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
* 역할?리사이클러뷰의 아이템을 꾸며주는 메소드 이다
* 책임?리사이클러뷰의 아이템을 사이즈등 꾸며줌을 해준다
* 사용하는 이유-
협력?
1.
* */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = parent.getAdapter().getItemCount();

        // center horizontally, calculate width and subtract half from center 가로로 중심을 잡고, 너비를 계산하고 중심에서 반을 뺍니다.

        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;
        // center vertically in the allotted space 할당된 공간에 수직으로 중심을 잡다
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;



        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


        // find active page (which should be highlighted) 활성 페이지 찾기(강조되어야 함) 활성 페이지 찾기(강조되어야 함)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();


        int activePosition = layoutManager.findFirstVisibleItemPosition();


        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        // find offset of active page (if the user is scrolling) 활성 페이지의 오프셋 찾기(사용자가 스크롤 중인 경우)

        final View activeChild = layoutManager.findViewByPosition(activePosition);
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();

        // on swipe the active item will be positioned from [-width, 0] 스와이프 시 활성 항목은 [-width, 0]에서 위치하게 됩니다.
        // interpolate offset for smooth animation 부드러운 애니메이션을 위해 간격띄우기 보간

        float progress = mInterpolator.getInterpolation(left * -1 / (float) width);


        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
    }




    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        mPaint.setColor(0x66FFFFFF);

        // width of item indicator including padding 패딩을 포함한 indicator 폭
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;

        for (int i = 0; i < itemCount; i++) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
            start += itemWidth;
        }
    }




    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress, int itemCount) {

        mPaint.setColor(0xFFFFFFFF);


        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;


        if (progress == 0F) {
            // no swipe, draw a normal indicator
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
        }



        else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // calculate partial highlight
            float partialLength = mIndicatorItemLength * progress;

            // draw the cut off highlight
            c.drawLine(highlightStart + partialLength, indicatorPosY,
                    highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);



            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + partialLength, indicatorPosY, mPaint);
            }
        }
    }





    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mIndicatorHeight;
    }
}
