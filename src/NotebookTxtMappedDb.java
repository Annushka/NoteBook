import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 24.04.13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class NotebookTxtMappedDb extends NotebookTxtDb {
    private File file;
    private LinkedHashMap<String, String> notebookCache;
    private NotebookTxtDb storage;


    NotebookTxtMappedDb(final String fileName) throws IOException {
        super(fileName);
        notebookCache = new LinkedHashMap<String, String>();
        DataManage DM = new DataManage();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                notebookCache.put(line, line = br.readLine());
            }
        } finally {
            br.close();
        }
    }

    public boolean isNameExists(final String name) throws IOException {
        return notebookCache.containsKey(name);
    }

    // запись данных (имя, телефон)  в Map
    public void addRecord(final String data) throws IOException {
        DM.OutOfString(data); // для доступа к имени
        String name = DataManage.name;
        String content = "";
        for (int i = 1; i < DataManage.DElem.length; i++) {
            content += DataManage.DElem[i] + " ";
        }

        notebookCache.put(name, content);

    }

    public void remove(final String name) {
        notebookCache.remove(name);
    }


    public String searchByName(final String name) throws IOException {
        if (!notebookCache.containsKey(name)) {
            return null;
        }
        return notebookCache.get(name);
    }

    public String searchByPhone(final String phone) throws IOException {
        Iterator it = notebookCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object str = entry.getValue();
            StringTokenizer ph = new StringTokenizer(str.toString());
            String curPhone = ph.nextToken();
            if (curPhone.equals(phone)) {
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public void Open() throws IOException {
        Iterator it = notebookCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + "  -  " + entry.getValue());
        }
    }

    public static void main(String[] args) throws IOException {
        NotebookTxtMappedDb m = new NotebookTxtMappedDb("filename");
        m.addRecord("anna 4444 Kiev 44");
        m.addRecord("misha 67574 Moscow 54");
        m.addRecord("pasha 9999 Volgograd 98");
        System.out.println(m.isNameExists("anna") + " - test isNameExists");
        System.out.println(m.searchByName("anna") + " - by name anna");
        System.out.println(m.searchByPhone("9999") + " - by phone 9999");
        System.out.println(m.notebookCache.values());
        m.remove("pasha");
        System.out.println(m.notebookCache.values());
        m.Open();

    }

}
