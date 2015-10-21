package com.anselmo.gallery.models;

/**
 * Created by naranya on 10/21/15.
 */
public class Result {
    private String status;

    public Result() {}

    public Result(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
