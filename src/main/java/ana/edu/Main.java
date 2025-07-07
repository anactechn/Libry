package ana.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Test;

public class Main {

        @Test
        public void test1() {
            final short BLOCK_LENGHT_OFFSET = 0;

            final short expectedBlockLength = 35;

            MutableDirectBuffer bookBuffer = new UnsafeBuffer(ByteBuffer.allocateDirect(35));

            bookBuffer.putShort(BLOCK_LENGHT_OFFSET, expectedBlockLength, ByteOrder.LITTLE_ENDIAN);

            final short blockLength = (bookBuffer.getShort(BLOCK_LENGHT_OFFSET, ByteOrder.LITTLE_ENDIAN));
            assertEquals(expectedBlockLength, blockLength);

    }
}