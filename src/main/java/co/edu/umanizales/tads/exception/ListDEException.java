package co.edu.umanizales.tads.exception;

public class ListDEException extends Exception {
    public ListDEException(String message) {
        super(message);
    }
    public static class EmptyListException extends ListDEException {
        public EmptyListException() {
            super("La lista esta vacia.");
        }
    }

    public static class InvalidPositionException extends ListDEException {
        public InvalidPositionException() {
            super("Posicion Invalida.");
        }
    }

    public static class ElementNotFoundException extends ListDEException {
        public ElementNotFoundException() {
            super("No se encontro el elemento.");
        }
    }
}
