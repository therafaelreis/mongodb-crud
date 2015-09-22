package test;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class CRUD {
	
	static MongoClient mongodb = new MongoClient("localhost", 27017);

	public static MongoCollection<Document> getConnection(String collectionName)throws UnknownHostException{
		MongoDatabase database = mongodb.getDatabase("dbtest");
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}  
	
	public static void insert() throws UnknownHostException{
		MongoCollection<Document> collection =  CRUD.getConnection("products");		
		Document document = new Document();
		document.put("description", "apples");
		document.put("amount", 13);
		document.put("active", true);
		
		collection.insertOne(document);
		System.out.println("Product " + document.get("description") +" persisted with success");
	}
	
	public static void findAll() throws UnknownHostException{
		MongoCollection<Document> collection =  CRUD.getConnection("products");
		MongoCursor<Document> cursor = collection.find().iterator();

		int i=1;
        while (cursor.hasNext()) { 
           System.out.println("Inserted Document: "+i); 
           System.out.println(cursor.next()); 
           i++;
        }	
	}
	
	public static void findByIsActive(boolean active) throws UnknownHostException{
		MongoCollection<Document> collection =  CRUD.getConnection("products");
		BasicDBObject query = new BasicDBObject("active", active);
		MongoCursor<Document> cursor = collection.find(query).iterator();
		
		int i=1;
        while (cursor.hasNext()) { 
           System.out.println("Product: "+i); 
           System.out.println(cursor.next()); 
           i++;
        }
	}
	
	public static void update(Integer newValue) throws UnknownHostException{
		MongoCollection<Document> collection =  CRUD.getConnection("products");
		BasicDBObject newDocument = new BasicDBObject().append("$set", new BasicDBObject("amount", newValue));
		
		BasicDBObject searchQuery = new BasicDBObject("active", true);
		collection.updateOne(searchQuery, newDocument);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		update(300);
		findAll();
		mongodb.close();
		
		
	}
}
