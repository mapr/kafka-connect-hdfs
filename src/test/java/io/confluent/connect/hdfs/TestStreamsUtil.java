package io.confluent.connect.hdfs;

import org.junit.Assert;
import org.junit.Test;

public class TestStreamsUtil {

    @Test
    public void testEscapeStreamsTopic() {
        String streamsTopic = "/name1/name-2/name+3/name$4:topic";
        String escaped = StreamsUtil.escapeTopic(streamsTopic);
        Assert.assertEquals("name1_name2_name3_name4_topic", escaped);
    }

    @Test
    public void testEscapeAlphanumeric() {
        String toBeEscaped = "simple_topic_name";
        Assert.assertEquals(toBeEscaped, StreamsUtil.escapeTopic(toBeEscaped));
    }

    @Test
    public void testEscapeNonAlphanumeric() {
        String toBeEscaped = "simple-topic-name";
        Assert.assertEquals("simpletopicname", StreamsUtil.escapeTopic(toBeEscaped));
    }
}
