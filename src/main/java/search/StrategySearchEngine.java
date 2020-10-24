package search;
import java.util.*;

abstract class StrategySearchEngine {
    protected String[] query;
    protected Map<String, ArrayList<Integer>> listPersons;
    protected String[] rows;

    StrategySearchEngine(String[] query, Map<String, ArrayList<Integer>> listPersons, String[] rows) {
        this.query = query;
        this.listPersons = listPersons;
        this.rows = rows;
    }

    abstract void processingSearchQuery();
}

class AllStrategySearchEngine extends StrategySearchEngine {

    AllStrategySearchEngine(String[] query, Map<String, ArrayList<Integer>> listPersons, String[] rows) {
        super(query, listPersons, rows);
    }

    @Override
    public void processingSearchQuery() {
        Set<Integer> listPersonsRes = new LinkedHashSet<>();

        ArrayList<Integer> numbers = listPersons.get(query[0]);

        if (numbers == null) {
            System.out.println("No matching people found.");
            return;
        }

        for (int num : numbers) {
            listPersonsRes.add(num);
        }

        for (String que : query) {
            if (listPersons.containsKey(que)) {
                ArrayList<Integer> numbersOther = listPersons.get(que);
                ArrayList<Integer> numbersR = new ArrayList<>();
                for (int num : numbersOther) {
                    numbersR.add(num);
                }
                listPersonsRes.retainAll(numbersR);
            }
        }

        for (int personIndex : listPersonsRes) {
            System.out.println(rows[personIndex]);
        }
    }
}

class AnyStrategySearchEngine extends   StrategySearchEngine {

    AnyStrategySearchEngine(String[] query, Map<String, ArrayList<Integer>> listPersons, String[] rows) {
        super(query, listPersons, rows);
    }

    @Override
    public void processingSearchQuery() {
        Set<Integer> listPersonsRes = new LinkedHashSet<>();

        for (String que : query) {
            if (listPersons.containsKey(que)) {
                ArrayList<Integer> numbers = listPersons.get(que);
                for (int num : numbers) {
                    listPersonsRes.add(num);
                }
            }
        }

        if (listPersonsRes.size() == 0) {
            System.out.println("No matching people found.");
            return;
        }

        for (int personIndex : listPersonsRes) {
            System.out.println(rows[personIndex]);
        }

    }

}

class NoneStrategySearchEngine extends   StrategySearchEngine {

    NoneStrategySearchEngine(String[] query, Map<String, ArrayList<Integer>> listPersons, String[] rows) {
        super(query, listPersons, rows);
    }

    @Override
    public void processingSearchQuery() {
        Set<Integer> listPersonsRes = new LinkedHashSet<>();

        for (String que : query) {
            if (listPersons.containsKey(que)) {
                ArrayList<Integer> numbers = listPersons.get(que);
                for (int num : numbers) {
                    listPersonsRes.add(num);
                }
            }
        }

        if (listPersonsRes.size() == 0) {
            System.out.println("No matching people found.");
            return;
        }

        for (int i = 0; i < rows.length; i++) {
            if (!listPersonsRes.contains(i)) {
                System.out.println(rows[i]);
            }
        }
    }
}
