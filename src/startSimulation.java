import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class startSimulation {
    ArrayList<ArrayList<Integer>> start() throws InterruptedException, NoSuchIndividuoException {

        Popolazione popolazione = new Popolazione(1000,1000,1000,1000,
                15, 20, 3, 70, 8);
        int iterazioni = 100;

        ArrayList<ArrayList<Integer>> data = new ArrayList<>();
        ArrayList<Integer> nMor = new ArrayList<>(), nPru = new ArrayList<>(), nAvv = new ArrayList<>(), nSpr = new ArrayList<>();

        Maschio maschio = null;
        Femmina femmina = null;

        boolean flag = true;

        for(int i = 0; i < iterazioni; i++){

            for(int j = 0; j < popolazione.getMaschi().size(); j++)
                popolazione.getMaschi().get(j).setEta(1);

            for(int k = 0; k < popolazione.getFemmine().size(); k++)
                popolazione.getFemmine().get(k).setEta(1);


            popolazione.invecchiamento();
            ExecutorService executorService = Executors.newCachedThreadPool();
            long startTime = System.currentTimeMillis();

            while (flag){
                try{
                    maschio = popolazione.estraiMaschio();
                }
                catch (NoSuchIndividuoException e){
                    System.out.println("Sono finiti gli individui di sesso: "+e.sex + "\n");
                    popolazione.info();
                    //return;
                }
                catch (Exception e){
                    e.printStackTrace();
                    //return;
                }

                if(maschio.getPartner() != null) {
                    femmina = maschio.getPartner();
                    popolazione.removeIndividuo(femmina);
                }
                else{
                    try {
                        femmina = popolazione.estraifemmina();
                        if(femmina.getPartner()!=null && femmina.getPartner() != maschio){
                            popolazione.addIndividuo(maschio);
                            popolazione.addIndividuo(femmina);
                            continue;
                        }
                    }
                    catch (NoSuchIndividuoException e){
                        System.out.println("Sono finiti gli individui di sesso: " + e.sex + "\n");
                        popolazione.info();
                        //return;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        //return;
                    }
                }

                executorService.submit(new Accoppiamento(maschio,femmina,popolazione));
                long estimatedTime = System.currentTimeMillis() - startTime;
                if(estimatedTime>100) flag = false;
            }

            executorService.shutdownNow();

            System.out.println("Fine della generazione. Sono rimasti:");
            popolazione.info();
            nMor.add(popolazione.getNumberMorigerati());
            nAvv.add(popolazione.getNumberAvventurieri());
            nPru.add(popolazione.getNumberPrudenti());
            nSpr.add(popolazione.getNumberSpregiudicate());

            System.out.println();

            flag = true;
        }
        data.add(nMor);data.add(nAvv);data.add(nPru);data.add(nSpr);
        return data;
    }
}
