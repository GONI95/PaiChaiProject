package com.example.paichaiproject.Gon;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paichaiproject.R;

import java.util.List;

public class LessonListAdapter extends BaseAdapter {
    private Context context;
    private List<Lesson> lessonList;

    public LessonListAdapter(Context context, List<Lesson> lessonList) {
        this.context = context;
        this.lessonList = lessonList;
        // 처음에는 빈 배열로 저장됨
    }

    @Override
    public int getCount() {
        return lessonList.size();
    }

    @Override
    public Object getItem(int position) {
        return lessonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 데이터 세트의 지정된 위치에 데이터를 표시하는 보기를 가져옴(데이터의 위치값, 해당 리스트뷰 항목, ListView)
        View v = View.inflate(context, R.layout.lesson, null);
        // inflate() 메소드로 lesson.xml의 뷰 객체를 반환 받음 -> res에 루트가 있음 root: null
        TextView nameText = (TextView) v.findViewById(R.id.lesson_name);
        TextView examText = (TextView) v.findViewById(R.id.lesson_exam);
        TextView projectText = (TextView) v.findViewById(R.id.lesson_project);
        TextView tipText = (TextView) v.findViewById(R.id.lesson_tip);
        TextView levelText = (TextView) v.findViewById(R.id.lesson_level);

        nameText.setText(lessonList.get(position).getLessonName());
        examText.setText(lessonList.get(position).getLessonExam());
        projectText.setText(lessonList.get(position).getLessonProject());
        tipText.setText(lessonList.get(position).getLessonTip());
        levelText.setText(lessonList.get(position).getLessonLevel());

        v.setTag(lessonList.get(position).getLessonName());
        return v;
    }
}
