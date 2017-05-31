package kalah;

import java.util.List;

/**
 * Created by bcoll on 31/05/2017.
 */
public interface PlayerPrinter {
    Object getStorePrinter();
    List<? extends Object> getHousePrinters();
}
