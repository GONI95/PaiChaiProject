package com.example.paichaiproject.Kyo;

import com.example.paichaiproject.Kyo.BasePlace;
import com.example.paichaiproject.R;

import java.util.ArrayList;

public class FoodStore {
    private ArrayList<BasePlace> foodStores = new ArrayList<>();        // 정보를 저장하기위한 ArrayList 생성


    public void init() {        // 리스트에 값을 넣어줌
        foodStores.add(new BasePlace(36.323664, 127.373591, "고구려", "고기무한리필", R.drawable.ic_launcher_foreground));
        foodStores.add(new BasePlace(36.323352, 127.370587, "맘스터치", "감튀머겅", R.drawable.ic_launcher_foreground));
        foodStores.add(new BasePlace(36.317186, 127.369849, "청년식당", "인싸들가는곳난안('못'아님)가봄", R.drawable.ic_launcher_foreground));
        foodStores.add(new BasePlace(36.322807, 127.370530, "짜이짱", "짜장면통일국룰", R.drawable.ic_launcher_foreground));
        foodStores.add(new BasePlace(231.1, 123.5, "음식점", "아아아", R.drawable.ic_launcher_foreground));
        foodStores.add(new BasePlace(231.1, 123.5, "음식점", "아아아", R.drawable.ic_launcher_foreground));
    }

    public ArrayList<BasePlace> getFoodStoreList() {        // 저장된 리스트(foodStores)를 리턴
        return foodStores;
    }
}

