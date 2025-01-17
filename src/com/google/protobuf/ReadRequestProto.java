// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ds/hdfs/readRequest.proto

package com.google.protobuf;

public final class ReadRequestProto {
  private ReadRequestProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ReadRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ds.hdfs.ReadRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string filename = 1;</code>
     * @return Whether the filename field is set.
     */
    boolean hasFilename();
    /**
     * <code>required string filename = 1;</code>
     * @return The filename.
     */
    java.lang.String getFilename();
    /**
     * <code>required string filename = 1;</code>
     * @return The bytes for filename.
     */
    com.google.protobuf.ByteString
        getFilenameBytes();

    /**
     * <code>required int32 blockNumber = 2;</code>
     * @return Whether the blockNumber field is set.
     */
    boolean hasBlockNumber();
    /**
     * <code>required int32 blockNumber = 2;</code>
     * @return The blockNumber.
     */
    int getBlockNumber();
  }
  /**
   * Protobuf type {@code ds.hdfs.ReadRequest}
   */
  public  static final class ReadRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ds.hdfs.ReadRequest)
      ReadRequestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ReadRequest.newBuilder() to construct.
    private ReadRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ReadRequest() {
      filename_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ReadRequest();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ReadRequest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
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
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              filename_ = bs;
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              blockNumber_ = input.readInt32();
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
      return com.google.protobuf.ReadRequestProto.internal_static_ds_hdfs_ReadRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.protobuf.ReadRequestProto.internal_static_ds_hdfs_ReadRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.protobuf.ReadRequestProto.ReadRequest.class, com.google.protobuf.ReadRequestProto.ReadRequest.Builder.class);
    }

    private int bitField0_;
    public static final int FILENAME_FIELD_NUMBER = 1;
    private volatile java.lang.Object filename_;
    /**
     * <code>required string filename = 1;</code>
     * @return Whether the filename field is set.
     */
    public boolean hasFilename() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required string filename = 1;</code>
     * @return The filename.
     */
    public java.lang.String getFilename() {
      java.lang.Object ref = filename_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          filename_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string filename = 1;</code>
     * @return The bytes for filename.
     */
    public com.google.protobuf.ByteString
        getFilenameBytes() {
      java.lang.Object ref = filename_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        filename_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int BLOCKNUMBER_FIELD_NUMBER = 2;
    private int blockNumber_;
    /**
     * <code>required int32 blockNumber = 2;</code>
     * @return Whether the blockNumber field is set.
     */
    public boolean hasBlockNumber() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>required int32 blockNumber = 2;</code>
     * @return The blockNumber.
     */
    public int getBlockNumber() {
      return blockNumber_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasFilename()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasBlockNumber()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, filename_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        output.writeInt32(2, blockNumber_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, filename_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, blockNumber_);
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
      if (!(obj instanceof com.google.protobuf.ReadRequestProto.ReadRequest)) {
        return super.equals(obj);
      }
      com.google.protobuf.ReadRequestProto.ReadRequest other = (com.google.protobuf.ReadRequestProto.ReadRequest) obj;

      if (hasFilename() != other.hasFilename()) return false;
      if (hasFilename()) {
        if (!getFilename()
            .equals(other.getFilename())) return false;
      }
      if (hasBlockNumber() != other.hasBlockNumber()) return false;
      if (hasBlockNumber()) {
        if (getBlockNumber()
            != other.getBlockNumber()) return false;
      }
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
      if (hasFilename()) {
        hash = (37 * hash) + FILENAME_FIELD_NUMBER;
        hash = (53 * hash) + getFilename().hashCode();
      }
      if (hasBlockNumber()) {
        hash = (37 * hash) + BLOCKNUMBER_FIELD_NUMBER;
        hash = (53 * hash) + getBlockNumber();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.google.protobuf.ReadRequestProto.ReadRequest parseFrom(
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
    public static Builder newBuilder(com.google.protobuf.ReadRequestProto.ReadRequest prototype) {
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
     * Protobuf type {@code ds.hdfs.ReadRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ds.hdfs.ReadRequest)
        com.google.protobuf.ReadRequestProto.ReadRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.google.protobuf.ReadRequestProto.internal_static_ds_hdfs_ReadRequest_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.google.protobuf.ReadRequestProto.internal_static_ds_hdfs_ReadRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.google.protobuf.ReadRequestProto.ReadRequest.class, com.google.protobuf.ReadRequestProto.ReadRequest.Builder.class);
      }

      // Construct using com.google.protobuf.ReadRequestProto.ReadRequest.newBuilder()
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
        filename_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        blockNumber_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.google.protobuf.ReadRequestProto.internal_static_ds_hdfs_ReadRequest_descriptor;
      }

      @java.lang.Override
      public com.google.protobuf.ReadRequestProto.ReadRequest getDefaultInstanceForType() {
        return com.google.protobuf.ReadRequestProto.ReadRequest.getDefaultInstance();
      }

      @java.lang.Override
      public com.google.protobuf.ReadRequestProto.ReadRequest build() {
        com.google.protobuf.ReadRequestProto.ReadRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.google.protobuf.ReadRequestProto.ReadRequest buildPartial() {
        com.google.protobuf.ReadRequestProto.ReadRequest result = new com.google.protobuf.ReadRequestProto.ReadRequest(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.filename_ = filename_;
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.blockNumber_ = blockNumber_;
          to_bitField0_ |= 0x00000002;
        }
        result.bitField0_ = to_bitField0_;
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
        if (other instanceof com.google.protobuf.ReadRequestProto.ReadRequest) {
          return mergeFrom((com.google.protobuf.ReadRequestProto.ReadRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.google.protobuf.ReadRequestProto.ReadRequest other) {
        if (other == com.google.protobuf.ReadRequestProto.ReadRequest.getDefaultInstance()) return this;
        if (other.hasFilename()) {
          bitField0_ |= 0x00000001;
          filename_ = other.filename_;
          onChanged();
        }
        if (other.hasBlockNumber()) {
          setBlockNumber(other.getBlockNumber());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasFilename()) {
          return false;
        }
        if (!hasBlockNumber()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.ReadRequestProto.ReadRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.google.protobuf.ReadRequestProto.ReadRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object filename_ = "";
      /**
       * <code>required string filename = 1;</code>
       * @return Whether the filename field is set.
       */
      public boolean hasFilename() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required string filename = 1;</code>
       * @return The filename.
       */
      public java.lang.String getFilename() {
        java.lang.Object ref = filename_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            filename_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string filename = 1;</code>
       * @return The bytes for filename.
       */
      public com.google.protobuf.ByteString
          getFilenameBytes() {
        java.lang.Object ref = filename_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          filename_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string filename = 1;</code>
       * @param value The filename to set.
       * @return This builder for chaining.
       */
      public Builder setFilename(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        filename_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string filename = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearFilename() {
        bitField0_ = (bitField0_ & ~0x00000001);
        filename_ = getDefaultInstance().getFilename();
        onChanged();
        return this;
      }
      /**
       * <code>required string filename = 1;</code>
       * @param value The bytes for filename to set.
       * @return This builder for chaining.
       */
      public Builder setFilenameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        filename_ = value;
        onChanged();
        return this;
      }

      private int blockNumber_ ;
      /**
       * <code>required int32 blockNumber = 2;</code>
       * @return Whether the blockNumber field is set.
       */
      public boolean hasBlockNumber() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>required int32 blockNumber = 2;</code>
       * @return The blockNumber.
       */
      public int getBlockNumber() {
        return blockNumber_;
      }
      /**
       * <code>required int32 blockNumber = 2;</code>
       * @param value The blockNumber to set.
       * @return This builder for chaining.
       */
      public Builder setBlockNumber(int value) {
        bitField0_ |= 0x00000002;
        blockNumber_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 blockNumber = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearBlockNumber() {
        bitField0_ = (bitField0_ & ~0x00000002);
        blockNumber_ = 0;
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


      // @@protoc_insertion_point(builder_scope:ds.hdfs.ReadRequest)
    }

    // @@protoc_insertion_point(class_scope:ds.hdfs.ReadRequest)
    private static final com.google.protobuf.ReadRequestProto.ReadRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.google.protobuf.ReadRequestProto.ReadRequest();
    }

    public static com.google.protobuf.ReadRequestProto.ReadRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<ReadRequest>
        PARSER = new com.google.protobuf.AbstractParser<ReadRequest>() {
      @java.lang.Override
      public ReadRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ReadRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ReadRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<ReadRequest> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.ReadRequestProto.ReadRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ds_hdfs_ReadRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ds_hdfs_ReadRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031ds/hdfs/readRequest.proto\022\007ds.hdfs\"4\n\013" +
      "ReadRequest\022\020\n\010filename\030\001 \002(\t\022\023\n\013blockNu" +
      "mber\030\002 \002(\005B\'\n\023com.google.protobufB\020ReadR" +
      "equestProto"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ds_hdfs_ReadRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ds_hdfs_ReadRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ds_hdfs_ReadRequest_descriptor,
        new java.lang.String[] { "Filename", "BlockNumber", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
