package Project.Entity;

import java.util.List;

public class User {
   private String name;
   private List<Account> accounts;
   private Integer id;

    public User(Integer id, String name, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public List<Account> getBalances() {
        return accounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", balances=" + accounts +
                ", id=" + id +
                '}';
    }
}

