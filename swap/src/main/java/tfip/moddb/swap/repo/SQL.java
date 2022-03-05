package tfip.moddb.swap.repo;

public class SQL {
    public final static String SQL_GET_ALL_REQ = "select res.id,res.chatid,res.usercomment,res.lesson,res.lessondate,res.lessontime,sch.sch,sch.classtype,sch.lessontype from swaprequests as res inner join schlesson as sch on res.id_schlesson=sch.id_schlesson order by res.lessondate";

    public final static String SQL_GET_ALL_REQ_BY_LIMIT = "select res.id,res.chatid,res.usercomment,res.lesson,res.lessondate,res.lessontime,sch.sch,sch.classtype,sch.lessontype from swaprequests as res inner join schlesson as sch on res.id_schlesson=sch.id_schlesson limit ? order by res.lessondate";

    public final static String SQL_GET_ALL_REQ_BY_LIMIT_BY_OFFSET = "select res.id,res.chatid,res.usercomment,res.lesson,res.lessondate,res.lessontime,sch.sch,sch.classtype,sch.lessontype from swaprequests as res inner join schlesson as sch on res.id_schlesson=sch.id_schlesson limit ? offset ? order by res.lessondate";

    public final static String SQL_GET_REQ_BY_ID = "select res.id,res.chatid,res.usercomment,res.lesson,res.lessondate,res.lessontime,sch.sch,sch.classtype,sch.lessontype from swaprequests as res inner join schlesson as sch on res.id_schlesson=sch.id_schlesson where res.id = ?";

    public final static String SQL_POST_REQ = "insert into swaprequests(chatid,usercomment,id_schlesson,lesson,lessondate,lessontime) values(?,?,?,?,?,?)";

    public final static String SQL_GET_REQ_BY_CHATID = "select res.id,res.chatid,res.usercomment,res.lesson,res.lessondate,res.lessontime,sch.sch,sch.classtype,sch.lessontype from swaprequests as res inner join schlesson as sch on res.id_schlesson=sch.id_schlesson where res.chatid = ? order by res.lessondate";

    public final static String SQL_DELETE_REQ_BY_ID = "delete from swaprequests where id = ?";
}