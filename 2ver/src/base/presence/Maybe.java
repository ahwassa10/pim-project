package base.presence;

public interface Maybe<T> extends MaybeSome<T> {
    One<T> certainly();
}