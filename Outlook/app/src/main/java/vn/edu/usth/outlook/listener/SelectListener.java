package vn.edu.usth.outlook.listener;

public interface SelectListener {
    default void onItemClicked(int position){
    }
    void onLongItemClick(int position);
}
