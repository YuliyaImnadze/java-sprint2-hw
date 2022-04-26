import java.util.HashMap;

public class Months {
    HashMap<Integer, String> months;

    public Months() {
        months = new HashMap<>();
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
    }

    public HashMap<Integer, String> getMonths() {
        return months;
    }
}
