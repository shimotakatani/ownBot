package spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * create time 22.02.2018
 *
 * @author nponosov
 */
@JsonIgnoreProperties
public class MessageDto {

    public String message = "";
    public Long chatId = 0L;
    public String mapString = "";
    public String timeString = "";

    public MessageDto(){

    }

    public MessageDto(String message, Long chatId){
        this.message = message;
        this.chatId = chatId;
    }

    @Override
    public String toString(){
        return this.message + " " + this.chatId;
    }
}