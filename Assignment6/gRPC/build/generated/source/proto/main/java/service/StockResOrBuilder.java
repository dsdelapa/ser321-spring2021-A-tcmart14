// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: services/stock.proto

package service;

public interface StockResOrBuilder extends
    // @@protoc_insertion_point(interface_extends:services.StockRes)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool success = 1;</code>
   * @return The success.
   */
  boolean getSuccess();

  /**
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>double quote = 3;</code>
   * @return The quote.
   */
  double getQuote();
}
