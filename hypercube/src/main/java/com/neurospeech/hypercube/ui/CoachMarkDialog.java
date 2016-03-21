package com.neurospeech.hypercube.ui;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.neurospeech.hypercube.R;
import com.neurospeech.hypercube.HyperCubeApplication;

import java.util.ArrayList;

/**
 * Created by Gigster on 08-01-2016.
 */
public class CoachMarkDialog extends AppCompatDialogFragment {




    public static class Builder{

        private ArrayList<CoachMark> marks = new ArrayList<CoachMark>();
        private FragmentManager fragmentManager;
        private Object owner;

        public static Builder NewBuilder(Fragment fragment){
            Builder b = new Builder();
            b.owner = fragment;
            b.fragmentManager = fragment.getChildFragmentManager();
            return b;
        }

        public static Builder NewBuilder(FragmentActivity fragment){
            Builder b = new Builder();
            b.owner = fragment;
            b.fragmentManager = fragment.getSupportFragmentManager();
            return b;
        }

        public Builder addMark(int imageResourceID,View ... views){
            CoachMark cm = new CoachMark(imageResourceID,views);
            marks.add(cm);
            return this;
        }

        public Builder addMark(View rootView, int imageResourceID,int ... viewIds){
            CoachMark cm = new CoachMark(rootView, imageResourceID,viewIds);
            marks.add(cm);
            return this;
        }

        public void show(){

            if(marks.isEmpty())
                return;

            String key = "coach_mark_" + owner.getClass().getName().toString();
            SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(HyperCubeApplication.application);
            if (preferences.getBoolean(key, false)){
                return;
            }

            preferences.edit()
                    .putBoolean(key, true)
                    .apply();

            View firstView = marks.get(0).anchors[0].view;
            if(firstView == null){
                firstView = marks.get(0).rootView;
            }
            final View first = firstView;
            first.postDelayed(new Runnable() {
                @Override
                public void run() {


                    for(final CoachMark mark:marks){
                        first.post(new Runnable() {
                            @Override
                            public void run() {
                                mark.prepareBitmap();
                            }
                        });
                    }


                    first.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CoachMarkDialog dialog = new CoachMarkDialog();
                                Rect windowRect = new Rect();
                                first.getWindowVisibleDisplayFrame(windowRect);
                                Bundle args = new Bundle();
                                args.putParcelableArray("coach_marks", marks.toArray(new CoachMark[0]));
                                args.putParcelable("window_rect", windowRect);
                                dialog.setArguments(args);
                                dialog.show(fragmentManager, "");
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });


                }
            }, 1000);
        }
    }


    private ViewPager viewPager;
    //private ImageView anchorView;

    public static class Anchor
            implements Parcelable {
        public Rect rect;
        //public Rect windowRect;
        //public Bitmap bitmap;
        public View view;
        public int viewId;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(viewId);
            dest.writeParcelable(rect, flags);
            //dest.writeParcelable(bitmap, flags);
        }

        public static final Creator<Anchor> CREATOR = new ClassLoaderCreator<Anchor>() {
            @Override
            public Anchor createFromParcel(Parcel source, ClassLoader loader) {
                Anchor anchor = new Anchor();
                anchor.viewId = source.readInt();
                anchor.rect = (Rect)source.readParcelable(loader);
                return anchor;
            }

            @Override
            public Anchor createFromParcel(Parcel source) {
                return createFromParcel(source, ClassLoader.getSystemClassLoader());
            }

            @Override
            public Anchor[] newArray(int size) {
                return new Anchor[size];
            }
        };
    }

    public static class CoachMark
            implements Parcelable{
        public Anchor[] anchors;
        public int layoutId;
        public View rootView;


        public CoachMark() {
            super();
        }

        public CoachMark(int imageID,View ... targets) {
            anchors = new Anchor[targets.length];
            for (int i = 0; i < anchors.length; i++) {
                Anchor anchor = new Anchor();
                anchor.view = targets[i];
                anchor.viewId = anchor.view.getId();
                anchors[i] = anchor;
            }
            layoutId = imageID;
        }

        public CoachMark(View rootView, int imageID,int ... targets) {
            this.rootView = rootView;
            anchors = new Anchor[targets.length];
            for (int i = 0; i < anchors.length; i++) {
                Anchor anchor = new Anchor();
                //anchor.view = targets[i];
                anchor.viewId = targets[i];
                anchors[i] = anchor;
            }
            layoutId = imageID;
        }


        private DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();

        /*public int pxToDp(int px)
        {
            return (int) (px * displayMetrics.density);
        }*/

        public void prepareBitmap() {


            for(Anchor anchor : anchors) {
                View view = anchor.view;
                if(view==null){
                    view = rootView.findViewById(anchor.viewId);
                }
                //anchor.bitmap = getViewBitmap(view);

                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int[] locations = new int[2];
                //view.getHitRect();
                view.getLocationInWindow(locations);
                r.set(
                        locations[0],
                        locations[1],
                        r.right,
                        r.bottom);
                //anchor.windowRect = new Rect();
                //view.getWindowVisibleDisplayFrame(anchor.windowRect);
                //view.getLocationOnScreen(locations);

                //view.getDrawingRect(r);

                //r = new Rect(locations[0],locations[1],r.right,r.bottom);
                //view.getWindowVisibleDisplayFrame(r);
                //r.set(r.left, r.top, view.getMeasuredWidth(), view.getMeasuredHeight());
                anchor.rect = r;
            }

            //int[] c = new int[2];
            //view.getLocationInWindow(c);
            //x = c[0];
            //y = c[1];

        }

        /**
         * Draw the view into a bitmap.
         */
        private Bitmap getViewBitmap(View v) {
            v.clearFocus();
            v.setPressed(false);

            boolean willNotCache = v.willNotCacheDrawing();
            v.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = v.getDrawingCacheBackgroundColor();
            v.setDrawingCacheBackgroundColor(0);

            if (color != 0) {
                v.destroyDrawingCache();
            }
            v.buildDrawingCache();
            Bitmap cacheBitmap = v.getDrawingCache();
            if (cacheBitmap == null) {
                Log.e("CoachMark", "failed getViewBitmap(" + v + ")", new RuntimeException());
                return null;
            }

            Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

            // Restore the view
            v.destroyDrawingCache();
            v.setWillNotCacheDrawing(willNotCache);
            v.setDrawingCacheBackgroundColor(color);

            return bitmap;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(layoutId);
            dest.writeParcelableArray(anchors,flags);
        }

        public static final Creator<CoachMark> CREATOR = new ClassLoaderCreator<CoachMark>() {
            @Override
            public CoachMark createFromParcel(Parcel source, ClassLoader loader) {
                CoachMark coachMark = new CoachMark();
                coachMark.layoutId = source.readInt();
                coachMark.anchors = (Anchor[])source.readParcelableArray(loader);
                return coachMark;
            }

            @Override
            public CoachMark createFromParcel(Parcel source) {
                return createFromParcel(source, ClassLoader.getSystemClassLoader());
            }

            @Override
            public CoachMark[] newArray(int size) {
                return new CoachMark[size];
            }
        };
    }

    public CoachMarkDialog() {

    }

    private CoachMark[] marks;

    @Override
    public int getTheme() {
        return R.style.Theme_AppCompat_Light_NoActionBar_CoachMark;
    }


    @Override
    public void setupDialog(Dialog dialog, int style) {
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        super.setupDialog(dialog, STYLE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_coachmark);
        viewPager = (ViewPager)dialog.findViewById(R.id.view_pager);


        loadMarks(dialog);

    }

    private Rect windowRect;

    private void loadMarks(Dialog dialog) {
        if(marks!=null)
            return;
        Bundle bundle = getArguments();
        if(bundle==null)
            return;
        marks = (CoachMark[]) bundle.getParcelableArray("coach_marks");

        windowRect = (Rect)bundle.getParcelable("window_rect");
        RelativeLayout layout = (RelativeLayout)dialog.findViewById(R.id.coach_mark_container);
        //viewPager.setMar(0,-windowRect.top,0,0);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, windowRect.top,0,0);
        viewPager.setLayoutParams(lp);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_coachmark,container,false);

        loadMarks(getDialog());

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentAdapter());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadMarks(getDialog());
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            CoachmarkFragment f = new CoachmarkFragment();
            Bundle args = new Bundle();
            args.putParcelableArray("anchors", marks[position].anchors);
            args.putInt("image", marks[position].layoutId);
            args.putParcelable("window_rect",windowRect);
            f.setArguments(args);
            return f;
        }

        @Override
        public int getCount() {
            return marks.length;
        }
    }

    public static class CoachmarkFragment extends Fragment{



        private Rect windowRect;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            //int statusBarHeight = getStatusBarHeight();



            Bundle args = getArguments();
            int layoutId = args.getInt("image", 0);
            View view = inflater.inflate(layoutId, container, false);
            //Log.i("rect-top",String.valueOf(statusBarHeight));

            windowRect = args.getParcelable("window_rect");

            float density = getContext().getResources().getDisplayMetrics().density;

            Log.i("Density is", String.valueOf(density));

            float padding = density*4;

            Anchor[] anchors = (Anchor[])args.getParcelableArray("anchors");
            for(final Anchor anchor : anchors) {
                final View imageView = view.findViewById(anchor.viewId);

                Rect rect = anchor.rect;

                int h = rect.height();

                h = (int)((float)h * (float)windowRect.bottom/ (float)windowRect.height() + 2*padding);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) rect.width(), h);

                //int statusBarHeight = (int) Math.ceil(25*getContext().getResources().getDisplayMetrics().density);
                //lp.setMargins((int)anchor.x,(int)anchor.y,0,0);
                lp.setMargins(rect.left, rect.top - windowRect.top - (int)padding, 0, 0);

                imageView.setLayoutParams(lp);

            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (Fragment f : getParentFragment().getFragmentManager().getFragments()) {
                        if (f instanceof CoachMarkDialog) {
                            ((CoachMarkDialog) f).next();
                            break;
                        }
                    }
                }
            });

            return view;
        }
    }

    private void next() {
        ViewPager viewPager = (ViewPager)getDialog().findViewById(R.id.view_pager);
        if(viewPager.getChildCount() == viewPager.getCurrentItem()+1){
            dismiss();
            return;
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);

    }
}
