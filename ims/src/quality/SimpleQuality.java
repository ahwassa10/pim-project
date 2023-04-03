package quality;

final class SimpleQuality implements Quality {
    private final String agent;
    private final String type;
    private final String entity;
    private final String data;

    SimpleQuality(String agent, String type, String entity, String data) {
        this.agent = agent;
        this.type = type;
        this.entity = entity;
        this.data = data;
    }

    public String getAgent() {
        return agent;
    }

    public String getType() {
        return type;
    }

    public String getEntity() {
        return entity;
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return String.format("Quality<Agent: %s, Type: %s, Entity: %s, Data: %s>",
                agent, type, entity, data);
    }
}
