package milliy.anonymous.milliytravel.util;

public class NumberUtils {

    /*public static String getRandomSmsCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(1000, 9999));
    }*/

    public static Integer getTotalPage(Long maxCount, Integer size) {

        if (size == 0) {
            return 0;
        }

        Integer totalPage = (int) (maxCount / size);

        if (maxCount % size == 0) {
            return totalPage;
        } else {
            return ++totalPage;
        }
    }
}
