package model;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Model for design layout
 */
@SuppressWarnings("serial")
public class DesignModel implements Serializable{
    String shape;
    String type;
    String flavour;
    String datetime;
    ArrayList<String> decorations;
    ArrayList<Integer> X;
    ArrayList<Integer> Y;

    public DesignModel(){}

    public DesignModel(String shape, String type, String flavour,  String datetime,
                       ArrayList<String> decorations, ArrayList<Integer> X, ArrayList<Integer> Y) {
        this.shape = shape;
        this.type = type;
        this.flavour = flavour;
        this.datetime = datetime;
        this.decorations = decorations;
        this.X = X;
        this.Y = Y;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public ArrayList<String> getDecorations() {
        return decorations;
    }

    public void setDecorations(ArrayList<String> decorations) {
        this.decorations = decorations;
    }

    public ArrayList<Integer> getX() {
        return X;
    }

    public void setX(ArrayList<Integer> x) {
        X = x;
    }

    public ArrayList<Integer> getY() {
        return Y;
    }

    public void setY(ArrayList<Integer> y) {
        Y = y;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
