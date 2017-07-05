package com.automatic.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Set;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {
	/**
	 * 全局异常返回类
	 * @author liaoke
	 *
	 */
	public static class GApiResult{
		private long timestamp;
		private Integer status;
		private String error;
		private String message;
		public String getError() {
			return error;
		}
		public String getMessage() {
			return message;
		}
		public Integer getStatus() {
			return status;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setError(String error) {
			this.error = error;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
	}
	
	
	
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GApiResult handle(ConstraintViolationException exception) {
        StringBuffer errorMessage = new StringBuffer();
        
        Set<ConstraintViolation<?>> set =  exception.getConstraintViolations();
        for (ConstraintViolation<?> cv : set) {
            String msg = cv.getMessage();
            Path path = cv.getPropertyPath();
            Object value = cv.getInvalidValue();
            errorMessage.append(String.format("Parameter %s (actual:%s, expect:%s) | ", path, value, msg));
        }

        GApiResult ret = new GApiResult();
        ret.setError("ConstraintViolation Exception");
        ret.setMessage(errorMessage.toString().substring(0,errorMessage.toString().length()-3));
        ret.setStatus(400);
        ret.setTimestamp(System.currentTimeMillis());
        return ret;
    }
	
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GApiResult handle(MethodArgumentNotValidException exception) {
        GApiResult ret = new GApiResult();
        ret.setError("ConstraintViolation Exception");
        ret.setStatus(400);
        ret.setTimestamp(System.currentTimeMillis());
        ret.setMessage( exception.getMessage());
        return ret;
    }
	
	
}
