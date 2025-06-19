package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

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
    
        return app;
    }

    /** 
     * Define all handlers for each endpoint
     */

    private void postRegistrationHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void getMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }

    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        context.json("sample text");
    }


}