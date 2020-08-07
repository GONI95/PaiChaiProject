package com.example.paichaiproject.Kyo;

public class BasePlace {        // 각 상점의 정보들을 저장하는 클래스
    private double x;
    private double y;
    private String name;
    private String desc;
    private int iconId;

    public BasePlace(double x, double y, String name, String desc, int iconId) {        // 생성자
        this.x = x;
        this.y = y;
        this.name = name;
        this.desc = desc;
        this.iconId = iconId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getIconId() {
        return iconId;
    }
}