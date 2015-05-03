package demo.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String redirect(final String path) {
        return "redirect:" + path;
    }
}
