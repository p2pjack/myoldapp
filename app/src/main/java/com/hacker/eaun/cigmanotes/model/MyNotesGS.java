package com.hacker.eaun.cigmanotes.model;

/**
 * Created by Eaun-Ballinger on 23/08/2016.
 *Create an object to pass data to the database and adapters
 */
public class MyNotesGS {

    private String _id,_action,_title,_message;
    private String _Code,_Supplier,_Planner,_PhoneNumber,_Type,_Country,_Parts;

public MyNotesGS(){

}


    public String getid() {
        return this._id;
    }

    public void setid(String id){
        this._id = id;
    }

    // getting action
    public String getAction(){
        return this._action;
    }

    // setting action
    public void setAction(String action){
        this._action = action;
    }

    // getting title
    public String getTitle(){
        return this._title;
    }

    // setting title
    public void setTitle(String title){
        this._title = title;
    }

    // getting message
    public String getMessage(){
        return this._message;
    }

    // setting message
    public void setMessage(String message){
        this._message = message;
    }

    // getting code
    public String getCode(){
        return this._Code;
    }

    // setting code
    public void setCode(String Code){
        this._Code = Code;
    }

    // getting Supplier
    public String getSupplier(){
        return this._Supplier;
    }

    // setting Supplier
    public void setSupplier(String Supplier){
        this._Supplier = Supplier;
    }

    // getting Planner
    public String getPlaner(){
        return this._Planner;
    }

    // setting vPlanner
    public void setPlanner(String Planner){
        this._Planner = Planner;
    }

    // getting Phone
    public String getPhone(){
        return this._PhoneNumber;
    }

    // setting Phone
    public void setPhone(String PhoneNumber){
        this._PhoneNumber = PhoneNumber;
    }

    // getting Type
    public String getType(){
        return this._Type;
    }

    // setting Type
    public void setType(String Type){
        this._Type = Type;
    }

    // getting Phone
    public String getCountry(){
        return this._Country;
    }

    // setting Phone
    public void setCountry(String Country){
        this._Country = Country;
    }

    // getting Parts
    public String getParts(){
        return this._Parts;
    }

    // setting Parts
    public void setParts(String Parts){
        this._Parts = Parts;
    }
}
