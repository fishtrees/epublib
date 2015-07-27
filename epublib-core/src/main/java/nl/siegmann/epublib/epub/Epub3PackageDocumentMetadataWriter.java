package nl.siegmann.epublib.epub;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import nl.siegmann.epublib.Constants;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.util.StringUtil;

import org.xmlpull.v1.XmlSerializer;

public class Epub3PackageDocumentMetadataWriter extends PackageDocumentBase {

    /**
     * Writes the book's metadata.
     *
     * @param book
     * @param serializer
     * @throws IOException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    public static void writeMetaData(Book book, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag(null, OPFTags.metadata);
        serializer.setPrefix(PREFIX_DUBLIN_CORE, NAMESPACE_DUBLIN_CORE);
        //serializer.setPrefix(PREFIX_OPF, NAMESPACE_OPF);

        writeIdentifiers(book.getMetadata().getIdentifiers(), serializer);
        writeSimpleMetdataElements(DCTags.title, book.getMetadata().getTitles(), serializer);
        writeSimpleMetdataElements(DCTags.subject, book.getMetadata().getSubjects(), serializer);
        writeSimpleMetdataElements(DCTags.description, book.getMetadata().getDescriptions(), serializer);
        writeSimpleMetdataElements(DCTags.publisher, book.getMetadata().getPublishers(), serializer);
        writeSimpleMetdataElements(DCTags.type, book.getMetadata().getTypes(), serializer);
        writeSimpleMetdataElements(DCTags.rights, book.getMetadata().getRights(), serializer);

        Calendar today = new GregorianCalendar();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("hh:mm:ss");

        serializer.startTag(null, "meta");
        serializer.attribute(null, "property", "dcterms:" + DCTags.modified);
        serializer.text(df1.format(today.getTime()) + "T" + df2.format(today.getTime()) + "Z");
        serializer.endTag(null, "meta");

        ArrayList<String> list = new ArrayList<String>();
        list.add(book.getMetadata().getLanguage());
        writeSimpleMetdataElements(DCTags.language, list, serializer);

        // write authors
        for (int i = 0; i < book.getMetadata().getAuthors().size(); i++) {
            Author author = book.getMetadata().getAuthors().get(i);
            serializer.startTag(NAMESPACE_DUBLIN_CORE, DCTags.creator);
            //serializer.attribute(NAMESPACE_OPF, OPFAttributes.role, author.getRelator().getCode());
            serializer.attribute(null, "id", "aut" + i);
            serializer.text(author.getFirstname() + " " + author.getLastname());
            serializer.endTag(NAMESPACE_DUBLIN_CORE, DCTags.creator);

            serializer.startTag(null, "meta");
            serializer.attribute(null, "refines", "#aut" + i);
            serializer.attribute(null, "property", OPFAttributes.role);
            serializer.attribute(null, "scheme", PREFIX_MARC + ":" + "relators");
            serializer.text("Aut");
            serializer.endTag(null, "meta");

            serializer.startTag(null, "meta");
            serializer.attribute(null, "refines", "#aut" + i);
            serializer.attribute(null, "property", OPFAttributes.file_as);
            serializer.text(author.getLastname() + ", " + author.getFirstname());
            serializer.endTag(null, "meta");
        }

        // write contributors
        for (Author author : book.getMetadata().getContributors()) {
            serializer.startTag(NAMESPACE_DUBLIN_CORE, DCTags.contributor);
            serializer.attribute(NAMESPACE_OPF, OPFAttributes.role, author.getRelator().getCode());
            serializer.attribute(NAMESPACE_OPF, OPFAttributes.file_as, author.getLastname() + ", " + author.getFirstname());
            serializer.text(author.getFirstname() + " " + author.getLastname());
            serializer.endTag(NAMESPACE_DUBLIN_CORE, DCTags.contributor);
        }

        // write dates
        for (Date date : book.getMetadata().getDates()) {
            serializer.startTag(NAMESPACE_DUBLIN_CORE, DCTags.date);
            if (date.getEvent() != null) {
                serializer.attribute(NAMESPACE_OPF, OPFAttributes.event, date.getEvent().toString());
            }
            serializer.text(date.getValue());
            serializer.endTag(NAMESPACE_DUBLIN_CORE, DCTags.date);
        }

        // write language
        if (StringUtil.isNotBlank(book.getMetadata().getLanguage())) {
            serializer.startTag(NAMESPACE_DUBLIN_CORE, "language");
            serializer.text(book.getMetadata().getLanguage());
            serializer.endTag(NAMESPACE_DUBLIN_CORE, "language");
        }

        // write other properties
        if (book.getMetadata().getOtherProperties() != null) {
            for (Map.Entry<QName, String> mapEntry : book.getMetadata().getOtherProperties().entrySet()) {
                serializer.startTag(mapEntry.getKey().getNamespaceURI(), mapEntry.getKey().getLocalPart());
                serializer.text(mapEntry.getValue());
                serializer.endTag(mapEntry.getKey().getNamespaceURI(), mapEntry.getKey().getLocalPart());

            }
        }

        // write coverimage
        if (book.getCoverImage() != null) { // write the cover image
            serializer.startTag(NAMESPACE_OPF, OPFTags.meta);
            serializer.attribute(Epub2Writer.EMPTY_NAMESPACE_PREFIX, OPFAttributes.name, OPFValues.meta_cover);
            serializer.attribute(Epub2Writer.EMPTY_NAMESPACE_PREFIX, OPFAttributes.content, book.getCoverImage().getId());
            serializer.endTag(NAMESPACE_OPF, OPFTags.meta);
        }

        // write generator
        serializer.startTag(NAMESPACE_OPF, OPFTags.meta);
        serializer.attribute(Epub2Writer.EMPTY_NAMESPACE_PREFIX, OPFAttributes.name, OPFValues.generator);
        serializer.attribute(Epub2Writer.EMPTY_NAMESPACE_PREFIX, OPFAttributes.content, Constants.EPUBLIB_GENERATOR_NAME);
        serializer.endTag(NAMESPACE_OPF, OPFTags.meta);

        serializer.endTag(null, OPFTags.metadata);
    }

    private static void writeSimpleMetdataElements(String tagName, List<String> values, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        for (String value : values) {

            if (StringUtil.isBlank(value)) {
                continue;
            }

            serializer.startTag(NAMESPACE_DUBLIN_CORE, tagName);
            serializer.text(value);
            serializer.endTag(NAMESPACE_DUBLIN_CORE, tagName);
        }
    }

    /**
     * Writes out the complete list of Identifiers to the package document. The
     * first identifier for which the bookId is true is made the bookId
     * identifier. If no identifier has bookId == true then the first bookId
     * identifier is written as the primary.
     *
     * @param identifiers
     * @param serializer
     * @throws IOException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     * @
     */
    private static void writeIdentifiers(List<Identifier> identifiers, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        Identifier bookIdIdentifier = Identifier.getBookIdIdentifier(identifiers);

        if (bookIdIdentifier == null) {
            return;
        }

        serializer.startTag(NAMESPACE_DUBLIN_CORE, DCTags.identifier);
        serializer.attribute(Epub2Writer.EMPTY_NAMESPACE_PREFIX, DCAttributes.id, BOOK_ID_ID);
        //serializer.attribute(NAMESPACE_OPF, OPFAttributes.scheme, bookIdIdentifier.getScheme());
        serializer.text(bookIdIdentifier.getValue());
        serializer.endTag(NAMESPACE_DUBLIN_CORE, DCTags.identifier);

        for (Identifier identifier : identifiers.subList(1, identifiers.size())) {

            if (identifier == bookIdIdentifier) {
                continue;
            }

            serializer.startTag(NAMESPACE_DUBLIN_CORE, DCTags.identifier);
            //serializer.attribute(NAMESPACE_OPF, "scheme", identifier.getScheme());
            serializer.text(identifier.getValue());
            serializer.endTag(NAMESPACE_DUBLIN_CORE, DCTags.identifier);
        }
    }

}
