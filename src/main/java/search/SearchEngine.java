package search;
import java.io.*;
import java.util.*;

class SearchEngine {
    private final Scanner scanner = new Scanner(System.in);
    private final String[] args;
    private File inputText;
    private int countPerson;
    private Map<String, ArrayList<Integer>> listPersons;
    private StrategySearchEngine strategy;
    private String[] rows;
    private boolean isRun;


    SearchEngine(String[] args) {
        this.args = args;
    }

    private void initInputData() {
        inputText = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--data")) {
                inputText = new File("./" + args[i + 1]);
                break;
            }
        }
    }

    private void getCountRowsInInput() {
        countPerson = 0;
        try(Scanner scannerFile = new Scanner(inputText)){
            while (scannerFile.hasNext()) {
                scannerFile.nextLine();
                countPerson++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
        }
    }

    private void setInvertedIndexMap() {
        rows = new String[countPerson];
        listPersons = new LinkedHashMap<>();
        try(Scanner scannerFile = new Scanner(inputText)){
            while (scannerFile.hasNext()) {
                for (int i = 0; i < rows.length; i++) {
                    rows[i] = scannerFile.nextLine();
                    String[] personsForMap = rows[i].split(" ");
                    for (String personForMap : personsForMap) {
                        if (listPersons.containsKey(personForMap.toLowerCase())) {
                            listPersons.get(personForMap.toLowerCase()).add(i);
                        } else {
                            listPersons.put(personForMap.toLowerCase(), new ArrayList<>(List.of(i)));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
        }
    }

    public void runSearchEngine() {
        initInputData();
        getCountRowsInInput();
        setInvertedIndexMap();

        isRun = true;

        while (isRun) {
            outMenuSearchEngine();
            processingChoiceSearchEngine();
        }

        System.out.println("Bye!");
    }

    private void outMenuSearchEngine() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    private void processingChoiceSearchEngine() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                setStrategyWorkSearchEngine();
                strategy.processingSearchQuery();
                break;
            case 2:
                outListOfPersons();
                break;
            case 0:
                isRun = false;
                break;
            default:
                System.out.println("Incorrect option! Try again.");
                break;
        }
    }

    private void setStrategyWorkSearchEngine() {
        String strategyName = scanner.nextLine().toUpperCase();
        System.out.println("Enter a name or email to search all suitable people.");
        String[] query = scanner.nextLine().trim().split(" ");
        switch (strategyName) {
            case "ALL":
                strategy = new AllStrategySearchEngine(query, listPersons, rows);
                break;
            case "ANY":
                strategy = new AnyStrategySearchEngine(query, listPersons, rows);
                break;
            default:
                strategy = new NoneStrategySearchEngine(query, listPersons, rows);
                break;
        }
    }

    private void outListOfPersons() {
        System.out.println("=== List of people ===");
        for (String row : rows) {
            System.out.println(row);
        }
    }
}
