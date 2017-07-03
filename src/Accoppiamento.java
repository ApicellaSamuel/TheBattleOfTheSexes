import java.util.*;

public class Accoppiamento extends Thread {

    private volatile Maschio maschio;
    private volatile Femmina femmina;
    private volatile Popolazione popolazione;

    volatile private double payoffMorPru;
    volatile private double payoffMorSpr;
    volatile private double payoffAvvSpr_spr;
    volatile private double payoffAvvSpr_avv;

    Accoppiamento(Maschio maschio, Femmina femmina, Popolazione popolazione){
        this.maschio = maschio;
        this.femmina = femmina;
        this.popolazione = popolazione;
        this.payoffMorPru = popolazione.getA()-(popolazione.getB()/2.0)-popolazione.getC();
        this.payoffMorSpr = popolazione.getA()-(popolazione.getB()/2.0);
        this.payoffAvvSpr_spr = popolazione.getA()-(popolazione.getB());
        this.payoffAvvSpr_avv = popolazione.getA();

    }

    synchronized private int nFigli(String coppia , boolean maschio){

        // il numero di figli è proporzionato al proprio payoff, randomizzato
        // in modo da tendere al valore atteso stimato in base al guadagno

        Random random = new Random();

        double max = popolazione.getMaxFigli();

        ArrayList<Double> values = new ArrayList<>();

        values.add(payoffMorPru);
        values.add(payoffMorSpr);
        values.add(payoffAvvSpr_spr);
        values.add(payoffAvvSpr_avv);

        double maxE = payoffMorPru;

        for(Double i : values)
            if(i > maxE)
                maxE = i;

        double x = 0;

        switch (coppia) {
            case "MorigeratoPrudente": {
                x = payoffMorPru;
                break;
            }
            case "MorigeratoSpregiudicata": {
                x = payoffMorSpr;
                break;
            }
            case "AvventurieroSpregiudicata": {
                if(maschio) x = payoffAvvSpr_avv;
                else x = payoffAvvSpr_spr;
                break;
            }
        }

        x = (max*x)/maxE;//numero figli = massimo numero figli * numero di figli / maxFigliTotale

        if(x<=0) return 0;
        //per evitare la virgola
        int intX = (int) x;
        if(random.nextInt(100) <= ( (int)(x - intX) * 100) )
            intX++;

        return intX;
    }

    @Override
    public void run() {
        double a = popolazione.getA(); double b = popolazione.getB(); double c = popolazione.getC();


        double ratioM_Male = ((double)popolazione.getNumberMorigerati()/popolazione.getMaschi().size());
        double ratioP_Female = ((double)popolazione.getNumberPrudenti()/popolazione.getFemmine().size());

        double Va_M = ratioP_Female*(a-(b/2.0)-c)+(1-ratioP_Female)*(a-(b/2.0));
        double Va_A = a*(1-ratioP_Female);//premio_figlio * rapporto_spr/femmine

        double Va_P = ratioM_Male*(a-(b/2.0)-c);
        double Va_S = ratioM_Male*(a-(b/2.0))+(1-ratioM_Male)*(a-b);

        // cambio della strategia di accoppiamento in base al guadagno stimato

        if(Va_M > Va_A){
            int età = maschio.getEtà();
            Femmina compagna = maschio.getPartner();
            maschio = new Morigerato();
            maschio.setEta(età);
            maschio.setPartner(compagna);
        }else{
            int età = maschio.getEtà();
            maschio = new Avventuriero();
            maschio.setEta(età);
            if(maschio.getPartner() != null)
                maschio.getPartner().setPartner(null);

            maschio.setPartner(null);
        }

        if(Va_P > Va_S){
            int età = femmina.getEtà();
            Maschio compagno = femmina.getPartner();
            femmina = new Prudente();
            femmina.setEta(età);
            femmina.setPartner(compagno);
        }else{
            int età = femmina.getEtà();
            Maschio compagno = femmina.getPartner();
            femmina = new Spregiudicata();
            femmina.setEta(età);
            femmina.setPartner(compagno);
        }

        String m, f;
        m = maschio.chisono();
        f = femmina.chisono();

        Random random = new Random();

        String concat = m+f;

        switch (concat){
            case "MorigeratoPrudente":{

                    int n = nFigli(concat, true);
                    for(int i = 0; i< n; i++)
                        if(random.nextBoolean())
                            popolazione.addIndividuo(new Morigerato());
                        else popolazione.addIndividuo(new Prudente());


                maschio.setPartner(femmina);
                femmina.setPartner(maschio);

                popolazione.addIndividuo(maschio);
                popolazione.addIndividuo(femmina);
                break;
            }

            case "MorigeratoSpregiudicata":{

                    int n = nFigli(concat, true);
                    for(int i = 0; i < n; i++)
                        if(random.nextBoolean())
                            popolazione.addIndividuo(new Morigerato());
                        else popolazione.addIndividuo(new Spregiudicata());

                maschio.setPartner(femmina);
                femmina.setPartner(maschio);

                popolazione.addIndividuo(maschio);
                popolazione.addIndividuo(femmina);
                break;
            }

            case "AvventurieroPrudente": {
                popolazione.addIndividuo(maschio);
                popolazione.addIndividuo(femmina);
                break;
            }

            case "AvventurieroSpregiudicata":{
                if(random.nextBoolean()){
                    int n = nFigli(concat, true);
                    for(int i = 0; i < n; i++)
                        popolazione.addIndividuo(new Avventuriero());
                }else{
                    int n = nFigli(concat, false);
                    for(int i = 0; i < n; i++)
                        popolazione.addIndividuo(new Spregiudicata());
                }

                popolazione.addIndividuo(maschio);
                popolazione.addIndividuo(femmina);

                break;
            }
        }
    }
}



