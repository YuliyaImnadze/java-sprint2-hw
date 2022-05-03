import java.util.ArrayList;
import java.util.HashMap;

public class CompareReports {

    Months months = new Months();

    ArrayList<String> comparisonMYReports(HashMap<Integer, Double[]> monthReportData, HashMap<Integer, Double[]> yearReportData) {
        ArrayList<String> monthsWithErrors = new ArrayList<>();
        for (int i = 0; i < months.getMonths().size(); i++) {
            if (!(yearReportData.get(i + 1)[0].equals(monthReportData.get(i + 1)[0])) || !(yearReportData.get(i + 1)[1].equals(monthReportData.get(i + 1)[1]))) {
                monthsWithErrors.add(months.getMonths().get(i + 1));
            }
        }
        return monthsWithErrors;
    }
}
