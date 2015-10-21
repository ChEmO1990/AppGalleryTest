package com.anselmo.gallery.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.anselmo.gallery.R;
import com.anselmo.gallery.db.Querys;
import com.anselmo.gallery.models.ImageGallery;
import com.anselmo.gallery.ui.fragments.RateFragment;
import com.github.mrengineer13.snackbar.SnackBar;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateGalleryActivity extends BaseActivity {
    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.btn_like)
    Button btnLike;

    @Bind(R.id.btn_unlike)
    Button btnUnlike;

    private ArrayList<ImageGallery> items;
    private MyFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_rate_gallery);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.activity_title_rating));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        items = Querys.getImagesFromDB();

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        for( int i = 0; i < items.size(); i++ ) {
            adapter.addFragment( RateFragment.newInstance(i));
        }

        this.pager.setAdapter(adapter);

        new SnackBar.Builder(this)
                .withMessage(getString(R.string.title_swipe_left))
                .withTypeFace(EasyFonts.robotoLight(this))
                .withTextColorId(R.color.colorPrimary)
                .withStyle(SnackBar.Style.DEFAULT)
                .withDuration(SnackBar.SHORT_SNACK)
                .show();

    }

    @OnClick(R.id.btn_like) void likeEvent() {
        Querys.updateLike(items.get(pager.getCurrentItem()));
        new SnackBar.Builder(this)
                .withMessage(getString(R.string.like_plus))
                .withTypeFace(EasyFonts.robotoLight(this))
                .withTextColorId(R.color.colorPrimary)
                .withStyle(SnackBar.Style.DEFAULT)
                .withDuration(SnackBar.SHORT_SNACK)
                .show();
    }

    @OnClick(R.id.btn_unlike) void unlikeEvent() {
        Querys.updateUnLike(items.get(pager.getCurrentItem()));
        new SnackBar.Builder(this)
                .withMessage(getString(R.string.like_less))
                .withTypeFace(EasyFonts.robotoLight(this))
                .withTextColorId(R.color.colorPrimary)
                .withStyle(SnackBar.Style.DEFAULT)
                .withDuration(SnackBar.SHORT_SNACK)
                .show();
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<Fragment>();
        }

        public void addFragment(Fragment fragment) {
            this.fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int arg0) {
            return this.fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}