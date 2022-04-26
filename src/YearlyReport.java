import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

import java.util.HashMap;

public class YearlyReport {

    boolean yearRepReady = false; // индикатор того, что файл с годовым отчетом считался
    String pathYear = "./resources/y.2021.csv";
    HashMap<Integer, Double[]> yearlyReportSeparated = new HashMap<>();

    Months months = new Months();
    ReadFileContentsOrNull readFileContentsOrNull = new ReadFileContentsOrNull();

    public boolean isYearRepReady() {
        return yearRepReady;
    }

    public HashMap<Integer, Double[]> transformYearRep() {
        if (readFileContentsOrNull.readFileContentsOrNull(pathYear) == null) {
            System.out.println("Данные отсутсвуют");
        } else {
            String fileContents = readFileContentsOrNull.readFileContentsOrNull(pathYear);
            String[] s1 = fileContents.split(System.lineSeparator()); // разделение элементов по символу переноса строки
            String[] s2;
            String[] s3;
            for (int i = 1; i < s1.length; i += 2) {
                s2 = s1[i].split(",");
                s3 = s1[i + 1].split(",");
                Double[] sTemp = new Double[2];
                if (!Boolean.parseBoolean(s2[2])) {
                    sTemp[0] = Double.valueOf(s2[1]);
                    sTemp[1] = Double.valueOf(s3[1]);
                } else {
                    sTemp[0] = Double.valueOf(s3[1]);
                    sTemp[1] = Double.valueOf(s2[1]);
                }
                yearlyReportSeparated.put(Integer.valueOf(s2[0]), sTemp); // перенос из массива в список
            }
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
                Double[] profitsLoss = new Double[2];
                profitsLoss[0] = 0.0;
                profitsLoss[1] = 0.0;
                for (int i = 0; i < months.getMonths().size(); i++) {
                    Double[] s2 = yearlyReportSeparated.get(i + 1);
                    double profit = s2[0] - s2[1];
                    System.out.println("Прибыль за " + months.getMonths().get(i + 1) + ": " + profit + " рублей");
                    profitsLoss[0] += s2[0];
                    profitsLoss[1] += s2[1];

                }
                DecimalFormat df = new DecimalFormat("########.##");
                System.out.println("Средний доход: " + df.format(profitsLoss[0] / months.getMonths().size()) + " рублей");
                System.out.println("Средний расход: " + df.format(profitsLoss[1] / months.getMonths().size()) + " рублей\n");
            }
        }
    }