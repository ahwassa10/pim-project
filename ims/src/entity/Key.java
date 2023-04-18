package entity;

public interface Key {
    String asString();
    Key getStem();
    Key getTip();
    int length();
}
