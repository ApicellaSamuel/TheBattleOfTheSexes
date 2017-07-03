abstract class Femmina extends Individuo{

    private volatile Maschio partner = null;

    synchronized Maschio getPartner(){
        return partner;
    }

    synchronized void setPartner(Maschio partner) {
        this.partner = partner;
    }
}
