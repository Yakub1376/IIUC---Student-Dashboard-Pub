package com.blogspot.softlabsja.iiucstudentapp;


import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
@Keep
public class helperClassFirebase {

    @PropertyName("id")
    private String id;
    @PropertyName("name")
    private String name;
    @PropertyName("semester")
    private String semester;
    @PropertyName("gender")
    private String gender;
    @PropertyName("img")
    private String img;

    public helperClassFirebase(){
    }

    public helperClassFirebase(String id, String name, String semester, String gender, String img) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.gender = gender;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
