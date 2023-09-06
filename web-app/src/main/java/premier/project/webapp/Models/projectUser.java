package premier.project.webapp.Models;

import jakarta.persistence.*;

@Entity
public class projectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 150)
    private char[] login;

    @Column(length = 150)
    private char[] password;

    public projectUser(){}
    public projectUser(char[] login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public char[] getLogin() {
        return login;
    }

    public void setLogin(char[] login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;
        projectUser user = (projectUser) obj;
        if(user.getLogin().length != this.login.length || user.getPassword().length != this.password.length) return false;
        for(int i = 0; i < this.password.length; i++){
            if(this.password[i] != user.getPassword()[i]){
                return false;
            }
        }
        for(int i = 0; i < this.login.length; i++){
            if(this.login[i] != user.getLogin()[i]){
                return false;
            }
        }
        return true;
    }
}
