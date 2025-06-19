package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO{

    // method to create an account in the database
    public Account createAccount(Account acc){
        // get connection to database
        Connection connection = ConnectionUtil.getConnection();

        try{
            // sql string for inserting the account into database
            String sql = "insert into account (username, password) values (?,?);";

            // conversion of string to PreparedStatement
            PreparedStatement ps = connection.prepareStatement(sql);

            // set values within PreparedStatement to given username and password
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());

            // execute the PreparedStatement to insert account
            ps.executeUpdate();

            // get generated key from PreparedStatement results
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedAccountId = (int) rs.getLong(1);
                return new Account(generatedAccountId, acc.getUsername(), acc.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // method to obtain an account from the database by username
    public Account getAccountByUser(String username){

        Connection connection = ConnectionUtil.getConnection();

        try{

            String sql = "select * from account where username = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Account acc = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), rs.getString("password"));

                return acc;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // method to obtain an account from the database by username and password
    public Account getAccount(Account acc){

        Connection connection = ConnectionUtil.getConnection();

        try{

            String sql = "select * from account where username = ? and password = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Account user = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), rs.getString("password"));

                return user;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

}

