class NoSuchIndividuoException extends Exception {

    public enum Sesso {
        Maschio,
        Femmina
    }

    Sesso sex;

    NoSuchIndividuoException(String sex){
        if (sex.equals("Maschio")) this.sex=Sesso.Maschio;
        else this.sex=Sesso.Femmina;
    }
}
