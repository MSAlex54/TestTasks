package my.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        if (inputNumbers.contains(null)) throw new CannotBuildPyramidException();
        int x = (8 * inputNumbers.size()) + 1;
        int[][] result;
        if (Math.sqrt(x) == (int) Math.sqrt(x)) {
            Collections.sort(inputNumbers);
            int rows = (int) ((Math.sqrt(x) - 1) / 2);
            int cols = rows * 2 - 1;
            result = new int[rows][cols];
            int index = 0;
            for (int i = 0; i < rows ; i++) {
                for (int j = 0; j < cols ; j++) {
                    if(j<rows-i-1||j>cols-(rows-i)||(j-rows-i)%2==0){
                        result[i][j] = 0;
                    } else {
                        result[i][j] = inputNumbers.get(index++);
                    }
                }
            }
        } else {
            throw new CannotBuildPyramidException();
        }
        return result;
    }


}
