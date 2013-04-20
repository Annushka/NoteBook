import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 13.04.13
 * Time: 6:38
 * To change this template use File | Settings | File Templates.
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class Create_Test {

    @Test   // существует ли имя, которого нет в списке -> false
    public void NotExName() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("miky", "8");
        n.addRecord("miky", "8");
        n.addRecord("miky", "8");
        assertFalse(n.isNameExists("sara"));


    }

    @Test  // Удаление всех контактов с одинаковыми именами
    public void RemoveAllSameContacts() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("lily", "100");
        n.remove("miky");
        assertFalse(n.isNameExists("mike"));

    }

    @Test  // Удаление несуществующего контакта
    public void RemoveNoExContact() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.remove("Lily");
        assertTrue(n.isNameExists("lily"));
    }


    @Test  // поиск по имени. Такого контакта нет.
    public void SearchN() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        Assert.assertEquals(null, n.searchByName("anna"));
    }


    @Test  // поиск по номеру. Такого контакта нет.
    public void SearchPh() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        Assert.assertEquals(null, n.searchByPhone("199993"));
    }


    @Test  // поиск по имени. Существование >1 контактов с таким именем.
    public void SearchOne() throws Exception {
        NotebookTxtDb n = new NotebookTxtDb("New.txt");
        n.addRecord("olimp", "11");
        n.addRecord("olimp", "0");
        n.addRecord("olimp", "8686");
        Assert.assertEquals("11", n.searchByName("olimp"));

    }
}
