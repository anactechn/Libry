package ana.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Test;

public class LibryTemplateTest {

    final short MESSAGE_HEADER_OFFSET = 0;

    final short BOOK_INFO_OFFSET = 0;
    final short BOOK_INFO_ACTING_BLOCK_LENGTH = 25;
    final short BOOK_INFO_ACTING_VERSION = 100;

    final short EMPLOYEE_INFO_OFFSET = 0;
    final short EMPLOYEE_INFO_ACTING_BLOCK_LENGTH = 32;
    final short EMPLOYEE_ACTING_VERSION = 100;

    final short SUPPLIER_INFO_OFFSET = 0;
    final short SUPPLIER_INFO_ACTING_BLOCK_LENGTH = 20;
    final short SUPPLIER_ACTING_VERSION = 100;

    final short BLOCK_LENGTH_OFFSET = 0;
    final short TEMPLATE_ID_OFFSET = 2;
    final short SCHEMA_ID_OFFSET = 4;
    final short VERSION_OFFSET = 6;

    final short BOOK_ID_OFFSET = 0;
    final short BOOK_GENRE_OFFSET = 4;
    final short PUBLISHED_DATE_OFFSET = 5;
    final short STOCK_OFFSET = 13;
    final short PRICE_OFFSET = 17;
    final short AUTHOR_LENGTH_OFFSET = 25;
    final short AUTHOR_VAR_DATA_OFFSET = 26;

    final short EMPLOYEE_ID_OFFSET = 0;
    final short DT_BIRTH_OFFSET = 4;
    final short DAILY_HOURS_OFFSET = 12;
    final short PAYMENT_FOR_HOUR_OFFSET = 20;
    final short ENTRY_DT_OFFSET = 24;
    final short EMPLOYEE_NAME_LENGTH_OFFSET = 32;
    final short EMPLOYEE_NAME_VAR_DATA_OFFSET = 33;

    final short SUPPLIER_ID_OFFSET = 0;
    final short SUPPLIER_STOCK_OFFSET = 8;
    final short NEXT_DELIVERY_OFFSET = 12;
    final short SUPPLIER_NAME_LENGTH_OFFSET = 20;
    final short SUPPLIER_NAME_VARDATA_OFFSET = 21;

    final byte MESSAGE_HEADER_LENGTH_CAPACITY = 8;

    final byte BOOK_ID_LENGTH_CAPACITY = 4;
    final byte BOOK_GENRE_LENGTH_CAPACITY = 1;
    final byte PUBLISHED_DATE_LENGTH_CAPACITY = 8;
    final byte STOCK_LENGTH_CAPACITY = 4;
    final byte PRICE_LENGTH_CAPACITY = 8;
    final byte AUTHOR_LENGTH_CAPACITY = 1;
    final byte BOOK_LENGTH_CAPACITY = 1;

    final byte EMPLOYEE_ID_LENGTH_CAPACITY = 4;
    final byte DT_BIRTH_LENGTH_CAPACITY = 8;
    final byte DAILY_HOURS_LENGTH_CAPACITY = 8;
    final byte PAYMENT_FOR_HOUR_LENGTH_CAPACITY = 4;
    final byte ENTRY_DT_LENGTH_CAPACITY = 8;
    final byte EMPLOYEE_LENGTH_CAPACITY = 1;
    final byte SSN_LENGTH_CAPACITY = 1;
    final byte ACTUAL_EMPLOYEE_FUNCTION_LENGTH_CAPACITY = 1;
    final byte EMPLOYEE_TELEPHONE_LENGTH_CAPACITY = 1;
    final byte EMPLOYEE_EMAIL_LENGTH_CAPACITY = 1;

    final byte SUPPLIER_ID_LENGTH_CAPACITY = 4;
    final byte SUPPLIER_STOCK_LENGTH_CAPACITY = 4;
    final byte NEXT_DELIVERY_LENGTH_CAPACITY = 8;
    final byte SUPPLIER_LENGTH_CAPACITY = 1;
    final byte EIN_LENGTH_CAPACITY = 1;
    final byte SUPPLIER_TELEPHONE_LENGTH_CAPACITY = 1;
    final byte SUPPLIER_EMAIL_LENGTH_CAPACITY = 1;

    @Test
    public void giveMessageHeader_WhenAssertEqualsAreAllCorrects_ThenPrintResult() {

        final short expectedBlockLength = 85;
        final short expectedTemplateId = 1;
        final short expectedSchemaId = 1;
        final short expectedVersion = 100;

        MutableDirectBuffer bufferMessageHeader = new UnsafeBuffer(ByteBuffer.allocateDirect(MESSAGE_HEADER_LENGTH_CAPACITY));

        bufferMessageHeader.putShort(BLOCK_LENGTH_OFFSET, expectedBlockLength, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(TEMPLATE_ID_OFFSET, expectedTemplateId, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(SCHEMA_ID_OFFSET, expectedSchemaId, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(VERSION_OFFSET, expectedVersion, ByteOrder.LITTLE_ENDIAN);

        final short blockLength = bufferMessageHeader.getShort(BLOCK_LENGTH_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short templateId = bufferMessageHeader.getShort(TEMPLATE_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short schemaId = bufferMessageHeader.getShort(SCHEMA_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short version = bufferMessageHeader.getShort(VERSION_OFFSET, ByteOrder.LITTLE_ENDIAN);

        assertEquals(expectedBlockLength, blockLength);
        assertEquals(expectedTemplateId, templateId);
        assertEquals(expectedSchemaId, schemaId);
        assertEquals(expectedVersion, version);

        MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
        headerDecoder.wrap(bufferMessageHeader, MESSAGE_HEADER_OFFSET);
        System.out.println(headerDecoder);
    }

    @Test
    public void giveMessageBook_WhenAssertEqualsAreAllCorrects_ThenPrintResult() {

        final int expectedBookId = 1497;
        final byte expectedBookGenre = 3;
        final long expectedPublishedDate = -453589200000L;
        final int expectedStock = 20;
        final long expectedPrice = 150;
        final byte expectedAuthorLength = 16;
        final String expectedAuthorVarData = "Vladimir Nabokov";
        final byte expectedBookLength = 6;
        final String expectedBookVarData = "Lolita";

        final short BOOK_LENGTH_OFFSET = (short) (AUTHOR_VAR_DATA_OFFSET + expectedAuthorLength);
        final byte BOOK_VAR_DATA_OFFSET = (byte) (BOOK_LENGTH_OFFSET + BOOK_LENGTH_CAPACITY);

        MutableDirectBuffer bufferMessageBook = new UnsafeBuffer(ByteBuffer.allocateDirect((BOOK_ID_LENGTH_CAPACITY + BOOK_GENRE_LENGTH_CAPACITY + PUBLISHED_DATE_LENGTH_CAPACITY + STOCK_LENGTH_CAPACITY + PRICE_LENGTH_CAPACITY + AUTHOR_LENGTH_CAPACITY + expectedAuthorLength + BOOK_LENGTH_CAPACITY+ expectedBookLength)));

        bufferMessageBook.putInt(BOOK_ID_OFFSET, expectedBookId);
        bufferMessageBook.putByte(BOOK_GENRE_OFFSET, expectedBookGenre);
        bufferMessageBook.putLong(PUBLISHED_DATE_OFFSET, expectedPublishedDate);
        bufferMessageBook.putInt(STOCK_OFFSET, expectedStock);
        bufferMessageBook.putLong(PRICE_OFFSET, expectedPrice);
        bufferMessageBook.putByte(AUTHOR_LENGTH_OFFSET, expectedAuthorLength);
        bufferMessageBook.putStringWithoutLengthAscii(AUTHOR_VAR_DATA_OFFSET, expectedAuthorVarData);
        bufferMessageBook.putByte(BOOK_LENGTH_OFFSET, expectedBookLength);
        bufferMessageBook.putStringWithoutLengthAscii(BOOK_VAR_DATA_OFFSET, expectedBookVarData);

        final int bookId = bufferMessageBook.getInt(BOOK_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte bookGenre = bufferMessageBook.getByte(BOOK_GENRE_OFFSET);
        final long publishedDate = bufferMessageBook.getLong(PUBLISHED_DATE_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int stock = bufferMessageBook.getInt(STOCK_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long price = bufferMessageBook.getLong(PRICE_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte authorLength = bufferMessageBook.getByte(AUTHOR_LENGTH_OFFSET);
        final String authorVarData = bufferMessageBook.getStringWithoutLengthAscii(AUTHOR_VAR_DATA_OFFSET, expectedAuthorLength);
        final byte bookLength = bufferMessageBook.getByte(BOOK_LENGTH_OFFSET);
        final String bookVarData = bufferMessageBook.getStringWithoutLengthAscii(BOOK_VAR_DATA_OFFSET, expectedBookLength);

        assertEquals(expectedBookId, bookId);
        assertEquals(expectedBookGenre, bookGenre);
        assertEquals(expectedPublishedDate, publishedDate);
        assertEquals(expectedStock, stock);
        assertEquals(expectedPrice, price);
        assertEquals(expectedAuthorLength, authorLength);
        assertEquals(expectedAuthorVarData, authorVarData);
        assertEquals(expectedBookLength, bookLength);
        assertEquals(expectedBookVarData, bookVarData);

        BookInfoDecoder bookDecoder = new BookInfoDecoder();
        bookDecoder.wrap(bufferMessageBook, BOOK_INFO_OFFSET, BOOK_INFO_ACTING_BLOCK_LENGTH, BOOK_INFO_ACTING_VERSION);
        System.out.println(bookDecoder);
    }

    @Test
    public void giveMessageEmployeeInfo_WhenAssertEqualsAreAllCorrects_ThenPrintResult() {

        final int expectedEmployeeId = 1024;
        final long expectedDtBirth = 395627553000L;
        final long expectedDailyHours = 28800000L;
        final int expectedPaymentForHour = 500;
        final long expectedEntryDt = 1689467553000L;
        final byte expectedEmployeeNameLength = 8;
        final String expectedEmployeeNameVarData = "John Doe";
        final byte expectedSSNLength = 11;
        final String expectedSSNVarData = "123-45-6789";
        final byte expectedActualEmployeeFunctionLength = 17;
        final String expectedActualEmployeeFunctionVarData = "Software Engineer";
        final byte expectedEmployeeTelephoneLength = 14;
        final String expectedEmployeeTelephoneVarData = "(123) 456-7890";
        final byte expectedEmployeeEmailLength = 20;
        final String expectedEmployeeEmailVarData = "john.doe@example.com";

        final short snnLengthOffset = (short) (EMPLOYEE_NAME_VAR_DATA_OFFSET + expectedEmployeeNameLength);
        final short snnVarDataOffset = snnLengthOffset + SSN_LENGTH_CAPACITY;
        final short actualEmployeeFunctionLengthOffset = (short) (snnVarDataOffset + expectedSSNLength);
        final short actualEmployeeVarDataOffset = actualEmployeeFunctionLengthOffset + ACTUAL_EMPLOYEE_FUNCTION_LENGTH_CAPACITY;
        final short employeeTelephoneLengthOffset = (short) (actualEmployeeVarDataOffset + expectedActualEmployeeFunctionLength);
        final short employeeTelephoneVarDataOffset = employeeTelephoneLengthOffset + EMPLOYEE_TELEPHONE_LENGTH_CAPACITY;
        final short employeeEmailLengthOffset = (short) (employeeTelephoneVarDataOffset + expectedEmployeeTelephoneLength);
        final short employeeEmailVarDataOffset = employeeEmailLengthOffset + EMPLOYEE_EMAIL_LENGTH_CAPACITY;

        MutableDirectBuffer bufferMessageEmployee = new UnsafeBuffer(ByteBuffer.allocateDirect(EMPLOYEE_ID_LENGTH_CAPACITY + DT_BIRTH_LENGTH_CAPACITY + DAILY_HOURS_LENGTH_CAPACITY + PAYMENT_FOR_HOUR_LENGTH_CAPACITY + ENTRY_DT_LENGTH_CAPACITY + EMPLOYEE_LENGTH_CAPACITY + expectedEmployeeNameLength + SSN_LENGTH_CAPACITY + expectedSSNLength + ACTUAL_EMPLOYEE_FUNCTION_LENGTH_CAPACITY + expectedActualEmployeeFunctionLength + EMPLOYEE_TELEPHONE_LENGTH_CAPACITY + expectedEmployeeTelephoneLength + EMPLOYEE_EMAIL_LENGTH_CAPACITY + expectedEmployeeEmailLength));

        bufferMessageEmployee.putInt(EMPLOYEE_ID_OFFSET, expectedEmployeeId);
        bufferMessageEmployee.putLong(DT_BIRTH_OFFSET, expectedDtBirth);
        bufferMessageEmployee.putLong(DAILY_HOURS_OFFSET, expectedDailyHours);
        bufferMessageEmployee.putInt(PAYMENT_FOR_HOUR_OFFSET, expectedPaymentForHour);
        bufferMessageEmployee.putLong(ENTRY_DT_OFFSET, expectedEntryDt);
        bufferMessageEmployee.putByte(EMPLOYEE_NAME_LENGTH_OFFSET, expectedEmployeeNameLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(EMPLOYEE_NAME_VAR_DATA_OFFSET, expectedEmployeeNameVarData);
        bufferMessageEmployee.putByte(snnLengthOffset, expectedSSNLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(snnVarDataOffset, expectedSSNVarData);
        bufferMessageEmployee.putByte(actualEmployeeFunctionLengthOffset, expectedActualEmployeeFunctionLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(actualEmployeeVarDataOffset, expectedActualEmployeeFunctionVarData);
        bufferMessageEmployee.putByte(employeeTelephoneLengthOffset, expectedEmployeeTelephoneLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(employeeTelephoneVarDataOffset, expectedEmployeeTelephoneVarData);
        bufferMessageEmployee.putByte(employeeEmailLengthOffset, expectedEmployeeEmailLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(employeeEmailVarDataOffset, expectedEmployeeEmailVarData);

        final int employeeId = bufferMessageEmployee.getInt(EMPLOYEE_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long dtBirth = bufferMessageEmployee.getLong(DT_BIRTH_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long dailyHours = bufferMessageEmployee.getLong(DAILY_HOURS_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int paymentForHour = bufferMessageEmployee.getInt(PAYMENT_FOR_HOUR_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long entryDt = bufferMessageEmployee.getLong(ENTRY_DT_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte employeeNameLength = bufferMessageEmployee.getByte(EMPLOYEE_NAME_LENGTH_OFFSET);
        final String employeeNameVarData = bufferMessageEmployee.getStringWithoutLengthAscii(EMPLOYEE_NAME_VAR_DATA_OFFSET, expectedEmployeeNameLength);
        final byte ssnLength = bufferMessageEmployee.getByte(snnLengthOffset);
        final String ssnVarData = bufferMessageEmployee.getStringWithoutLengthAscii(snnVarDataOffset, expectedSSNLength);
        final byte actualEmployeeFunctionLength = bufferMessageEmployee.getByte(actualEmployeeFunctionLengthOffset);
        final String actualEmployeeFunctionVarData = bufferMessageEmployee.getStringWithoutLengthAscii(actualEmployeeVarDataOffset, expectedActualEmployeeFunctionLength);
        final byte employeeTelephoneLength = bufferMessageEmployee.getByte(employeeTelephoneLengthOffset);
        final String employeeTelephoneVarData = bufferMessageEmployee.getStringWithoutLengthAscii(employeeTelephoneVarDataOffset, expectedEmployeeTelephoneLength);
        final byte employeeEmailLength = bufferMessageEmployee.getByte(employeeEmailLengthOffset);
        final String employeeEmailVarData = bufferMessageEmployee.getStringWithoutLengthAscii(employeeEmailVarDataOffset, expectedEmployeeEmailLength);

        assertEquals(expectedEmployeeId, employeeId);
        assertEquals(expectedDtBirth, dtBirth);
        assertEquals(expectedDailyHours, dailyHours);
        assertEquals(expectedPaymentForHour, paymentForHour);
        assertEquals(expectedEntryDt, entryDt);
        assertEquals(expectedEmployeeNameLength, employeeNameLength);
        assertEquals(expectedEmployeeNameVarData, employeeNameVarData);
        assertEquals(expectedSSNLength, ssnLength);
        assertEquals(expectedSSNVarData, ssnVarData);
        assertEquals(expectedActualEmployeeFunctionLength, actualEmployeeFunctionLength);
        assertEquals(expectedActualEmployeeFunctionVarData, actualEmployeeFunctionVarData);
        assertEquals(expectedEmployeeTelephoneLength, employeeTelephoneLength);
        assertEquals(expectedEmployeeTelephoneVarData, employeeTelephoneVarData);
        assertEquals(expectedEmployeeEmailLength, employeeEmailLength);
        assertEquals(expectedEmployeeEmailVarData, employeeEmailVarData);

        EmployeeInfoDecoder employeeDecoder = new EmployeeInfoDecoder();
        employeeDecoder.wrap(bufferMessageEmployee, EMPLOYEE_INFO_OFFSET, EMPLOYEE_INFO_ACTING_BLOCK_LENGTH, EMPLOYEE_ACTING_VERSION);
        System.out.println(employeeDecoder);
    }

    @Test
    public void giveMessageSupplierInfo_WhenAssertEqualsAreAllCorrects_ThenPrintResult() {

        final int expectedSupplierId = 5678;
        final int expectedBookId = 1497;
        final int expectedSupplierStock = 100;
        final long expectedNextDelivery = 1753231793000L;
        final byte expectedSupplierNameLength = 13;
        final String expectedSupplierNameVarData = "Book Supplier";
        final byte expectedEINLength = 10;
        final String expectedEINVarData = "12-3456789";
        final byte expectedSupplierTelephoneLength = 14;
        final String expectedSupplierTelephoneVarData = "(987) 654-3210";
        final byte expectedSupplierEmailLength = 20;
        final String expectedSupplierEmailVarData = "supplier@example.com";

        final short bookIdOffset = 4;
        final short einLengthOffset = (short) (SUPPLIER_NAME_VARDATA_OFFSET + expectedSupplierNameLength);
        final short einVarDataOffset = einLengthOffset + EIN_LENGTH_CAPACITY;
        final short supplierTelephoneLengthOffset = (short) (einVarDataOffset + expectedEINLength);
        final short supplierTelephoneVarDataOffset = supplierTelephoneLengthOffset + SUPPLIER_TELEPHONE_LENGTH_CAPACITY;
        final short supplierEmailLengthOffset = (short) (supplierTelephoneVarDataOffset + expectedSupplierTelephoneLength);
        final short supplierEmailVarDataOffset = supplierEmailLengthOffset + SUPPLIER_EMAIL_LENGTH_CAPACITY;

        MutableDirectBuffer bufferMessageSupplier = new UnsafeBuffer(ByteBuffer.allocateDirect(SUPPLIER_ID_LENGTH_CAPACITY + BOOK_ID_LENGTH_CAPACITY + SUPPLIER_STOCK_LENGTH_CAPACITY + NEXT_DELIVERY_LENGTH_CAPACITY + SUPPLIER_LENGTH_CAPACITY + expectedSupplierNameLength + EIN_LENGTH_CAPACITY + expectedEINLength + SUPPLIER_TELEPHONE_LENGTH_CAPACITY + expectedSupplierTelephoneLength + SUPPLIER_EMAIL_LENGTH_CAPACITY + expectedSupplierEmailLength));

        bufferMessageSupplier.putInt(SUPPLIER_ID_OFFSET, expectedSupplierId);
        bufferMessageSupplier.putInt(bookIdOffset, expectedBookId);
        bufferMessageSupplier.putInt(SUPPLIER_STOCK_OFFSET, expectedSupplierStock);
        bufferMessageSupplier.putLong(NEXT_DELIVERY_OFFSET, expectedNextDelivery);
        bufferMessageSupplier.putByte(SUPPLIER_NAME_LENGTH_OFFSET, expectedSupplierNameLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(SUPPLIER_NAME_VARDATA_OFFSET, expectedSupplierNameVarData);
        bufferMessageSupplier.putByte(einLengthOffset, expectedEINLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(einVarDataOffset, expectedEINVarData);
        bufferMessageSupplier.putByte(supplierTelephoneLengthOffset, expectedSupplierTelephoneLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(supplierTelephoneVarDataOffset, expectedSupplierTelephoneVarData);
        bufferMessageSupplier.putByte(supplierEmailLengthOffset, expectedSupplierEmailLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(supplierEmailVarDataOffset, expectedSupplierEmailVarData);

        final int supplierId = bufferMessageSupplier.getInt(SUPPLIER_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int bookId = bufferMessageSupplier.getInt(bookIdOffset, ByteOrder.LITTLE_ENDIAN);
        final int supplierStock = bufferMessageSupplier.getInt(SUPPLIER_STOCK_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long nextDelivery = bufferMessageSupplier.getLong(NEXT_DELIVERY_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte supplierNameLength = bufferMessageSupplier.getByte(SUPPLIER_NAME_LENGTH_OFFSET);
        final String supplierNameVarData = bufferMessageSupplier.getStringWithoutLengthAscii(SUPPLIER_NAME_VARDATA_OFFSET, expectedSupplierNameLength);
        final byte einLength = bufferMessageSupplier.getByte(einLengthOffset);
        final String einVarData = bufferMessageSupplier.getStringWithoutLengthAscii(einVarDataOffset, expectedEINLength);
        final byte supplierTelephoneLength = bufferMessageSupplier.getByte(supplierTelephoneLengthOffset);
        final String supplierTelephoneVarData = bufferMessageSupplier.getStringWithoutLengthAscii(supplierTelephoneVarDataOffset, expectedSupplierTelephoneLength);
        final byte supplierEmailLength = bufferMessageSupplier.getByte(supplierEmailLengthOffset);
        final String supplierEmailVarData = bufferMessageSupplier.getStringWithoutLengthAscii(supplierEmailVarDataOffset, expectedSupplierEmailLength);

        assertEquals(expectedSupplierId, supplierId);
        assertEquals(expectedBookId, bookId);
        assertEquals(expectedSupplierStock, supplierStock);
        assertEquals(expectedNextDelivery, nextDelivery);
        assertEquals(expectedSupplierNameLength, supplierNameLength);
        assertEquals(expectedSupplierNameVarData, supplierNameVarData);
        assertEquals(expectedEINLength, einLength);
        assertEquals(expectedEINVarData, einVarData);
        assertEquals(expectedSupplierTelephoneLength, supplierTelephoneLength);
        assertEquals(expectedSupplierTelephoneVarData, supplierTelephoneVarData);
        assertEquals(expectedSupplierEmailLength, supplierEmailLength);
        assertEquals(expectedSupplierEmailVarData, supplierEmailVarData);

        SupplierInfoDecoder supplierDecoder = new SupplierInfoDecoder();
        supplierDecoder.wrap(bufferMessageSupplier, SUPPLIER_INFO_OFFSET, SUPPLIER_INFO_ACTING_BLOCK_LENGTH, SUPPLIER_ACTING_VERSION);
        System.out.println(supplierDecoder);
    }

}

