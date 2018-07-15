package com.example.kotkarandsons.stalkcode;

public class UserProfile {
    public String userName;
    public  String email;
    public  String age;
    public UserProfile()
    {}

    public UserProfile(String userName, String email, String age) {
        this.userName = userName;
        this.email = email;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
