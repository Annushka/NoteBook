import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 06.06.13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    public String name;
    public String phone;
    public String address;
    public int age;
   /// public String age;
    public static int num = 4;

    Record(String line) {                                                      // сделать без data, age должен быть int, написать тесты
        StringTokenizer s = new StringTokenizer(line);
        name = s.nextToken();
        phone = s.nextToken();
        address = s.nextToken();
        age = Integer.valueOf(s.nextToken());             // приведение типа object в int
    }

    public String toString() {
        return name + " " + phone + " " + address + " " + age;
    }

    public static void main(String[] args) throws Exception {
        final Record r = new Record("name phone1 Argentina 19");
        System.out.println(r.name);
        System.out.println(r.age);
        System.out.println(r.address);
        //System.out.println(r.name == r.phone);

    }

}
