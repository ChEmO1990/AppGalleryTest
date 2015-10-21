package com.anselmo.gallery;

/**
 * Created by naranya on 10/21/15.
 */
public class ImageGallery {
    private int id;
    private String path;
    private String title;

    public ImageGallery() {}

    public ImageGallery(int id, String path, String title) {
        this.id = id;
        this.path = path;
        this.title = title;
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
}
