package com.amcef.svancarek.testovaciezadanie.postservice.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CustomGlobalExceptionHandlerTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private CustomGlobalExceptionHandler exceptionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleValidationException() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Test violation message");
        violations.add(violation);

        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test violation message", response.getBody().get("error"));
    }

    @Test
    public void testHandleTypeMismatchException() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(null, null, "field", null, null);
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleTypeMismatchException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input: null is not a valid number", response.getBody().get("error"));
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleMethodArgumentNotValidException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("defaultMessage", response.getBody().get("field"));
    }

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Test illegal argument");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgumentException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test illegal argument", response.getBody().get("error"));
    }

    @Test
    public void testHandleApiCommunicationException() {
        ApiCommunicationException exception = new ApiCommunicationException("Test API communication error");
        doNothing().when(logger).error(anyString(), any(Throwable.class));

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleApiCommunicationException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("API communication error: Test API communication error", response.getBody().get("error"));
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Test exception");
        doNothing().when(logger).error(anyString(), any(Throwable.class));

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGenericException(exception);
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred.", response.getBody().get("error"));
    }
}
