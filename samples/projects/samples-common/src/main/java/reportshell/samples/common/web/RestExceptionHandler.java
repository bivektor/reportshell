package reportshell.samples.common.web;

import com.bivektor.reportshell.core.input.binding.InputControlsBindException;
import com.bivektor.reportshell.web.ErrorCodes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @Nullable
  @ExceptionHandler(InputControlsBindException.class)
  public final ResponseEntity<Object> handleControlBindException(InputControlsBindException ex, WebRequest request)
      throws Exception {
    var responseException = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request content.");
    responseException.setType(ErrorCodes.INPUT_CONTROLS_BINDING_ERROR.getTypeUri());
    responseException.getBody().setProperty("errors", toErrorsMap(ex.getBindingResult()));
    return handleException(responseException, request);
  }

  @Nullable
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request
  ) {
    ex.getBody().setProperty("errors", toErrorsMap(ex.getBindingResult()));
    return super.handleMethodArgumentNotValid(ex, headers, status, request);
  }

  protected Map<String, List<String>> toErrorsMap(Errors errors) {
    var messageSource = getMessageSource();
    // Use Map<String, List<String>> to support multiple errors per field
    var errorsMap = new HashMap<String, List<String>>();

    for (ObjectError error : errors.getAllErrors()) {
      // Determine the field name - use empty string for global errors
      String fieldName = "";
      if (error instanceof FieldError) {
        fieldName = ((FieldError) error).getField();
      }

      // Get or create the list of errors for this field
      var errorList = errorsMap.computeIfAbsent(fieldName, k -> new java.util.ArrayList<>());

      // Resolve the error message
      String errorMessage = messageSource != null
          ? messageSource.getMessage(error, java.util.Locale.getDefault())
          : error.getDefaultMessage();

      if (errorMessage == null) {
        errorMessage = "Unknown binding/validation error";
      }

      errorList.add(errorMessage);
    }

    // Convert to JSON
    return errorsMap;
  }
}
