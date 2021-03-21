package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class CreateEmailInputRsDTO {

    private int statusCode;
    private boolean error;
    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }
    public boolean getError() {
        return error;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setStatusCode( int statusCode ) {
        this.statusCode = statusCode;
    }
    public void setError( boolean error ) {
        this.error = error;
    }
    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }

}
