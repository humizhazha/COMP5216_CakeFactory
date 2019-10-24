package model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * User POJO.
 */
@IgnoreExtraProperties
public class User {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String AVATAR = "avatar";
    public static final String ARTICLE = "articles";
    public static final String DESIGN = "designs";
    public static final String ORDER = "orders";

    private String username;
    private String email;
    private String mobile;
    private String avatar;
    private ArrayList<String> articles;
    private int designs;
    private int orders;

    public User() {}

    public User(String username, String email, String mobile, String avatar,
                ArrayList<String> articles, int designs, int orders) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.avatar = avatar;
        this.articles = articles;
        this.designs = designs;
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<String> articles) {
        this.articles = articles;
    }

    public int getDesigns() {
        return designs;
    }

    public void setDesigns(int designs) {
        this.designs = designs;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}