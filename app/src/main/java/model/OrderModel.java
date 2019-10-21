package model;

/**
 * Model for order layout
 */
public class OrderModel {
    private Integer image;
    private String title;
    private String size;
    private String  date;
    private String price;

    public OrderModel(Integer image, String title, String size, String date, String price) {
        this.image = image;
        this.title = title;
        this.size = size;
        this.date = date;
        this.price = price;
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

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
