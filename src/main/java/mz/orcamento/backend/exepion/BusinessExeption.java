package mz.orcamento.backend.exepion;

public class BusinessExeption extends RuntimeException {
    public BusinessExeption(String message) {
        super(message);
    }
}
