// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package fr.unice.scale.latencyaware.producer;

/**
 * Protobuf type {@code ArrivalResponse}
 */
public final class ArrivalResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ArrivalResponse)
    ArrivalResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ArrivalResponse.newBuilder() to construct.
  private ArrivalResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ArrivalResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ArrivalResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ArrivalResponse(
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
          case 13: {

            arrival_ = input.readFloat();
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
    return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            fr.unice.scale.latencyaware.producer.ArrivalResponse.class, fr.unice.scale.latencyaware.producer.ArrivalResponse.Builder.class);
  }

  public static final int ARRIVAL_FIELD_NUMBER = 1;
  private float arrival_;
  /**
   * <code>float arrival = 1;</code>
   * @return The arrival.
   */
  @java.lang.Override
  public float getArrival() {
    return arrival_;
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
    if (java.lang.Float.floatToRawIntBits(arrival_) != 0) {
      output.writeFloat(1, arrival_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (java.lang.Float.floatToRawIntBits(arrival_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(1, arrival_);
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
    if (!(obj instanceof fr.unice.scale.latencyaware.producer.ArrivalResponse)) {
      return super.equals(obj);
    }
    fr.unice.scale.latencyaware.producer.ArrivalResponse other = (fr.unice.scale.latencyaware.producer.ArrivalResponse) obj;

    if (java.lang.Float.floatToIntBits(getArrival())
        != java.lang.Float.floatToIntBits(
            other.getArrival())) return false;
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
    hash = (37 * hash) + ARRIVAL_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getArrival());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static fr.unice.scale.latencyaware.producer.ArrivalResponse parseFrom(
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
  public static Builder newBuilder(fr.unice.scale.latencyaware.producer.ArrivalResponse prototype) {
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
   * Protobuf type {@code ArrivalResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ArrivalResponse)
      fr.unice.scale.latencyaware.producer.ArrivalResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              fr.unice.scale.latencyaware.producer.ArrivalResponse.class, fr.unice.scale.latencyaware.producer.ArrivalResponse.Builder.class);
    }

    // Construct using fr.unice.scale.latencyaware.producer.ArrivalResponse.newBuilder()
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
      arrival_ = 0F;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return fr.unice.scale.latencyaware.producer.Service.internal_static_ArrivalResponse_descriptor;
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalResponse getDefaultInstanceForType() {
      return fr.unice.scale.latencyaware.producer.ArrivalResponse.getDefaultInstance();
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalResponse build() {
      fr.unice.scale.latencyaware.producer.ArrivalResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public fr.unice.scale.latencyaware.producer.ArrivalResponse buildPartial() {
      fr.unice.scale.latencyaware.producer.ArrivalResponse result = new fr.unice.scale.latencyaware.producer.ArrivalResponse(this);
      result.arrival_ = arrival_;
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
      if (other instanceof fr.unice.scale.latencyaware.producer.ArrivalResponse) {
        return mergeFrom((fr.unice.scale.latencyaware.producer.ArrivalResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(fr.unice.scale.latencyaware.producer.ArrivalResponse other) {
      if (other == fr.unice.scale.latencyaware.producer.ArrivalResponse.getDefaultInstance()) return this;
      if (other.getArrival() != 0F) {
        setArrival(other.getArrival());
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
      fr.unice.scale.latencyaware.producer.ArrivalResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (fr.unice.scale.latencyaware.producer.ArrivalResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private float arrival_ ;
    /**
     * <code>float arrival = 1;</code>
     * @return The arrival.
     */
    @java.lang.Override
    public float getArrival() {
      return arrival_;
    }
    /**
     * <code>float arrival = 1;</code>
     * @param value The arrival to set.
     * @return This builder for chaining.
     */
    public Builder setArrival(float value) {
      
      arrival_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>float arrival = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearArrival() {
      
      arrival_ = 0F;
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


    // @@protoc_insertion_point(builder_scope:ArrivalResponse)
  }

  // @@protoc_insertion_point(class_scope:ArrivalResponse)
  private static final fr.unice.scale.latencyaware.producer.ArrivalResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new fr.unice.scale.latencyaware.producer.ArrivalResponse();
  }

  public static fr.unice.scale.latencyaware.producer.ArrivalResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ArrivalResponse>
      PARSER = new com.google.protobuf.AbstractParser<ArrivalResponse>() {
    @java.lang.Override
    public ArrivalResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ArrivalResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ArrivalResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ArrivalResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public fr.unice.scale.latencyaware.producer.ArrivalResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

