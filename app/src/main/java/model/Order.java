package model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Model for order layout
 */
@IgnoreExtraProperties
public class Order {
    private String datetime;
    private String design;
    private String user;
    private String price;
    private String firstname;
    private String lastname;
    private String phone;
    private String addr;
    private String postcode;
    private String state;
    private String delivery;

    public Order(){}

    public Order(String datetime, String design, String user, String price, String firstname, String lastname,
                 String phone, String addr, String postcode, String state, String delivery) {
        this.datetime = datetime;
        this.design = design;
        this.user = user;
        this.price = price;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.addr = addr;
        this.postcode = postcode;
        this.state = state;
        this.delivery = delivery;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
