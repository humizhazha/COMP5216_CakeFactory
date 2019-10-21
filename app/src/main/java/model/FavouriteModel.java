package model;

/**
 * Model for favourite layout
 */
public class FavouriteModel {
    private int image;
    private String title;
    private String subtitle;
    private int rating;
    private String review;

    public FavouriteModel(int image, String title, String subtitle, int rating, String review){
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
        this.review = review;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
