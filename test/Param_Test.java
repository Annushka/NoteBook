import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
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
    @Rule
    public static TemporaryFolder folder = new TemporaryFolder();


    @Parameters
    public static Collection getParameters() throws IOException {
        File fFolder1 = folder.newFile("filename.txt");
        return Arrays.asList(

                new Object[]{new NotebookTxtDb(fFolder1.getName())},
                new Object[]{new NotebookTxtMappedDb(fFolder1.getName())});

    }


    @Test
    public void phoneSearchTest() throws Exception {
        //1. поиск по номеру. Такого контакта нет.
        Assert.assertEquals(null, implementation.searchByPhone("999"));
    }

    @Test
    public void existContactTest() throws Exception {

        //2.такой контакт есть.
        implementation.addRecord("imp", "10");
        Assert.assertEquals("imp", implementation.searchByPhone("10"));

        implementation.remove("imp");

    }

    @Test
    public void samePhonesTest() throws Exception {
        //3.существует 2 контакта с одинаковыми номерами. Результатом поиска по телефону
        // является первый из них.
        implementation.addRecord("olimp", "95");
        implementation.addRecord("pasha", "95");
        Assert.assertEquals("olimp", implementation.searchByPhone("95"));
        implementation.remove("olimp");
        implementation.remove("pasha");
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
        implementation.remove("j");
    }

    @Test
    public void notSameContactsTest() throws Exception {
        //6.Контакты, у которых у одного имя записано с заглавной буквы, а у другого
        // с маленькой - это разные контакты
        implementation.addRecord("Q", "8");
        implementation.addRecord("q", "7");
        Assert.assertEquals("8", implementation.searchByName("Q"));

        implementation.remove("Q");
        implementation.remove("q");
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

