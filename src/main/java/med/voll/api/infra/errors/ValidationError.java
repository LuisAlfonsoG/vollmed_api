package med.voll.api.infra.errors;

public class ValidationError extends RuntimeException {
    public ValidationError(String string) {
        super(string);
    }
}
