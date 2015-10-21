package com.anselmo.gallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anselmo.gallery.R;
import com.anselmo.gallery.models.ImageGallery;
import com.anselmo.gallery.ui.ShowResultActivity;
import com.bumptech.glide.Glide;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;
import java.util.List;

/**
 * Created by Anselmo on 10/21/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Activity context;
    private List<ImageGallery> items;


    public GalleryAdapter(Activity context, List<ImageGallery> mAmiiBos) {
        this.context = context;
        this.items = mAmiiBos;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_gallery, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);


        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        ImageGallery image = items.get(position);

        Glide.with(context)
                .load( new File( image.getPath() ))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.image);

        viewHolder.title.setText( image.getTitle() );

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView title;
        public TextView serie;
        public TextView store;

        public ViewHolder(View itemView) {

            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image_item);
            title = (TextView) itemView.findViewById(R.id.title_item);

            title.setTypeface(EasyFonts.robotoBold(context));

            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Intent i = new Intent(context, ShowResultActivity.class);
            i.putExtra("index", position);
            context.startActivity(i);
        }
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return items.size();
    }
}
