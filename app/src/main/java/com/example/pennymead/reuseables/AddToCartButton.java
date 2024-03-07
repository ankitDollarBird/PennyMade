package com.example.pennymead.reuseables;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.pennymead.R;
import com.google.android.material.button.MaterialButton;

public class AddToCartButton extends MaterialButton {

    public AddToCartButton(@NonNull Context context) {
        super(context);
    }

    public AddToCartButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddToCartButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        setBackgroundColor(ContextCompat.getColor(getContext(), R.drawable.button_shape));
        setTextColor(ContextCompat.getColor(getContext(), R.color.text_white_color));
        setText("My Button Default Text");
    }

    public void setButtonLogicForScreen1(){
        //do your button functionalities for Screen1 (which is also reused in case some other screen needs the same functionalities)
    }

    public void setButtonLogicForScreen2(){
        //do your button functionalities for Screen2 (which is also reused in case some other screen needs the same functionalities)
    }
}
