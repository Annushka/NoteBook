import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        n = new NotebookTxtDb(fileName);
        MapText = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = br.readLine()) != null) {
            MapText.put(line, line = br.readLine());
        }
    }

    public boolean isNameExists(final String name) throws IOException {
        return MapText.containsKey(name);
    }

    // запись данных (имя, телефон) в файл в столбик и в hashMap
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

    public void Open() throws IOException {
        Iterator it = MapText.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + "  -  " + entry.getValue());
        }
    }


    public static void main(String[] args) throws IOException {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        //  no.addRecord("olimp", "95");
        //no.addRecord("j", "11");
        //no.addRecord("imp", "0");
        //no.addRecord("i", "99");
        //no.searchByPhone("99");
        //no.remove("olimp");
        //System.out.println(no.searchByPhone("999"));
        no.Open();


    }
}
