package com.helloworld;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DefaultDataGenerator implements IDataGenerator
{

	public int doSomething(int y) {
		return 0;
	}

	public String doSomething2(int x) {
		return null;
	}

	public int doSomething3(int z) throws Exception {
		return 0;
	}

	public InputStream getData() {
		try {
			return new FileInputStream("Koder");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}

// Att skapa Android paket
// AndroidGenerator ist�llet f�r FileInputStream
// Felhantering
// Fil
// Skapa en Stream ut av data f�lten
// En app av Clienten som fungerar p� datorn.
