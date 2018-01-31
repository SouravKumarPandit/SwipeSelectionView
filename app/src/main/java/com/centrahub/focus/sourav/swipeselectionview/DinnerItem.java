package com.centrahub.focus.sourav.swipeselectionview;

/**
 * Created by sourav on 29-Jan-18.
 */

public class DinnerItem {

    int iPosition;
    String sLabel;
    String sDescription;

    public int getiPosition() {
        return iPosition;
    }

    public void setiPosition(int iPosition) {
        this.iPosition = iPosition;
    }

    public String getsLabel() {
        return sLabel;
    }

    public void setsLabel(String sLabel) {
        this.sLabel = sLabel;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public int compareTo(DinnerItem calenderBean) {
        if (iPosition != calenderBean.getiPosition()) {
            return iPosition > calenderBean.getiPosition() ? 1 : -1;
        } /*else if (iPosition != calenderBean.getItemPosion()) {
            return iPosition > calenderBean.getItemPosion() ? 1 : -1;
        } else if (iPosition != calenderBean.getItemPosion()) {
            return iPosition > calenderBean.getItemPosion() ? 1 : -1;
        }*/
        return 0;
    }
}
