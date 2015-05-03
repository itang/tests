package demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // protected Logger getLogger(){
    // return this.logger;
    // }
}
