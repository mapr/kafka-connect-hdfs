/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.connect.hdfs;

public class StreamsUtil {

  /**
   * For Streams topic remove starting '/' and replace
   * all other '/' and ':' by '_'.
   *
   * <p>For all topic names passed in
   * remove non-alphanumeric or non '_' characters
   *
   * @param topic topic to be escaped
   * @return valid string that can be used as part of MapR-FS path or as a hive table name.
   */
  public static String escapeTopic(String topic) {
    if (topic.startsWith("/") && topic.contains(":")) {
      topic = topic.substring(1).replaceAll("[/:]", "_");
    }
    // remove all non-alphanumeric or non '_' characters
    return topic.replaceAll("[^A-Za-z0-9_]", "");
  }
}
