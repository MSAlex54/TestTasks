import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Group implements Serializable {
    private ArrayList<String[]> list = new ArrayList<>();

    public Group(String [] arr) {
        list.add(arr);
    }

    public void add(String[] arr) {
        list.add(arr);
    }

    public int size() {
        return list.size();
    }
    public String toString(int num){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Группа " + num + "\n" );
        for (String [] s: list) {
            stringBuilder.append(Arrays.toString(s) + "\n");
        }
        return stringBuilder.toString();
    }
    public static final Comparator<Group> COMPARE_BY_COUNT = new Comparator<Group>() {
        @Override
        public int compare(Group lhs, Group rhs) {
            return rhs.size() - lhs.size();
        }
    };


}
