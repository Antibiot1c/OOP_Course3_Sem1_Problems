package clientserver;

import java.io.Serializable;

public class MyObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;

    public MyObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
