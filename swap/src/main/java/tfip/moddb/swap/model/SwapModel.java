package tfip.moddb.swap.model;

import java.sql.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class SwapModel {
    
    int id;
    int chatid;
    String userComment;
    String lesson;
    Date lessonDate;
    String lessonTime;
    String sch;
    String classType;
    String lessonType;

    
    public SwapModel(int id, int chatid, String userComment, String lesson, Date lessonDate, String lessonTime, String sch,
            String classType, String lessonType) {
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


    public Date getLessonDate() {
        return lessonDate;
    }


    public void setLessonDate(Date lessonDate) {
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

    public static SwapModel populate(SqlRowSet rs){
        final SwapModel swapModel = new SwapModel(
            rs.getInt("id"),
            rs.getInt("chatid"),
            rs.getString("usercomment"),
            rs.getString("lesson"),
            rs.getDate("lessondate"),
            rs.getString("lessontime"),
            rs.getString("sch"),
            rs.getString("classtype"),
            rs.getString("lessontype")
        );
        return swapModel;
    }

}
