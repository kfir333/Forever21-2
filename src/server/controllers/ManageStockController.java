package server.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.enums.OperationType;
import common.controllers.enums.ReturnMessageType;
import common.entity.Book;
import common.entity.Copy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ManageStockController {
	private static ManageStockController instance;

	 private ManageStockController(){}
	 
	 public static ManageStockController getInstance(){
	        if(instance == null){
	            instance = new ManageStockController();
	        }
	        return instance;
	    }
	 
	 public Message addNewBook(Object msg) throws SQLException 
	 {
		 String query=(String)((Message)msg).getObj();
		 DBcontroller dbControllerObj=DBcontroller.getInstance();
	   	 Boolean res = dbControllerObj.insert(query);
	   	 if(res)
	   	 {
	   		return new Message(OperationType.AddNewBook, null , ReturnMessageType.Successful); 
	   	 }
	   	 else return new Message(OperationType.AddNewBook, null , ReturnMessageType.Unsuccessful); 
	 }
	 
	 public Message getCopiesbyCatalogNumber(Object msg) throws SQLException 
	 {
		 List<Copy> copies_list= new ArrayList<Copy>();
		 
		 String query=(String)((Message)msg).getObj();
		 DBcontroller dbControllerObj=DBcontroller.getInstance();
		 ResultSet result = dbControllerObj.query(query);
		 if(result != null) 
		 {
			 while(result.next()) 
			 {
				 copies_list.add(new Copy(result.getString("copyID"),result.getInt("bCatalogNum"),result.getBoolean("isAvilable")));	 
			 }
			 return new Message(OperationType.GetCopiesOfSelectedBook, copies_list , ReturnMessageType.Successful); 
		 }
		 return new Message(OperationType.GetCopiesOfSelectedBook, copies_list , ReturnMessageType.Unsuccessful); 
	 }
	 

	 /**
	    * This method update Subscriber details by Librarian
	    * @param Message
	    * Message.getObj() return String[3]
	    * 
	    * String[0] = subId
	    * String[1] = query 1 to update user table
	    * String[2] = query 2 to update subscriber table
	    * 
	    * @return if Successful: Message with updated Subscriber Object
	    * @author kfir3
	    */ 
	public Message editDetailsByLibrarian(Object msg) throws SQLException {
		String[] params =  (String[]) ((Message)msg).getObj();
		DBcontroller dbControllerObj=DBcontroller.getInstance();
	
		Boolean res0 = dbControllerObj.update(params[1]);
		Boolean res1 = dbControllerObj.update(params[2]);
		
		if(res0 && res1) 
			 return new Message(OperationType.EditDetailsByLibrarian, SubscriberController.getSubscriberById(params[0]) , ReturnMessageType.Successful); 

		 return new Message(OperationType.EditDetailsByLibrarian, null , ReturnMessageType.Unsuccessful); 
	}
	 
	public Message addNewCopy(Object msg) throws SQLException 
	 {
		 	Object[] m=(Object[])((Message)msg).getObj();
	    	DBcontroller dbControllerObj=DBcontroller.getInstance();
	       	Boolean res1=dbControllerObj.insert((String)m[0]);
	    	Boolean res2=dbControllerObj.update((String)m[1]);	
	    	
	    	if(res1 && res2)
    			return new Message(OperationType.AddNewCopy, (Copy)m[2] , ReturnMessageType.Successful);
    		else
    			return new Message(OperationType.AddNewCopy, null , ReturnMessageType.Unsuccessful);
	 }
	 
	 public Message deleteCopy(Object msg) throws SQLException, IOException 
	 {
		 	String[] query=(String[])((Message)msg).getObj();
	    	DBcontroller dbControllerObj=DBcontroller.getInstance();
	       	Boolean res1=dbControllerObj.insert(query[0]);
	    	Boolean res2=dbControllerObj.update(query[1]);	
	    	

	    	if(res1 && res2)
    			return new Message(OperationType.DeleteCopy, null , ReturnMessageType.Successful);
    		else
    			return new Message(OperationType.DeleteCopy, null , ReturnMessageType.Unsuccessful);
	    	
	 }
	 
	 public Message updateBookDetails(Object msg) throws SQLException 
	 {
		 String query=(String)((Message)msg).getObj();
		 DBcontroller dbControllerObj=DBcontroller.getInstance();
	   	 Boolean res = dbControllerObj.update(query);
	   	 if(res)
	   	 {
	   		return new Message(OperationType.UpdateBookDetails, null , ReturnMessageType.Successful); 
	   	 }
	   	 else return new Message(OperationType.UpdateBookDetails, null , ReturnMessageType.Unsuccessful);  
	 }
	 
	 
	 
	  public static Copy getCopyById(String copyID) throws SQLException
	  {
		 String query= "select * from obl.copeis where copyID= '"+copyID+"'";
		 DBcontroller dbControllerObj=DBcontroller.getInstance();
	   	 ResultSet res = dbControllerObj.query(query);
	   	 if(res.next()) 
	   	 {
	   		 Copy copy= new Copy(res.getString("copyID"),res.getInt("bCatalogNum"),res.getBoolean("isAvilable"));
	   		 return copy;
	   	 }
	   	 return null;
	   	 
	  }
		 
	  public static Book getBookByCatalogNumber(int catalogNumber) throws SQLException
	  {
		 String query= "select * from obl.books where bCatalogNum= '"+catalogNumber+"'";
		 DBcontroller dbControllerObj=DBcontroller.getInstance();
	   	 ResultSet rs = dbControllerObj.query(query);
	   	 if(rs.next()) 
	   	 {
				List<String> authors= Arrays.asList(rs.getString("bAuthor").split(","));
    			List<String> genres= Arrays.asList(rs.getString("bGenre").split(","));
    			Book book=new Book(rs.getInt("bCatalogNum"), rs.getString("bName"),  rs.getString("bDescription"), 
    					authors, genres, rs.getInt("bCopiesNum"), rs.getDate("bPurchaseDate"), 
    					rs.getString("bShelfLocation"), rs.getString("bEdition"), rs.getDate("bPrintDate"), rs.getBoolean("bIsPopular"),rs.getInt("bAvilableCopiesNum"));
	   	 return book;
	   	 }
	   	 return null;
	   	 
	  }
}

