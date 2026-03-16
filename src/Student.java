

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return prenume.equals(student.prenume) &&
                nume.equals(student.nume) &&
                formațieDeStudiu.equals(student.formațieDeStudiu);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(prenume, nume, formațieDeStudiu);
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


