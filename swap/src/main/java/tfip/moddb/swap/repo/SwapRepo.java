package tfip.moddb.swap.repo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import static tfip.moddb.swap.repo.SQL.*;

import tfip.moddb.swap.model.SwapModel;

@Repository
public class SwapRepo {
    
    @Autowired
    private JdbcTemplate template;

    Date date = new Date();

    // ensure that lesson date must be in the future not past
    private List<SwapModel> filterSwap(List<SwapModel> swaplist){
        List<SwapModel> filteredSwapModels =swaplist.stream()
            .filter(swap -> swap.getLessonDate().after(date) )
            .toList();
        return filteredSwapModels;
    }


    // get all swap id with different option
    public List<SwapModel> getAllSwap(){
        final List<SwapModel> swapModels = new LinkedList<>();
        final SqlRowSet rs=  template.queryForRowSet(SQL_GET_ALL_REQ);
        while(rs.next()){
            swapModels.add(SwapModel.populate(rs));
        }

        return filterSwap(swapModels);
    }

    
    // get all swap id with different option
    public List<SwapModel> getAllSwap(int limit){
        final List<SwapModel> swapModels = new LinkedList<>();
        final SqlRowSet rs=  template.queryForRowSet(SQL_GET_ALL_REQ_BY_LIMIT,limit);
        while(rs.next()){
            swapModels.add(SwapModel.populate(rs));
        }

        return filterSwap(swapModels);
        }


    // get all swap id with different option
    public List<SwapModel> getAllSwap(int limit, int offset){
        final List<SwapModel> swapModels = new LinkedList<>();
        final SqlRowSet rs=  template.queryForRowSet(SQL_GET_ALL_REQ_BY_LIMIT_BY_OFFSET,limit,offset);
        while(rs.next()){
            swapModels.add(SwapModel.populate(rs));
        }

        return filterSwap(swapModels);
    }


    // find by swap id 
    public SwapModel getSwapByID(int id){
        final SqlRowSet rs=  template.queryForRowSet(SQL_GET_REQ_BY_ID,id);
        while(rs.next()){
            SwapModel swapModel = SwapModel.populate(rs);
            return swapModel;
        }
        return null;
    }

    // adding of swap model
    public boolean addSwap(SwapModel swapModel){
        int id_schlesson = 0;
        if (swapModel.getSch().equalsIgnoreCase("cdc") && swapModel.getLessonType().equalsIgnoreCase("prac")){
            if (swapModel.getClassType().equalsIgnoreCase("3a")) id_schlesson = 1;
            if (swapModel.getClassType().equalsIgnoreCase("3")) id_schlesson = 2;
            if (swapModel.getClassType().equalsIgnoreCase("2b")) id_schlesson = 3;
            if (swapModel.getClassType().equalsIgnoreCase("2a")) id_schlesson = 4;
            if (swapModel.getClassType().equalsIgnoreCase("2")) id_schlesson = 5;
            int add = template.update(SQL_POST_REQ,swapModel.getChatid(), swapModel.getUserComment(),id_schlesson,swapModel.getLesson(),swapModel.getLessonDate(),swapModel.getLessonTime());
            return add>0;
        }
        else if (swapModel.getSch().equalsIgnoreCase("cdc") && swapModel.getLessonType().equalsIgnoreCase("sim")){
            if (swapModel.getClassType().equalsIgnoreCase("3a")) id_schlesson = 6;
            if (swapModel.getClassType().equalsIgnoreCase("3")) id_schlesson = 7;
            if (swapModel.getClassType().equalsIgnoreCase("2b")) id_schlesson = 8;
            if (swapModel.getClassType().equalsIgnoreCase("2a")) id_schlesson = 9;
            if (swapModel.getClassType().equalsIgnoreCase("2")) id_schlesson = 10;
            int add = template.update(SQL_POST_REQ,swapModel.getChatid(), swapModel.getUserComment(),id_schlesson,swapModel.getLesson(),swapModel.getLessonDate(),swapModel.getLessonTime());
            return add>0;
        }
        return false;
    }


    // getting all swap request from chatid 
    public List<SwapModel> getSwapbyChatid(int chatid){
        final List<SwapModel> swapModels = new LinkedList<>();
        final SqlRowSet rs =  template.queryForRowSet(SQL_GET_REQ_BY_CHATID,chatid);
        while(rs.next()){
            swapModels.add(SwapModel.populate(rs));
        }
        return filterSwap(swapModels);
    }


    // delete swap request by id
    public boolean deleteSwapbyID(int id){
        int add = template.update(SQL_DELETE_REQ_BY_ID, id);
        return add>0;
    } 
}
