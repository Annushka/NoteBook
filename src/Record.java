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
    public String age;
    public static int num = 4;

    Record(String line) {
        String data[] = {name, phone, address, age};
        StringTokenizer s = new StringTokenizer(line);
        int i = 0;
        while (s.hasMoreTokens()) {
            data[i] = s.nextToken();
            i++;
        }
        name = data[0];
        phone = data[1];
        address = data[2];
        age = data[3];
    }

    public String toString() {
        return name + " " + phone + " " + address + " " + age;
    }

    public static void main(String[] args) throws Exception {
        //final Record r = new Record("Anna 1222 rrew 4");
        //System.out.println(r.address);

    }

}
