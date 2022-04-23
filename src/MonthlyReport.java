import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MonthlyReport {
    String pathMonths = "";
    HashMap<String, ArrayList<String>> monthRep = new HashMap<>();
    HashMap<Integer, String> months;
    ArrayList<String> linesSeparation;
    String[] fileContents = new String[3];
    boolean monthRepReady = false; // индикатор того, что файлы с месячным отчетом считались
    double[][] profitsLossAllMonths = new double[3][2];// общие доходы и расходы за каждый месяц
    HashMap<String, ArrayList<String>> allItemsForYear = new HashMap<>();
    HashMap<String, ArrayList<Boolean>> allExpsForYear = new HashMap<>();
    HashMap<String, ArrayList<Double>> allQuantsForYear = new HashMap<>();
    HashMap<String, ArrayList<Integer>> allSumsForYear = new HashMap<>();

    ArrayList<String> mostProfitableItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostProfitsByMonth = new ArrayList<>();
    ArrayList<String> mostLosingItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostLossByMonth = new ArrayList<>();


    public double[][] getProfitsLossAllMonths() {
        return profitsLossAllMonths;
    }

    public String readFileContentsOrNull(String path) { // "1 - Считать все месячные отчёты"
        try {
            return Files.readString(Path.of(pathMonths));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public HashMap<String, ArrayList<String>> splitFile() {

        for (int i = 1; i <= 3; i++) {
            pathMonths = "C:\\Users\\klooo\\IdeaProjects\\second-prodject\\m.20210";
            pathMonths += i + ".csv";
            fileContents[i - 1] = readFileContentsOrNull(pathMonths);
        }

        String[] line;
        for (int i = 0; i < fileContents.length; i++) {
            String[] lines = fileContents[i].split(System.lineSeparator());
            linesSeparation = new ArrayList<>();
            for (int j = 1; j < lines.length; j++) {
                line = lines[j].split(",");
                linesSeparation.addAll(Arrays.asList(line));
            }
            monthRep.put(months.get(i + 1), linesSeparation);
        }
        monthRepReady = true;
        return monthRep;
    }

    public MonthlyReport() {
        months = new HashMap<>();
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
    }

    public HashMap<Integer, String> getMonths() {
        return months;
    }

    public boolean isMonthRepReady() {
        return monthRepReady;
    }

    public void getMaxSumAndItem() {

        for (int i = 0; i < months.size(); i++) {
                 ArrayList<String> all_items = new ArrayList<>();
                 ArrayList<Boolean> all_exps = new ArrayList<>();
                 ArrayList<Double> all_quants = new ArrayList<>();
                 ArrayList<Integer> all_sums = new ArrayList<>();
                 ArrayList<String> m_rep = splitFile().get(months.get(i + 1));

                for (int k = 0; k < m_rep.size(); k += 4) {
                    all_items.add(m_rep.get(k));
                    all_exps.add(Boolean.valueOf(m_rep.get(k + 1)));
                    all_quants.add(Double.valueOf(m_rep.get(k + 2)));
                    all_sums.add(Integer.valueOf(m_rep.get(k + 3)));
                }


        int max_index = 0; // индекс ячейки с максимальным доходом
        int max_exp_index = 0; // индекс ячейки с максимальной тратой
        double max_quant_sum = 0; // максимальный доход
        double max_exp = 0; // максимальная трата
                 double profitMonth = 0; //общий доход за месяц
                 double lossMonth = 0; //общий расход за месяц
        for (int j = 0; j < all_items.size(); j++) {
            if (!all_exps.get(j)) {
                if (all_quants.get(j) * all_sums.get(j) > max_quant_sum) {
                    max_quant_sum = all_quants.get(j) * all_sums.get(j);
                    max_index = j;
                    }
                profitMonth += all_quants.get(j) * all_sums.get(j);
            } else {
                if (all_quants.get(j) * all_sums.get(j) > max_exp) {
                    max_exp = all_quants.get(j) * all_sums.get(j);
                    max_exp_index = j;
                }
                lossMonth += all_quants.get(j) * all_sums.get(j);
            }
            }

            mostProfitableItemsByMonth.add(all_items.get(max_index));
            mostProfitsByMonth.add(max_quant_sum);
            mostLosingItemsByMonth.add(all_items.get(max_exp_index));
            mostLossByMonth.add(max_exp);
            profitsLossAllMonths[i][0] = profitMonth;
            profitsLossAllMonths[i][1] = lossMonth;
            allItemsForYear.put(months.get(i + 1), all_items);
            allExpsForYear.put(months.get(i + 1), all_exps);
            allQuantsForYear.put(months.get(i + 1), all_quants);
            allSumsForYear.put(months.get(i + 1), all_sums);
        }
    }

    void printAllInfo()
    {
        for (int i = 0; i < months.size(); i++)
        {
            System.out.println("Данные за: " + months.get(i + 1));
            System.out.println("Самый прибыльный товар: " + mostProfitableItemsByMonth.get(i) + " - " + mostProfitsByMonth.get(i) + " рублей"); // здесь выводит не те суммы
            System.out.println("Самая большая трата: " + mostLosingItemsByMonth.get(i) + " - " + mostLossByMonth.get(i) + " рублей");
            System.out.println("-----------------");
        }
    }
}




