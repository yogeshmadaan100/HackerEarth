package com.example.hackerearthassignment;

import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import android.util.Log;

public class DataController
{
  private String json;
  private ObjectMapper objectMapper = null;
  private JsonFactory jsonFactory = null;
  private JsonParser jp = null;
  private ArrayList<DataModel> recipes=new ArrayList<DataModel>();
  private Datas mRecipes = null;

  public DataController(String json)
    {
	  this.json=json;
      objectMapper = new ObjectMapper();
      jsonFactory = new JsonFactory();
    }
public void init()
{
	try{
		JsonFactory jsonfactory = new JsonFactory();
 	 
     //input file
   

     JsonParser jsonParser = jsonfactory.createJsonParser(json);
    
     // Begin the parsing procedure
     while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

         String token = jsonParser.getCurrentName();

        
     

             System.out.println("names :");

              //the next token will be '[' that means that we have an array
              jsonParser.nextToken();

              // parse tokens until you find  ']'
            DataModel dataModel=new DataModel();
              while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

            	  String name ,link;
             if(jsonParser.getText().equalsIgnoreCase("status"))
             {
            	 dataModel=new DataModel();
                 
            	 jsonParser.nextToken();
            	 //ystem.out.println("status as"+jsonParser.getText());
            	 dataModel.setStatus(jsonParser.getText());
            	 name=jsonParser.getText();
             }
             if(jsonParser.getText().equalsIgnoreCase("challenge type"))
             {
            	 jsonParser.nextToken();
            	 //System.out.println("challenge as"+jsonParser.getText());
            	 link=jsonParser.getText();
            	 
            	 
            	 dataModel.setChallenge_type(jsonParser.getText());
            	 recipes.add(dataModel);
            	 //Log.e("item", "added");
             }
             if(jsonParser.getText().equalsIgnoreCase("description"))
             {
            	 jsonParser.nextToken();
            	 //System.out.println("description as"+jsonParser.getText());
            	 dataModel.setDescription(jsonParser.getText());
            	 name=jsonParser.getText();
             }
             if(jsonParser.getText().equalsIgnoreCase("title"))
             {
            	 jsonParser.nextToken();
            	// System.out.println("title as"+jsonParser.getText());
            	 dataModel.setTitle(jsonParser.getText());
            	 name=jsonParser.getText();
             }
             if(jsonParser.getText().equalsIgnoreCase("url"))
             {
            	 jsonParser.nextToken();
            	 System.out.println("url as"+jsonParser.getText());
            	 dataModel.setUrl(jsonParser.getText());
            	 name=jsonParser.getText();
            	 recipes.add(dataModel);
             }
             
             
              }
              

          
     }
	}catch(Exception e)
	{
		
	}
}
  

  public ArrayList<DataModel> findAll()
    {
	 Log.e("recipes length find all", ""+recipes.size());
      return recipes;
    }

  public DataModel findById(int id)
    {
      return recipes.get(id);
    }
}
