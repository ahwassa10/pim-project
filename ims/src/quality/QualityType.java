package quality;

public interface QualityType {
    static String from(String agent, String type) {
        return agent + ":" + type;
    }
}
