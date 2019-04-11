package com.example.sarathomsonS1508045;

//
// Name                 __Sara Thomson_______
// Student ID           __S1508045___________
// Programme of Study   __Computing BSc(Hons)
//


public class Earthquake {

    private String title;
    private String description;
    private String pubDate;
    private String category;
    private String depth;
    private double[] latlong;
    private String location;
    private String magnitude;


    //default value constructor

    public Earthquake() {title = "UK Earthquake Alert";}




    //toString() method
    public String toString ()
    {
        String instanceInfo;
        //instanceInfo = "Title: " + getTitle();
        //instanceInfo += "Description: " + getDescription();
        instanceInfo = "Location: " + getLocation()+"\n";
        instanceInfo += "Date: " + getPubDate()+"\n";
        //instanceInfo += "Category: " + getCategory();
        instanceInfo += "Depth: " + getDepth()+"\n";
        instanceInfo += "Lat/Long: " + getLatlong()[0]+ ", " + getLatlong()[1] +"\n";
        instanceInfo += "Magnitude: " + getMagnitude()+"\n";

        return instanceInfo;

    }



    //getters and setters
    public String getTitle(){
        return title;
    }

    public void setTitle(String pTitle) {
        this.title = pTitle;
    }



    public String getDescription(){
        return description;
    }

    public void setDescription(String pDescription){
        this.description = pDescription;
    }



    public String getPubDate(){
        return pubDate;
    }

    public void setPubDate(String pPubDate){
        this.pubDate = pPubDate;
    }



    public String getCategory(){
        return category;
    }

    public void setCategory(String pCategory){
        this.category = pCategory;
    }



    public String getDepth(){
        return depth;
    }

    public void setDepth(String pDepth){
        this.depth = pDepth;
    }



    public double[] getLatlong(){
        return latlong;
    }

    public void setLatLong(String pLatLong) {
        String[] s = pLatLong.split(",");
        double[] x = new double[2];
        x[0] = Double.parseDouble(s[0]);
        x[1] = Double.parseDouble(s[1]);

        this.latlong = x;
    }



    public String getLocation(){
        return location;
    }

    public void setLocation(String pLocation) {
        this.location = pLocation;
    }



    public String getMagnitude(){
        return magnitude;
    }

    public void setMagnitude(String pMagnitude) {
        this.magnitude = pMagnitude;
    }


}