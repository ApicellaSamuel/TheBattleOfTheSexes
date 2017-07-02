import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Federico on 21/06/2017.
 *
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, NoSuchIndividuoException {

        Popolazione pop = new Popolazione(1000,1000,1000,1000, 15, 20, 3, 70, 8);
        int iterazioni = 100000000;

        Maschio maschio;
        Femmina femmina;

        boolean flag = true;

        int a = pop.getA(); int b = pop.getB(); int c = pop.getC();

        for(int i = 0; i < iterazioni; i++){

            for(int j = 0; j < pop.getMaschi().size(); j++)
                pop.getMaschi().get(j).setEta(1);


            for(int k = 0; k < pop.getFemmine().size(); k++)
                pop.getFemmine().get(k).setEta(1);


            pop.invecchiamento();
            ExecutorService executorService = Executors.newCachedThreadPool();
            long startTime = System.currentTimeMillis();

            while (flag){
                try{
                    maschio = pop.estraiMaschio();
                }
                catch (NoSuchIndividuoException e){
                    System.out.println("Sono finiti gli individui di sesso: "+e.sex + "\n");
                    pop.info();
                    return;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return;
                }


                if(maschio.getCompagna() != null) {
                    femmina = maschio.getCompagna();
                    pop.removeIndividuo(femmina);

                }
                else{
                    try {
                        femmina = pop.estraifemmina();
                        if(femmina.getCompagno()!=null && femmina.getCompagno() != maschio){
                            pop.addIndividuo(maschio);
                            pop.addIndividuo(femmina);
                            continue;
                        }
                    }
                    catch (NoSuchIndividuoException e){
                        System.out.println("Sono finiti gli individui di sesso: " + e.sex + "\n");
                        pop.info();
                        return;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                }

                executorService.submit(new Accoppiamento(maschio,femmina,pop));
                long estimatedTime = System.currentTimeMillis() - startTime;
                if(estimatedTime>3000) flag = false;
                //pop.info();
            }

            //pop.invecchiamento();

            executorService.shutdownNow();


            double ratioM_Male = ((double)pop.getNumberMorigerati()/pop.getMaschi().size());
            double ratioP_Female = ((double) pop.getNumberPrudenti()/pop.getFemmine().size());

            double Va_M = ratioP_Female*(a-(b/2.0)-c)+(1-ratioP_Female)*(a-(b/2.0));
            double Va_A = a*(1-ratioP_Female);

            double Va_P = ratioM_Male*(a-(b/2.0)-c);
            double Va_S = ratioM_Male*(a-(b/2.0))+(1-ratioM_Male)*(a-(b));


            System.out.println("Fine della generazione. Sono rimasti:");
            pop.info();

            System.out.println("Valori attesi:");
            System.out.println("VA_m: "+Va_M);
            System.out.println("VA_a: "+Va_A);
            System.out.println("VA_p: "+Va_P);
            System.out.println("VA_s: "+Va_S);
            System.out.println();

            flag = true;
        }
    }
}
