//  StrutsTestCase - a JUnit extension for testing Struts actions
//  within the context of the ActionServlet.
//  Copyright (C) 2001 Deryl Seale
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
//  this address: Deryl Seale - deryl@acm.org

package servletunit.struts.tests;

import servletunit.struts.MockStrutsTestCase;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import examples.LoginForm;

public class TestGetActionForm extends MockStrutsTestCase {

    public TestGetActionForm(String testName) {
        super(testName);
    }

    public void testSuccessfulLogin() {

        addRequestParameter("username","deryl");
        addRequestParameter("password","radar");
        setRequestPathInfo("/login");
        actionPerform();
        verifyForward("success");
        verifyForwardPath("/main/success.jsp");
        assertEquals("deryl",getSession().getAttribute("authentication"));
        verifyNoActionErrors();
        ActionForm form = getActionForm();
        assertNotNull(form);
        assertEquals(((LoginForm) form).getUsername(),"deryl");
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestGetActionForm.class);
    }


}
