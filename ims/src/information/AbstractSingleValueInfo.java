package information;

public abstract class AbstractSingleValueInfo<T> implements Info {
	private final T value;
	
	public AbstractSingleValueInfo(T value) {
		this.value = value;
	}
	
	public boolean equals(Object o) {
		if (o == this) {return true;}
		if (!(o instanceof AbstractSingleValueInfo<?>)) {return false;}
		
		AbstractSingleValueInfo<?> asvi = (AbstractSingleValueInfo<?>) o;
		return value.equals(asvi.value);
	}
	
	public T get() {
		return value;
	}
	
	public int hashCode() {
		return value.hashCode();
	}
	
	public String toString() {
		return String.format("%s<%s>", getInfoType().getName(), get());
	}
}
