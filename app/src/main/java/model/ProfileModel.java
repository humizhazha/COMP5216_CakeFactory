package model;

/**
 * Model for profile function bar
 */
public class ProfileModel {
    private Integer icon;
    private Integer arrow;
    private String title;

    public ProfileModel(Integer icon, Integer arrow, String title) {
        this.icon = icon;
        this.arrow = arrow;
        this.title = title;
    }

    public Integer getIcon() {
        return this.icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getArrow() {
        return this.arrow;
    }

    public void setArrow(Integer arrow) {
        this.arrow = arrow;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
