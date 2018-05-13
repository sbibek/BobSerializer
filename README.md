# BOBC ```Byte-Object-Byte Conversion```

BOBC is a conversion/serialization library conceived as a need of working on byte level when designing low byte level protocol between systems speaking java and C. Yes, there are ways to do this with existing Java API and special classes such as ByteBuffer etc. As per my experience goes, I spent more time figuring out to deal with the conversion (issues like unsigned, order or packing and unpacking etc). Hence, easy to use annotation driven conversion/serialization library that just works. 

Please note that the project is still at infant stage. More design, features will be added on the go as things start taking shape. Getting started with just simple annotation driven conversion API and simple staright forward usage using Struct class for standard data types. I will be trying to make design to allow flexibility for adding custom coversion routines so that conversion/serialziation can be done for any  kind of classes.

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
    
    @UInt32Field
    private Long payloadSize;
    
    @ULongField
    private BigInteger timeStampNanoSec;
}
````
Struct.class is a abstract class exposing functionality of conversion to any class extending it. And yes, Unsigned :D. Java doesn't have language support for unsigned. So how do we overcome this? You may have already recognized the pattern. Unsigned will be handled by type promotion to higher byte type class in Java. Unsigned Short -> Integer, Unsigned Integer -> Long, Unsigned Long -> BigInteger. The details of the conversion cases will be provided below. If we even use type promotion, conversion from annotated type to actual type works. But what for the way oppsite, it will be lossy, nah? Oh yes, it will be lossy if you literally cast it that way. But the catch here is that since annotation defines your long to behave as Unisgned Int32, then its clear to programmer that the long should only hold values for Unsigned Int32. This way the reverse conversion will not be lossy as it always will fall under the upper and lower bounds. Hence, programmer using this library should make sure that if you are assuming a field to be of unsigned type then you should maintain it in the logic that it behaves as unsigned. One way library can make sure of that will be checking the bounds before making conversions. Same applies for all the types included in BOBC.

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
Thats very easy, right?

# BOBC API
The *Struct* way of coversion will be set as standard way of doing serialization as it becomes very easy that way during usage. But under the hood, the calls are made to BOBC conversion APIS and those APIS are available for use programatically without having to use *Struct*. Docs coming soon :)
