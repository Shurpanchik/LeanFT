package com.company;

import com.hp.lft.report.*;
import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.stdwin.*;
import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.stdwin.Button;
import com.hp.lft.sdk.stdwin.ButtonDescription;
import com.hp.lft.sdk.stdwin.MenuDescription;
import com.hp.lft.sdk.stdwin.Window;
import com.hp.lft.sdk.web.*;
import com.hp.lft.sdk.web.EditField;
import com.hp.lft.sdk.web.EditFieldDescription;
import com.hp.lft.sdk.web.Menu;
import javafx.beans.property.ReadOnlySetPropertyBase;
import models.perfomancelab.PerfomanceLabSoftwarePage;
import models.perfomancelab.PerfomanceLabStartPage;
import models.resmon.Notepad;
import models.resmon.Resmon;
import models.search.SearchPage;
import models.search.SearchResults;
import org.junit.*;
import unittesting.UnitTestClassBase;

import java.awt.image.RenderedImage;
import java.io.IOException;

public class LeanFtTest extends UnitTestClassBase {

    public LeanFtTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        instance = new LeanFtTest();
        globalSetup(LeanFtTest.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        globalTearDown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public  void testNotepadExample() throws Exception{

        new ProcessBuilder("C:\\Windows\\System32\\notepad.exe").start();

        Thread.sleep(3000);

        Window notepadWin = Desktop.describe(Window.class,
                new WindowDescription.Builder().windowTitleRegExp("Notepad").build());

        Editor editor = notepadWin.describe(Editor.class,
                new EditorDescription.Builder().nativeClass("Edit").windowClassRegExp("Edit").build());

        editor.sendKeys("This is automated text");

        Thread.sleep(3000);
        notepadWin.close();

        Desktop.describe(Window.class, new WindowDescription.Builder()
                .ownedWindow(false).childWindow(false).windowClassRegExp("Notepad").windowTitleRegExp(" Notepad").build())
                .describe(Dialog.class, new DialogDescription.Builder()
                        .ownedWindow(true).childWindow(false).text("Notepad").nativeClass("#32770").build())
                .describe(Button.class, new ButtonDescription.Builder()
                        .text("Do&n't Save").nativeClass("Button").build()).click();
    }
    @Test
    public void testNotepad() throws GeneralLeanFtException, IOException {
        Process process = new ProcessBuilder("C:\\Windows\\System32\\notepad.exe").start();

        Window notepadWindow =
                Desktop.describe(Window.class, new WindowDescription.Builder().windowClassRegExp("Notepad").build());


        Notepad notepad = new Notepad(notepadWindow);
        System.out.println(notepadWindow.isActive());
        System.out.println(notepad.MenuFile().exists());
        notepad.MenuFile().click();
        //process.destroy();
    }

    @Test
    public void testResmon() throws GeneralLeanFtException, IOException {
      // Process process = new ProcessBuilder("C:\\Windows\\System32\\resmon.exe").start();
        Process process2 = new ProcessBuilder("C:\\Windows\\System32\\notepad.exe").start();

        Window notepadWindow =
                Desktop.describe(Window.class, new WindowDescription.Builder().windowClassRegExp("Notepad").build());

    //    Window window = Desktop.describe(Window.class, new WindowDescription.Builder()

                //.attachedText("Монитор ресурсов")
               // .windowTitleRegExp("Монитор ресурсов")
       //         .windowClassRegExp("WdcWindow")
               // .windowClassRegExp()
     //           .build());

      //  System.out.println(window.getDisplayName());

    //    Resmon resmon = new Resmon(window);
        Notepad notepad = new Notepad(notepadWindow);
        System.out.println(notepadWindow.isActive());
        System.out.println(notepad.MenuFile().exists());
        notepad.MenuFile().click();
      //  resmon.TabPanel().BrowserTab().click();
    //    resmon.BrowserTab().click();

        /*
        Resmon resmon = Desktop.describe(Resmon.class,
                new WindowDescription.Builder().windowTitleRegExp("resmon").build());
        resmon.describe(Menu.class, new MenuDescription.Builder().build()).doubleClick();
        */
        //process.destroy();
    }

    /*
        1. Запустить любой из браузеров на выбор, приведённых в исходных данных;

        2. Зайти в сайт любой на выбор поисковой системы, из приведённых в исходных данных;

        3. Набрать в поисковой строке название компании – performance lab, произвести поиск;

        4. Дождаться появления результатов поиска и перейти по ссылке из результатов поиска на сайт компании «performance lab» (http://www.performance-lab.ru);

        5. На сайте компании перейти на вкладку «Услуги»;

        6. Перейти в меню «Тестирование»;

        7. В открывшемся списке перейти в опцию «Автоматизация тестирования»;

        8. Далее сохранить текст страницы в текстовый файл вида (*.txt) на диск C:\, где * - это уникальное имя файла, содержащее название поисковой системы, в которой выполнялся поиск;
     */
    @Test
    public void googleTest()throws GeneralLeanFtException, IOException {
        Browser browser = BrowserFactory.launch(BrowserType.CHROME);
        browser.navigate("https://www.google.ru/");



        // отправляем поисковой запрос perfomance-lab
        SearchPage searchPage = new SearchPage(browser);
        browser.describe(EditField.class, searchPage.SearchPanel().getDescription()).setValue("perfomance-lab");
        browser.describe(EditField.class, (Description) searchPage.SearchPanel().getDescription()).submit();

        // ищем ссылку на перфоманс-лаб и переключаемся на соседнюю вкладку, так как она _blank
        SearchResults searchResults = new SearchResults(browser);
        searchResults.PerfomanceLabLink().click();

       RenderedImage image = browser.getSnapshot();

        // переключаем вкладку
        //TODO пока заглушка переключения на новую вкладку
        browser.closeAllTabs();

        browser = BrowserFactory.launch(BrowserType.CHROME);
        browser.navigate("http://www.performance-lab.ru/");

        // переходим в меню Тестирование
        PerfomanceLabStartPage startPagePerfomanceLab = new PerfomanceLabStartPage(browser);
        startPagePerfomanceLab.ServicesAndProductsItem().getInnerText();
        startPagePerfomanceLab.ServicesAndProductsItem().TestItem().click();

        // переходим по ссылке Автоматизация тестирования

        PerfomanceLabSoftwarePage softwarePage = new PerfomanceLabSoftwarePage(browser);
        softwarePage.AutoTestLink().click();

    }

    @Test
    public void testReporter() throws Exception{

        ModifiableReportConfiguration reportConfig = ReportConfigurationFactory.createDefaultReportConfiguration();
        reportConfig.setOverrideExisting(true);
        reportConfig.setTargetDirectory("RunResults"); // The folder must exist under C:\
        reportConfig.setReportFolder("myreportdirectory");
        reportConfig.setTitle("My Report Title");
        reportConfig.setDescription("Report Description");
        reportConfig.setSnapshotsLevel(CaptureLevel.OnError);

        Reporter.init(reportConfig);
        Browser browser = BrowserFactory.launch(BrowserType.CHROME);
        try{
            //Navigate to http://www.softpost.org/selenium-test-page/
            browser.navigate("http://www.softpost.org/selenium-test-page/");
            browser.sync();

            //set value in edit box
            EditField firstName = browser.describe(EditField.class,new EditFieldDescription.Builder()
                    .id("fn").build());
            firstName.setValue("Sagar");

            //take screenshot
            RenderedImage img = browser.getPage().getSnapshot();

            if (firstName.getValue().equalsIgnoreCase("sagar"))
                Reporter.reportEvent("Verify Editbox","Value is sagar", Status.Passed,img);
            else
                Reporter.reportEvent("Verify Editbox","Value is not sagar", Status.Failed,img);

        }
        catch(AssertionError ex){
            //Report the Exception
            Reporter.reportEvent("Exception","Test failed", Status.Failed, ex);
            throw ex;
        }
        finally{
            //Close the browser
            browser.close();
        }
    }

}