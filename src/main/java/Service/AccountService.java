package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService{
    
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
}