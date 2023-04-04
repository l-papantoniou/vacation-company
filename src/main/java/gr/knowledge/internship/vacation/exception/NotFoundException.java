package gr.knowledge.internship.vacation.exception;

public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 6769829250639411991L;

    public NotFoundException(String message){super(message);}
}
