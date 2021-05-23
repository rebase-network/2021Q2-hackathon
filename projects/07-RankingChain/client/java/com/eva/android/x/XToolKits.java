package com.eva.android.x;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * 一个用于各种Android新版UI特性、效果的工具类。
 *
 * <p>
 * 因Android厂商、系统版本等等因素，导致Android应用的一些UI特性存在各种各样的
 * 兼容性问题，因而为了使用这些特性，可能需要花费极大的兼容处理成本。本类中的相
 * 关方法，力求在兼容成本、兼容覆盖面等方面，达到一个平衡，确保既能使用一些新特
 * 性从而提升用户体验，又得保证最低成本。
 *
 * @author Jack Jiang
 * @since 4.5
 */
public class XToolKits
{
    /**
     * 调用此方法，可以实现承浸式界面效果（比如用于显示图片、app启动时的闪屏界面
     * 等，尤其刘海屏下的视觉效果会很棒）。
     * <p>
     * 注：承浸式界面效果由Android系统在android 4.4版及以上系统中提供。
     *
     * @param ac 要设置的界面
     * @param translucent true 实现承浸式界面效果
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarTranslucent(Activity ac, boolean translucent)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    , WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 调用此方法，可以实现让界面从系统底部的虚拟物理按键下传过并显示出来，实现全承浸式界面效果（比如用于显示图片时）。
     * <p>
     * 注：全承浸式界面效果由Android系统在android 4.4版及以上系统中提供。
     *
     * @param ac 要设置的界面
     * @param translucent true 实现承浸式界面效果
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavigationBarTranslucent(Activity ac, boolean translucent)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    , WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置状态栏文字颜色为深色（系统默认是白色）。
     * <p>
     * 说明：之所以有时候要设置为深色，是因为有些界面，沉浸式效果时的背景用的是白色，
     * 系统默认的状态栏文字颜色也是白色，这样的话就看不清系统的时间等等内容了，太难看。
     * <p>
     * 兼容性：如果要兼容Android 6.0以下版本，则非常费事，因为各厂商（小米、oppo、魅族等等）
     * 的设置方法都不一样，而且也不统一。所以，为了保持代码逻辑的简洁简单，就只使用Android 6.0
     * 中官方的标准方法来实现。如果要在低版本手机上使用这个特性？不好意思，不支持！
     * <p>
     * 参考资料：
     * https://blog.csdn.net/woainijinying/article/details/82459131
     * https://www.jianshu.com/p/abd021c22728
     * https://www.jianshu.com/p/7392237bc1de
     *
     * @param ac 要设置的界面
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBarTextColorDark(Activity ac)
    {
        ac.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
