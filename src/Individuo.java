public abstract class Individuo {

    private volatile int età = 0;

    synchronized int getEtà(){
        return età;
    }

    synchronized void setEta(int età){
        this.età += età;
    }

    public abstract String chisono();

}
