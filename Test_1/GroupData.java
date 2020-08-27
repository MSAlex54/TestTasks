import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GroupData {
    public static void main(String[] args) {
        Date startData = new Date();
        String filePath = "C:\\1\\lng-big.csv"; //путь к файлу
//        String filePath = "C:\\1\\lng.csv"; //путь к файлу
        Set<String> stringSet = new HashSet<>(); //сет для сбора уникальныйх значений
//        String regex = "((?:\\\"[^\\D]*\\\")(?:;*)){3}"; //регулярка для валидации корректности строки
        String regex = "((?<=^|;)((((?:\\\")[\\d\\.]*(?:\\\"))|(?:))(?:;|$))){3}"; //регулярка для валидации корректности строки
        List<Group> groups = new ArrayList<>(); //лист для сбора групп
        Map<String, int[]> coordinates = new HashMap<>(); // номера групп в зависимости от положения значения String - значение, int[] - массив номеров групп для 1,2,3 столбцов

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath)); //создаем буферны ввода данных
            stringSet = reader.lines().filter(s -> Pattern.matches(regex, s)).collect(Collectors.toSet()); //считываем сразу в сет используя фильтр по регулярке
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close(); //закрываем поток
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (stringSet.size() > 0) {
            for (String s : stringSet) {
                String[] currVals = s.split(";"); //бъем строку на массив строк
                boolean needNewGroup = true; // флаг для создания новой группы
                int groupNum = -1; // номер группы куда пойдут значения
                for (int i = 0; i < currVals.length; i++) { //для каждого значения в строке
                    if (!currVals[i].equals("\"\"") && !currVals[i].equals("") && coordinates.containsKey(currVals[i])) { //проверяем наличие в мапе с "координатами"
                        groupNum = coordinates.get(currVals[i])[i]; //если есть - достаем номер где уже есть это значение в этом столбце
                        if (groupNum != -1) { //если номер группы -1 - то значит ранее не добавлялось по данному столбцу пропускаем и проверяем дальше
                            groups.get(groupNum).add(currVals); //добавляем строку значений в группу
                            needNewGroup = false; // снимаем флаг создания новой группы
                            break; //покидаем цикл
                        }
                    }
                }
                if (needNewGroup) { //если нужна новая группа
                    groupNum = groups.size(); //запоминаем номер группы
                    Group group = new Group(currVals); // создаем новую группу
                    groups.add(group); //добавляем в список групп
                }
                for (int i = 0; i < currVals.length; i++) { //обновляем координаты
                    if (!currVals[i].equals("\"\"")&&!currVals[i].equals("")) { // если пустое значение - пропускаем
                        int[] coordinate = coordinates.containsKey(currVals[i]) ? coordinates.get(currVals[i]) : new int[]{-1, -1, -1}; //получаем массив координат или создаем новый
                        coordinate[i] = groupNum; //присваиваем соответствующему столбцу номер групп где соддержится значение в данном столбце
                        coordinates.put(currVals[i], coordinate); // обновляем данные в мапе
                    }
                }
            }
        }
        System.out.println("Количество групп с более чем 1 элементом: " + groups.stream().filter(group -> group.size() > 1).count());
        Collections.sort(groups, Group.COMPARE_BY_COUNT);

        for (int i = 0; i < groups.size() ; i++) {
            System.out.println(groups.get(i).toString(i+1));
        }

        Date finishDate = new Date();
        System.out.println("Время выполнения: " + (finishDate.getTime() - startData.getTime()) / 1000 + (" сек."));
    }
}