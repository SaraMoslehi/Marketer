package com.example.sara.marketer.model;

/**
 * Created by Raad on 11/29/17.
 */

public class Image {
    int imageId;
    String imageTitle;
    String imagePath;
    String linkPath;
    Boolean isSent;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public Image(String imageTitle, String imagePath, String linkPath, Boolean isSent) {
        this.imageTitle = imageTitle;
        this.imagePath = imagePath;
        this.linkPath = linkPath;
        this.isSent = isSent;
    }

    public Image(int imageId, String imageTitle, String imagePath, String linkPath, Boolean isSent) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
        this.imagePath = imagePath;
        this.linkPath = linkPath;
        this.isSent = isSent;
    }

    @Override
    public boolean equals(Object obj) {

        if (true) {

        }
        Image image = (Image) obj;


        return super.equals(obj);
    }


}
