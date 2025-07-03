package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();

    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Create endpoint for POST /register
        app.post("/register", this::postRegistrationHandler );

        // Create endpoint for POST /login
        app.post("/login", this::postLoginHandler);

        // Create endpoint for POST /messages
        app.post("/messages", this::postMessagesHandler);

        // Create endpoint for GET /messages
        app.get("/messages", this::getAllMessagesHandler);

        // Create endpoint for GET /messages/{message_id}
        app.get("/messages/{message_id}", this::getMessageHandler);

        // Create endpoint for DELETE /messages/{message_id}
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        // Create endpoint for PATCH /messages/{message_id}
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        
        // Create endpoint for GET /accounts/{account_id}/messages
        app.get("/account/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }

    /** 
     * Define all handlers for each endpoint
     */

    private void postRegistrationHandler(Context context) throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account new_acc = accountService.postAccount(acc);
        if (new_acc != null){
            context.json(mapper.writeValueAsString(new_acc));
            context.status(200);
        }else{
            context.status(400);
        }
        
    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account login = accountService.loginAccount(acc);
        if (login != null){
            context.json(mapper.writeValueAsString(login));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage != null){
            context.json(mapper.writeValueAsString(postedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        
        List<Message> allMessages = messageService.getAllMessages();
        
        if (!(allMessages.isEmpty())){
            context.json(allMessages);
        }

        context.status(200);
    }

    private void getMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message retrievedMessage = messageService.getMessageById(message_id);

        if (retrievedMessage != null){
            context.json(mapper.writeValueAsString(retrievedMessage));
        }

        context.status(200);
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message deletedMessage = messageService.deleteMessageById(message_id);

        if (deletedMessage != null){
            context.json(mapper.writeValueAsString(deletedMessage));
        }

        context.status(200);
    }

    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.readValue(context.body(), String.class);
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message updatedMessage = messageService.updateMessageById(message, message_id);

        if (updatedMessage != null){
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
        
    }

    private void getMessagesByAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int account_id = context.pathParamAsClass("account_id", Integer.class).get();
        List<Message> userMessages = messageService.getMessagesByAccountId(account_id);

        if (!(userMessages.isEmpty())){
            context.json(userMessages);
        }

        context.status(200);
    }


}