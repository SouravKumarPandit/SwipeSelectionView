package com.centrahub.focus.sourav.swipeselectionview.swipeSelection;

/**
 * Created by sourav on 29-Jan-18.
 */

public class ItemListHolderDTO {

    int itemPosion;
    String itemLabel;

    public ItemListHolderDTO(String preValue, int iPosition) {
        this.setItemLabel(preValue);
        this.setItemPosion(iPosition);
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public int getItemPosion() {
        return itemPosion;
    }

    public void setItemPosion(int itemPosion) {
        this.itemPosion = itemPosion;
    }


    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public int compareTo(ItemListHolderDTO calenderBean) {
        if (itemPosion != calenderBean.getItemPosion()) {
            return itemPosion > calenderBean.getItemPosion() ? 1 : -1;
        }
        return 0;
    }
}
