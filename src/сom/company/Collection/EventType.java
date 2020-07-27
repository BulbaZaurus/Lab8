package сom.company.Collection;

import java.io.Serializable;

/**
 * В данном ENUM находятся все возможные виды EventType
 */
public enum EventType implements Serializable {

    BASEBALL("BASEBALL",200),
    THEATRE_PERFORMANCE("THEATRE_PERFORMANCE",300),
    EXPOSITION("EXPOSITION",100);
    public final String NAME;
    public final int VALUE;

    EventType(String NAME,int VALUE) {
        this.NAME=NAME;
        this.VALUE=VALUE;
    }

    public int getVALUE() {
        return VALUE;
    }



    @Override
    public String toString() {
        return NAME;
    }
}
