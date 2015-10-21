package com.anselmo.gallery.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.anselmo.gallery.R;
import com.anselmo.gallery.db.Querys;
import com.vstechlab.easyfonts.EasyFonts;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowResultActivity extends BaseActivity {
    @Bind(R.id.btn_return_list)
    Button returnList;

    @Bind(R.id.lbl_liked_count)
    TextView liked;

    @Bind(R.id.lbl_unliked_count)
    TextView unliked;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.activity_title_results));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            index = extras.getInt("index");

            liked.setText(String.valueOf( Querys.getImagesFromDB().get(index).getLiked_count() ));
            liked.setTypeface(EasyFonts.robotoBold(this));

            unliked.setText( String.valueOf( Querys.getImagesFromDB().get(index).getUnliked_count() ));
            unliked.setTypeface(EasyFonts.robotoBold(this));
        }
    }

    @OnClick(R.id.btn_return_list) void close() {
        finish();
    }


}
