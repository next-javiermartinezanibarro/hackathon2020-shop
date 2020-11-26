package com.bbva.hackathon.bbvakids.shop.client;


public class Profile {
    public Long id;
    public String name;
    public String lastName;
    public String nickName;
    public String email;

    public int level;
    public int currentExp;
    public int nextLevelExp;
    public double money;

    public long gems;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                ", gems='" + gems + '\'' +
                '}';
    }
}
