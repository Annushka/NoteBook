import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Анна
 * Date: 01.05.13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Parameterized.class)
public class Param_Test {
    @Parameters
    public static Collection getParameters() throws IOException {
        return Arrays.asList(
                new Object[]{new NotebookTxtDb("filename.txt")},
                new Object[]{new NotebookTxtMappedDb("filename.txt")});
    }

    // @Rule
    //public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void phoneSearchTest() throws Exception {
        //1. поиск по номеру. Такого контакта нет.
        implementation.addRecord("olimp", "95");
        implementation.addRecord("i", "99");
        Assert.assertEquals(null, implementation.searchByPhone("999"));
    }

    @Test
    public void existContactTest() throws Exception {
        //2.такой контакт есть.
        implementation.addRecord("imp", "0");
        Assert.assertEquals("imp", implementation.searchByPhone("0"));
    }

    @Test
    public void samePhonesTest() throws Exception {
        //3.существует 2 контакта с одинаковыми номерами. Результатом поиска по телефону
        // является первый из них.
        implementation.addRecord("pasha", "95");
        Assert.assertEquals("olimp", implementation.searchByPhone("95"));
    }

    @Test
    public void noExistContactTest() throws Exception {
        //4.поиск по имени. Такого контакта нет.
        Assert.assertEquals(null, implementation.searchByName("anna"));
    }

    @Test
    public void addSameContact() throws Exception {
        //5.Проверка существования 2х контактов с одинаковыми номерами. Контакт должен быть 1 и номер
        // его является последним из добавленных с таким именем.
        implementation.addRecord("j", "11");
        implementation.addRecord("j", "111");
        Assert.assertEquals("111", implementation.searchByName("j"));
    }

    @Test
    public void notSameContactsTest() throws Exception {
        //6.Контакты, у которых у одного имя записано с заглавной буквы, а у другого
        // с маленькой - это разные контакты
        implementation.addRecord("J", "8");
        Assert.assertEquals("8", implementation.searchByName("J"));
    }

    @Test
    public void removeTest() throws Exception {
        //8.Проверка удаления контакта
        implementation.addRecord("masha", "516");
        implementation.remove("masha");
        assertFalse(implementation.isNameExists("masha"));
    }


    public Param_Test(NotebookDb implementation) {
        this.implementation = implementation;
    }

    private NotebookDb implementation;
}

