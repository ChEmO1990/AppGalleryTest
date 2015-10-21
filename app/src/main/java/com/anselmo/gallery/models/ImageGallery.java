package com.anselmo.gallery.models;

/**
 * Created by naranya on 10/21/15.
 */
public class ImageGallery {
    private int id;
    private String path;
    private String title;
    private int liked_count;
    private int unliked_count;

    public ImageGallery() {}

    public ImageGallery(int id, String path, String title, int liked_count, int unliked_count) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.liked_count = liked_count;
        this.unliked_count = unliked_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLiked_count() {
        return liked_count;
    }

    public void setLiked_count(int liked_count) {
        this.liked_count = liked_count;
    }

    public int getUnliked_count() {
        return unliked_count;
    }

    public void setUnliked_count(int unliked_count) {
        this.unliked_count = unliked_count;
    }
}
