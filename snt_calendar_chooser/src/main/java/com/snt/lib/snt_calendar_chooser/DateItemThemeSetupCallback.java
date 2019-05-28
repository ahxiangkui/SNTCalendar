package com.snt.lib.snt_calendar_chooser;

import android.support.annotation.LayoutRes;
import android.view.View;

public interface DateItemThemeSetupCallback {
    void setupItemTheme(@LayoutRes int itemLayoutRes, View holderItemView, int themeType);
}
