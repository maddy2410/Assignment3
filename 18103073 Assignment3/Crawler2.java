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


public class Crawler2{
    public static void main(String[] args) {
        try {
            CSVWriter csvwriterUrl = new CSVWriter(new FileWriter(new File("faculty_urls.csv")));
            CSVWriter csvwriterTag = new CSVWriter(new FileWriter(new File("faculty_text.csv")));
            String[] headerUrl = {
                "TextForURL",
                "URL"
            };
            String[] headerTag = {
                "TypeOfTag",
                "TextEnclosed"
            };
            csvwriterUrl.writeNext(headerUrl);
            csvwriterTag.writeNext(headerTag);
            ArrayList < String > urlList = new ArrayList < String > ();
            Set < String > urlSet = new HashSet < String > ();
            String url = "https://pec.ac.in/aero";
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
                    String title = document.title();
                    if (currentUrl.contains("faculty")) {
                        csvwriterTag.writeNext(new String[] {});
                        csvwriterTag.writeNext(new String[] {
                            "url: " + (i + 1), currentUrl
                        });
                        csvwriterTag.writeNext(new String[] {
                            "titleTag",
                            title
                        });
                        csvwriterUrl.writeNext(new String[] {});
                        csvwriterUrl.writeNext(new String[] {
                            "url: " + (i + 1), currentUrl
                        });
                    }
                    Elements links = document.select("a[href]");
                    for (Element link: links) {
                        String Url, urlText;
                        Url = link.absUrl("href");
                        urlText = link.text();
                        if ((!urlSet.contains(Url)) && Url.contains("https://pec.ac.in/") && urlText.length() > 0) {
                            if (Url.contains("faculty")) {
                                csvwriterUrl.writeNext(new String[] {
                                    urlText,
                                    Url
                                });
                            }
                            urlSet.add(Url);
                            urlList.add(Url);
                        }
                    }
                    if (currentUrl.contains("faculty")) {
                        Elements paragraphTag = document.select("p");
                        for (Element p: paragraphTag) {
                            String text;
                            text = p.text();
                            if (text.length() > 2) {
                                csvwriterTag.writeNext(new String[] {
                                    "pTag",
                                    text
                                });
                            }
                        }

                        Elements h1Tag = document.select("h1");
                        for (Element h: h1Tag) {
                            String text;
                            text = h.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "h1Tag",
                                    text
                                });
                            }
                        }

                        Elements h2Tag = document.select("h2");
                        for (Element h: h2Tag) {
                            String text;
                            text = h.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "h2Tag",
                                    text
                                });
                            }
                        }

                        Elements h3Tag = document.select("h3");
                        for (Element h: h3Tag) {
                            String text;
                            text = h.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "h3Tag",
                                    text
                                });
                            }
                        }

                        Elements h4Tag = document.select("h4");
                        for (Element h: h4Tag) {
                            String text;
                            text = h.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "h4Tag",
                                    text
                                });
                            }
                        }
                        Elements imgTag = document.select("img");
                        for (Element im: imgTag) {
                            String text, src;
                            text = im.text();
                            src = im.attr("src");
                            if (text.length() > 0 && src.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "imgTag",
                                    src
                                });
                            }
                        }
                        Elements trTag = document.select("tr");
                        for (Element t: trTag) {
                            String text;
                            text = t.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "trTag",
                                    text
                                });
                            }
                        }
                        Elements tdTag = document.select("td");
                        for (Element t: tdTag) {
                            String text;
                            text = t.text();
                            if (text.length() > 0) {
                                csvwriterTag.writeNext(new String[] {
                                    "tdTag",
                                    text
                                });
                            }
                        }
                        System.out.println("URL: " + currentUrl);
                    }
                } catch (org.jsoup.UnsupportedMimeTypeException e) {
                    System.out.println("UnsupportedMimeTypeException occurs.");
                } catch (IOException e) {
                    System.out.println("IOException occurs.");
                }
            }
            csvwriterTag.close();
            csvwriterUrl.close();
        } catch (org.jsoup.UnsupportedMimeTypeException e) {
            System.out.println("UnsupportedMimeTypeException occurs.");
        } catch (IOException e) {
            System.out.println("IOException occurs.");
        }
    }
}