import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        CompareReports compareReports = new CompareReports();

        while (true) {
            printMenu();
            int command = scanner.nextInt();

            if (command == 1) {
                monthlyReport.readAllMonthFiles();

            } else if (command == 2) {
                yearlyReport.transformYearRep();

            } else if (command == 3) {
                if (!monthlyReport.isMonthRepReady() || !yearlyReport.isYearRepReady()) {
                    System.out.println("\nНеобходимо считать месячные и годовой отчеты\n");
                } else {
                    if (!monthlyReport.isFileSplit()) {
                        monthlyReport.splitFile();
                    }
                    ArrayList<String> comparisonResults = compareReports.comparisonMYReports(monthlyReport.getTotalPL(),
                            yearlyReport.getYearlyReportSeparated());
                    if (!comparisonResults.isEmpty()) {
                        System.out.println("\nОшибки по месяцам: \n");
                        System.out.println(comparisonResults);
                    } else {
                        System.out.println("\nОперация успешно завершена\n");
                    }
                }

            } else if (command == 4) {
                if (!monthlyReport.isMonthRepReady()) {
                    System.out.println("\nНеобходимо считать месячные отчеты\n");
                } else {
                    if (!monthlyReport.isFileSplit()) {
                        monthlyReport.splitFile();
                    }
                    monthlyReport.getMaxSumAndItem();
                    monthlyReport.printAllInfo();
                }

            } else if (command == 5) {
                if (!yearlyReport.isYearRepReady()) {
                    System.out.println("\nНеобходимо считать годовой отчет\n");
                } else {
                    yearlyReport.allYearRepInfo();
                }

            } else if (command == 123) {
                System.out.println("Выход");
                break;

            } else {
                System.out.println("\nИзвините, такой команды нет.\n");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Доступные варианты команд: ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("123 - Выйти из программы");
    }
}