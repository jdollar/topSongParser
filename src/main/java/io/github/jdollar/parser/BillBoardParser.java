package io.github.jdollar.parser;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jdollar on 3/21/2015.
 */
public class BillBoardParser {

    private static String NOT_AVAILABLE_STRING = "NOT AVAILABLE";
    private static String ERROR_STRING = "An error has occurred";
    private static String BILL_BOARD_HTML_LOOKUP_SONG = ".row-title h2";
    private static String BILL_BOARD_HTML_LOOKUP_ARTIST = ".row-title h3 a";
    private static String ARIA_HTML_LOOKUP_SONG = "div.col-6 h3";
    private static String ARIA_HTML_LOOKUP_ARTIST = "div.col-6 p";

    public static String BILL_BOARD_HOT_100_URL = "http://www.billboard.com/charts/hot-100";
    public static String ARIA_TOP_50_URL = "http://www.ariacharts.com.au/chart/singles";

    public static Map<Integer, Pair<String, String>> getTop10Songs() {
        Map<Integer, Pair<String, String>> songTitleAndArtist = new LinkedHashMap<Integer, Pair<String, String>>();

        try {
            Document billboardPage = Jsoup.connect(BILL_BOARD_HOT_100_URL).get();
            Elements songTitleElements = billboardPage.select(BILL_BOARD_HTML_LOOKUP_SONG);
            Elements songArtistElements = billboardPage.select(BILL_BOARD_HTML_LOOKUP_ARTIST);

            iterateNElements(songTitleAndArtist, songTitleElements, songArtistElements, 10);
        } catch (Exception e) {
            e.printStackTrace();
            songTitleAndArtist.put(0, new Pair(ERROR_STRING, ERROR_STRING));
        }

        return songTitleAndArtist;
    }

    public static Map<Integer, Pair<String, String>> getTop100Songs() {
        Map<Integer, Pair<String, String>> songTitleAndArtist = new LinkedHashMap<Integer, Pair<String, String>>();

        try {
            Document billboardPage = Jsoup.connect(BILL_BOARD_HOT_100_URL).get();
            Elements songTitleElements = billboardPage.select(BILL_BOARD_HTML_LOOKUP_SONG);
            Elements songArtistElements = billboardPage.select(BILL_BOARD_HTML_LOOKUP_ARTIST);

            iterateAllElements(songTitleAndArtist, songTitleElements, songArtistElements);
        } catch (Exception e) {
            e.printStackTrace();
            songTitleAndArtist.put(0, new Pair(ERROR_STRING, ERROR_STRING));
        }

        return songTitleAndArtist;
    }

    public static Map<Integer, Pair<String, String>> getTop10AusieSongs() {
        Map<Integer, Pair<String, String>> songTitleAndArtist = new LinkedHashMap<Integer, Pair<String, String>>();

        try {
            Document billboardPage = Jsoup.connect(ARIA_TOP_50_URL).get();
            Elements songTitleElements = billboardPage.select(ARIA_HTML_LOOKUP_SONG);
            Elements songArtistElements = billboardPage.select(ARIA_HTML_LOOKUP_ARTIST);

            iterateNElements(songTitleAndArtist, songTitleElements, songArtistElements, 10);
        } catch (Exception e) {
            e.printStackTrace();
            songTitleAndArtist.put(0, new Pair(ERROR_STRING, ERROR_STRING));
        }

        return songTitleAndArtist;
    }

    public static Map<Integer, Pair<String, String>> getTop50AusieSongs() {
        Map<Integer, Pair<String, String>> songTitleAndArtist = new LinkedHashMap<Integer, Pair<String, String>>();

        try {
            Document billboardPage = Jsoup.connect(ARIA_TOP_50_URL).get();
            Elements songTitleElements = billboardPage.select(ARIA_HTML_LOOKUP_SONG);
            Elements songArtistElements = billboardPage.select(ARIA_HTML_LOOKUP_ARTIST);

            iterateAllElements(songTitleAndArtist, songTitleElements, songArtistElements);
        } catch (Exception e) {
            e.printStackTrace();
            songTitleAndArtist.put(0, new Pair(ERROR_STRING, ERROR_STRING));
        }

        return songTitleAndArtist;
    }

    private static void iterateAllElements(Map<Integer, Pair<String, String>> songTitleAndArtist, Elements songTitleElements, Elements songArtistElements) {
        int currentIndex = 0;
        String[] splitArtist;
        for (Element songTitleElement : songTitleElements) {
            if (songTitleElement != null) {
                if (songArtistElements != null
                        && songArtistElements.size() >= currentIndex
                        && songArtistElements.get(currentIndex) != null) {
                    splitArtist = songArtistElements.get(currentIndex).text().split("\\|");
                    if (splitArtist != null && splitArtist.length >= 1) {
                        songTitleAndArtist.put(currentIndex, new Pair(songTitleElement.text(), splitArtist[0]));
                    } else {
                        songTitleAndArtist.put(currentIndex, new Pair(songTitleElement.text(), NOT_AVAILABLE_STRING));
                    }
                } else {
                    songTitleAndArtist.put(currentIndex, new Pair(songTitleElement.text(), NOT_AVAILABLE_STRING));
                }
            }

            currentIndex++;
        }
    }

    private static void iterateNElements(Map<Integer, Pair<String, String>> songTitleAndArtist, Elements songTitleElements, Elements songArtistElements, int rowCount) {
        Element songTitleElement;
        String[] splitArtist;
        for (int i = 0; i < rowCount; i++) {
            if (songTitleElements != null && songTitleElements.size() >= i && songTitleElements.get(i) != null) {
                songTitleElement = songTitleElements.get(i);
                if (songTitleElement != null) {
                    if (songArtistElements != null && songArtistElements.size() >= i && songArtistElements.get(i) != null) {
                        splitArtist = songArtistElements.get(i).text().split("\\|");
                        if (splitArtist != null && splitArtist.length >= 1) {
                            songTitleAndArtist.put(i, new Pair(songTitleElement.text(), splitArtist[0]));
                        } else {
                            songTitleAndArtist.put(i , new Pair(songTitleElement.text(), NOT_AVAILABLE_STRING));
                        }
                    } else {
                        songTitleAndArtist.put(i , new Pair(songTitleElement.text(), NOT_AVAILABLE_STRING));
                    }
                }
            }
        }
    }
}
