package nl.siegmann.epublib.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import nl.siegmann.epublib.service.MediatypeService;
import nl.siegmann.epublib.util.StringUtil;

/**
 * A Book's collection of Metadata. In the future it should contain all Dublin
 * Core attributes, for now it contains a set of often-used ones.
 *
 * @author paul
 *
 */
public class Metadata implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2437262888962149444L;

    public static final String DEFAULT_LANGUAGE = "en";

    private boolean autoGeneratedId = true;
    private List<CreatorContributor> contributors = new ArrayList<CreatorContributor>();
    private List<CreatorContributor> creators = new ArrayList<CreatorContributor>();
    private List<Date> dates = new ArrayList<Date>();
    private String language = DEFAULT_LANGUAGE;
    private Map<QName, String> otherProperties = new HashMap<QName, String>();
    private List<String> rights = new ArrayList<String>();
    private List<String> titles = new ArrayList<String>();
    private List<Identifier> identifiers = new ArrayList<Identifier>();
    private List<String> subjects = new ArrayList<String>();
    private String format = MediatypeService.EPUB.getName();
    private List<String> types = new ArrayList<String>();
    private List<String> descriptions = new ArrayList<String>();
    private List<String> publishers = new ArrayList<String>();
    private Map<String, String> metaAttributes = new HashMap<String, String>();
    private List<String> coverages = new ArrayList<String>();
    private List<String> relations = new ArrayList<String>();
    private List<String> sources = new ArrayList<String>();

    public Metadata() {
        identifiers.add(new Identifier());
        autoGeneratedId = true;
    }

    public boolean isAutoGeneratedId() {
        return autoGeneratedId;
    }

    /**
     * Metadata properties not hard-coded like the author, title, etc.
     *
     * @return Metadata properties not hard-coded like the author, title, etc.
     */
    public Map<QName, String> getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(Map<QName, String> otherProperties) {
        this.otherProperties = otherProperties;
    }

    public Date addDate(Date date) {
        this.dates.add(date);
        return date;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public CreatorContributor addContributor(CreatorContributor contributor) {
        contributors.add(contributor);
        return contributor;
    }

    public List<CreatorContributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<CreatorContributor> contributors) {
        this.contributors = contributors;
    }

    public CreatorContributor addCreator(CreatorContributor creator) {
        creators.add(creator);
        return creator;
    }

    public List<CreatorContributor> getCreators() {
        return creators;
    }

    public void setCreators(List<CreatorContributor> creators) {
        this.creators = creators;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setRights(List<String> rights) {
        this.rights = rights;
    }

    public List<String> getRights() {
        return rights;
    }

    /**
     * Gets the first non-blank title of the book. Will return "" if no title
     * found.
     *
     * @return the first non-blank title of the book.
     */
    public String getFirstTitle() {
        if (titles == null || titles.isEmpty()) {
            return "";
        }
        for (String title : titles) {
            if (StringUtil.isNotBlank(title)) {
                return title;
            }
        }
        return "";
    }

    public String addTitle(String title) {
        this.titles.add(title);
        return title;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getTitles() {
        return titles;
    }

    public String addPublisher(String publisher) {
        this.publishers.add(publisher);
        return publisher;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public String addDescription(String description) {
        this.descriptions.add(description);
        return description;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public Identifier addIdentifier(Identifier identifier) {
        if (autoGeneratedId && (!(identifiers.isEmpty()))) {
            identifiers.set(0, identifier);
        } else {
            identifiers.add(identifier);
        }
        autoGeneratedId = false;
        return identifier;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
        autoGeneratedId = false;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String addType(String type) {
        this.types.add(type);
        return type;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getMetaAttribute(String name) {
        return metaAttributes.get(name);
    }

    public void setMetaAttributes(Map<String, String> metaAttributes) {
        this.metaAttributes = metaAttributes;
    }

    public List<CreatorContributor> getAuthors() {
        ArrayList<CreatorContributor> authors = new ArrayList<CreatorContributor>();

        for (CreatorContributor c : creators) {
            if (c.getRelators().contains(Relator.AUTHOR)) {
                authors.add(c);
            }
        }

        for (CreatorContributor c : contributors) {
            if (c.getRelators().contains(Relator.AUTHOR)) {
                authors.add(c);
            }
        }

        return authors;
    }

    public List<String> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<String> coverages) {
        this.coverages = coverages;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
}
