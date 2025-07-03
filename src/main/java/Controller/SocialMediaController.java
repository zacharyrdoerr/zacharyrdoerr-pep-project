package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;
import Model.MessageUpdate;

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
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        return app;
    }

    /** 
     * Define all handlers for each endpoint
     */

    // handler for account registration
    private void postRegistrationHandler(Context context) throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();

        // read account from body and use mapper to create account object
        Account acc = mapper.readValue(context.body(), Account.class);

        // call postAccount within accountService
        Account new_acc = accountService.postAccount(acc);

        // check for null, if not return the account with status 200
        if (new_acc != null){
            context.status(200).json(mapper.writeValueAsString(new_acc));
        }else{
            context.status(400);
        }
        
    }

    // handler for account login
    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        // use mapper to create account object from json body
        Account acc = mapper.readValue(context.body(), Account.class);

        // obtain account from database 
        Account login = accountService.loginAccount(acc);

        // check for null, if not return account with status 200
        if (login != null){
            context.status(200).json(mapper.writeValueAsString(login));  
        }else{
            context.status(401);
        }
    }

    // handler for posting messages 
    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        // use mapper to create message object from json body
        Message message = mapper.readValue(context.body(), Message.class);
        
        // call postMessage from messageService
        Message postedMessage = messageService.postMessage(message);

        // check if message is null, if not return message with 200 status
        if (postedMessage != null){
            context.status(200).json(mapper.writeValueAsString(postedMessage));
        }else{
            context.status(400);
        }
    }

    // handler for retrieving all messages in database
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        
        // call getAllMessages and return the list with status 200
        List<Message> allMessages = messageService.getAllMessages();
        context.status(200).json(allMessages);
        
    }

    // handler for getting message by id
    private void getMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        // uses pathParamAsClass method to retrive the id from the path
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message retrievedMessage = messageService.getMessageById(message_id);

        if (retrievedMessage != null){
            context.json(mapper.writeValueAsString(retrievedMessage));
        }

        context.status(200);
    }

    // handler for deleting message by id
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message deletedMessage = messageService.deleteMessageById(message_id);

        if (deletedMessage != null){
            context.json(mapper.writeValueAsString(deletedMessage));
        }

        context.status(200);
    }

    // handler for updating messages
    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        // uses MessageUpdate class with one String object to parse the message from json body
        MessageUpdate message = mapper.readValue(context.body(), MessageUpdate.class);
        int message_id = context.pathParamAsClass("message_id", Integer.class).get();
        Message updatedMessage = messageService.updateMessageById(message.getMessage_text(), message_id);

        if (updatedMessage != null){

            context.status(200).json(updatedMessage);
            
        }else{
            context.status(400);
        }
        
    }

    // handler for obtaining all messages by an account id
    private void getMessagesByAccountHandler(Context context) throws JsonProcessingException{
        
        int account_id = context.pathParamAsClass("account_id", Integer.class).get();
        List<Message> userMessages = messageService.getMessagesByAccountId(account_id);


        context.status(200).json(userMessages);
        
    }


}