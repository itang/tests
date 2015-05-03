package demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String redirect(String path) {
        return "redirect:" + path;
    }
}
