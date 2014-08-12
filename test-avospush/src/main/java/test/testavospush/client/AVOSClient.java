package test.testavospush.client;

public interface AVOSClient {
    String pushToInstallationId(String installationId, String msg);
}
