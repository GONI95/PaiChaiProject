package com.example.paichaiproject.Kyo;

import com.example.paichaiproject.Kyo.BasePlace;
import com.example.paichaiproject.R;

import java.util.ArrayList;

public class LIBStore {
    private ArrayList<BasePlace> LIBStores = new ArrayList<>();


    public void init() {
        LIBStores.add(new BasePlace(36.320770, 127.366165, "학교도서관", "3번가봄좋음", R.drawable.ic_launcher_foreground));
        LIBStores.add(new BasePlace(36.324677, 127.370542, "메디팜보령약국", "아프지말고참지도말고", R.drawable.ic_launcher_foreground));
        LIBStores.add(new BasePlace(36.323282, 127.370485, "닛폰헤어더클레식", "이시국에?", R.drawable.ic_launcher_foreground));
        LIBStores.add(new BasePlace(231.1, 123.5, "도서관", "아아아", R.drawable.ic_launcher_foreground));
        LIBStores.add(new BasePlace(231.1, 123.5, "도서관", "아아아", R.drawable.ic_launcher_foreground));

    }

    public ArrayList<BasePlace> getLIBStoresList() {
        return LIBStores;
    }
}
