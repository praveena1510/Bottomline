package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("all")
public class CreateEmailInputDTO {

	private int useCase;
    private String description;
    private CreateEmailInputRqDTO rq;
    private CreateEmailInputRsDTO rs;
    
    public CreateEmailInputRsDTO getRs() {
        return rs;
    }

    public CreateEmailInputRqDTO getRq() { return rq; }

    public String getDescription() {
        return description;
    }

    public int getUseCase() { return useCase; }

    // Setter Methods

    public void setRs( CreateEmailInputRsDTO rs ) {
        this.rs = rs;
    }

    public void setRq( CreateEmailInputRqDTO rq ) {
        this.rq = rq;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public void setUseCase( int rq ) {
        this.useCase = useCase;
    }

}
