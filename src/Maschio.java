public abstract class Maschio extends Individuo {

    private volatile Femmina partner = null;

    public synchronized Femmina getPartner(){
        return partner;
    }

    synchronized void setPartner(Femmina partner) {
        this.partner = partner;
    }

}
