import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 24.04.13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtMappedDb implements NotebookDb {
    private File file;
    private Map<String, String> MapText;
    private NotebookTxtDb n;

    NotebookTxtMappedDb(final String fileName) throws IOException {
       /* file = new File(fileName);
        fileText = new HashMap<String, String>();
        if (!file.exists()) {
           // fileText = new HashMap<String, String>();
            file.createNewFile();
            System.out.println("New file " + fileName + " has been created in the current directory");
        }*/
        n = new NotebookTxtDb("New.txt");
        if (MapText == null) {
            MapText = new HashMap<String, String>();
        }
    }


    public boolean isNameExists(final String name) throws IOException {
        return MapText.containsKey(name);
    }

    // запись данных (имя, телефон) в файле в столбик
    public void addRecord(final String name, final String phone) throws IOException {
        MapText.put(name, phone);
        n.addRecord(name, phone);

    }

    public void remove(final String name) {
        MapText.remove(name);
        n.remove(name);
    }

    public String searchByName(final String name) throws IOException {
        if (!MapText.containsKey(name)) {
            return null;
        }
        return MapText.get(name);
    }

    public String searchByPhone(final String phone) throws IOException {
        Iterator it = MapText.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() == phone) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    void Open() throws IOException {
        n.Open();
    }

    public static void main(String[] args) throws IOException {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("olimp", "95");
        no.addRecord("j", "11");
        no.addRecord("imp", "0");
        no.addRecord("i", "99");
        no.searchByPhone("99");
        //no.remove("olimp");
        //System.out.println(no.searchByName("olimp"));
        //   no.fileText.put("o","78");
        //   System.out.println("o - "+no.fileText.get("o"));
        System.out.println(no.MapText.keySet());


    }
}
