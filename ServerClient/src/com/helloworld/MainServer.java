
package com.helloworld;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.ByteArrayOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EmptyStackException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

// Serializable

// This is a comment
public class MainServer extends AbstractHandler
{
	FileOutputStream fos = null;
	ByteArrayOutputStream baos = null;
	InputStream is = null;
	
	int bytesRead = -1;
	int bytesAvailable = -1;
	byte[] buffer = null;
	
	public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Andrei Zimine</h1>");

        is = request.getInputStream();
        bytesAvailable = is.available();

        try
        {
        	
            buffer = new byte[bytesAvailable];
            
            baos = new ByteArrayOutputStream();
            
            bytesRead = is.read(buffer);
        	
        	if(bytesRead != -1 && bytesAvailable != 0)
        		fos = new FileOutputStream("output_" + request.hashCode() + ".txt");       	
        	else if (bytesAvailable == 0)
            	bytesRead = -1;
        	else
            	throw new EmptyStackException();

        	while (bytesRead > -1) 
        	{
        		baos.write(buffer);
        		baos.writeTo(fos);
        		bytesRead = is.read(buffer);
        	}
        }
        
    	catch(IOException e)
    	{
    		System.err.println("Caught IOException: " + e.getMessage());
    	}

        finally 
        {
        	if(fos != null)
        		fos.close();
        }
        
    }
	
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new MainServer());
        server.start();
        server.join();
    }
}