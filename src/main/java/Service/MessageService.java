package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService{

    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
}