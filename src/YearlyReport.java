import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

import java.util.HashMap;

public class YearlyReport {

    boolean yearRepReady = false; // индикатор того, что файл с годовым отчетом считался
    String pathYear = "C:\\Users\\klooo\\IdeaProjects\\second-prodject\\y.2021.csv";
    HashMap<Integer, Double[]> yearlyReportSeparated = new HashMap<>();

    MonthlyReport monthlyReport = new MonthlyReport();

    public boolean isYearRepReady() {
        return yearRepReady;
    }

    public String readFileContentsOrNull(String path) { // "1 - Считать отчёты из файла"
        try {
            return Files.readString(Path.of(pathYear));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

      public HashMap<Integer, Double[]> transformYearRep() {
        String fileContents = readFileContentsOrNull(pathYear);
        String[] s1 = fileContents.split(System.lineSeparator()); // разделение элементов по символу переноса строки
        String[] s2;
        String[] s3;
        for (int i = 1; i < s1.length; i += 2) {
            s2 = s1[i].split(",");
            s3 = s1[i + 1].split(",");
            Double[] s_temp = new Double[2];
            if (!Boolean.parseBoolean(s2[2])) {
                s_temp[0] = Double.valueOf(s2[1]);
                s_temp[1] = Double.valueOf(s3[1]);
            } else {
                s_temp[0] = Double.valueOf(s3[1]);
                s_temp[1] = Double.valueOf(s2[1]);
            }

            yearlyReportSeparated.put(Integer.valueOf(s2[0]), s_temp); // перенос из массива в список
        }
        yearRepReady = true;
        return yearlyReportSeparated;
    }

    void allYearRepInfo () {
        if (yearRepReady) {
            String[] s1 = pathYear.split(".csv"); // отрезаем от имени файла всё до .csv
            char[] year = new char[4];
            s1[0].getChars(s1[0].length() - 4, s1[0].length(), year, 0); // вырезаем 4 последних символа, складываем в ch1
            System.out.println("Данные за год: " + String.valueOf(year) + "\n-----------------");
            Double[] profits_loss = new Double[2];
            profits_loss[0] = 0.0;
            profits_loss[1] = 0.0;
            for (int i = 0; i < monthlyReport.getMonths().size(); i++) {
                Double[] s2 = yearlyReportSeparated.get(i + 1);
                double profit = s2[0] - s2[1];
                System.out.println("Прибыль за " + monthlyReport.getMonths().get(i + 1) + ": " + profit + " рублей");
                profits_loss[0] += s2[0];
                profits_loss[1] += s2[1];

            }
            DecimalFormat df = new DecimalFormat("########.##");
            System.out.println("Средний доход: " + df.format(profits_loss[0] / monthlyReport.getMonths().size()) + " рублей");
            System.out.println("Средний расход: " + df.format(profits_loss[1] / monthlyReport.getMonths().size()) + " рублей\n");
        }
    }
}