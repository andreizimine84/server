package com.helloworld;


import java.io.IOException;
import java.io.InputStream;

import android.content.Context;


public interface IDataGenerator
{
	InputStream getData(Context context) throws NullPointerException, IOException;
}
