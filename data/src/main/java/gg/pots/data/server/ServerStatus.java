package gg.pots.data.server;

public enum ServerStatus {

    ONLINE("&aOnline"),
    OFFLINE("&cOffline"),
    WHITELISTED("&dWhitelisted"),
    LOADING("&eLoading...");

    private final String format;

    /**
     * Enum constructor
     *
     * @param format The format of the status
     */
    ServerStatus(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
