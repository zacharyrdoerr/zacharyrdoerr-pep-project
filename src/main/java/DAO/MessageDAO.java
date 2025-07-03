package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO{
    
    // method to post a message to the database
    public Message postMessage(Message message){
        
        // get connection to database
        Connection connection = ConnectionUtil.getConnection();

        try{
            // sql string for inserting the account into database
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?);";

            // conversion of string to PreparedStatement
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // set values within PreparedStatement 
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            // execute the PreparedStatement to insert account
            ps.executeUpdate();

            // get generated key from PreparedStatement results
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedMessageId = (int) rs.getLong(1);
                return new Message(generatedMessageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    // method to retrieve all messages from the database
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{

            String sql = "select * from message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch")
                                            );
                messages.add(message);                          
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // method to retrieve a single message from the database by id
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "select * from message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                Message message = new Message(id, 
                                rs.getInt("posted_by"), 
                                rs.getString("message_text"), 
                                rs.getLong("time_posted_epoch")
                                );

                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    // method to delete a specified message from the database by id
    public void deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "delete from message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    // method to update a specified message from the database by id
    public int updateMessageById(String message, int id){
        
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "update message set message_text = ? where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message);
            ps.setInt(2, id);

            int rows_updated = ps.executeUpdate();
            return rows_updated;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Message> getMessagesByAccountId(int id){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "select * from message where posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch")
                                            );
                messages.add(message);                          
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }
}