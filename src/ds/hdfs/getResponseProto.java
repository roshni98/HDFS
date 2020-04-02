// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: getResponse.proto

package ds.hdfs;

public final class getResponseProto {
  private getResponseProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface getResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:hdfs.getResponse)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required bytes data = 1;</code>
     * @return Whether the data field is set.
     */
    boolean hasData();
    /**
     * <code>required bytes data = 1;</code>
     * @return The data.
     */
    com.google.protobuf.ByteString getData();

    /**
     * <code>required string filename = 2;</code>
     * @return Whether the filename field is set.
     */
    boolean hasFilename();
    /**
     * <code>required string filename = 2;</code>
     * @return The filename.
     */
    java.lang.String getFilename();
    /**
     * <code>required string filename = 2;</code>
     * @return The bytes for filename.
     */
    com.google.protobuf.ByteString
        getFilenameBytes();
  }
  /**
   * Protobuf type {@code hdfs.getResponse}
   */
  public  static final class getResponse extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:hdfs.getResponse)
      getResponseOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use getResponse.newBuilder() to construct.
    private getResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private getResponse() {
      data_ = com.google.protobuf.ByteString.EMPTY;
      filename_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new getResponse();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private getResponse(
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
              bitField0_ |= 0x00000001;
              data_ = input.readBytes();
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              filename_ = bs;
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
      return ds.hdfs.getResponseProto.internal_static_hdfs_getResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ds.hdfs.getResponseProto.internal_static_hdfs_getResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ds.hdfs.getResponseProto.getResponse.class, ds.hdfs.getResponseProto.getResponse.Builder.class);
    }

    private int bitField0_;
    public static final int DATA_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString data_;
    /**
     * <code>required bytes data = 1;</code>
     * @return Whether the data field is set.
     */
    public boolean hasData() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required bytes data = 1;</code>
     * @return The data.
     */
    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    public static final int FILENAME_FIELD_NUMBER = 2;
    private volatile java.lang.Object filename_;
    /**
     * <code>required string filename = 2;</code>
     * @return Whether the filename field is set.
     */
    public boolean hasFilename() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>required string filename = 2;</code>
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
     * <code>required string filename = 2;</code>
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

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasData()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasFilename()) {
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
        output.writeBytes(1, data_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, filename_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, data_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, filename_);
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
      if (!(obj instanceof ds.hdfs.getResponseProto.getResponse)) {
        return super.equals(obj);
      }
      ds.hdfs.getResponseProto.getResponse other = (ds.hdfs.getResponseProto.getResponse) obj;

      if (hasData() != other.hasData()) return false;
      if (hasData()) {
        if (!getData()
            .equals(other.getData())) return false;
      }
      if (hasFilename() != other.hasFilename()) return false;
      if (hasFilename()) {
        if (!getFilename()
            .equals(other.getFilename())) return false;
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
      if (hasData()) {
        hash = (37 * hash) + DATA_FIELD_NUMBER;
        hash = (53 * hash) + getData().hashCode();
      }
      if (hasFilename()) {
        hash = (37 * hash) + FILENAME_FIELD_NUMBER;
        hash = (53 * hash) + getFilename().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ds.hdfs.getResponseProto.getResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ds.hdfs.getResponseProto.getResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ds.hdfs.getResponseProto.getResponse parseFrom(
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
    public static Builder newBuilder(ds.hdfs.getResponseProto.getResponse prototype) {
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
     * Protobuf type {@code hdfs.getResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:hdfs.getResponse)
        ds.hdfs.getResponseProto.getResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ds.hdfs.getResponseProto.internal_static_hdfs_getResponse_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ds.hdfs.getResponseProto.internal_static_hdfs_getResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ds.hdfs.getResponseProto.getResponse.class, ds.hdfs.getResponseProto.getResponse.Builder.class);
      }

      // Construct using ds.hdfs.getResponseProto.getResponse.newBuilder()
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
        data_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        filename_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ds.hdfs.getResponseProto.internal_static_hdfs_getResponse_descriptor;
      }

      @java.lang.Override
      public ds.hdfs.getResponseProto.getResponse getDefaultInstanceForType() {
        return ds.hdfs.getResponseProto.getResponse.getDefaultInstance();
      }

      @java.lang.Override
      public ds.hdfs.getResponseProto.getResponse build() {
        ds.hdfs.getResponseProto.getResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ds.hdfs.getResponseProto.getResponse buildPartial() {
        ds.hdfs.getResponseProto.getResponse result = new ds.hdfs.getResponseProto.getResponse(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          to_bitField0_ |= 0x00000001;
        }
        result.data_ = data_;
        if (((from_bitField0_ & 0x00000002) != 0)) {
          to_bitField0_ |= 0x00000002;
        }
        result.filename_ = filename_;
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
        if (other instanceof ds.hdfs.getResponseProto.getResponse) {
          return mergeFrom((ds.hdfs.getResponseProto.getResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ds.hdfs.getResponseProto.getResponse other) {
        if (other == ds.hdfs.getResponseProto.getResponse.getDefaultInstance()) return this;
        if (other.hasData()) {
          setData(other.getData());
        }
        if (other.hasFilename()) {
          bitField0_ |= 0x00000002;
          filename_ = other.filename_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        if (!hasData()) {
          return false;
        }
        if (!hasFilename()) {
          return false;
        }
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ds.hdfs.getResponseProto.getResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ds.hdfs.getResponseProto.getResponse) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>required bytes data = 1;</code>
       * @return Whether the data field is set.
       */
      public boolean hasData() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required bytes data = 1;</code>
       * @return The data.
       */
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <code>required bytes data = 1;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required bytes data = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
        bitField0_ = (bitField0_ & ~0x00000001);
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }

      private java.lang.Object filename_ = "";
      /**
       * <code>required string filename = 2;</code>
       * @return Whether the filename field is set.
       */
      public boolean hasFilename() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>required string filename = 2;</code>
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
       * <code>required string filename = 2;</code>
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
       * <code>required string filename = 2;</code>
       * @param value The filename to set.
       * @return This builder for chaining.
       */
      public Builder setFilename(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        filename_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string filename = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearFilename() {
        bitField0_ = (bitField0_ & ~0x00000002);
        filename_ = getDefaultInstance().getFilename();
        onChanged();
        return this;
      }
      /**
       * <code>required string filename = 2;</code>
       * @param value The bytes for filename to set.
       * @return This builder for chaining.
       */
      public Builder setFilenameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        filename_ = value;
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


      // @@protoc_insertion_point(builder_scope:hdfs.getResponse)
    }

    // @@protoc_insertion_point(class_scope:hdfs.getResponse)
    private static final ds.hdfs.getResponseProto.getResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ds.hdfs.getResponseProto.getResponse();
    }

    public static ds.hdfs.getResponseProto.getResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<getResponse>
        PARSER = new com.google.protobuf.AbstractParser<getResponse>() {
      @java.lang.Override
      public getResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new getResponse(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<getResponse> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<getResponse> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ds.hdfs.getResponseProto.getResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_hdfs_getResponse_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_hdfs_getResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021getResponse.proto\022\004hdfs\"-\n\013getResponse" +
      "\022\014\n\004data\030\001 \002(\014\022\020\n\010filename\030\002 \002(\tB\033\n\007ds.h" +
      "dfsB\020getResponseProto"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_hdfs_getResponse_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_hdfs_getResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_hdfs_getResponse_descriptor,
        new java.lang.String[] { "Data", "Filename", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
