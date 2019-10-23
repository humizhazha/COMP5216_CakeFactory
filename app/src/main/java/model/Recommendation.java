package model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Recommendation POJO.
 */
@IgnoreExtraProperties
public class Recommendation {

    public static final String TOPIC = "topic";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String NUMBER  = "number";

    private String topic;
    private String title;
    private String description;
    private String image;
    private int number;

    public Recommendation() {}

    public Recommendation(String topic, String image, String title, String description, int number) {
        this.topic = topic;
        this.title = title;
        this.description = description;
        this.image = image;
        this.number = number;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
