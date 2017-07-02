/**
 * Created by Federico on 26/06/2017.
 */
public class Test {
    public static void main(String[] args) throws Exception
    {
        Popolazione pop = new Popolazione(1,0,1,0, 15, 20, 3, 50, 4);

        Maschio m ;
        Femmina f;


        m = pop.estraiMaschio();
        f = pop.estraifemmina();

        Thread acc = new Accoppiamento(m, f, pop);

        acc.run();

        acc.interrupt();

        for (Individuo i : pop.getFemmine()){
            System.out.println(i.getEtà());
        }
    }

}
