package com.centrahub.focus.sourav.swipeselectionview.swipeSelection;

/**
 * Created by sourav on 30-Jan-18.
 */

public class ListSwipeUtil {

    public static String[] itemListString;

    public static ItemListHolderDTO getItemList(int iPosition) {
        ItemListHolderDTO weekUtilCalender = new ItemListHolderDTO(itemListString[iPosition], iPosition);
        return weekUtilCalender;
    }

    public static ItemListHolderDTO getFirstItem(String newValue, int iPosition, boolean isLast, boolean isFirst) {
        return getItemList(iPosition);
    }

    public static ItemListHolderDTO previousUnit( int iPosition) {
        iPosition--;
        if (iPosition <0)
            iPosition = itemListString.length - 1;
        return getItemList(iPosition);

    }
    public static ItemListHolderDTO nextUnit( int iPosition) {
        iPosition++;
        if (iPosition == itemListString.length) {
            iPosition=0;
        }
         return getItemList(iPosition);
    }
}
