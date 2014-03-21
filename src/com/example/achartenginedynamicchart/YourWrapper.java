package com.example.achartenginedynamicchart;

import android.view.View;
import android.widget.Button;

public class YourWrapper
{
    private View base;
    private Button button;

    public YourWrapper(View base)
    {
        this.base = base;
    }

    public Button getButton()
    {
        if (button == null)
        {
            button = (Button) base.findViewById(R.id.icon);
        }
        return (button);
    }
}