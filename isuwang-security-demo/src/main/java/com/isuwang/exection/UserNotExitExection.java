package com.isuwang.exection;

public class UserNotExitExection extends RuntimeException {

    private String id;

    public UserNotExitExection(String id){
        super("user not exit!");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
