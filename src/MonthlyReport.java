import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MonthlyReport {

    String pathMonths = "";
    String[] fileContents = new String[3];
    boolean monthRepReady = false; // индикатор того, что файлы с месячным отчетом считались
    boolean isFileSplit = false; // переменная, чтобы не вызывать метод Split дважды

    double[][] profitsLossAllMonths = new double[3][2];// общие доходы и расходы за каждый месяц
    HashMap<Integer, ArrayList<MonthlyReportItem>> allDataFromMRByMonth = new HashMap<>();
    HashMap<String, ArrayList<String>> monthRep = new HashMap<>();
    ArrayList<String> linesSeparation;
    ArrayList<String> mostProfitableItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostProfitsByMonth = new ArrayList<>();
    ArrayList<String> mostLosingItemsByMonth = new ArrayList<>();
    ArrayList<Double> mostLossByMonth = new ArrayList<>();

    Months months = new Months();
    ReadFileContentsOrNull readFileContentsOrNull = new ReadFileContentsOrNull();

    public void readAllMonthFiles() {
        for (int i = 1; i <= 3; i++) {
            pathMonths = "./resources/m.20210";
            pathMonths += i + ".csv";
            if (readFileContentsOrNull.readFileContentsOrNull(pathMonths) == null) {
                System.out.println("Данные за " + months.getMonths().get(i) + " отсутсвуют");
                System.exit(1);
            } else {

                fileContents[i - 1] = readFileContentsOrNull.readFileContentsOrNull(pathMonths);
            }
        }
        monthRepReady = true;
        System.out.println("\nСчитывание месячных отчетов завершено\n");
    }

    public void splitFile() {
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

        for (int i = 0; i < months.getMonths().size(); i++) {
            ArrayList<String> mRep = monthRep.get(String.valueOf((i + 1)));
            ArrayList<MonthlyReportItem> allDataFromMonthRep = new ArrayList<>();
            for (int k = 0; k < mRep.size(); k += 4) {
                MonthlyReportItem mRI = new MonthlyReportItem();
                mRI.itemName = mRep.get(k);
                mRI.isExpense = Boolean.valueOf(mRep.get(k + 1));
                mRI.quantity = Double.valueOf(mRep.get(k + 2));
                mRI.sumOfOne = Integer.valueOf(mRep.get(k + 3));
                allDataFromMonthRep.add(mRI);
            }
            allDataFromMRByMonth.put(i + 1, allDataFromMonthRep);
            isFileSplit = true;
        }
    }

    public boolean isFileSplit() {
        return isFileSplit;
    }

    public boolean isMonthRepReady() {
        return monthRepReady;
    }

    public void getMaxSumAndItem() {
        for (int i = 0; i < allDataFromMRByMonth.size(); i++) {
            ArrayList<MonthlyReportItem> allDataFromMonthRep = allDataFromMRByMonth.get(i + 1);

            int maxIndex = 0; // индекс ячейки с максимальным доходом
            int maxExpIndex = 0; // индекс ячейки с максимальной тратой
            double maxQuantSum = 0; // максимальный доход
            double maxExp = 0; // максимальная трата
            double profitMonth = 0; //общий доход за месяц
            double lossMonth = 0; //общий расход за месяц

            for (int j = 0; j < allDataFromMonthRep.size(); j++) {
                if (!allDataFromMonthRep.get(j).isExpense) {
                    if (allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne > maxQuantSum) {
                        maxQuantSum = allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne;
                        maxIndex = j;
                    }
                    profitMonth += allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne;
                } else {
                    if (allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne > maxExp) {
                        maxExp = allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne;
                        maxExpIndex = j;
                    }
                    lossMonth += allDataFromMonthRep.get(j).quantity * allDataFromMonthRep.get(j).sumOfOne;
                }
            }

            mostProfitableItemsByMonth.add(allDataFromMonthRep.get(maxIndex).itemName);
            mostProfitsByMonth.add(maxQuantSum);
            mostLosingItemsByMonth.add(allDataFromMonthRep.get(maxExpIndex).itemName);
            mostLossByMonth.add(maxExp);
            profitsLossAllMonths[i][0] = profitMonth;
            profitsLossAllMonths[i][1] = lossMonth;
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

    public HashMap<Integer, Double[]> getTotalPL() {
        HashMap<Integer, Double[]> hMSumPL = new HashMap<>();

        for (int i = 0; i < allDataFromMRByMonth.size(); i++) {
            ArrayList<MonthlyReportItem> allDataFromMonthRep = allDataFromMRByMonth.get(i + 1);
            double sumP = 0;
            double sumL = 0;
            for (MonthlyReportItem monthlyReportItem : allDataFromMonthRep) {
                if (monthlyReportItem.isExpense) {
                    sumL += monthlyReportItem.sumOfOne * monthlyReportItem.quantity;
                } else {
                    sumP += monthlyReportItem.sumOfOne * monthlyReportItem.quantity;
                }
            }
            Double[] mRS = new Double[2];
            mRS[0] = sumP;
            mRS[1] = sumL;
            hMSumPL.put(i + 1, mRS);
        }
        return hMSumPL;
    }

}




