package dto;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@SuppressWarnings("all")
public class TokenResponseDTO {

	@JsonProperty("access_token")
    private String acccessToken;
    
    private String scope;
    
    @JsonProperty("token_type")
    private String tokenType;
    
    @JsonProperty("expires_in")
    private int expiresIn;
    
    public void setScope( String scope ) {
        this.scope = scope;
    }

    public void setAccessToken( String acccessToken ) {
        this.acccessToken = acccessToken;
    }
    
    public void setTokenType( String tokenType ) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn( int expiresIn ) {
        this.expiresIn = expiresIn;
    }
    
    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getAcccessToken() {
        return acccessToken;
    }


}
