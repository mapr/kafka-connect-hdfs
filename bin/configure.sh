#!/bin/bash
# Configure classpath depending on environment components installed.
# This script should be invoked after each Hive is installed/reinstalled on
# the local node.

base_dir="$( cd "$(dirname "$0")/.." ; pwd -P )"

# Include hive jars
hive_base="/opt/mapr/hive/hive-*"

declare -a hive_jars=(
"$hive_base/lib/hive-ant-*.jar" \
"$hive_base/lib/hive-cli-*.jar" \
"$hive_base/lib/hive-common-*.jar" \
"$hive_base/lib/hive-exec-*.jar" \
"$hive_base/hcatalog/share/hcatalog/hive-hcatalog-core-*.jar" \
"$hive_base/lib/hive-metastore-*.jar" \
"$hive_base/lib/hive-serde-*.jar" \
"$hive_base/lib/hive-service-*.jar" \
"$hive_base/lib/hive-shims-*.jar" \
"$hive_base/lib/hive-shims-0.23-*.jar" \
"$hive_base/lib/hive-shims-0.20S-*.jar" \
"$hive_base/lib/hive-shims-common-*.jar" \
"$hive_base/lib/hive-shims-scheduler-*.jar" \
)

# $1 - src jar
# $2 - dest dir
# $3 - jar name wildcard that is used to remove old jars in the dest directory before creating symlink
function create_symlinc
{
    rm -Rf "$2/$3"
    echo "Creating symlink $1 -> $2"
    ln -sf "$1" "$2"
}

# This jar name cannot fit to the below algorithm because of conflicts so we process it separately
shims_jar=$(ls /opt/mapr/hive/hive-*/lib/hive-shims-*.jar | grep -v '0.20S\|0.23\|common\|scheduler')
if [ -f "$shims_jar" ]; then
    old_jar_path=$(ls $base_dir/share/java/kafka-connect-hdfs/hive-shims-*.jar | grep -v '0.20S\|0.23\|common\|scheduler')
    old_jar=${old_jar_path##*/}
    create_symlinc "$shims_jar" "$base_dir/share/java/kafka-connect-hdfs/." "$old_jar"
fi

for file in "${hive_jars[@]}"
do
    jar_name_wildcard=${file##*/}
    file=$(ls ${file})

    if [ -f "$file" ]; then
        create_symlinc "$file" "$base_dir/share/java/kafka-connect-hdfs/." "$jar_name_wildcard"

    else
        echo "Warning! Symlink was not created, missing $file"
    fi
done