import java.util.*;

class Popolazione {
    private int a,b,c;
    private ArrayList<Maschio> maschi = new ArrayList<>();
    private ArrayList<Femmina> femmine = new ArrayList<>();
    volatile private int etaMorte;
    volatile private int maxFigli;

    Popolazione(int morigerati, int avventurieri, int prudenti, int spregiudicate, int a, int b, int c, int etaMorte, int maxNumeroFigli) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.etaMorte = etaMorte;
        this.maxFigli = maxNumeroFigli;

        for(int i = 0; i < morigerati; i++)
            maschi.add(new Morigerato());

        for(int i = 0; i < avventurieri; i++)
            maschi.add(new Avventuriero());

        for(int i = 0; i < prudenti; i++)
            femmine.add(new Prudente());

        for(int i = 0; i < spregiudicate; i++)
            femmine.add(new Spregiudicata());
    }

    synchronized int getNumberMorigerati() {
        ArrayList<Integer> mor= new ArrayList<>();
        this.getMaschi().forEach((m)-> {
            if (m.chisono().equals("Morigerato"))
                mor.add(1);
        });
        return mor.size();
    }


    synchronized int getNumberAvventurieri() {
        ArrayList<Integer> avv= new ArrayList<>();
        this.getMaschi().forEach((m)-> {
            if (m.chisono().equals("Avventuriero")) {
                avv.add(1);
            }
        });
        return avv.size();
    }


    synchronized int getNumberPrudenti() {
        ArrayList<Integer> pru = new ArrayList<>();
        this.getFemmine().forEach((f)-> {
            if (f.chisono().equals("Prudente")) {
                pru.add(1);
            }
        });
        return pru.size();
    }


    synchronized int getNumberSpregiudicate() {
        ArrayList<Integer> spr= new ArrayList<>();
        this.getFemmine().forEach((f)-> {
            if (f.chisono().equals("Spregiudicata")){
                spr.add(1);
            }
        });
        return spr.size();
    }

    synchronized int getA() {
        return this.a;
    }

    synchronized int getB() {
        return this.b;
    }

    synchronized int getC() {
        return this.c;
    }

    synchronized ArrayList<Maschio> getMaschi() {
        return this.maschi;
    }

    synchronized ArrayList<Femmina> getFemmine() {
        return this.femmine;
    }

    synchronized int getEtaMorte(){return this.etaMorte;}

    synchronized int getMaxFigli(){return this.maxFigli;}


    synchronized void addIndividuo(Maschio maschio){
        this.maschi.add(maschio);
    }
    synchronized void addIndividuo(Femmina femmina){
        this.femmine.add(femmina);
    }

    synchronized void removeIndividuo(Maschio maschio){
        this.maschi.remove(maschio);
    }
    synchronized void removeIndividuo(Femmina femmina){
        this.femmine.remove(femmina);
    }


    synchronized Maschio estraiMaschio() throws NoSuchIndividuoException{
        if (this.getMaschi().size() == 0) throw new NoSuchIndividuoException("Maschio");

        Random random = new Random();
        return this.maschi.remove(random.nextInt(maschi.size()));
    }

    synchronized Femmina estraifemmina() throws NoSuchIndividuoException {
        if (this.getFemmine().size() == 0) throw new NoSuchIndividuoException("Femmina");

        Random random = new Random();
        return this.femmine.remove(random.nextInt(femmine.size()));

    }


    synchronized void invecchiamento(){

        int c = 0;

        Random random = new Random();

        for (int i = 0; i < this.getFemmine().size(); i++){

            int epsilon = random.nextInt(this.getEtaMorte()/4);//muore nell'intorno di un quarto[60/100]

            int soglia = (random.nextBoolean())?this.getEtaMorte()+epsilon:this.getEtaMorte()-epsilon;

            if (this.getFemmine().get(i).getEtà() >= soglia){
                if(this.getFemmine().get(i).getPartner() != null)
                    this.getFemmine().get(i).getPartner().setPartner(null);

                this.removeIndividuo(this.getFemmine().get(i));
                i--;
                c++;
            }
        }

        for (int i = 0; i < this.getMaschi().size(); i++) {

            int epsilon = random.nextInt(this.getEtaMorte()/4);
            int soglia = (random.nextBoolean())?this.getEtaMorte()+epsilon:this.getEtaMorte()-epsilon;

            if(this.getMaschi().get(i).getEtà() >= soglia){
                if(this.getMaschi().get(i).getPartner() != null)
                    this.getMaschi().get(i).getPartner().setPartner(null);

                this.removeIndividuo(this.getMaschi().get(i));
                i--;
                c++;
            }
        }
        System.out.println("Sono morti in: " + c);
    }

    synchronized void info(){

        float nMaschi = this.getMaschi().size();
        float nFemmine = this.getFemmine().size();

        System.out.println("Numero morigerati: " + this.getNumberMorigerati() + "; Numero avventurieri: " + this.getNumberAvventurieri());
        System.out.println("Numero prudenti: " + this.getNumberPrudenti()+ "; Numero spregiudicate: " + this.getNumberSpregiudicate());
        System.out.println("Numero Femmine: "+this.getFemmine().size()+" Numero Maschi: "+ this.getMaschi().size());
        System.out.println("I morigerati sono il "+(this.getNumberMorigerati()/nMaschi)*100+"% della popolazione Maschile.");
        System.out.println("Le prudenti sono il "+(this.getNumberPrudenti()/nFemmine)*100+"% della popolazione Femminile.");
        System.out.println("Totale individui: "+(int)(nFemmine+nMaschi));
        System.out.println();
    }
}
