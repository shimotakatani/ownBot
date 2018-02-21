package spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.services.TelegrammService;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
@RestController
@RequestMapping("/api")
public class SimpleRestController {
    public static final Logger LOG = LoggerFactory.getLogger(SimpleRestController.class);

    @Autowired
    TelegrammService telegrammService;

    @RequestMapping(value = "/test_send_message", method = RequestMethod.GET)
    public String test(@RequestParam(value = "chatId") Long chatId, @RequestParam(value = "message", defaultValue = "") String message){
        telegrammService.sendMessage(message,chatId);
        return "OK";
    }
}
