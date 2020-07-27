package сom.company.Collection;

import java.io.Serializable;

/**
 * В данном ENUM находятся все возможные виды TicketType
 */
public enum TicketType implements Serializable {
    VIP("VIP"),
    USUAL("USUAL"),
    BUDGETARY("BUDGETARY"),
    CHEAP("CHEAP");

    public final String NAMETIcket;

    TicketType(String NAMETIcket) {
        this.NAMETIcket=NAMETIcket;
    }
    @Override
    public String toString(){
        return NAMETIcket;
    }
}
