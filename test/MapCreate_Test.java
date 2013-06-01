/*
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

*/
/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 01.05.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 *//*

public class MapCreate_Test {
    @Test   // существует ли имя, которого нет в списке -> false
    public void NotExName2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("miky", "8");
        no.addRecord("miky", "8");
        no.addRecord("miky", "8");
        assertFalse(no.isNameExists("sara"));

    }

    @Test  // Удаление всех контактов с одинаковыми именами
    public void RemoveAllSameContacts2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("lily", "100");
        no.remove("miky");
        assertFalse(no.isNameExists("mike"));
    }

    @Test  // Удаление несуществующего контакта
    public void RemoveNoExContact2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("lily", "100");
        no.remove("Lily");
        assertTrue(no.isNameExists("lily"));
    }

    @Test  // поиск по имени. Такого контакта нет.
    public void SearchN2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        Assert.assertEquals(null, no.searchByName("anna"));
    }

    @Test  // поиск по номеру. Такого контакта нет.
    public void SearchPh2() throws Exception {
        NotebookTxtMappedDb no = new NotebookTxtMappedDb("New.txt");
        no.addRecord("olimp", "95");
        no.addRecord("j", "11");
        no.addRecord("imp", "0");
        no.addRecord("i", "99");
        no.searchByPhone("99");
        //no.remove("olimp");
        Assert.assertEquals(null, no.searchByPhone("999"));
    }

    @Test
    public void restart() throws Exception {
        final NotebookTxtMappedDb db = new NotebookTxtMappedDb("New1.txt");
        db.addRecord("olimp", "95");
        db.addRecord("j", "11");
        db.addRecord("imp", "0");
        db.addRecord("i", "99");
        final NotebookTxtMappedDb db1 = new NotebookTxtMappedDb("New1.txt");
        Assert.assertEquals("olimp", db1.searchByPhone("95"));
    }

}
*/
