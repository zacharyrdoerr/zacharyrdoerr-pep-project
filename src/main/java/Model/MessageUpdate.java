package Model;

// Class to parse the updated message for Updating
public class MessageUpdate {
    
    public String message_text;

    // default no args constructor
    public MessageUpdate(){

    } 
    // constructor containing just th e
    public MessageUpdate(String message){
        this.message_text = message;
    }

    // getter and setter methods

    public String getMessage_text(){
        return message_text;
    }

    public void setMessage_text(String message){
        this.message_text = message;
    }




}
