package io.github.jdollar.parser;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jdollar on 3/22/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class BillBoardParserTest {
    private static final String BILL_BOARD_TEST_HTML_RESOURCE_PATH = "testParserHtml\\billBoardTopTestHtml.html";
    private static final String ARIA_TEST_HTML_RESOURCE_PATH = "testParserHtml\\ariaTopTestHtml.html";

    @Before
    public void doSetUp() {

    }

    @After
    public void doTearDown() {

    }

    @Test
    public void testBillBoardTopTen_getValidValues() throws Exception {
        String htmlString = getHtmlFileAsString(BILL_BOARD_TEST_HTML_RESOURCE_PATH);
        setupJsoupValidMocks(Jsoup.parse(htmlString));

        Map<String, String> resultMap = BillBoardParser.getTop10Songs();
        assert resultMap != null;
        assert resultMap.size() == 10;
    }

    @Test
    public void testBillBoardTopTen_urlUnreachable() {

    }

    @Test
    public void testBillBoardTopHundred_getValidValues() throws Exception {
        String htmlString = getHtmlFileAsString(BILL_BOARD_TEST_HTML_RESOURCE_PATH);
        setupJsoupValidMocks(Jsoup.parse(htmlString));

        Map<String, String> resultMap = BillBoardParser.getTop100Songs();
        assert resultMap != null;
        assert resultMap.size() == 100;
    }

    @Test
    public void testBillBoardTopHundred_urlUnreachable() {

    }

    @Test
    public void testAriaTopTen_getValidValues() throws Exception {
        String htmlString = getHtmlFileAsString(ARIA_TEST_HTML_RESOURCE_PATH);
        setupJsoupValidMocks(Jsoup.parse(htmlString));

        Map<String, String> resultMap = BillBoardParser.getTop10AusieSongs();
        assert resultMap != null;
        assert resultMap.size() == 10;
    }

    @Test
    public void testAriaTopTen_urlUnreachable() {

    }

    @Test
    public void testAriaTopFifty_getValidValues() throws Exception {
        String htmlString = getHtmlFileAsString(ARIA_TEST_HTML_RESOURCE_PATH);
        setupJsoupValidMocks(Jsoup.parse(htmlString));

        Map<String, String> resultMap = BillBoardParser.getTop50AusieSongs();
        assert resultMap != null;
        assert resultMap.size() == 50;
    }

    @Test
    public void testAriaTopFifty_urlUnreachable() {

    }

    private void setupJsoupValidMocks(Document testDocument) throws Exception {
        PowerMockito.mockStatic(Jsoup.class);
        Connection connection = Mockito.mock(Connection.class);
        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
        PowerMockito.when(connection.get()).thenReturn(testDocument);
    }

    private String getHtmlFileAsString(String fileName) {
        String htmlString = "";
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            htmlString = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return htmlString;
    }
}
