package com.helloworld;
/**
 * 
 * @author andreizimine
 * Används för att läsa av flaggor för t. ex systemfel eller andra variabelelement som klasser behöver för att 
 * få ett direkt värde. Alternativt användning inför framtida utveckling är Intent,s som gör samma arbete.
 *
 */
public class TempFolder
{
	static String folderUrl;
	static String nameOfFile;
	static int mAddNumber = 0;
	static int mAddBackNumber = 0;
	static boolean connectException = false;
	static boolean connectRWException = false;
	static boolean connectTemp = false;
	private static TempFolder instance = null;
	
    public static TempFolder getInstance() {
        if (instance == null) {
            instance = new TempFolder();
        }
        return instance;
    }

	public static void setRWConnectException(boolean value){
		connectRWException = value;
	}
	
	public static boolean getRWConnectException(){
		return connectRWException;
	}
    
    public static void setAddBackNumber(int addBackNumber){
    	mAddBackNumber = addBackNumber;
    }
    
    public static int getAddBackNumber(){
    	return mAddBackNumber;
    }
    
    public static void setAddNumber(int addNumber){
    	mAddNumber = addNumber;
    }
    
    public static int getAddNumber(){
    	return mAddNumber;
    }
    
	public static void setConnectException(boolean value){
		connectException = value;
	}
	
	public static boolean getConnectException(){
		return connectException;
	}

	public static String getFolder(){
		return folderUrl;
	}
	public static void setFolder(String newUrl){
		folderUrl = newUrl;
	}
	public static String getFile(){
		return nameOfFile;
	}
	
	public static void setFile(String newFile){
		nameOfFile = newFile;
	}
}
