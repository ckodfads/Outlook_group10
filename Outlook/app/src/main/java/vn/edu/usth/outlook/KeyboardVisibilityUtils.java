package vn.edu.usth.outlook;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardVisibilityUtils implements ViewTreeObserver.OnGlobalLayoutListener {
    private final View rootView;
    private final OnKeyboardVisibilityListener onKeyboardVisibilityListener;

    public KeyboardVisibilityUtils(View rootView, OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        this.rootView = rootView;
        this.onKeyboardVisibilityListener = onKeyboardVisibilityListener;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        int screenHeight = rootView.getHeight();
        int keypadHeight = screenHeight - rect.bottom;
        boolean isVisible = keypadHeight > screenHeight * 0.15;
        onKeyboardVisibilityListener.onKeyboardVisibilityChanged(isVisible);
    }

    public interface OnKeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean isVisible);
    }
}
