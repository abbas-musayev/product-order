package az.abbas.product.order.exception;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomizedHandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptionMy(Exception ex, WebRequest request){
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message,request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ThrowNotFoundExpHandle.class)
    public final ResponseEntity<Object> notFoundException(ThrowNotFoundExpHandle ex, WebRequest request){
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message,request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ThrowInsufficientBalanceException.class)
    public final ResponseEntity<Object> insufficientBalance(ThrowInsufficientBalanceException ex, WebRequest request){
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message,request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INSUFFICIENT_STORAGE);
    }



}
