package app;

import java.io.IOException;
import java.util.List;

public class CrawlerApp {

    public static final String SERPER_API_KEY = "60cc580c34f242bdc9366806a79b83c3c7bfff59";

    private static final List<String> TOPICS = List.of("Agent Cooper", "Sherlock", "Poirot", "Detective Monk");


    public static void main(String[] args) throws IOException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
        photoCrawler.downloadPhotoExamples();
//        photoCrawler.downloadPhotosForQuery(TOPICS.get(0));
//        photoCrawler.downloadPhotosForMultipleQueries(TOPICS);
    }
}