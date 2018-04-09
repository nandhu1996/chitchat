package com.example.nandhu.chitchat;

/**
 * Created by NANDHU on 23-09-2017.
 */

public class Userslist {
        private String name,mob,status;
        public Userslist(String name,String mob,String status){
            this.name=name;
            this.mob=mob;
            this.status=status;
        }
        public String getname(){
            return name;
        }
    public String getmob(){
        return mob;
    }
    public String getStatus(){
        return status;
    }

}
