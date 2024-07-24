// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package fr.unice.scale.latencyaware.producer;

/**
 * Protobuf type {@code ArrivalRequest}
 */
public final class ArrivalRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ArrivalRequest)
    ArrivalRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ArrivalRequest.newBuilder() to construct.
  private ArrivalRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ArrivalRequest() {
    arrivalrequest_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ArrivalRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ArrivalRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            arrivalrequest_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            fr.unice.scale.latencyaware.producer.ArrivalRequest.class, fr.unice.scale.latencyaware.producer.ArrivalRequest.Builder.class);
  }

  public static final int ARRIVALREQUEST_FIELD_NUMBER = 1;
  private volatile java.lang.Object arrivalrequest_;
  /**
   * <code>string arrivalrequest = 1;</code>
   * @return The arrivalrequest.
   */
  @java.lang.Override
  public java.lang.String getArrivalrequest() {
    java.lang.Object ref = arrivalrequest_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      arrivalrequest_ = s;
      return s;
    }
  }
  /**
   * <code>string arrivalrequest = 1;</code>
   * @return The bytes for arrivalrequest.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getArrivalrequestBytes() {
    java.lang.Object ref = arrivalrequest_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      arrivalrequest_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(arrivalrequest_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, arrivalrequest_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(arrivalrequest_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, arrivalrequest_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof fr.unice.scale.latencyaware.producer.ArrivalRequest)) {
      return super.equals(obj);
    }
    fr.unice.scale.latencyaware.producer.ArrivalRequest other = (fr.unice.scale.latencyaware.producer.ArrivalRequest) obj;

    if (!getArrivalrequest()
        .equals(other.getArrivalrequest())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ARRIVALREQUEST_FIELD_NUMBER;
    hash = (53 * hash) + getArrivalrequest().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(fr.unice.scale.latencyaware.producer.ArrivalRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ArrivalRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ArrivalRequest)
      fr.unice.scale.latencyaware.producer.ArrivalRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              fr.unice.scale.latencyaware.producer.ArrivalRequest.class, fr.unice.scale.latencyaware.producer.ArrivalRequest.Builder.class);
    }

    // Construct using fr.unice.scale.latencyaware.producer.ArrivalRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      arrivalrequest_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalRequest_descriptor;
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalRequest getDefaultInstanceForType() {
      return fr.unice.scale.latencyaware.producer.ArrivalRequest.getDefaultInstance();
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalRequest build() {
      fr.unice.scale.latencyaware.producer.ArrivalRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalRequest buildPartial() {
      fr.unice.scale.latencyaware.producer.ArrivalRequest result = new fr.unice.scale.latencyaware.producer.ArrivalRequest(this);
      result.arrivalrequest_ = arrivalrequest_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof fr.unice.scale.latencyaware.producer.ArrivalRequest) {
        return mergeFrom((fr.unice.scale.latencyaware.producer.ArrivalRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(fr.unice.scale.latencyaware.producer.ArrivalRequest other) {
      if (other == fr.unice.scale.latencyaware.producer.ArrivalRequest.getDefaultInstance()) return this;
      if (!other.getArrivalrequest().isEmpty()) {
        arrivalrequest_ = other.arrivalrequest_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      fr.unice.scale.latencyaware.producer.ArrivalRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (fr.unice.scale.latencyaware.producer.ArrivalRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object arrivalrequest_ = "";
    /**
     * <code>string arrivalrequest = 1;</code>
     * @return The arrivalrequest.
     */
    public java.lang.String getArrivalrequest() {
      java.lang.Object ref = arrivalrequest_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        arrivalrequest_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string arrivalrequest = 1;</code>
     * @return The bytes for arrivalrequest.
     */
    public com.google.protobuf.ByteString
        getArrivalrequestBytes() {
      java.lang.Object ref = arrivalrequest_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        arrivalrequest_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string arrivalrequest = 1;</code>
     * @param value The arrivalrequest to set.
     * @return This builder for chaining.
     */
    public Builder setArrivalrequest(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      arrivalrequest_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string arrivalrequest = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearArrivalrequest() {
      
      arrivalrequest_ = getDefaultInstance().getArrivalrequest();
      onChanged();
      return this;
    }
    /**
     * <code>string arrivalrequest = 1;</code>
     * @param value The bytes for arrivalrequest to set.
     * @return This builder for chaining.
     */
    public Builder setArrivalrequestBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      arrivalrequest_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ArrivalRequest)
  }

  // @@protoc_insertion_point(class_scope:ArrivalRequest)
  private static final fr.unice.scale.latencyaware.producer.ArrivalRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new fr.unice.scale.latencyaware.producer.ArrivalRequest();
  }

  public static fr.unice.scale.latencyaware.producer.ArrivalRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ArrivalRequest>
      PARSER = new com.google.protobuf.AbstractParser<ArrivalRequest>() {
    @java.lang.Override
    public ArrivalRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ArrivalRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ArrivalRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ArrivalRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public fr.unice.scale.latencyaware.producer.ArrivalRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
