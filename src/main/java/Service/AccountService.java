package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService{
    
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // methods

    public Account postAccount(Account acc){

        String user = acc.getUsername();
        String password = acc.getPassword();

        // perform checks to validate the new user
        if (user == null || user.isBlank()){
            return null;
        }
        if (password.length() < 4){
            return null;
        }
        if (accountDAO.getAccountByUser(user) != null){
            return null;
        }

        // call createAccount to add account to database and return the new Account
        return accountDAO.createAccount(acc);
        
    }

    public Account loginAccount(Account acc){
        return accountDAO.getAccount(acc);
    }

    
}