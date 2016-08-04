// Juan Pablo Anaya
// MDF3 - 201608
// News
package com.paix.jpam.anayajuan_ce03.NewsFeed;

import java.io.Serializable;

public class News implements Serializable {

    /*Properties*/
    private final String section;
    private final String subSection;
    private final String title;
    private final String newsUrl;
    private final String thumbnailUrl;
    private final String description;
    private final String imageUrl;

    /*Constructor*/
    public News(String section, String subsection, String title, String newsUrl,
                String thumbnailUrl, String description, String imageUrl) {
        this.section = section;
        this.subSection = subsection;
        this.title = title;
        this.newsUrl = newsUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.imageUrl = imageUrl;

    }

    /*Getters*/
    //Section
    public String getSection() {
        return section;
    }

    //SubSection
    public String getSubSection() {
        return subSection;
    }

    //Title
    public String getTitle() {
        return title;
    }

    //News URL
    public String getNewsUrl() {
        return newsUrl;
    }

    //Thumbnail URL
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    //Description
    public String getDescription() {
        return description;
    }

    //Image URL
    public String getImageUrl() {
        return imageUrl;
    }

}
