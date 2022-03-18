// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route.proto

package route;

public interface RequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:route.Request)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int64 offset = 1;</code>
   * @return Whether the offset field is set.
   */
  boolean hasOffset();
  /**
   * <code>optional int64 offset = 1;</code>
   * @return The offset.
   */
  long getOffset();

  /**
   * <code>int64 origin = 2;</code>
   * @return The origin.
   */
  long getOrigin();

  /**
   * <code>int64 destination = 3;</code>
   * @return The destination.
   */
  long getDestination();

  /**
   * <code>string path = 4;</code>
   * @return The path.
   */
  java.lang.String getPath();
  /**
   * <code>string path = 4;</code>
   * @return The bytes for path.
   */
  com.google.protobuf.ByteString
      getPathBytes();

  /**
   * <code>int64 response_time = 5;</code>
   * @return The responseTime.
   */
  long getResponseTime();

  /**
   * <code>int64 chunk_size = 6;</code>
   * @return The chunkSize.
   */
  long getChunkSize();

  /**
   * <code>optional int64 package_index = 7;</code>
   * @return Whether the packageIndex field is set.
   */
  boolean hasPackageIndex();
  /**
   * <code>optional int64 package_index = 7;</code>
   * @return The packageIndex.
   */
  long getPackageIndex();

  /**
   * <code>optional int64 time_sum = 8;</code>
   * @return Whether the timeSum field is set.
   */
  boolean hasTimeSum();
  /**
   * <code>optional int64 time_sum = 8;</code>
   * @return The timeSum.
   */
  long getTimeSum();

  /**
   * <code>optional bool slow_start_completed = 9;</code>
   * @return Whether the slowStartCompleted field is set.
   */
  boolean hasSlowStartCompleted();
  /**
   * <code>optional bool slow_start_completed = 9;</code>
   * @return The slowStartCompleted.
   */
  boolean getSlowStartCompleted();

  /**
   * <code>int32 algorithm = 10;</code>
   * @return The algorithm.
   */
  int getAlgorithm();
}