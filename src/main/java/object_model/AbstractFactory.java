package object_model;

public abstract class AbstractFactory {

    public abstract ObjectMetric getObjectMetric();
    public abstract Extractor getExtractor();
    public abstract Searcher getSearcher();
    public abstract Comparator getComparator();
}
