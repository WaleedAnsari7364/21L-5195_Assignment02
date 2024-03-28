package com.example.a21l_5195_assignment02;

public class Restaurant {
    String Name;
    String Location;
    String Phone;
    String Description;

    String Rating;
    public Restaurant() {
    }

    public Restaurant(String name, String location, String phone, String description,String rating) {
        Name = name;
        Location = location;
        Phone = phone;
        Description = description;
        Rating=rating;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDescription() {
        return Description;
    }

    public String getRating() {
        return Rating;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "Name='" + Name + '\'' +
                ", Location='" + Location + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Description='" + Description + '\'' +
                ", Rating=" + Rating +
                '}';
    }
}
