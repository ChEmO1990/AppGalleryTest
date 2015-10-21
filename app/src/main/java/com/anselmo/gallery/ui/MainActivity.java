package com.anselmo.gallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.anselmo.gallery.R;
import com.anselmo.gallery.adapters.GalleryAdapter;
import com.anselmo.gallery.db.Querys;
import com.anselmo.gallery.models.ImageGallery;
import com.anselmo.gallery.utils.DividerItemDecoration;
import com.github.mrengineer13.snackbar.SnackBar;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Bind(R.id.recycler_gallery)
    RecyclerView recyclerView;

    @Bind(R.id.btn_rate)
    Button rateButton;

    @Bind(R.id.btn_new_image)
    Button newImageButton;

    private ArrayList<ImageGallery> items;
    private GalleryAdapter adapter;

    private static int RESULT_LOAD_IMAGE = 1;
    int REQUEST_CAMERA = 0;
    int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Gallery List");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //Init array
        items = new ArrayList<>();
        adapter = new GalleryAdapter(this, items);
        recyclerView.setAdapter(adapter);

        //Load images
        if( Querys.getImagesFromDB() != null ) {
            items.clear();
            items.addAll(Querys.getImagesFromDB());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Load images
        if( Querys.getImagesFromDB() != null ) {
            items.clear();
            items.addAll(Querys.getImagesFromDB());
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_new_image) void saveImage() {
        Intent intent = new Intent( this, AddPhotoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_rate) void rate() {
        if( Querys.getImagesFromDB() != null ) {
            if( Querys.getImagesFromDB().size() >= 3 ) {
                Intent intent = new Intent( this, RateGalleryActivity.class);
                startActivity(intent);
            } else {
                new SnackBar.Builder(this)
                        .withMessage(getString(R.string.title_not_yet))
                        .withTypeFace(EasyFonts.robotoLight(this))
                        .withTextColorId(R.color.colorPrimary)
                        .withStyle(com.github.mrengineer13.snackbar.SnackBar.Style.DEFAULT)
                        .withDuration(com.github.mrengineer13.snackbar.SnackBar.MED_SNACK)
                        .show();
            }
        }

    }
}
