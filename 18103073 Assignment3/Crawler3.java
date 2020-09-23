import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/*
Assignment3
By :  Madhav Oberoi 
18103073
*/


public class Crawler3
{
    public static void main(String[] args) {
        try {
            CSVWriter csvwriterPdf = new CSVWriter(new FileWriter(new File("pdfs.csv")));
            String[] headerPdf = {
                "pdfURL"
            };
            csvwriterPdf.writeNext(headerPdf);
            ArrayList < String > urlList = new ArrayList < String > ();
            Set < String > urlSet = new HashSet < String > ();
            Set < String > pdfSet = new HashSet < String > ();
            String url = "https://pec.ac.in";
            urlList.add(url);
            urlSet.add(url);
            String currentUrl;
            for (int i = 0; i < urlList.size(); i++) {
                try {
                	if (i > 200) {
                        break; 
                    }
                    currentUrl = urlList.get(i);
                    Document document = Jsoup.connect(currentUrl).get();
                    Elements links = document.select("a[href]");
                    for (Element link: links) {
                        String Url, urlText;
                        Url = link.absUrl("href");
                        urlText = link.text();
                        if ((!urlSet.contains(Url)) && Url.contains("https://pec.ac.in/")) {
                            if (Url.endsWith(".pdf") && !pdfSet.contains(Url)) {
                                csvwriterPdf.writeNext(new String[] {
                                    Url
                                });
                                pdfSet.add(Url);
                            } else {
                                urlList.add(Url);
                                urlSet.add(Url);
                            }
                        }
                    }
                    Elements iframe = document.select("iframe");
                    for (Element frame: iframe) {
                        String Url;
                        Url = frame.attr("data-src");
                        if (!urlSet.contains(Url) && Url.contains("https://pec.ac.in/") && Url.endsWith(".pdf") && !pdfSet.contains(Url)) {
                            csvwriterPdf.writeNext(new String[] {
                                Url
                            });
                            pdfSet.add(Url);
                        }
                    }
                    System.out.println("URL: " + currentUrl);
                } catch (IOException e) {
                    System.out.println("IOException occurs.");
                }
            }
            csvwriterPdf.close();
        } catch (IOException e) {
            System.out.println("IOException occurs.");
        }
    }
}