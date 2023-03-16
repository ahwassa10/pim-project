package information;

public interface Info {
	InfoPair<? extends Info> asInfoPair();
	InfoType getInfoType();
}
