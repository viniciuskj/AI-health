package com.synback.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Credentials")
public class AuthenticationUser implements Cloneable {

    private String userId;
    private String email;
    private String password;
    private String role;

    // Construtor padrão necessário para a desserialização
    public AuthenticationUser() {
    }

    public AuthenticationUser(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = "customer";
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Id: " + userId + '\n' +
                "Email: " + email.toString() + '\n' +
                "Password: " + password + '\n' +
                "Role: " + role;
    }

    public String getRole() {
        return role;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        AuthenticationUser user = (AuthenticationUser) obj;

        if (user.userId != this.userId || user.email != this.email ||
                user.password != this.password ||
                user.role != this.role)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + userId.hashCode();
        result = 7 * result + email.hashCode();
        result = 7 * result + password.hashCode();
        result = 7 * result + role.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private AuthenticationUser(AuthenticationUser modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.email = modelo.email;
        this.password = modelo.password;
        this.userId = modelo.userId;
    }

    public Object clone() {
        AuthenticationUser ret = null;

        try {
            ret = new AuthenticationUser(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
