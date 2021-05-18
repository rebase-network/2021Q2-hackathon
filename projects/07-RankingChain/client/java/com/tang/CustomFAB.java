package com.tang;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by DELL on 2020/7/23.
 */

public class CustomFAB extends ImageButton {

    private static final int RAD = 56;
    private Context ctx;
    private int bgColorFAB;
    private int bgColorPressed;


    public CustomFAB(Context context) {
        super(context);
        this.ctx = context;
        init(null);
    }

    public CustomFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init(attrs);
    }

    public CustomFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        init(attrs);
    }

    public CustomFAB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.ctx = context;
        init(attrs);
    }

    private Drawable createButton(int color) {
        OvalShape oShape = new OvalShape();
        ShapeDrawable sd = new ShapeDrawable(oShape);

        setWillNotDraw(false);
        sd.getPaint().setColor(color);

        OvalShape oShape1 = new OvalShape();
        ShapeDrawable sd1 = new ShapeDrawable(oShape);


        sd1.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0,0,0, height,
                        new int[] {
                                Color.WHITE,
                                Color.GRAY,
                                Color.DKGRAY,
                                Color.BLACK
                        }, null, Shader.TileMode.REPEAT);

                return lg;
            }
        });

        LayerDrawable ld = new LayerDrawable(new Drawable[] { sd1, sd });
        ld.setLayerInset(0, 5, 5, 0, 0);//后面几个参数表示的是影音的大小
        ld.setLayerInset(1, 0, 0, 5, 5);

        return ld;
    }


    private void init(AttributeSet attrSet) {
        Resources.Theme theme = ctx.getTheme();
        TypedArray arr = theme.obtainStyledAttributes(attrSet, R.styleable.FAB, 0, 0);
        try {
            //setBgColor(arr.getColor(R.styleable.FAB_bg_color, Color.BLUE));
            setBgColor(arr.getColor(R.styleable.FAB_bg_colorFAB, Color.BLUE));
            setBgColorPressed(arr.getColor(R.styleable.FAB_bg_color_pressed, Color.GRAY));
            StateListDrawable sld = new StateListDrawable();

           sld.addState(new int[] {android.R.attr.state_pressed}, createButton(bgColorPressed));
       //     sld.addState(new int[] {}, createButton(bgColorFAB));  //实现三维实体效果
            setBackground(sld);
        }

        catch(Throwable t) {}
        finally {
            arr.recycle();
        }

    }

    public void setBgColor(int color) {
        this.bgColorFAB = color;
    }

    public void setBgColorPressed(int color) {
        this.bgColorPressed = color;
    }



}
