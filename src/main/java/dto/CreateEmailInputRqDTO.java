package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class CreateEmailInputRqDTO {

    private String to;
    private String from;
    private String subject;
    private String bodyText;
    
    public void setTo( String to ) {
        this.to = to;
    }

    public void setFrom( String from ) {
        this.from = from;
    }
    
    public void setSubject( String subject ) {
        this.subject = subject;
    }

    public void setBodyText( String bodyText ) {
        this.bodyText = bodyText;
    }
    
    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBodyText() {
        return bodyText;
    }


}
