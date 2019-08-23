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

package io.confluent.connect.hdfs.filter;

import org.apache.hadoop.fs.Path;
import org.apache.kafka.common.TopicPartition;

import io.confluent.connect.hdfs.HdfsSinkConnectorConstants;

public class TopicPartitionCommittedFileFilter extends CommittedFileFilter {
  private TopicPartition tp;

  public TopicPartitionCommittedFileFilter(TopicPartition tp) {
    this.tp = tp;
  }

  @Override
  public boolean accept(Path path) {
    if (!super.accept(path)) {
      return false;
    }
    String filename = path.getName();
    String[] parts = filename.split(HdfsSinkConnectorConstants.COMMMITTED_FILENAME_SEPARATOR_REGEX);
    String topic = "/".concat(parts[0].replace('_', ':'));
    int partition = Integer.parseInt(parts[1]);
    return topic.equals(tp.topic().replaceAll("-", "")) && partition == tp.partition();
  }
}
