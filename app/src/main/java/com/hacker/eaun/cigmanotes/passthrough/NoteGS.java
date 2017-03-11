package com.hacker.eaun.cigmanotes.passthrough;


/**
 * Created by Eaun-Ballinger on 31/08/2016.
 *  Getter and Setter for My Notes
 */
public class NoteGS {

    private String _TITLE,_NOTE;
    private int _ID;
    // Empty constructor
    public NoteGS(){}


    // Send and Receive

    public int getID() {
        return this._ID;
    }

    public void setID(int ID){
        this._ID = ID;
    }

    public String getTITLE() {
        return this._TITLE;
    }

    public void setTITLE(String TITLE){
        this._TITLE = TITLE;
    }

    public String getNOTE() {
        return this._NOTE;
    }

    public void setNOTE(String NOTE){
        this._NOTE = NOTE;
    }

}
