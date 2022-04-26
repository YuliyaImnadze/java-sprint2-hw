import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MonthlyReport {
    String pathMonths = "";
    HashMap<String, ArrayList<String>> monthRep = new HashMap<>();
    ArrayList<String> linesSeparation;
    String[] fileContents = new String[3];
    boolean monthRepReady = false; // индикатор того, что файлы с месячным отчетом считались
    double[][] profitsLossAllMonths = new double[3][2];// общие доходы и расходы за каждый месяц
    HashMap<String, ArrayList<String>> allItemsForYear = new HashMap<>();
    HashMap<String, ArrayList<Boolean>> allExpsForYear = new HashMap<>();
    HashMap<String, ArrayList<Double>> allQuantsForYear = new HashMap<>();
    HashMap<String, ArrayList<Integer>> allSumsForYear = new HashMap<>();

    Months months = new Months();
    ReadFileContentsOrNull readFileContentsOrNull = new ReadFileContentsOrNull();

    ArrayList<String> mostProfitableItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostProfitsByMonth = new ArrayList<>();
    ArrayList<String> mostLosingItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostLossByMonth = new ArrayList<>();


    public double[][] getProfitsLossAllMonths() {
        return profitsLossAllMonths;
    }


    public HashMap<String, ArrayList<String>> splitFile() {

        for (int i = 1; i <= 3; i++) {
            pathMonths = "./resources/m.20210";
            pathMonths += i + ".csv";
            if (readFileContentsOrNull.readFileContentsOrNull(pathMonths) == null) {
                System.out.println("Данные за " + months.getMonths().get(i) + " отсутсвуют");
                System.exit (1);
            } else {
            }
            fileContents[i - 1] = readFileContentsOrNull.readFileContentsOrNull(pathMonths);

        }

        String[] line;
        for (int i = 0; i < fileContents.length; i++) {
            String[] lines = fileContents[i].split(System.lineSeparator());
            linesSeparation = new ArrayList<>();
            for (int j = 1; j < lines.length; j++) {
                line = lines[j].split(",");
                linesSeparation.addAll(Arrays.asList(line));
            }
            monthRep.put(String.valueOf((i + 1)), linesSeparation);
        }
        monthRepReady = true;
        return monthRep;
    }

    public boolean isMonthRepReady() {
        return monthRepReady;
    }

    public void getMaxSumAndItem() {

        for (int i = 0; i < months.getMonths().size(); i++) {
            ArrayList<String> allItems = new ArrayList<>();
            ArrayList<Boolean> allExps = new ArrayList<>();
            ArrayList<Double> allQuants = new ArrayList<>();
            ArrayList<Integer> allSums = new ArrayList<>();
            ArrayList<String> mRep = splitFile().get(String.valueOf((i + 1)));

            for (int k = 0; k < mRep.size(); k += 4) {
                allItems.add(mRep.get(k));
                allExps.add(Boolean.valueOf(mRep.get(k + 1)));
                allQuants.add(Double.valueOf(mRep.get(k + 2)));
                allSums.add(Integer.valueOf(mRep.get(k + 3)));
            }


            int maxIndex = 0; // индекс ячейки с максимальным доходом
            int maxExpIndex = 0; // индекс ячейки с максимальной тратой
            double maxQuantSum = 0; // максимальный доход
            double maxExp = 0; // максимальная трата
            double profitMonth = 0; //общий доход за месяц
            double lossMonth = 0; //общий расход за месяц
            for (int j = 0; j < allItems.size(); j++) {
                if (!allExps.get(j)) {
                    if (allQuants.get(j) * allSums.get(j) > maxQuantSum) {
                        maxQuantSum = allQuants.get(j) * allSums.get(j);
                        maxIndex = j;
                    }
                    profitMonth += allQuants.get(j) * allSums.get(j);
                } else {
                    if (allQuants.get(j) * allSums.get(j) > maxExp) {
                        maxExp = allQuants.get(j) * allSums.get(j);
                        maxExpIndex = j;
                    }
                    lossMonth += allQuants.get(j) * allSums.get(j);
                }
            }

            mostProfitableItemsByMonth.add(allItems.get(maxIndex));
            mostProfitsByMonth.add(maxQuantSum);
            mostLosingItemsByMonth.add(allItems.get(maxExpIndex));
            mostLossByMonth.add(maxExp);
            profitsLossAllMonths[i][0] = profitMonth;
            profitsLossAllMonths[i][1] = lossMonth;
            allItemsForYear.put(months.getMonths().get(i + 1), allItems);
            allExpsForYear.put(months.getMonths().get(i + 1), allExps);
            allQuantsForYear.put(months.getMonths().get(i + 1), allQuants);
            allSumsForYear.put(months.getMonths().get(i + 1), allSums);
        }
    }

    void printAllInfo() {
        for (int i = 0; i < months.getMonths().size(); i++) {
            System.out.println("Данные за: " + months.getMonths().get(i + 1));
            System.out.println("Самый прибыльный товар: " + mostProfitableItemsByMonth.get(i) + " - " + mostProfitsByMonth.get(i) + " рублей"); // здесь выводит не те суммы
            System.out.println("Самая большая трата: " + mostLosingItemsByMonth.get(i) + " - " + mostLossByMonth.get(i) + " рублей");
            System.out.println("-----------------");
        }
    }
}




