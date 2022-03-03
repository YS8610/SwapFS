package tfip.moddb.swap.model;

public class SwapNGModel {

    int id;
    int chatid;
    String userComment;
    String lesson;
    String lessonDate;
    String lessonTime;
    String sch;
    String classType;
    String lessonType;
    
    public SwapNGModel(int id, int chatid, String userComment, String lesson, String lessonDate, String lessonTime,
            String sch, String classType, String lessonType) {
        this.id = id;
        this.chatid = chatid;
        this.userComment = userComment;
        this.lesson = lesson;
        this.lessonDate = lessonDate;
        this.lessonTime = lessonTime;
        this.sch = sch;
        this.classType = classType;
        this.lessonType = lessonType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getSch() {
        return sch;
    }

    public void setSch(String sch) {
        this.sch = sch;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getLessonType() {
        return lessonType;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    
}
