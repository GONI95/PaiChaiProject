package com.example.paichaiproject.Kyo;

import com.example.paichaiproject.Kyo.BasePlace;
import com.example.paichaiproject.R;

import java.util.ArrayList;

public class PCStore {
    private ArrayList<BasePlace> PCStores = new ArrayList<>();


    public void init() {
        PCStores.add(new BasePlace(36.316299, 127.373609, "리썬즈피시방", "후문쪽에서는제일좋다", R.drawable.ic_launcher_foreground));
        PCStores.add(new BasePlace(36.322892, 127.370924, "궁노래방", "아아아", R.drawable.ic_launcher_foreground));
        PCStores.add(new BasePlace(36.317407, 127.368997, "피시방", "아아아", R.drawable.ic_launcher_foreground));


    }

    public ArrayList<BasePlace> getPCStoresList() {
        return PCStores;
    }
}
