package com.example.paichaiproject.Kyo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paichaiproject.R;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {     // 리사이클러 뷰 설정을 위한 클래스

    private ArrayList<BasePlace> listData = new ArrayList<>();      // 뷰에 보여줄 상점목록을 저장하기 위해 객체생성
    private Marker placeMarker;

    public interface OnItemClickListener {      // 클릭리스너를 구글맵 액티비티에 쓰기위해 생성
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {     // 뷰를 보관하는 ItemViewHolder 클래스를 생성


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);     // item에 뷰를 만들어줌
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {        // onBind를 호출
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(BasePlace data) {
        listData.add(data);
    }

    public BasePlace getItem(int pos) {
        return listData.get(pos);
    }

    void setMarker(Marker marker) {
        placeMarker = marker;
    }

    public void setList(ArrayList<BasePlace> placeList) {
        listData = placeList;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {      // 리스너와 id 정의(초기화)하는 뷰를 보관하고있는 클래스

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && mListener != null) {       // 클릭된 아이템의 위치정보가 없거나 앱이 초기화되기전 클릭리스너가 호출되면 무시
                        mListener.onItemClick(v, pos);
                    }
                }
            });
        }

        void onBind(BasePlace data) {       // BasePlace 내부 정보를 받아서 뷰에 보여준다
            textView1.setText(data.getName());
            textView2.setText(data.getDesc());
            imageView.setImageResource(data.getIconId());
        }
    }
}