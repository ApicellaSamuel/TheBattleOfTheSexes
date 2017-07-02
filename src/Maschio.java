/**
 * Created by Federico on 22/06/2017.
 *
 */
public abstract class Maschio extends Individuo {

    private volatile Femmina compagna = null;

    public synchronized Femmina getCompagna() {
        return compagna;
    }

    synchronized void setCompagna(Femmina compagna) {
        this.compagna = compagna;
    }


}
