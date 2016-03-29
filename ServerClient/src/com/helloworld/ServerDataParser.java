package com.helloworld;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ServerDataParser {

	private static char ASCII_SOT = '\002';
	private static char ASCII_SOH = '\001';
	private static char ASCII_TE = '\003';
	private static char ASCII_US = '\037';
	private static char ASCII_RS = '\030';
	private static char ASCII_EOT = '\004';
	
	
	// input buffer (inputstream) - output block (string)
	public String getNextBlock(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int singleChar = 0;
		String value = null;

		while((singleChar = input.read()) != -1){
			value = String.valueOf((char)singleChar);
			baos.write(value.getBytes());
			if(singleChar == ASCII_SOT)
				break;
			if(singleChar == ASCII_EOT)
				return baos.toString();
		}
		if(singleChar == -1)
			return null;
		
		return baos.toString();
	}
	
	// Parse title from block - input block (string) - output title (string)
	public String getTitleFromBlock(String block) throws IOException {
		StringReader reader = new StringReader(block);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int singleChar = 0; 
		String stringValueOf = null;
		while((singleChar = reader.read()) != -1){
			stringValueOf = String.valueOf((char)singleChar);
			baos.write(stringValueOf.getBytes());
			if(singleChar == ASCII_TE){
				return baos.toString().trim();
			}
		}
		return "";
	}
	
	// Get all key/values from block
	public LinkedHashMap<String,String> getKeyValues(String block) throws IOException {
		LinkedHashMap<String, String> keyValueFinal = new LinkedHashMap<String, String>();

		Iterator<String> keyValue2Iterator = getKeys(block).iterator();
		Iterator<String> keyValueIterator = getValues(block).iterator();
		while(keyValue2Iterator.hasNext() && keyValueIterator.hasNext()){
			keyValueFinal.put(keyValue2Iterator.next(), keyValueIterator.next());
		}
		
		return keyValueFinal;	
	}
	
	public List<String> getKeys(String block) throws IOException{
		StringReader reader = new StringReader(block);
		int singleChar = 0;
		String value = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String> keyValue = new ArrayList<String>();
		while((singleChar = reader.read()) != -1){	
			value = String.valueOf((char)singleChar);
			baos.write(value.getBytes()); 
			if(singleChar == ASCII_TE){
				baos.reset();
			}
			if(singleChar == ASCII_US){
				keyValue.add(baos.toString());
				baos.reset();
				while((singleChar = reader.read()) != -1){
					value = String.valueOf((char)singleChar);
					baos.write(value.getBytes());
					if(singleChar == ASCII_RS){	
						baos.reset();
						break;
					}
				}
			}
		}
		return keyValue;
	}
	
	public List<String> getValues(String block) throws IOException{
		StringReader reader = new StringReader(block);
		int singleChar = 0;
		String value = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String> keyValue = new ArrayList<String>();
		while((singleChar = reader.read()) != -1){
			value = String.valueOf((char)singleChar);
			baos.write(value.getBytes());
			if(singleChar == ASCII_US){
				baos.reset();
				while((singleChar = reader.read()) != -1){
					value = String.valueOf((char)singleChar);
					baos.write(value.getBytes());
					if(singleChar == ASCII_RS){
						keyValue.add(baos.toString());
						baos.reset();
						break;
					}
				}
			}
		}
		return keyValue;
	}
	
	public static void main(String argv[]) throws IOException {
		// put file in buffer
		String fileName = argv[0];
		InputStream is = new FileInputStream(fileName);
		//markedStream = is;
		ServerDataParser sdp = new ServerDataParser();
		// get blocks from buffer
		String block = sdp.getNextBlock(is);
		while (block != null) {
			String title = sdp.getTitleFromBlock(block);
			//if (title.equals(argv[1])) {
				sdp.printBlock(block);
			//}
			
			block = sdp.getNextBlock(is);
		}
	}

	private void printBlock(String block) throws IOException {
		String title = getTitleFromBlock(block);
		System.out.println("Title: " + title);
		LinkedHashMap<String,String> keyValues = getKeyValues(block);
		for (Entry<String,String> entry : keyValues.entrySet()) {
			System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
		}
	}
}
