# BOB-Serialization 

BOB is an annotation driven serialization library for Java. Annotations has made my life easier in many project and I wanted to exted that experience to the serialization world where annotation driven serialization was missing.    


## Getting started
````java
@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
public class DummyProtocolHeader extends Struct<DummyProtocolHeader> {
    @UShortField
    private Integer checksum;
    
    @ShortField
    private Short errorCode;
    
    @UShortField
    private Integer transactionCode
    
    @ULongField
    private BigInteger timeStampNanoSec;
}
````
````Struct.class```` is a abstract class exposing functionality of conversion to any class extending it. And yes, Unsigned :D. Java doesn't have language support for unsigned. So how do we overcome this? You may have already recognized the pattern. Unsigned will be handled by type promotion to higher byte type class in Java. ````Unsigned Short -> Integer, Unsigned Integer -> Long, Unsigned Long -> BigInteger````. The details of the conversion cases will be provided below. If we even use type promotion, conversion from annotated type to actual type works. But what for the way oppsite, it will be lossy, nah? Oh yes, it will be lossy if you literally cast it that way. But the catch here is that since annotation defines your long to behave as Unisgned Int32, then its clear to programmer that the long should only hold values for Unsigned Int32. This way the reverse conversion will not be lossy as it always will fall under the upper and lower bounds. Hence, programmer using this library should make sure that if you are assuming a field to be of unsigned type then you should maintain it in the logic that it behaves as unsigned. One way library can make sure of that will be checking the bounds before making conversions. Same applies for all the types included in BOBC.

So, how to convert our header to bytes?
````java
DummyProtocolHeader header = new DummyProtocolHeader();
// do some set operations
byte[] serializedBytes = header.toBytes();
````
Simple right? Now the BOBC takes care of converting the Object into bytes as per the order defined by annotaion and annotations used in the fields in the order they are placed inside the class. 

Now, how to get Object from bytes? Actually there are two ways to achieve this.
1. Create new Instance after consuming the byte[]
2. Consume the byte[] and set results to the calling Object

````java
byte[] data = say_we_received_data_from_server();
DummyProtocolHeader header = new DummyProtocolHeader();
// data unpacked from byte[] will be set to header itself, case 2
header.from(data);

// create new instance from byte[], case 1
DummyProtocolHeader newHeader = header.newInstanceFrom(data);
````
That was very easy, right?

# Currently supported types and conversion scheme
BOBC Field Type | Currently supported conversion to-from
----------------|---------------------------------------
ByteField|Byte, byte, String
ShortField|Short, short, String
UShortField|Integer, int, String
Int32Field|Integer, int, String
UInt32Field|Long, long, String
Int64Field|Long, long, String
UInt64Field|BigInteger, String
LongField|Long, long, String
ULongField|BigInteger, String
FloatField|Float, float, String
DoubleField|Double, double, String

# BOBC API
The *Struct* way of coversion will be set as standard way of doing serialization as it becomes very easy that way during usage. But under the hood, the calls are made to BOBC conversion APIS and those APIS are available for use programatically without having to use *Struct*.

All the API is exposed via ````Conversion.class````
````java
// instantiating the converter giving the byte ordering and classes
Converter converter = Converter.builder().order(order).add(DummyProtocolHeader.class).build();

// you can add any number of classes to pack or unpack from, order is important as BOBC packs or unpacks byte[] using the same order
Converter converter = Converter.builder().order(order).add(A.class,B.class,C.class).build();
````
Using the converter to get Objects from byte[]
````java
byte[] data = suppose_we_received_data_from_server();
Converter converter = Converter.builder().order(order).add(DummyProtocolHeader.class).build();

//Suppose we already have an Object of DummyProtocolheader
DummyProtocolHeader header = new DummyProtocolHeader();

// Conversion api can use the available instances through map
Map<Class<?>,Object> instances = new HashMap<>();
instances.put(DummyProtocolHeader.class,header);

// results will be stored in ObjectResults
ObjectResults results = converter.convert(data, instances);
// optionally if you want to converter to create new instances, just omit instances
// now header variable will be automatically populated (if instance is provided)

// Get call will give the same header instance if isntance was provided
DummyProtocolHeader header2 = results.get(DummyProtocolHeader.class);
// header == header2 (if map of instance was provided)
````

How to convert from Object(s) to respective bytes?
````java
// build converter for our Dummy Protocol Header
Converter converter = Converter.builder().order(order).add(DummyProtocolHeader.class).build();

// lets suppose we have an Object for the header, all set to be converted
DummyProtocolHeader header = new DummyProtocolHeader();

// now lets convert
byte[] data = converter.convert(header);
````

All examples has only one argument for add and convert, but actually there can be multiple files. Lets consider that there is another ````class DummyProtocolPayload````
````java
@ByteOrdering(ByteOrder.LITTLE_ENDIAN)
public class DummyProtocolPayload extends Struct<DummyProtocolPayload> {
    @Int32Field
    public Integer payloadChecksum;
    
    @LongField
    public Long somePayload;
}
````

Now, the BOBC API can take both at the same time.
````java
Converter converter = Converter.builder().order(order).add(DummyProtocolHeader.class,DummyProtocolPayload.class).build();

DummyProtocolHeader header = new DummyProtocolHeader();
DummyProtocolPayload payload = new DummyProtocolPayload();

// now convert both into bytes, the order of addition of Objects are critical.
byte[] data = converter.convert(header,payload);
````

Same thing is true for reverse operation.

