package com.scriptfloor.hda.models;

/**
 * Created by LINCOLN on 3/24/2019.
 */

public class NewsModel {
    private int NewsID,NewsStatus;
    private String newsTitle,NewsContent,newsLink,NewsAuthor,NewsPic,NewsCategory,DateAdded;


    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int newsID) {
        NewsID = newsID;
    }

    public int getNewsStatus() {
        return NewsStatus;
    }

    public void setNewsStatus(int newsStatus) {
        NewsStatus = newsStatus;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return NewsContent;
    }

    public void setNewsContent(String newsContent) {
        NewsContent = newsContent;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsPic() {
        return NewsPic;
    }

    public void setNewsPic(String newsPic) {
        NewsPic = newsPic;
    }

    public String getNewsCategory() {
        return NewsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        NewsCategory = newsCategory;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getNewsAuthor() {
        return NewsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        NewsAuthor = newsAuthor;
    }
}
