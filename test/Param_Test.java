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

    @Test //
    public void test() throws Exception {
        //1. поиск по номеру. Такого контакта нет.
        //2.такой контакт есть.
        //3.существует 2 контакта с одинаковыми номерами. Результатом поиска по телефону
        // является первый из них.
        //4.поиск по имени. Такого контакта нет.
        //5.Проверка существования 2х контактов с одинаковыми номерами. Контакт должен быть 1 и номер
        // его является последним из добавленных с таким именем.
        //6.Контакты, у которых у одного имя записано с заглавной буквы, а у другого
        // с маленькой - это разные контакты
        //7.Проверка последовательности записей в file/map.
        //8.Проверка удаления контакта

        implementation.addRecord("olimp", "95");
        implementation.addRecord("j", "11");
        implementation.addRecord("imp", "0");
        implementation.addRecord("i", "99");
        implementation.addRecord("j", "111");
        implementation.addRecord("J", "8");
        implementation.addRecord("pasha", "95");
        implementation.addRecord("masha", "516");
        implementation.Open();
        implementation.remove("masha");
        Assert.assertEquals(null, implementation.searchByPhone("999"));
        Assert.assertEquals("imp", implementation.searchByPhone("0"));
        Assert.assertEquals("olimp", implementation.searchByPhone("95"));
        Assert.assertEquals(null, implementation.searchByName("anna"));
        Assert.assertEquals("111", implementation.searchByName("j"));
        Assert.assertEquals("8", implementation.searchByName("J"));
        Assert.assertEquals("olimp 95 j 111 imp 0 i 99 J 8 pasha 95 ", implementation.Open());
        assertFalse(implementation.isNameExists("masha"));
    }

    public Param_Test(NotebookDb implementation) {
        this.implementation = implementation;
    }

    private NotebookDb implementation;
}

