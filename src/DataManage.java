/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 29.05.13
 * Time: 6:33
 * To change this template use File | Settings | File Templates.
 */
public class DataManage {
    public static String name, phone, adress;
    public static String[] DElem = new String[3];


    public String ToString(String name, String phone, String adress) {
        return name + " " + phone + " " + adress;
    }

    public void OutOfString(String data) {
        DElem = data.split("\\s");
        name = DElem[0];
        phone = DElem[1];
        adress = DElem[2];
    }


    public static void main(String[] args) {
        DataManage DM = new DataManage();
        String myDatas = DM.ToString("Anna", "12345", "Moscow");
        DM.OutOfString(myDatas);
    }

}
