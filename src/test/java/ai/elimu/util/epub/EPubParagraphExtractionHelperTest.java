package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EPubParagraphExtractionHelperTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testExtractParagraphsFromChapterFile_BEN_GDL_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("আজকে ছুটির দিন আনন্দে হারাই!\n" +
                " চলো সবে পোশাকের উৎসবে যাই!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_BEN_SW_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("ভীমের\n" +
"                    শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।"));
        assertThat(storyBookParagraphs.get(1), is("ধোপা\n" +
"                    রামু সুযোগ পেলেই ভীমকে বকা দেয়।"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1349() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Fifth grade student, Little Miss Grace,"));
        assertThat(storyBookParagraphs.get(1), is("was totally fascinated by outer space ."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Some wild cats have stripes."));
        assertThat(storyBookParagraphs.get(1), is("Like the tiger!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Some wild cats have spots."));
        assertThat(storyBookParagraphs.get(1), is("Like the leopard and the leopard cat!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_faa0d66e() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(3));
        assertThat(storyBookParagraphs.get(0), is("WAAAAHHHH!"));
        assertThat(storyBookParagraphs.get(1), is("Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol."));
        assertThat(storyBookParagraphs.get(2), is("WAAAAHHHH!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1287_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1287.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("उस मोटे राजा के पास एक पतला कुत्ता था ।\n"
                + "\n"
                + "एक दिन\n"
                + "\n"
                + " मोटा \n"
                + "\n"
                + "राजा\n"
                + "\n"
                + "  और उसका पतला कुत्ता  सैर करने गए।"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("एक बादल घुमने चला."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("साथ में चिड़िया भी उड़ती"));
        assertThat(storyBookParagraphs.get(1), is("हुई चली."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_SWA_GDL_30() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("swa-gdl-30.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("Kuku na Jongoo walikuwa marafiki."));
    }
}
