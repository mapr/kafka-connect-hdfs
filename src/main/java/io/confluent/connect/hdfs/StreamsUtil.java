package io.confluent.connect.hdfs;

public class StreamsUtil {

    /**
     * For Streams topic remove starting '/' and replace
     * all other '/' and ':' by '_'.
     *
     * For all topic names passed in
     * remove non-alphanumeric or non '_' characters
     *
     * @param topic topic to be escaped
     * @return valid string that can be used
     * as part of MapR-FS path or as a hive table name.
     */
    public static String escapeTopic(String topic) {
        if (topic.startsWith("/") && topic.contains(":")) {
            topic = topic.substring(1).replaceAll("[/:]", "_");
        }
        // remove all non-alphanumeric or non '_' characters
        return topic.replaceAll("[^A-Za-z0-9_]", "");
    }

}
