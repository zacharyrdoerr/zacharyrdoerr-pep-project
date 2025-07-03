package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import Model.Account;

import java.util.List;

public class MessageService{

    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    public Message postMessage(Message message){

        String message_text = message.getMessage_text();
        int posted_by = message.getPosted_by();
        

        if (!(message_text.isBlank()) && message_text.length() < 256 && accountDAO.getAccountById(posted_by) != null){
            return messageDAO.postMessage(message);
        }else{
            return null;
        }
        
    }

    public List<Message> getAllMessages(){

        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){

        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){

        Message message = getMessageById(id);
        
        if (message != null){
            messageDAO.deleteMessageById(id);
        }
        
        return message;
    }

    public Message updateMessageById(String message, int id){

        messageDAO.updateMessageById(message, id);

        Message updated_message = getMessageById(id);

        return updated_message;
    }

    public List<Message> getMessagesByAccountId(int id){

        return messageDAO.getMessagesByAccountId(id);
    }



}