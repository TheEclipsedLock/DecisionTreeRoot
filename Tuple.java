/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.sourcecode;

/**
 *
 * @author Ivan Brenes
 */
public class Tuple {
    //These elements are based off of page 338's table
    private int id;
    private String age;
    private String income;
    private String student;
    private String credit_rating;
    private String likelyToBuyComputer;
    
    //The constructor helps to initialize the variables with actaul values.
    public Tuple(String id, String age, String income, String student, String credit_rating, String likelyToBuyComputer){
        this.id = Integer.parseInt(id);
        this.age = age;
        this.income = income;
        this.student = student;
        this.credit_rating = credit_rating;
        this.likelyToBuyComputer = likelyToBuyComputer;
    }
    
    //The rest are getters for the variables.
    public int getID(){
        return id;
    }
    
    public String getAge(){
        return age;
    }
    
    public String getIncome(){
        return income;
    }
    
    public String getStudent(){
        return student;
    }
    
    public String getCredit(){
        return credit_rating;
    }
    
    public String getLikely(){
        return likelyToBuyComputer;
    }
    
    public String getAll(){
        return id + " " + age + " " + income + " " + student + " " + credit_rating + " " + likelyToBuyComputer;
    }
    
}
