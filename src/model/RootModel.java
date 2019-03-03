package model;
import java.io.*;

public class RootModel extends Viewable implements Serializable{
	private User user;
	private String userName;
	
	public User user(){
		return user;
	}
	
	public RootModel(){
	}
	
	public void setFile(String userName) throws IOException{
		try{
			this.userName = userName + ".txt";
	        get(this.userName);
	        System.out.println("file successfully read"); //TODO remove this
	        update();
	    }
	    catch(IOException e){
	        System.out.println("Read file not closed");
	    }
	}
	
	private void get(String userName) throws IOException{
	    ObjectInputStream in = null;
	    try{ 
	        FileInputStream inFile = new FileInputStream(userName);
	        in = new ObjectInputStream(inFile);
	        user = (User) in.readObject();//cast the data from the input file to the user class
	        System.out.println("Read from file");
	    }
	    catch(FileNotFoundException e){
	        //create a new object if there was no file to read from
	        user = new User();
	    }
	    catch(IOException e){
	        System.out.println(e.toString());
	    }   
	    catch(ClassNotFoundException e){
	        System.out.println(e.toString());
	    }
	    finally{ //if the file exists then close it
	        if (in != null)
	            in.close();
	    }
	}
	
	public void store() throws IOException{
	    ObjectOutputStream out = null;
	    //will create the file only if it does not exist
	    File f = new File(this.userName);
	    f.createNewFile();
	    try{ 
	        FileOutputStream outFile = new FileOutputStream(userName);
	        out = new ObjectOutputStream(outFile);
	        out.writeObject(user);
	        System.out.println("Stored to file");
	    }
	    catch(IOException e){
	        System.out.println(e.toString());
	    }
	    finally{
	        if (out != null)
	            out.close();
	    }
	}	
}