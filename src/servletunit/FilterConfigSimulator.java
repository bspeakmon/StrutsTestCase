//  FilterConfigSimulator - A mock object for the servlet 2.3 FilterConfig
//  Interface.
//  Copyright (C) 2002 Sean Pritchard
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//
//  You may view the full text here: http://www.gnu.org/copyleft/lesser.txt
//
//  If you have any questions or suggestions, you may contact me at
//  this address: Sean Pritchard smpritchard@yahoo.com

package servletunit;

import javax.servlet.FilterConfig;
import java.util.Enumeration;
import javax.servlet.ServletContext;

/**
 *  A unit test tool simulating the the FilerConfig <p>
 *
 * @author     Sean Pritchard
 * @created    May 11, 2002
 * @version    1.0
 */

public class FilterConfigSimulator implements FilterConfig {

    private ServletContext context = null;


    /**
     *  Constructor for the FilterConfigSimulator object
     *
     * @param  context  The ServletContext to be returned by getServletContext
     */
    public FilterConfigSimulator(ServletContext context) {
        this.context = context;
    }


    /**
     *  Gets the filterName attribute of the FilterConfigSimulator object
     *
     * currently not supported
     *
     * @return    The filterName value
     */
    public String getFilterName() {

        throw new java.lang.UnsupportedOperationException("Method getFilterName() not yet implemented.");
    }


    /**
     *  Gets the initParameter attribute of the FilterConfigSimulator object
     *
     * currently not supported
     *
     * @param  parm1  Description of the Parameter
     * @return        The initParameter value
     */
    public String getInitParameter(String parm1) {

        throw new java.lang.UnsupportedOperationException("Method getInitParameter() not yet implemented.");
    }


    /**
     *  Gets the initParameterNames attribute of the FilterConfigSimulator
     *  object
     *
     * currently not supported
     *
     * @return    The initParameterNames value
     */
    public Enumeration getInitParameterNames() {

        throw new java.lang.UnsupportedOperationException("Method getInitParameterNames() not yet implemented.");
    }


    /**
     *  Gets the servletContext attribute of the FilterConfigSimulator object
     *
     * @return    The servletContext value
     */
    public ServletContext getServletContext() {
        return context;
    }
}