import java.util.ArrayList;
import java.util.HashMap;

public class CompareReports {

    YearlyReport yearlyReport = new YearlyReport();
    MonthlyReport monthlyReport = new MonthlyReport();

    ArrayList <String> comparisonMYReports() {

        monthlyReport.getMaxSumAndItem();
        HashMap<Integer, Double[]> yearReportData = yearlyReport.transformYearRep();
        ArrayList<String> monthsWithErrors = new ArrayList<>();
        for (int i = 0; i < monthlyReport.getMonths().size(); i++) {

            if ((!yearReportData.get(i + 1)[0].equals(monthlyReport.getProfitsLossAllMonths()[i][0])) || (!yearReportData.get(i + 1)[1].equals(monthlyReport.getProfitsLossAllMonths()[i][1])))
            {
                monthsWithErrors.add(monthlyReport.getMonths().get(i + 1));
            }
                  }
        return monthsWithErrors;
    }
}
