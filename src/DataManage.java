/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 29.05.13
 * Time: 6:33
 * To change this template use File | Settings | File Templates.
 */
public class DataManage {
    public static String name, phone, adress, age;
    public static String[] DElem = new String[4];


    public String ToString(String name, String phone, String adress) {
        return name + " " + phone + " " + adress + " " + age;
    }

    public void OutOfString(String data) {
        DElem = data.split(" ");
        name = DElem[0];
        phone = DElem[1];
        adress = DElem[2];
        age = DElem[3];
    }


    public static void main(String[] args) {
        DataManage DM = new DataManage();
        String myDatas = DM.ToString("Anna", "12345", "Moscow");
        DM.OutOfString(myDatas);
    }

}
