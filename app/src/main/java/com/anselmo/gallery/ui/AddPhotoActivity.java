package com.anselmo.gallery.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.anselmo.gallery.R;
import com.anselmo.gallery.db.Querys;
import com.anselmo.gallery.models.ImageGallery;
import com.github.mrengineer13.snackbar.SnackBar;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPhotoActivity extends BaseActivity {
    @Bind(R.id.btn_select_image)
    Button selectImage;

    @Bind(R.id.btn_save_image)
    Button saveImage;

    @Bind(R.id.edit_title_image)
    EditText editTitle;

    @Bind(R.id.image_preview)
    ImageView image;

    private static int REQUEST_CAMERA       = 0;
    private static int SELECT_FILE          = 1;
    private boolean status_save = false;
    private File destination = null;
    private String selectedImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.activity_title_addphotos));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btn_select_image) void openDialogImages() {
        selectImage();
    }

    @OnClick(R.id.btn_save_image) void saveImage() {
        if( TextUtils.isEmpty( editTitle.getText().toString() ) ) {
            new SnackBar.Builder(this)
                    .withMessage(getString(R.string.error_save_empty))
                    .withTypeFace(EasyFonts.robotoLight(this))
                    .withTextColorId(R.color.colorPrimary)
                    .withStyle(SnackBar.Style.DEFAULT)
                    .withDuration(SnackBar.MED_SNACK)
                    .show();
        } else {
            if (status_save && destination != null) {
                //Capture Photo
                Querys.addImage(new ImageGallery(0, destination.getAbsolutePath(), editTitle.getText().toString(), 0, 0));
                finish();
            } else if (status_save && selectedImagePath != null) {
                Querys.addImage(new ImageGallery(0, selectedImagePath, editTitle.getText().toString(), 0, 0));
                finish();
            } else {
                new SnackBar.Builder(this)
                        .withMessage(getString(R.string.error_save_no_image))
                        .withTypeFace(EasyFonts.robotoLight(this))
                        .withTextColorId(R.color.colorPrimary)
                        .withStyle(SnackBar.Style.DEFAULT)
                        .withDuration(SnackBar.MED_SNACK)
                        .show();
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = getResources().getStringArray(R.array.array_options);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        status_save = true;

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        image.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        selectedImagePath = cursor.getString(column_index);

        status_save = true;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        image.setImageBitmap(bm);
    }
}
