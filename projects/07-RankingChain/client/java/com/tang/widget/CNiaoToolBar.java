//package com.tang.widget;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
////import android.support.v7.widget.TintTypedArray;
//import android.content.res.TypedArray;
//import android.support.v7.widget.Toolbar;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.tang.R;
//
//
//
//public class CNiaoToolBar extends Toolbar {
//
//
//
//    private LayoutInflater mInflater;
//
//    private View mView;
//    private TextView mTextTitle;
//    private EditText mSearchView;
//    private Button mRightButton;
//
//
//    public CNiaoToolBar(Context context) {
//       this(context,null);
//    }
//
//    public CNiaoToolBar(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CNiaoToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//
//
//        initView();
//        setContentInsetsRelative(10,10);
//
//
//
//
//        if(attrs !=null) {
//            final TypedArray a = context.obtainStyledAttributes(getContext(), attrs,
//                    R.styleable.CNiaoToolBar, defStyleAttr, 0);
//
//
//            final Drawable rightIcon = a.getDrawable(R.styleable.CNiaoToolBar_rightButtonIcon);
//            if (rightIcon != null) {
//                //setNavigationIcon(navIcon);
//                setRightButtonIcon(rightIcon);
//            }
//
//
//            boolean isShowSearchView = a.getBoolean(R.styleable.CNiaoToolBar_isShowSearchView,false);
//
//            if(isShowSearchView){
//
//                showSearchView();
//                hideTitleView();
//
//            }
//
//
//
//            CharSequence rightButtonText = a.getText(R.styleable.CNiaoToolBar_rightButtonText);
//            if(rightButtonText !=null){
//                setRightButtonText(rightButtonText);
//            }
//
//
//
//            a.recycle();
//        }
//
//    }
//
//    private void initView() {
//
//
//        if(mView == null) {
//
//            mInflater = LayoutInflater.from(getContext());
//            mView = mInflater.inflate(R.layout.chain_toolbar, null);
//
//
//            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
//            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
//            mRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);
//
//
//
//            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
//
//            addView(mView, lp);
//        }
//
//
//
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public void  setRightButtonIcon(Drawable icon){
//
//        if(mRightButton !=null){
//
//            mRightButton.setBackground(icon);
//            mRightButton.setVisibility(VISIBLE);
//        }
//
//    }
//
//    public void  setRightButtonIcon(int icon){
//
//        setRightButtonIcon(getResources().getDrawable(icon));
//    }
//
//
//    public  void setRightButtonOnClickListener(OnClickListener li){
//
//        mRightButton.setOnClickListener(li);
//    }
//
//    public void setRightButtonText(CharSequence text){
//        mRightButton.setText(text);
//        mRightButton.setVisibility(VISIBLE);
//    }
//
//    public void setRightButtonText(int id){
//        setRightButtonText(getResources().getString(id));
//    }
//
//
//
//    public Button getRightButton(){
//
//        return this.mRightButton;
//    }
//
//
//
//    @Override
//    public void setTitle(int resId) {
//
//        setTitle(getContext().getText(resId));
//    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//
//        initView();
//        if(mTextTitle !=null) {
//            mTextTitle.setText(title);
//            showTitleView();
//        }
//
//
//
//
//
//    }
//
//
//
//    public  void showSearchView(){
//
//        if(mSearchView !=null)
//            mSearchView.setVisibility(VISIBLE);
//
//    }
//
//
//    public void hideSearchView(){
//        if(mSearchView !=null)
//            mSearchView.setVisibility(GONE);
//    }
//
//    public void showTitleView(){
//        if(mTextTitle !=null)
//            mTextTitle.setVisibility(VISIBLE);
//    }
//
//
//    public void hideTitleView() {
//        if (mTextTitle != null)
//            mTextTitle.setVisibility(GONE);
//
//    }
//
//
////
////    private void ensureRightButtonView() {
////        if (mRightImageButton == null) {
////            mRightImageButton = new ImageButton(getContext(), null,
////                    android.support.v7.appcompat.R.attr.toolbarNavigationButtonStyle);
////            final LayoutParams lp = generateDefaultLayoutParams();
////            lp.gravity = GravityCompat.START | (Gravity.VERTICAL_GRAVITY_MASK);
////            mRightImageButton.setLayoutParams(lp);
////        }
////    }
//
//
//}
