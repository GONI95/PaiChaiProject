package com.example.paichaiproject.Gon;

public class Lesson {
    String lessonName;
    String lessonExam;
    String lessonProject;
    String lessonTip;
    String lessonLevel;

    public Lesson(String Name, String Exam, String Project, String Tip, String Level) {
        //생성자
        this.lessonName = Name;
        this.lessonExam = Exam;
        this.lessonProject = Project;
        this.lessonTip = Tip;
        this.lessonLevel = Level;
    }

    public String getLessonName() { //설정자
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonExam() {
        return lessonExam;
    }

    public void setLessonExam(String lessonExam) {  // 접근자
        this.lessonExam = lessonExam;
    }

    public String getLessonProject() {
        return lessonProject;
    }

    public void setLessonProject(String lessonProject) {
        this.lessonProject = lessonProject;
    }

    public String getLessonTip() {
        return lessonTip;
    }

    public void setLessonTip(String lessonTip) {
        this.lessonTip = lessonTip;
    }

    public String getLessonLevel() {
        return lessonLevel;
    }

    public void setLessonLevel(String lessonLevel) {
        this.lessonLevel = lessonLevel;
    }
}
