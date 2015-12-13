package com.zespolowka;

public class User {
    private String name;
    private long id;
    private String Password;
    private String ConfPassword;
 
 public String getName(){
      return name;
  }
 public  void setName(String tname){
      name=tname;
  }
  public long getId(){
      return id;
  }
 public void setId(long tid){
      id=tid;
  }
 public String getPassword(){
      return Password;
  }
 public void setPassword(String tpass){
      Password=tpass;
  }
 public void setConfPassword(String tpass){
     ConfPassword=tpass;
 }
 public String getConfPassword(){
     return ConfPassword;
 }
}
