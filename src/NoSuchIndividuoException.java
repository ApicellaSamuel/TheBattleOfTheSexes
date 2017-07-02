/**
 * Created by Federico on 01/07/2017.
 */
public class NoSuchIndividuoException extends Exception {

    public enum Sesso {

        Maschio,
        Femmina// opzionalmente può terminare con ";"
    }

    Sesso sex;

    NoSuchIndividuoException(String sex){
        if (sex.equals("Maschio")){

            this.sex=Sesso.Maschio;
        }
        else{

            this.sex=Sesso.Femmina;
        }
    }
}
