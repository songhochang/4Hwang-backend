/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.green.sahwang.model.payment.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class PurchasePaidEventAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -3796832107767195849L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PurchasePaidEventAvroModel\",\"namespace\":\"com.green.sahwang.model.payment.avro\",\"fields\":[{\"name\":\"purchaseId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"memberId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"transactionId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"paymentStatus\",\"type\":{\"type\":\"enum\",\"name\":\"PaymentStatus\",\"symbols\":[\"PAYING\",\"PAID\",\"COMPLETED\"]}},{\"name\":\"paymentMethod\",\"type\":{\"type\":\"enum\",\"name\":\"PaymentMethod\",\"symbols\":[\"CREDIT_CARD\",\"ACCOUNT_TRANSFER\"]}},{\"name\":\"amount\",\"type\":\"int\"},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"shippingAddress\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PurchasePaidEventAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PurchasePaidEventAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<PurchasePaidEventAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<PurchasePaidEventAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<PurchasePaidEventAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this PurchasePaidEventAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a PurchasePaidEventAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a PurchasePaidEventAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static PurchasePaidEventAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String purchaseId;
  private java.lang.String memberId;
  private java.lang.String transactionId;
  private com.green.sahwang.model.payment.avro.PaymentStatus paymentStatus;
  private com.green.sahwang.model.payment.avro.PaymentMethod paymentMethod;
  private int amount;
  private long timestamp;
  private java.lang.String shippingAddress;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PurchasePaidEventAvroModel() {}

  /**
   * All-args constructor.
   * @param purchaseId The new value for purchaseId
   * @param memberId The new value for memberId
   * @param transactionId The new value for transactionId
   * @param paymentStatus The new value for paymentStatus
   * @param paymentMethod The new value for paymentMethod
   * @param amount The new value for amount
   * @param timestamp The new value for timestamp
   * @param shippingAddress The new value for shippingAddress
   */
  public PurchasePaidEventAvroModel(java.lang.String purchaseId, java.lang.String memberId, java.lang.String transactionId, com.green.sahwang.model.payment.avro.PaymentStatus paymentStatus, com.green.sahwang.model.payment.avro.PaymentMethod paymentMethod, java.lang.Integer amount, java.lang.Long timestamp, java.lang.String shippingAddress) {
    this.purchaseId = purchaseId;
    this.memberId = memberId;
    this.transactionId = transactionId;
    this.paymentStatus = paymentStatus;
    this.paymentMethod = paymentMethod;
    this.amount = amount;
    this.timestamp = timestamp;
    this.shippingAddress = shippingAddress;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return purchaseId;
    case 1: return memberId;
    case 2: return transactionId;
    case 3: return paymentStatus;
    case 4: return paymentMethod;
    case 5: return amount;
    case 6: return timestamp;
    case 7: return shippingAddress;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: purchaseId = value$ != null ? value$.toString() : null; break;
    case 1: memberId = value$ != null ? value$.toString() : null; break;
    case 2: transactionId = value$ != null ? value$.toString() : null; break;
    case 3: paymentStatus = (com.green.sahwang.model.payment.avro.PaymentStatus)value$; break;
    case 4: paymentMethod = (com.green.sahwang.model.payment.avro.PaymentMethod)value$; break;
    case 5: amount = (java.lang.Integer)value$; break;
    case 6: timestamp = (java.lang.Long)value$; break;
    case 7: shippingAddress = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'purchaseId' field.
   * @return The value of the 'purchaseId' field.
   */
  public java.lang.String getPurchaseId() {
    return purchaseId;
  }


  /**
   * Sets the value of the 'purchaseId' field.
   * @param value the value to set.
   */
  public void setPurchaseId(java.lang.String value) {
    this.purchaseId = value;
  }

  /**
   * Gets the value of the 'memberId' field.
   * @return The value of the 'memberId' field.
   */
  public java.lang.String getMemberId() {
    return memberId;
  }


  /**
   * Sets the value of the 'memberId' field.
   * @param value the value to set.
   */
  public void setMemberId(java.lang.String value) {
    this.memberId = value;
  }

  /**
   * Gets the value of the 'transactionId' field.
   * @return The value of the 'transactionId' field.
   */
  public java.lang.String getTransactionId() {
    return transactionId;
  }


  /**
   * Sets the value of the 'transactionId' field.
   * @param value the value to set.
   */
  public void setTransactionId(java.lang.String value) {
    this.transactionId = value;
  }

  /**
   * Gets the value of the 'paymentStatus' field.
   * @return The value of the 'paymentStatus' field.
   */
  public com.green.sahwang.model.payment.avro.PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }


  /**
   * Sets the value of the 'paymentStatus' field.
   * @param value the value to set.
   */
  public void setPaymentStatus(com.green.sahwang.model.payment.avro.PaymentStatus value) {
    this.paymentStatus = value;
  }

  /**
   * Gets the value of the 'paymentMethod' field.
   * @return The value of the 'paymentMethod' field.
   */
  public com.green.sahwang.model.payment.avro.PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }


  /**
   * Sets the value of the 'paymentMethod' field.
   * @param value the value to set.
   */
  public void setPaymentMethod(com.green.sahwang.model.payment.avro.PaymentMethod value) {
    this.paymentMethod = value;
  }

  /**
   * Gets the value of the 'amount' field.
   * @return The value of the 'amount' field.
   */
  public int getAmount() {
    return amount;
  }


  /**
   * Sets the value of the 'amount' field.
   * @param value the value to set.
   */
  public void setAmount(int value) {
    this.amount = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public long getTimestamp() {
    return timestamp;
  }


  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(long value) {
    this.timestamp = value;
  }

  /**
   * Gets the value of the 'shippingAddress' field.
   * @return The value of the 'shippingAddress' field.
   */
  public java.lang.String getShippingAddress() {
    return shippingAddress;
  }


  /**
   * Sets the value of the 'shippingAddress' field.
   * @param value the value to set.
   */
  public void setShippingAddress(java.lang.String value) {
    this.shippingAddress = value;
  }

  /**
   * Creates a new PurchasePaidEventAvroModel RecordBuilder.
   * @return A new PurchasePaidEventAvroModel RecordBuilder
   */
  public static com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder newBuilder() {
    return new com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder();
  }

  /**
   * Creates a new PurchasePaidEventAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PurchasePaidEventAvroModel RecordBuilder
   */
  public static com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder newBuilder(com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder other) {
    if (other == null) {
      return new com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder();
    } else {
      return new com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new PurchasePaidEventAvroModel RecordBuilder by copying an existing PurchasePaidEventAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new PurchasePaidEventAvroModel RecordBuilder
   */
  public static com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder newBuilder(com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel other) {
    if (other == null) {
      return new com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder();
    } else {
      return new com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for PurchasePaidEventAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PurchasePaidEventAvroModel>
    implements org.apache.avro.data.RecordBuilder<PurchasePaidEventAvroModel> {

    private java.lang.String purchaseId;
    private java.lang.String memberId;
    private java.lang.String transactionId;
    private com.green.sahwang.model.payment.avro.PaymentStatus paymentStatus;
    private com.green.sahwang.model.payment.avro.PaymentMethod paymentMethod;
    private int amount;
    private long timestamp;
    private java.lang.String shippingAddress;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.purchaseId)) {
        this.purchaseId = data().deepCopy(fields()[0].schema(), other.purchaseId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.memberId)) {
        this.memberId = data().deepCopy(fields()[1].schema(), other.memberId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[2].schema(), other.transactionId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.paymentStatus)) {
        this.paymentStatus = data().deepCopy(fields()[3].schema(), other.paymentStatus);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.paymentMethod)) {
        this.paymentMethod = data().deepCopy(fields()[4].schema(), other.paymentMethod);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.amount)) {
        this.amount = data().deepCopy(fields()[5].schema(), other.amount);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[6].schema(), other.timestamp);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.shippingAddress)) {
        this.shippingAddress = data().deepCopy(fields()[7].schema(), other.shippingAddress);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
    }

    /**
     * Creates a Builder by copying an existing PurchasePaidEventAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.purchaseId)) {
        this.purchaseId = data().deepCopy(fields()[0].schema(), other.purchaseId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.memberId)) {
        this.memberId = data().deepCopy(fields()[1].schema(), other.memberId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.transactionId)) {
        this.transactionId = data().deepCopy(fields()[2].schema(), other.transactionId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.paymentStatus)) {
        this.paymentStatus = data().deepCopy(fields()[3].schema(), other.paymentStatus);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.paymentMethod)) {
        this.paymentMethod = data().deepCopy(fields()[4].schema(), other.paymentMethod);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.amount)) {
        this.amount = data().deepCopy(fields()[5].schema(), other.amount);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[6].schema(), other.timestamp);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.shippingAddress)) {
        this.shippingAddress = data().deepCopy(fields()[7].schema(), other.shippingAddress);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'purchaseId' field.
      * @return The value.
      */
    public java.lang.String getPurchaseId() {
      return purchaseId;
    }


    /**
      * Sets the value of the 'purchaseId' field.
      * @param value The value of 'purchaseId'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setPurchaseId(java.lang.String value) {
      validate(fields()[0], value);
      this.purchaseId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'purchaseId' field has been set.
      * @return True if the 'purchaseId' field has been set, false otherwise.
      */
    public boolean hasPurchaseId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'purchaseId' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearPurchaseId() {
      purchaseId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'memberId' field.
      * @return The value.
      */
    public java.lang.String getMemberId() {
      return memberId;
    }


    /**
      * Sets the value of the 'memberId' field.
      * @param value The value of 'memberId'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setMemberId(java.lang.String value) {
      validate(fields()[1], value);
      this.memberId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'memberId' field has been set.
      * @return True if the 'memberId' field has been set, false otherwise.
      */
    public boolean hasMemberId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'memberId' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearMemberId() {
      memberId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'transactionId' field.
      * @return The value.
      */
    public java.lang.String getTransactionId() {
      return transactionId;
    }


    /**
      * Sets the value of the 'transactionId' field.
      * @param value The value of 'transactionId'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setTransactionId(java.lang.String value) {
      validate(fields()[2], value);
      this.transactionId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'transactionId' field has been set.
      * @return True if the 'transactionId' field has been set, false otherwise.
      */
    public boolean hasTransactionId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'transactionId' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearTransactionId() {
      transactionId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'paymentStatus' field.
      * @return The value.
      */
    public com.green.sahwang.model.payment.avro.PaymentStatus getPaymentStatus() {
      return paymentStatus;
    }


    /**
      * Sets the value of the 'paymentStatus' field.
      * @param value The value of 'paymentStatus'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setPaymentStatus(com.green.sahwang.model.payment.avro.PaymentStatus value) {
      validate(fields()[3], value);
      this.paymentStatus = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'paymentStatus' field has been set.
      * @return True if the 'paymentStatus' field has been set, false otherwise.
      */
    public boolean hasPaymentStatus() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'paymentStatus' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearPaymentStatus() {
      paymentStatus = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'paymentMethod' field.
      * @return The value.
      */
    public com.green.sahwang.model.payment.avro.PaymentMethod getPaymentMethod() {
      return paymentMethod;
    }


    /**
      * Sets the value of the 'paymentMethod' field.
      * @param value The value of 'paymentMethod'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setPaymentMethod(com.green.sahwang.model.payment.avro.PaymentMethod value) {
      validate(fields()[4], value);
      this.paymentMethod = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'paymentMethod' field has been set.
      * @return True if the 'paymentMethod' field has been set, false otherwise.
      */
    public boolean hasPaymentMethod() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'paymentMethod' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearPaymentMethod() {
      paymentMethod = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'amount' field.
      * @return The value.
      */
    public int getAmount() {
      return amount;
    }


    /**
      * Sets the value of the 'amount' field.
      * @param value The value of 'amount'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setAmount(int value) {
      validate(fields()[5], value);
      this.amount = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'amount' field has been set.
      * @return True if the 'amount' field has been set, false otherwise.
      */
    public boolean hasAmount() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'amount' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearAmount() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public long getTimestamp() {
      return timestamp;
    }


    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setTimestamp(long value) {
      validate(fields()[6], value);
      this.timestamp = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearTimestamp() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'shippingAddress' field.
      * @return The value.
      */
    public java.lang.String getShippingAddress() {
      return shippingAddress;
    }


    /**
      * Sets the value of the 'shippingAddress' field.
      * @param value The value of 'shippingAddress'.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder setShippingAddress(java.lang.String value) {
      validate(fields()[7], value);
      this.shippingAddress = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'shippingAddress' field has been set.
      * @return True if the 'shippingAddress' field has been set, false otherwise.
      */
    public boolean hasShippingAddress() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'shippingAddress' field.
      * @return This builder.
      */
    public com.green.sahwang.model.payment.avro.PurchasePaidEventAvroModel.Builder clearShippingAddress() {
      shippingAddress = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PurchasePaidEventAvroModel build() {
      try {
        PurchasePaidEventAvroModel record = new PurchasePaidEventAvroModel();
        record.purchaseId = fieldSetFlags()[0] ? this.purchaseId : (java.lang.String) defaultValue(fields()[0]);
        record.memberId = fieldSetFlags()[1] ? this.memberId : (java.lang.String) defaultValue(fields()[1]);
        record.transactionId = fieldSetFlags()[2] ? this.transactionId : (java.lang.String) defaultValue(fields()[2]);
        record.paymentStatus = fieldSetFlags()[3] ? this.paymentStatus : (com.green.sahwang.model.payment.avro.PaymentStatus) defaultValue(fields()[3]);
        record.paymentMethod = fieldSetFlags()[4] ? this.paymentMethod : (com.green.sahwang.model.payment.avro.PaymentMethod) defaultValue(fields()[4]);
        record.amount = fieldSetFlags()[5] ? this.amount : (java.lang.Integer) defaultValue(fields()[5]);
        record.timestamp = fieldSetFlags()[6] ? this.timestamp : (java.lang.Long) defaultValue(fields()[6]);
        record.shippingAddress = fieldSetFlags()[7] ? this.shippingAddress : (java.lang.String) defaultValue(fields()[7]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PurchasePaidEventAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<PurchasePaidEventAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PurchasePaidEventAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<PurchasePaidEventAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.purchaseId);

    out.writeString(this.memberId);

    out.writeString(this.transactionId);

    out.writeEnum(this.paymentStatus.ordinal());

    out.writeEnum(this.paymentMethod.ordinal());

    out.writeInt(this.amount);

    out.writeLong(this.timestamp);

    out.writeString(this.shippingAddress);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.purchaseId = in.readString();

      this.memberId = in.readString();

      this.transactionId = in.readString();

      this.paymentStatus = com.green.sahwang.model.payment.avro.PaymentStatus.values()[in.readEnum()];

      this.paymentMethod = com.green.sahwang.model.payment.avro.PaymentMethod.values()[in.readEnum()];

      this.amount = in.readInt();

      this.timestamp = in.readLong();

      this.shippingAddress = in.readString();

    } else {
      for (int i = 0; i < 8; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.purchaseId = in.readString();
          break;

        case 1:
          this.memberId = in.readString();
          break;

        case 2:
          this.transactionId = in.readString();
          break;

        case 3:
          this.paymentStatus = com.green.sahwang.model.payment.avro.PaymentStatus.values()[in.readEnum()];
          break;

        case 4:
          this.paymentMethod = com.green.sahwang.model.payment.avro.PaymentMethod.values()[in.readEnum()];
          break;

        case 5:
          this.amount = in.readInt();
          break;

        case 6:
          this.timestamp = in.readLong();
          break;

        case 7:
          this.shippingAddress = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










