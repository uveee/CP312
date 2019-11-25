import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Index {

    public static void main(String[] args) throws Exception
    {
        try
        {
            HashMap<String, String> researcherMap= new HashMap<>();

            Scanner scanner = new Scanner(System.in);
            String newLine;

            while (!(newLine = scanner.nextLine()).isEmpty())
            {
                String[] researcher = newLine.split(" ");

                String name = researcher[0] + " " + researcher[1];
                int[] citations = new int[researcher.length-2];

                for (int i = 0; i < citations.length; i++)
                {
                    citations[i] = Integer.parseInt(researcher[i+2]);
                }

                int hIndex;

                if (citations[citations.length-1] >= citations.length)
                {
                    hIndex = citations.length;
                }

                else{
                    hIndex = findHIndex(citations, 0, citations.length-1);
                }

                String hIndexString = String.valueOf(hIndex);
                researcherMap.put(name, hIndexString);
            }

            scanner.close();

            //Sort

            Map<String, String> sortedResearchers = sortByHIndex(researcherMap, false);

            for (HashMap.Entry<String, String> entry: sortedResearchers.entrySet())
            {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private static int findHIndex(int[] C, int min, int max)
    {

        int mid = (min+max)/2;

        //Base case where we don't have a case of papers with the same citations
        if (C[mid] <= mid && C[mid-1] > (mid - 1))
        {
            return mid;
        }

        //Search left side
        else if (C[mid] > mid)
        {
            return findHIndex(C, mid, max);
        }

        //Search right side, h index could be smaller
        else if (C[mid] < mid && C[mid-1] <= mid - 1)
        {
            return findHIndex(C, min, mid);
        }

        else
        {
            return -1;
        }
    }

    private static Map<String, String> sortByHIndex(Map<String, String> unsortMap, final boolean asc)
    {
        List<Entry<String, String>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort by h index

        list.sort(
            (temp_1, temp_2) -> asc ?
                temp_1.getValue().compareTo(temp_2.getValue()) == 0 ?
                temp_1.getValue().compareTo(temp_2.getValue()) : temp_1.getKey().compareTo(temp_2.getKey()) : temp_2.getValue().compareTo(temp_1.getValue()) == 0 ?
                temp_2.getKey().compareTo(temp_1.getKey()) : temp_2.getValue().compareTo(temp_1.getValue())
        );

        list.sort(
                (temp1, temp2) -> !asc ?
                temp1.getValue().compareTo(temp2.getValue()) == 0 ?
                temp1.getKey().compareTo(temp2.getKey()) : temp2.getKey().compareTo(temp1.getKey()) : temp1.getValue().compareTo(temp2.getValue()) == 0 ?
                temp2.getValue().compareTo(temp1.getValue()) : temp2.getKey().compareTo(temp1.getKey())
        );

        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }
}
