package com.zespolowka;

public class User {
    private String name;
    private long id;
    private String password;
 
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
      return password;
  }
 public void setPassword(String tpass){
      password=tpass;
  }
}
