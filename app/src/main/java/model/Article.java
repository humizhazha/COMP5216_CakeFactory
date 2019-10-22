package model;

public class Article {
    private int newsimage1;
    private String newsimage2;
    private String newsname;
    private String time;
    private String news;
    private String newssub;


    public Article(int newsimage1, String newsimage2, String newsname, String time, String news, String newssub) {

        this.newsimage1 = newsimage1;
        this.newsimage2 = newsimage2;
        this.newsname = newsname;
        this.time = time;
        this.news = news;
        this.newssub = newssub;

    }

    public int getNewsimage1() {
        return newsimage1;
    }

    public void setNewsimage1(int newsimage1) {
        this.newsimage1 = newsimage1;
    }

    public String getNewsimage2() {
        return newsimage2;
    }

    public void setNewsimage2(String newsimage2) {
        this.newsimage2 = newsimage2;
    }

    public String getNewsname() {
        return newsname;
    }

    public void setNewsname(String newsname) {
        this.newsname = newsname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getNewssub() {
        return newssub;
    }

    public void setNewssub(String newssub) {
        this.newssub = newssub;
    }



}
