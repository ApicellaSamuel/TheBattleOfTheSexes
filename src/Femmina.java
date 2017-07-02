/**
 * Created by Federico on 22/06/2017.
 */
public abstract class Femmina extends Individuo{

    private volatile Maschio compagno = null;

    public synchronized Maschio getCompagno() {
        return compagno;
    }

    public synchronized void setCompagno(Maschio compagno) {
        this.compagno = compagno;
    }
}
