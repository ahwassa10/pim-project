package quality;

public interface Quality {
    static Quality from(String agent, String type, String entity, String data) {
        return new SimpleQuality(agent, type, entity, data);
    }

    default boolean equalTo(Quality other) {
        return getAgent().equals(other.getAgent()) &&
               getType().equals(other.getType()) &&
               getEntity().equals(other.getEntity()) &&
               getData().equals(other.getData());
    }

    public String getAgent();

    public String getType();

    public String getEntity();

    public String getData();
}
