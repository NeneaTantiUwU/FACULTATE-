package Lab7;

public class PasswordMaker {
    private static final int MAGIC_NUMBER = 3;
    private static final String MAGIC_STRING;

    private static String name;

    private static PasswordMaker instance;
    private static int accessCount = 0;

    static {
        StringRandomizer srand = new StringRandomizer();
        MAGIC_STRING = srand.randomString(20);
    }

    public static PasswordMaker getInstance() {

        accessCount++;

        if (instance == null) { // Prima verificare (fără lock pentru performanță)
            synchronized (PasswordMaker.class) {
                if (instance == null) { // A doua verificare (cu lock)
                    instance = new PasswordMaker(name);
                }
            }
        }
        return instance;
    }

    public PasswordMaker(String name) {
        this.name = name;
    }

    public static int getAccessCount() {
        return accessCount;
    }

    public static String getCallingCounts() {
        return "";
    }


    public String getPassword() {
        java.util.Random r = new java.util.Random();
        String ln = ""+name.length();
        ln += r.nextInt(101);
        StringRandomizer srand = new StringRandomizer();
        return srand.randomString(MAGIC_NUMBER) + srand.randomString(10, MAGIC_STRING) + ln;
    }
}

