package servletunit.struts;

import junit.framework.AssertionFailedError;
import org.apache.struts.action.*;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.DefinitionsUtil;
import org.apache.struts.tiles.NoSuchDefinitionException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;


/**
 * Contains code common to both MockStrutsTestCase and CactusStrutsTestCase.
 * It's always good to get rid of redundancy!
 */
public class Common {

    /**
     * Verifies if the ActionServlet controller sent these error messages.
     * There must be an exact match between the provided error messages, and
     * those sent by the controller, in both name and number.
     *
     * @param request HttpServletRequest used in this test.
     * @param errorNames a String array containing the error message keys
     * to be verified, as defined in the application resource properties
     * file.
     *
     * @exception AssertionFailedError if the ActionServlet controller
     * sent different error messages than those in <code>errorNames</code>
     * after executing an Action object.
     */
    protected static void verifyActionErrors(HttpServletRequest request, String[] errorNames) {
        int actualLength = 0;

        ActionErrors errors = (ActionErrors) request.getAttribute(Action.ERROR_KEY);
        if (errors == null) {
            throw new AssertionFailedError("was expecting some error messages, but received none.");
        } else {
            Iterator iterator = errors.get();
            while (iterator.hasNext()) {
                actualLength++;
                boolean throwError = true;
                ActionError error = (ActionError) iterator.next();
                for (int x = 0; x < errorNames.length; x++) {
                    if (error.getKey().equals(errorNames[x]))
                        throwError = false;
                }
                if (throwError) {
                    throw new AssertionFailedError("received unexpected error \"" + error.getKey() + "\"");
                }
            }
            if (actualLength != errorNames.length) {
                throw new AssertionFailedError("was expecting " + errorNames.length + " error(s), but received " + actualLength + " error(s)");
            }
        }
    }

    /**
     * Verifies that the ActionServlet controller sent no error messages upon
     * executing an Action object.
     *
     * @param request HttpServletRequest used in this test.
     *
     * @exception AssertionFailedError if the ActionServlet controller
     * sent any error messages after excecuting and Action object.
     */
    protected static void verifyNoActionErrors(HttpServletRequest request) {
        ActionErrors errors = (ActionErrors) request.getAttribute(Action.ERROR_KEY);
        if (errors != null) {
            Iterator iterator = errors.get();
            if (iterator.hasNext()) {
                StringBuffer errorText = new StringBuffer();
                while (iterator.hasNext()) {
                    errorText.append(" \"");
                    errorText.append(((ActionError) iterator.next()).getKey());
                    errorText.append("\"");
                }
                throw new AssertionFailedError("was expecting no error messages, but received: " + errorText.toString());
            }
        }
    }

    /**
     * Retrieves a forward uri for tile - this is required for applications
     * using the tiles framework, since the actual forward URI must
     * be fetched from the tile definition.
     */
    protected static String getTilesForward(String forwardPath, HttpServletRequest request, ServletContext context, ServletConfig config) {

        String result = null;
        try {
            ComponentDefinition definition;
            // Get definition of tiles/component corresponding to uri.
            definition = DefinitionsUtil.createDefinitionsFactory(context, config).getDefinition(forwardPath, request, context);
            if( definition != null ) {
                // We have a definition.
                // We use it to complete missing attribute in context.
                // We also get uri, controller.
                result = definition.getPath();
            }
            definition = DefinitionsUtil.getActionDefinition(request);
            if( definition != null ) {
                // We have a definition.
                // We use it to complete missing attribute in context.
                // We also overload uri and controller if set in definition.
                if(definition.getPath()!= null)
                    result = definition.getPath();
            }
            return result;
        } catch (NoSuchDefinitionException nsde) {
            return null;
        } catch (DefinitionsFactoryException dfe) {
            return null;
        } catch (NullPointerException npe) {
            // can happen if tiles is not at all used.
            return null;
        }
    }

    /**
     * Verifies that ActionServlet used this logical forward or input mapping.
     *
     * @throws AssertionFailedError if expected and actual paths do not match.
     */
    protected static void verifyForwardPath(ActionServlet actionServlet, String actionPath, String forwardName, String actualForwardPath, boolean isInputPath, HttpServletRequest request, ServletContext context, ServletConfig config) {
        if ((forwardName == null) && (isInputPath)) {
            forwardName = actionServlet.findMapping(actionPath).getInput();
            if (forwardName == null)
                throw new AssertionFailedError("no input mapping defined!");
        }
        if (!isInputPath) {
            ActionForward expectedForward = actionServlet.findMapping(actionPath).findForward(forwardName);
            if (expectedForward == null)
                expectedForward = actionServlet.findForward(forwardName);
            if (expectedForward == null)
                throw new AssertionFailedError("Cannot find forward '" + forwardName + "'  - it is possible that it is not mapped correctly.");
            forwardName = expectedForward.getPath();

            String tilesForward = getTilesForward(forwardName,request,context,config);
            if (tilesForward != null)
                forwardName = tilesForward;
        }
        forwardName = request.getContextPath() + forwardName;
        if (!forwardName.equals(stripJSessionID(actualForwardPath)))
            throw new AssertionFailedError("was expecting '" + forwardName + "' but received '" + actualForwardPath + "'");
    }

    /**
     * Strips off *.do from action paths specified as such.
     */
    protected static String stripActionPath(String path) {
        if (path == null)
            return null;

        int slash = path.lastIndexOf("/");
        int period = path.lastIndexOf(".");
        if ((period >= 0) && (period > slash))
            path = path.substring(0, period);
        return path;
    }

    /**
     * Strip ;jsessionid=<sessionid> from path.
     * @return stripped path
     */
    protected static String stripJSessionID(String path)
    {
        if (path == null)
            return null;

        int jsess_idx = path.indexOf(";jsessionid=");
        if (jsess_idx > 0)
        {
            // Strip jsessionid from obtained path
            StringBuffer buf = new StringBuffer(path);
            path = buf.delete(jsess_idx, jsess_idx + 44).toString();
        }
        return path;
    }

    /**
     * Returns any ActionForm instance stored in the request or session, if available.
     */
    protected static ActionForm getActionForm(ActionServlet actionServlet, String actionPath, HttpServletRequest request)
    {
        ActionForm form;
        // It's deprecated, I know.  It's a lot of trouble to do it differently for now.
        ActionMapping mapping = actionServlet.findMapping(actionPath);
        System.out.println("looking for this key: " + mapping.getAttribute() + " in this scope: " + mapping.getScope());
        if ("request".equals(mapping.getScope())) {
            form = (ActionForm) request.getAttribute(mapping.getAttribute());
            System.out.println("form = " + form);
        } else {
            HttpSession session = request.getSession();
            form = (ActionForm) session.getAttribute(mapping.getAttribute());
        }
        return form;
    }

}
