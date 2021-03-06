// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ca/evanjones/protorpc/Protocol.proto

package protorpc.ca.evanjones.protorpc;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

public final class Protocol {
  private Protocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public enum Status
      implements com.google.protobuf.ProtocolMessageEnum {
    INVALID(0, -1),
    OK(1, 0),
    ERROR_USER(2, 1),
    ERROR_COMMUNICATION(3, 2),
    ;
    
    
    public final int getNumber() { return value; }
    
    public static Status valueOf(int value) {
      switch (value) {
        case -1: return INVALID;
        case 0: return OK;
        case 1: return ERROR_USER;
        case 2: return ERROR_COMMUNICATION;
        default: return null;
      }
    }
    
    public static com.google.protobuf.Internal.EnumLiteMap<Status>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<Status>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Status>() {
            public Status findValueByNumber(int number) {
              return Status.valueOf(number)
    ;        }
          };
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return protorpc.ca.evanjones.protorpc.Protocol.getDescriptor().getEnumTypes().get(0);
    }
    
    private static final Status[] VALUES = {
      INVALID, OK, ERROR_USER, ERROR_COMMUNICATION, 
    };
    public static Status valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    private final int index;
    private final int value;
    private Status(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    static {
      protorpc.ca.evanjones.protorpc.Protocol.getDescriptor();
    }
    
    // @@protoc_insertion_point(enum_scope:protorpc.Status)
  }
  
  public static final class RpcRequest extends
      com.google.protobuf.GeneratedMessage {
    // Use RpcRequest.newBuilder() to construct.
    private RpcRequest() {
      initFields();
    }
    private RpcRequest(boolean noInit) {}
    
    private static final RpcRequest defaultInstance;
    public static RpcRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public RpcRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protorpc.ca.evanjones.protorpc.Protocol.internal_static_protorpc_RpcRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protorpc.ca.evanjones.protorpc.Protocol.internal_static_protorpc_RpcRequest_fieldAccessorTable;
    }
    
    // required int32 sequence_number = 1;
    public static final int SEQUENCE_NUMBER_FIELD_NUMBER = 1;
    private boolean hasSequenceNumber;
    private int sequenceNumber_ = 0;
    public boolean hasSequenceNumber() { return hasSequenceNumber; }
    public int getSequenceNumber() { return sequenceNumber_; }
    
    // required string method_name = 2;
    public static final int METHOD_NAME_FIELD_NUMBER = 2;
    private boolean hasMethodName;
    private String methodName_ = "";
    public boolean hasMethodName() { return hasMethodName; }
    public String getMethodName() { return methodName_; }
    
    // required bytes request = 3;
    public static final int REQUEST_FIELD_NUMBER = 3;
    private boolean hasRequest;
    private com.google.protobuf.ByteString request_ = com.google.protobuf.ByteString.EMPTY;
    public boolean hasRequest() { return hasRequest; }
    public com.google.protobuf.ByteString getRequest() { return request_; }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      if (!hasSequenceNumber) return false;
      if (!hasMethodName) return false;
      if (!hasRequest) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasSequenceNumber()) {
        output.writeInt32(1, getSequenceNumber());
      }
      if (hasMethodName()) {
        output.writeString(2, getMethodName());
      }
      if (hasRequest()) {
        output.writeBytes(3, getRequest());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasSequenceNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getSequenceNumber());
      }
      if (hasMethodName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getMethodName());
      }
      if (hasRequest()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getRequest());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

	  @Override
	  protected Message.Builder newBuilderForType(BuilderParent parent) {
		  return null;
	  }

	  public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(protorpc.ca.evanjones.protorpc.Protocol.RpcRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private protorpc.ca.evanjones.protorpc.Protocol.RpcRequest result;
      
      // Construct using protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new protorpc.ca.evanjones.protorpc.Protocol.RpcRequest();
        return builder;
      }
      
      protected protorpc.ca.evanjones.protorpc.Protocol.RpcRequest internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new protorpc.ca.evanjones.protorpc.Protocol.RpcRequest();
        return this;
      }

		@Override
		protected FieldAccessorTable internalGetFieldAccessorTable() {
			return null;
		}

		public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.getDescriptor();
      }
      
      public protorpc.ca.evanjones.protorpc.Protocol.RpcRequest getDefaultInstanceForType() {
        return protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public protorpc.ca.evanjones.protorpc.Protocol.RpcRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private protorpc.ca.evanjones.protorpc.Protocol.RpcRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public protorpc.ca.evanjones.protorpc.Protocol.RpcRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        protorpc.ca.evanjones.protorpc.Protocol.RpcRequest returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protorpc.ca.evanjones.protorpc.Protocol.RpcRequest) {
          return mergeFrom((protorpc.ca.evanjones.protorpc.Protocol.RpcRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(protorpc.ca.evanjones.protorpc.Protocol.RpcRequest other) {
        if (other == protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.getDefaultInstance()) return this;
        if (other.hasSequenceNumber()) {
          setSequenceNumber(other.getSequenceNumber());
        }
        if (other.hasMethodName()) {
          setMethodName(other.getMethodName());
        }
        if (other.hasRequest()) {
          setRequest(other.getRequest());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setSequenceNumber(input.readInt32());
              break;
            }
            case 18: {
              setMethodName(input.readString());
              break;
            }
            case 26: {
              setRequest(input.readBytes());
              break;
            }
          }
        }
      }
      
      
      // required int32 sequence_number = 1;
      public boolean hasSequenceNumber() {
        return result.hasSequenceNumber();
      }
      public int getSequenceNumber() {
        return result.getSequenceNumber();
      }
      public Builder setSequenceNumber(int value) {
        result.hasSequenceNumber = true;
        result.sequenceNumber_ = value;
        return this;
      }
      public Builder clearSequenceNumber() {
        result.hasSequenceNumber = false;
        result.sequenceNumber_ = 0;
        return this;
      }
      
      // required string method_name = 2;
      public boolean hasMethodName() {
        return result.hasMethodName();
      }
      public String getMethodName() {
        return result.getMethodName();
      }
      public Builder setMethodName(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasMethodName = true;
        result.methodName_ = value;
        return this;
      }
      public Builder clearMethodName() {
        result.hasMethodName = false;
        result.methodName_ = getDefaultInstance().getMethodName();
        return this;
      }
      
      // required bytes request = 3;
      public boolean hasRequest() {
        return result.hasRequest();
      }
      public com.google.protobuf.ByteString getRequest() {
        return result.getRequest();
      }
      public Builder setRequest(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasRequest = true;
        result.request_ = value;
        return this;
      }
      public Builder clearRequest() {
        result.hasRequest = false;
        result.request_ = getDefaultInstance().getRequest();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:protorpc.RpcRequest)
    }
    
    static {
      defaultInstance = new RpcRequest(true);
      protorpc.ca.evanjones.protorpc.Protocol.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:protorpc.RpcRequest)
  }
  
  public static final class RpcResponse extends
      com.google.protobuf.GeneratedMessage {
    // Use RpcResponse.newBuilder() to construct.
    private RpcResponse() {
      initFields();
    }
    private RpcResponse(boolean noInit) {}
    
    private static final RpcResponse defaultInstance;
    public static RpcResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public RpcResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protorpc.ca.evanjones.protorpc.Protocol.internal_static_protorpc_RpcResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protorpc.ca.evanjones.protorpc.Protocol.internal_static_protorpc_RpcResponse_fieldAccessorTable;
    }
    
    // required int32 sequence_number = 1;
    public static final int SEQUENCE_NUMBER_FIELD_NUMBER = 1;
    private boolean hasSequenceNumber;
    private int sequenceNumber_ = 0;
    public boolean hasSequenceNumber() { return hasSequenceNumber; }
    public int getSequenceNumber() { return sequenceNumber_; }
    
    // required .protorpc.Status status = 2;
    public static final int STATUS_FIELD_NUMBER = 2;
    private boolean hasStatus;
    private protorpc.ca.evanjones.protorpc.Protocol.Status status_;
    public boolean hasStatus() { return hasStatus; }
    public protorpc.ca.evanjones.protorpc.Protocol.Status getStatus() { return status_; }
    
    // optional bytes response = 3;
    public static final int RESPONSE_FIELD_NUMBER = 3;
    private boolean hasResponse;
    private com.google.protobuf.ByteString response_ = com.google.protobuf.ByteString.EMPTY;
    public boolean hasResponse() { return hasResponse; }
    public com.google.protobuf.ByteString getResponse() { return response_; }
    
    // optional string error_reason = 4;
    public static final int ERROR_REASON_FIELD_NUMBER = 4;
    private boolean hasErrorReason;
    private String errorReason_ = "";
    public boolean hasErrorReason() { return hasErrorReason; }
    public String getErrorReason() { return errorReason_; }
    
    private void initFields() {
      status_ = protorpc.ca.evanjones.protorpc.Protocol.Status.INVALID;
    }
    public final boolean isInitialized() {
      if (!hasSequenceNumber) return false;
      if (!hasStatus) return false;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasSequenceNumber()) {
        output.writeInt32(1, getSequenceNumber());
      }
      if (hasStatus()) {
        output.writeEnum(2, getStatus().getNumber());
      }
      if (hasResponse()) {
        output.writeBytes(3, getResponse());
      }
      if (hasErrorReason()) {
        output.writeString(4, getErrorReason());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasSequenceNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getSequenceNumber());
      }
      if (hasStatus()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(2, getStatus().getNumber());
      }
      if (hasResponse()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getResponse());
      }
      if (hasErrorReason()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(4, getErrorReason());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

	  @Override
	  protected Message.Builder newBuilderForType(BuilderParent parent) {
		  return null;
	  }

	  public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static protorpc.ca.evanjones.protorpc.Protocol.RpcResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(protorpc.ca.evanjones.protorpc.Protocol.RpcResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private protorpc.ca.evanjones.protorpc.Protocol.RpcResponse result;
      
      // Construct using protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new protorpc.ca.evanjones.protorpc.Protocol.RpcResponse();
        return builder;
      }
      
      protected protorpc.ca.evanjones.protorpc.Protocol.RpcResponse internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new protorpc.ca.evanjones.protorpc.Protocol.RpcResponse();
        return this;
      }

		@Override
		protected FieldAccessorTable internalGetFieldAccessorTable() {
			return null;
		}

		public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.getDescriptor();
      }
      
      public protorpc.ca.evanjones.protorpc.Protocol.RpcResponse getDefaultInstanceForType() {
        return protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public protorpc.ca.evanjones.protorpc.Protocol.RpcResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private protorpc.ca.evanjones.protorpc.Protocol.RpcResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public protorpc.ca.evanjones.protorpc.Protocol.RpcResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        protorpc.ca.evanjones.protorpc.Protocol.RpcResponse returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protorpc.ca.evanjones.protorpc.Protocol.RpcResponse) {
          return mergeFrom((protorpc.ca.evanjones.protorpc.Protocol.RpcResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(protorpc.ca.evanjones.protorpc.Protocol.RpcResponse other) {
        if (other == protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.getDefaultInstance()) return this;
        if (other.hasSequenceNumber()) {
          setSequenceNumber(other.getSequenceNumber());
        }
        if (other.hasStatus()) {
          setStatus(other.getStatus());
        }
        if (other.hasResponse()) {
          setResponse(other.getResponse());
        }
        if (other.hasErrorReason()) {
          setErrorReason(other.getErrorReason());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setSequenceNumber(input.readInt32());
              break;
            }
            case 16: {
              int rawValue = input.readEnum();
              protorpc.ca.evanjones.protorpc.Protocol.Status value = protorpc.ca.evanjones.protorpc.Protocol.Status.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(2, rawValue);
              } else {
                setStatus(value);
              }
              break;
            }
            case 26: {
              setResponse(input.readBytes());
              break;
            }
            case 34: {
              setErrorReason(input.readString());
              break;
            }
          }
        }
      }
      
      
      // required int32 sequence_number = 1;
      public boolean hasSequenceNumber() {
        return result.hasSequenceNumber();
      }
      public int getSequenceNumber() {
        return result.getSequenceNumber();
      }
      public Builder setSequenceNumber(int value) {
        result.hasSequenceNumber = true;
        result.sequenceNumber_ = value;
        return this;
      }
      public Builder clearSequenceNumber() {
        result.hasSequenceNumber = false;
        result.sequenceNumber_ = 0;
        return this;
      }
      
      // required .protorpc.Status status = 2;
      public boolean hasStatus() {
        return result.hasStatus();
      }
      public protorpc.ca.evanjones.protorpc.Protocol.Status getStatus() {
        return result.getStatus();
      }
      public Builder setStatus(protorpc.ca.evanjones.protorpc.Protocol.Status value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasStatus = true;
        result.status_ = value;
        return this;
      }
      public Builder clearStatus() {
        result.hasStatus = false;
        result.status_ = protorpc.ca.evanjones.protorpc.Protocol.Status.INVALID;
        return this;
      }
      
      // optional bytes response = 3;
      public boolean hasResponse() {
        return result.hasResponse();
      }
      public com.google.protobuf.ByteString getResponse() {
        return result.getResponse();
      }
      public Builder setResponse(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasResponse = true;
        result.response_ = value;
        return this;
      }
      public Builder clearResponse() {
        result.hasResponse = false;
        result.response_ = getDefaultInstance().getResponse();
        return this;
      }
      
      // optional string error_reason = 4;
      public boolean hasErrorReason() {
        return result.hasErrorReason();
      }
      public String getErrorReason() {
        return result.getErrorReason();
      }
      public Builder setErrorReason(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasErrorReason = true;
        result.errorReason_ = value;
        return this;
      }
      public Builder clearErrorReason() {
        result.hasErrorReason = false;
        result.errorReason_ = getDefaultInstance().getErrorReason();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:protorpc.RpcResponse)
    }
    
    static {
      defaultInstance = new RpcResponse(true);
      protorpc.ca.evanjones.protorpc.Protocol.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:protorpc.RpcResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protorpc_RpcRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protorpc_RpcRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protorpc_RpcResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protorpc_RpcResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n$ca/evanjones/protorpc/Protocol.proto\022\010" +
      "protorpc\"K\n\nRpcRequest\022\027\n\017sequence_numbe" +
      "r\030\001 \002(\005\022\023\n\013method_name\030\002 \002(\t\022\017\n\007request\030" +
      "\003 \002(\014\"p\n\013RpcResponse\022\027\n\017sequence_number\030" +
      "\001 \002(\005\022 \n\006status\030\002 \002(\0162\020.protorpc.Status\022" +
      "\020\n\010response\030\003 \001(\014\022\024\n\014error_reason\030\004 \001(\t*" +
      "O\n\006Status\022\024\n\007INVALID\020\377\377\377\377\377\377\377\377\377\001\022\006\n\002OK\020\000\022" +
      "\016\n\nERROR_USER\020\001\022\027\n\023ERROR_COMMUNICATION\020\002" +
      "B\027\n\025ca.evanjones.protorpc"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_protorpc_RpcRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_protorpc_RpcRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protorpc_RpcRequest_descriptor,
              new String[] { "SequenceNumber", "MethodName", "Request", },
              protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.class,
              protorpc.ca.evanjones.protorpc.Protocol.RpcRequest.Builder.class);
          internal_static_protorpc_RpcResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_protorpc_RpcResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protorpc_RpcResponse_descriptor,
              new String[] { "SequenceNumber", "Status", "Response", "ErrorReason", },
              protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.class,
              protorpc.ca.evanjones.protorpc.Protocol.RpcResponse.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
