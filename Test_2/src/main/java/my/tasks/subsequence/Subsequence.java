package my.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if (x==null||y==null) throw new IllegalArgumentException();
        if (x.isEmpty()) return true;
        if (x.equals(y)) return true;
        int index = 0;
        for (int i = 0; i < y.size() ; i++) {
            if (y.get(i).equals(x.get(index))){
                index++;
                if (index>=x.size()) {
                    return true;
                }
            }
        }
        return false;
    }
}
