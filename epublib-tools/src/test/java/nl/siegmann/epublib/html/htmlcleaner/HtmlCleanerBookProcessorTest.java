package nl.siegmann.epublib.html.htmlcleaner;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import nl.siegmann.epublib.Constants;
import nl.siegmann.epublib.bookprocessor.Epub2HtmlCleanerBookProcessor;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.service.MediatypeService;
import org.junit.Ignore;
import org.junit.Test;

public class HtmlCleanerBookProcessorTest {

    @Ignore
    @Test
    public void testSimpleDocument1() {
        Book book = new Book();
        String testInput = "<html><head><title>title</title></head><body>Hello, world!</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testSimpleDocument2() {
        Book book = new Book();
        String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title><link foo=\"bar\" /></head><body background=\"red\">Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String result = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testSimpleDocument3() {
        Book book = new Book();
        String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title></head><body>Hello, world! ß</body></html>";
        try {
            Resource resource = new Resource(null, testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html", MediatypeService.XHTML, Constants.CHARACTER_ENCODING);
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String result = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testSimpleDocument4() {
        Book book = new Book();
        String testInput = "<html><head><title>title</title></head><body>Hello, world!\nHow are you ?</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!\nHow are you ?</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testMetaContentType() {
        Book book = new Book();
        String testInput = "<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.CHARACTER_ENCODING + "\" /></head><body>Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testDocType1() {
        Book book = new Book();
        String testInput = "<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.CHARACTER_ENCODING + "\" /></head><body>Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testDocType2() {
        Book book = new Book();
        String testInput = Constants.DOCTYPE_XHTML + "\n<html><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"/></head><body>Hello, world!</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.CHARACTER_ENCODING + "\" /></head><body>Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testXmlNS() {
        Book book = new Book();
        String testInput = "<html><head><title>title</title></head><body xmlns:xml=\"xml\">Hello, world!</html>";
        String expectedResult = Constants.DOCTYPE_XHTML + "\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>Hello, world!</body></html>";
        try {
            Resource resource = new Resource(testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html");
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String actualResult = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    @Ignore
    @Test
    public void testApos() {
        Book book = new Book();
        String testInput = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>test page</title></head><body>'hi'</body></html>";
        try {
            Resource resource = new Resource(null, testInput.getBytes(Constants.CHARACTER_ENCODING), "test.html", MediatypeService.XHTML, Constants.CHARACTER_ENCODING);
            book.getResources().add(resource);
            Epub2HtmlCleanerBookProcessor htmlCleanerBookProcessor = new Epub2HtmlCleanerBookProcessor();
            byte[] processedHtml = htmlCleanerBookProcessor.processHtml(resource, book, Constants.CHARACTER_ENCODING);
            String result = new String(processedHtml, Constants.CHARACTER_ENCODING);
            assertEquals(Constants.DOCTYPE_XHTML + "\n" + testInput, result);
        } catch (IOException e) {
            assertTrue(e.getMessage(), false);
        }
    }
}
