package mz.orcamento.backend.exepion;

public class BusinessExepion extends RuntimeException {
    public BusinessExepion(String message) {
        super(message);
    }
}
