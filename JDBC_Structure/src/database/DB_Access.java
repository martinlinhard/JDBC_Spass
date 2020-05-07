/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class DB_Access {

    private static DB_Access db = null; //theInstance
    private DB_Database realDB;
    
    public static DB_Access getDB(){
        if(db == null) {
            DB_Access.db = new DB_Access();
        }
        return db;
    }

    private DB_Access() {
        try {
            this.realDB = new DB_Database();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DB_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void test() throws SQLException {
        Statement s = this.realDB.getStatement();
        this.realDB.returnStatement(s);
    }

}
