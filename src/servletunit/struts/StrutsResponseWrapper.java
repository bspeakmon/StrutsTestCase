package servletunit.struts;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.Locale;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import junit.framework.AssertionFailedError;

public class StrutsResponseWrapper implements HttpServletResponse
{
    
    private HttpServletResponse response;
    private String redirectLocation;
    
    public StrutsResponseWrapper(HttpServletResponse response) {
	this.response = response;
    }

    public void addCookie(Cookie cookie)
    {
	this.response.addCookie(cookie);
    }

    public void addDateHeader(String name, long date)
    {
	this.response.addDateHeader(name,date);
    }
    
    public void addHeader(String name, String value)
    {
	this.response.addHeader(name,value);
    }
    
    public void addIntHeader(String name, int value)
    {
	this.response.addIntHeader(name,value);
    }

    public boolean containsHeader(String name)
    {
	return this.response.containsHeader(name);
    }

    public String encodeRedirectUrl(String url)
    {
	return this.response.encodeRedirectUrl(url);
    }

    public String encodeRedirectURL(String url)
    {
	return this.response.encodeRedirectURL(url);
    }

    public String encodeUrl(String url)
    {
	return this.response.encodeUrl(url);
    }

    public String encodeURL(String url)
    {
	return this.response.encodeURL(url);
    }

    public void flushBuffer() throws IOException
    {
	this.response.flushBuffer();
    }
    
    public int getBufferSize()
    {
	return this.response.getBufferSize();
    }
    
    public String getCharacterEncoding()
    {
	return this.response.getCharacterEncoding();
    }
    
    public Locale getLocale()
    {
	return this.response.getLocale();
    }
    
    public ServletOutputStream getOutputStream() throws IOException
    {
	return this.response.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException
    {
	return this.response.getWriter();
    }

    public boolean isCommitted()
    {
	return this.response.isCommitted();
    }

    public void reset()
    {
	this.response.reset();
    }

    public void resetBuffer()
    {
	this.response.resetBuffer();
    }

    public void sendError(int sc) throws IOException
    {
	this.response.sendError(sc);
    }

    public void sendError(int sc, String msg) throws IOException
    {
	this.response.sendError(sc,msg);
    }

    public void sendRedirect(String location) throws IOException
    {
	this.redirectLocation = location;
	this.response.sendRedirect(location);
    }

    public void setBufferSize(int size)
    {
	this.response.setBufferSize(size);
    }

    public void setContentLength(int len)
    {
	this.response.setContentLength(len);
    }

    public void setContentType(String type)
    {
	this.response.setContentType(type);
    }

    public void setDateHeader(String name, long date)
    {
	this.response.setDateHeader(name,date);
    }

    public void setHeader(String name, String value)
    {
	this.response.setHeader(name,value);
    }

    public void setIntHeader(String name, int value)
    {
	this.response.setIntHeader(name,value);
    }
    
    public void setStatus(int sc)
    {
	this.response.setStatus(sc);
    }

    public void setStatus(int sc, String sm)
    {
	this.response.setStatus(sc,sm);
    }

    public void setLocale(Locale loc)
    {
	this.response.setLocale(loc);
    }

    public String getRedirectLocation() {
	return this.redirectLocation;
    }

}

