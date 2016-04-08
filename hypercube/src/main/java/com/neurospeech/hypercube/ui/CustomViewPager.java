package com.neurospeech.hypercube.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.neurospeech.hypercube.HyperCubeApplication;


/**
 * ViewPager creates 2 or more fragments even if only 1 fragment is to be displayed.
 * So we cannot use onCreate event to initialize fragment.
 *
 * So we will listen for Page Change event and call fragmentResumed method of PagerFragment.
 *
 * However, page change is not fired for first time, so we will call fragmentResumed method
 * after Adapter is set.
 */

public class CustomViewPager extends ViewPager {


    public boolean isAllowSwipe() {
        return allowSwipe;
    }

    public void setAllowSwipe(boolean allowSwipe) {
        this.allowSwipe = allowSwipe;
    }

    private boolean allowSwipe = false;

    public CustomViewPager(Context context) {
        super(context);

        addOnPageChangeListener(pageChangeListener);

    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(pageChangeListener);
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        //invokeOnResume();
    }



    public void invokeOnResume() {
        HyperCubeApplication.current.post(new Runnable() {
            @Override
            public void run() {
                if (getChildCount() > 0) {
                    pageChangeListener.onPageSelected(getCurrentItem());
                }
            }
        }, 500);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(allowSwipe)
            return super.onInterceptTouchEvent(event);
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(allowSwipe)
            return super.onTouchEvent(event);
        // Never allow swiping to switch between pages
        return false;
    }

    public PagerFragment getSelectedFragment() {
        return selectedFragment;
    }

    PagerFragment selectedFragment;


    PageChangeListener pageChangeListener = new PageChangeListener();

    class PageChangeListener implements OnPageChangeListener{


        /*public PageChangeListener() {
            super();

            AndroidAppActivity.post(new Runnable() {
                @Override
                public void run() {
                    if(getCurrentItem()!=-1){
                        onPageSelected(getCurrentItem());
                    }
                }
            });
        }*/


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {




        }

        @Override
        public void onPageSelected(int position) {



            if(selectedFragment != null){
                selectedFragment.fragmentPaused();
            }

            if(!(getAdapter() instanceof FragmentPagerAdapter)){
                return;
            }


            PagerAdapter adapter = getAdapter();

            Object obj = adapter.instantiateItem(CustomViewPager.this,position);
            if(obj instanceof PagerFragment){
                selectedFragment = (PagerFragment)obj;
                postFragmentResumed((Fragment)selectedFragment);

            }
        }



        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * If view is not created, we should postpone calling fragmentResumed method.
     * @param fragment
     */
    private void postFragmentResumed(final Fragment fragment) {
        if (fragment.getView() == null) {
            HyperCubeApplication.current.post(new Runnable() {
                @Override
                public void run() {
                    postFragmentResumed(fragment);
                }
            });
            return;
        }
        ((PagerFragment)fragment).fragmentResumed();
    }

}