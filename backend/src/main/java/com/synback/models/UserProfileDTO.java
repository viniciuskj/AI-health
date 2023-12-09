package com.synback.models;

public class UserProfileDTO {
    private String userId;
    private String name;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Id: " + userId + '\n' +
                "Name: " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        UserProfileDTO user = (UserProfileDTO) obj;

        if (user.userId != this.userId ||
                user.name != this.name)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + userId.hashCode();
        result = 7 * result + name.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private UserProfileDTO(UserProfileDTO modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.userId = modelo.userId;
        this.name = modelo.name;
    }

    public Object clone() {
        UserProfileDTO ret = null;

        try {
            ret = new UserProfileDTO(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
