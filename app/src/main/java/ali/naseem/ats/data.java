package ali.naseem.ats;

public class data {

    public String nrc,email,number,chalan;

    public data(String nrc, String email, String number,String chalan) {
        this.nrc = nrc;
        this.email = email;
        this.number = number;
        this.chalan = chalan;
    }

    public String getNrc() {
        return nrc;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getChalan() {
        return chalan;
    }

    public data(){

    }
}
