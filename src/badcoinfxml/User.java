package badcoinfxml;
import java.util.*;


public class User {
    static final Map<String, User> USERS = new HashMap<>();
    public String id = "";
    public String email = "";
    public String password = "";
    public Wallet wallet = null;
    public static User of(String id) {
        User user = USERS.get(id);
        return user;
    }
    
    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
        USERS.put(id, this);
        this.wallet = new Wallet();
    }
}
