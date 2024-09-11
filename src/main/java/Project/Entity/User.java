package Project.Entity;

import java.util.List;

public class User {
   private String name;
   private List<Balance> balances;
   private Integer id;

    public User(Integer id, String name, List<Balance> balances) {
        this.id = id;
        this.name = name;
        this.balances = balances;
    }

    public String getName() {
        return name;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", balances=" + balances +
                ", id=" + id +
                '}';
    }
}

