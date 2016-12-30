/**
 * 
 */
package io.jbatista.springauth.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Processes errors responses
 * 
 * @see org.springframework.boot.autoconfigure.web.BasicErrorController
 * 
 * @author jbatista
 */
@Controller
@RequestMapping(path = "${server.error.path:${error.path:/error}}", produces = "application/json")
public class RestErrorController extends BasicErrorController {

    /**
     * Constructor.
     * 
     * @param errorAttributes
     *            Provides access to error attributes which can be logged or
     *            presented to the user.
     */
    public RestErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    /**
     * Overrides the default behavior to return only "message" attribute.
     * 
     * @param request
     *            HTTP request.
     * @return response mapping.
     */
    @RequestMapping(produces = "application/json")
    @ResponseBody
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> attributes = getErrorAttributes(request, false);
        HttpStatus status = getStatus(request);
        String message = (String) attributes.get("message");
        return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("message2", message), status);
    }
}
