package ana.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Test;
import uk.co.real_logic.sbe.ir.generated.MessageHeaderDecoder;

public class Main {

        @Test
        public void test1() {

            final short BLOCK_LENGHT_OFFSET = 0;
            final short TEMPLATE_ID_OFFSET = 2;
            final short SCHEMA_ID_OFFSET = 4;
            final short VERSION_OFFSET = 6;

            final short expectedBlockLength = 35;
            final short expectedTemplateId = 1;
            final short expectedSchemaId = 1;
            final short expectedVersion = 100;

            MutableDirectBuffer bufferMessageHeader = new UnsafeBuffer(ByteBuffer.allocateDirect(35));

            bufferMessageHeader.putShort(BLOCK_LENGHT_OFFSET, expectedBlockLength, ByteOrder.LITTLE_ENDIAN);
            bufferMessageHeader.putShort(TEMPLATE_ID_OFFSET, expectedTemplateId, ByteOrder.LITTLE_ENDIAN);
            bufferMessageHeader.putShort(SCHEMA_ID_OFFSET, expectedSchemaId, ByteOrder.LITTLE_ENDIAN);
            bufferMessageHeader.putShort(VERSION_OFFSET, expectedVersion, ByteOrder.LITTLE_ENDIAN);

            final short blockLength = (bufferMessageHeader.getShort(BLOCK_LENGHT_OFFSET, ByteOrder.LITTLE_ENDIAN));
            final short templateId = (bufferMessageHeader.getShort(TEMPLATE_ID_OFFSET, ByteOrder.LITTLE_ENDIAN));
            final short schemaId = (bufferMessageHeader.getShort(SCHEMA_ID_OFFSET, ByteOrder.LITTLE_ENDIAN));
            final short version = (bufferMessageHeader.getShort(VERSION_OFFSET, ByteOrder.LITTLE_ENDIAN));


            assertEquals(expectedBlockLength, blockLength);
            assertEquals(expectedTemplateId, templateId);
            assertEquals(expectedSchemaId, schemaId);
            assertEquals(expectedVersion, version);


            MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
            headerDecoder.wrap(bufferMessageHeader, 0);
            System.out.println(headerDecoder);
    }
}