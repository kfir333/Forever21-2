package server.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.enums.OperationType;
import common.controllers.enums.ReturnMessageType;
import common.entity.Book;
import common.entity.BorrowCopy;
import common.entity.Copy;
import common.entity.Subscriber;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
/**
 * The ManageStockController class represent the manage stock controller on the server side
 * @author  Kfir Wilfand
 * @author Bar Korkos
 * @author Zehavit Otmazgin
 * @author Noam Drori
 * @author Sapir Hochma
 */
public class ManageStockController {
	/**instance is a singleton of the class */
	private static ManageStockController instance;

	private ManageStockController(){}
	/**
	 * getInstance is creating the singleton object of the class
	 */
	public static ManageStockController getInstance(){
		if(instance == null){
			instance = new ManageStockController();
		}
		return instance;
	}
	/**
	 * addNewBook is adding a new book to the inventory
	 * @param msg contains the message from the client 
	 * @throws SQLException when occurs
	 * @return Message to the client
	 */
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

	/**
	 * getCopiesbyCatalogNumber is getting the copies by using catalog number
	 * @param msg contains the message from the client 
	 * @throws SQLException when occurs
	 * @return Message to the client
	 */
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

	/**
	 * addNewCopy is adding a new copy to the inventory
	 * @param msg contains the message from the client 
	 * @throws SQLException when occurs
	 * @return Message to the client
	 */
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
	/**
	 * deleteCopy is deleting a copy from the inventory
	 * @param msg contains the message from the client 
	 * @throws SQLException when occurs
	 * @throw IOException
	 * @return Message to the client
	 */
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
	/**
	 * updateBookDetails is updating details about book
	 * @param msg contains the message from the client 
	 * @throws SQLException when occurs
	 * @return Message to the client
	 */
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


	/**
	 * getCopyById is returning copy by his id
	 * @param copyID contains the id of the copy 
	 * @throws SQLException when occurs
	 * @return Copy details to the client
	 */
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
	/**
	 * getBookByCatalogNumber is returning book by his catalog number
	 * @param catalogNumber contains the book catalog number
	 * @throws SQLException when occurs
	 * @return book details to the client
	 */
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

	/**
	 * getBookOrderQueue is returning queue of subscriber for specific book
	 * @param bookCatalogNamber contains the book catalog number
	 * @throws SQLException when occurs
	 * @return Queue<Subscriber> 
	 */
	public static Queue<Subscriber> getBookOrderQueue(int bookCatalogNamber) throws SQLException
	{
		Queue<Subscriber> orderQueue= new  ArrayDeque<Subscriber>();
		String query="Select boSubNum from obl.books_orders where boCatalogNum="+bookCatalogNamber+" order by dateOfOrder";
		DBcontroller dbControllerObj=DBcontroller.getInstance();
		ResultSet rs = dbControllerObj.query(query);
		while(rs.next())
		{
			Subscriber sub=SubscriberController.getSubscriberById(rs.getString("boSubNum"));
			orderQueue.add(sub);
		}
		return orderQueue;
	}
	/**
	 * getBorrowCopyByCopyID is returning a borrow copy by his copy id
	 * @param bookCatalogNamber contains the book catalog number
	 * @throws SQLException when occurs
	 * @return BorrowCopy 
	 */
	public static BorrowCopy getBorrowCopyByCopyID(String copyID) throws SQLException
	{
		BorrowCopy borrow=null;
		String query="select copyID, subNum, actualReturnDate, borrowDate, returnDueDate from obl.borrows where copyID='"+copyID+"' and actualReturnDate IS NULL";
		System.out.println(query);
		DBcontroller dbControllerObj=DBcontroller.getInstance();
		ResultSet rs = dbControllerObj.query(query);
		if(rs.next())
			borrow=new BorrowCopy(rs.getString("copyID"),rs.getInt("subNum"),rs.getDate("borrowDate"), rs.getDate("returnDueDate"));
		return borrow ;
	}

}

