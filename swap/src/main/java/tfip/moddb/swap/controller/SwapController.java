package tfip.moddb.swap.controller;

import java.sql.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tfip.moddb.swap.model.SwapModel;
import tfip.moddb.swap.model.SwapNGModel;
import tfip.moddb.swap.repo.SwapRepo;
import tfip.moddb.swap.service.TelegramService;

@RestController
@RequestMapping(path = "/api")
public class SwapController {

    @Autowired
    SwapRepo swapRepo;

    @Autowired
    TelegramService telegramService;

    Gson gson = new Gson();
    
    @GetMapping(path="/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTestPage(){
        JsonObject msg = new JsonObject();
        msg.addProperty("msg", "ok");
        return new ResponseEntity<String>(msg.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/swaps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllSwapRequest(
        @RequestParam(defaultValue = "10") Integer limit,
        @RequestParam(defaultValue = "0") Integer offset
    ){
        List<SwapModel> swapModels = swapRepo.getAllSwap();
        String swapModelsJsonString = gson.toJson(swapModels).toString();
        return new ResponseEntity<String>(swapModelsJsonString, HttpStatus.OK);
    }

    @GetMapping(path = "/swap/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSwapByID(@PathVariable String id){
        JsonObject msg = new JsonObject();
        try {
            SwapModel swapModel = swapRepo.getSwapByID(Integer.parseInt(id));
            String swapModelJsonString = gson.toJson(swapModel).toString();
            if (swapModelJsonString.length()>10){
                return new ResponseEntity<String>(swapModelJsonString, HttpStatus.OK);
            }
            msg.addProperty("msg", "not found");
            return new ResponseEntity<String>(msg.toString(), HttpStatus.NOT_FOUND);
        } 
        catch (NumberFormatException e) {
            msg.addProperty("msg", "not found");
            return new ResponseEntity<String>(msg.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/send",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendTelegramMsg(@RequestBody String telegramJsonString){
        JsonObject msg = (JsonObject) JsonParser.parseString(telegramJsonString);
        String chatid = msg.get("chatid").getAsString();
        String jsonMsg = msg.get("msg").getAsString();
        String contact = msg.get("contact").getAsString();
        JsonObject result = new JsonObject();
        if (telegramService.sendMsg(chatid, jsonMsg+"\nContact Detail: "+contact)){
            result.addProperty("msg", "ok");
            return new ResponseEntity<String>(result.toString(), HttpStatus.OK); 
        }
        result.addProperty("msg", "fail");
        return new ResponseEntity<String>(result.toString(), HttpStatus.BAD_REQUEST); 
    }


    @GetMapping(path = "/{chatid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSwapbyChatid(){
        return null;
    }


    @PostMapping(path="add",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> setSwap(@RequestBody String json){
        SwapNGModel swaptobeAdded = gson.fromJson(json, SwapNGModel.class );
        SwapModel swapModelConverted = new SwapModel(
            swaptobeAdded.getId(),
            swaptobeAdded.getChatid(),
            swaptobeAdded.getUserComment(),
            swaptobeAdded.getLesson(),
            Date.valueOf( swaptobeAdded.getLessonDate().split("T")[0]),
            swaptobeAdded.getLessonTime(),
            swaptobeAdded.getSch(),
            swaptobeAdded.getClassType(),
            swaptobeAdded.getLessonType()
        );
        
        boolean isAdded = swapRepo.addSwap(swapModelConverted);

        JsonObject result = new JsonObject();
        if (isAdded){
            result.addProperty("msg", "ok");
            return new ResponseEntity<String>(result.toString(), HttpStatus.OK); 
        }
        result.addProperty("msg", "fail");
        return new ResponseEntity<String>(result.toString(), HttpStatus.BAD_REQUEST);
    }

}
