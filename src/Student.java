

class Student extends Object{
    public int numărMatricol;
    public String prenume;
    public String nume;
    public String formațieDeStudiu;

    Student(int numărMatricol, String prenume, String nume, String formațieDeStudiu){
        this.numărMatricol = numărMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formațieDeStudiu = formațieDeStudiu;
    }

    public String toString() {
        return "Student{" +
                "numărMatricol=" + numărMatricol +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", formațieDeStudiu='" + formațieDeStudiu + '\'' +
                '}';
    }
}


