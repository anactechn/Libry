package ana.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Test;

public class LibryTemplateTest {

    @Test
    public void giveMessageHeader_WhenAssertEqualsAreAllCorrects_ThenPrintResult() {
        final short BLOCK_LENGHT_OFFSET = 0;
        final short TEMPLATE_ID_OFFSET = 2;
        final short SCHEMA_ID_OFFSET = 4;
        final short VERSION_OFFSET = 6;

        final short expectedBlockLength = 85;
        final short expectedTemplateId = 1;
        final short expectedSchemaId = 1;
        final short expectedVersion = 100;

        MutableDirectBuffer bufferMessageHeader = new UnsafeBuffer(ByteBuffer.allocateDirect(8));

        bufferMessageHeader.putShort(BLOCK_LENGHT_OFFSET, expectedBlockLength, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(TEMPLATE_ID_OFFSET, expectedTemplateId, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(SCHEMA_ID_OFFSET, expectedSchemaId, ByteOrder.LITTLE_ENDIAN);
        bufferMessageHeader.putShort(VERSION_OFFSET, expectedVersion, ByteOrder.LITTLE_ENDIAN);

        final short blockLength = bufferMessageHeader.getShort(BLOCK_LENGHT_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short templateId = bufferMessageHeader.getShort(TEMPLATE_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short schemaId = bufferMessageHeader.getShort(SCHEMA_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final short version = bufferMessageHeader.getShort(VERSION_OFFSET, ByteOrder.LITTLE_ENDIAN);

        assertEquals(expectedBlockLength, blockLength);
        assertEquals(expectedTemplateId, templateId);
        assertEquals(expectedSchemaId, schemaId);
        assertEquals(expectedVersion, version);

        MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
        headerDecoder.wrap(bufferMessageHeader, 0);
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

        MutableDirectBuffer bufferMessageBook = new UnsafeBuffer(ByteBuffer.allocateDirect(
            4 + 1 + 8 + 4 + 8 + 1 + expectedAuthorLength + 1 + expectedBookLength
        ));

        final short BOOK_ID_OFFSET = 0;
        final short BOOK_GENRE_OFFSET = 4;
        final short PUBLISHED_DATE_OFFSET = 5;
        final short STOCK_OFFSET = 13;
        final short PRICE_OFFSET = 17;
        final short AUTHOR_LENGTH_OFFSET = 25;
        final short AUTHOR_VARDATA_OFFSET = 26;
        final short BOOK_LENGTH_OFFSET = (short) (AUTHOR_VARDATA_OFFSET + expectedAuthorLength);
        final byte BOOK_VARDATA_OFFSET = (byte) (BOOK_LENGTH_OFFSET + 1);

        bufferMessageBook.putInt(BOOK_ID_OFFSET, expectedBookId);
        bufferMessageBook.putByte(BOOK_GENRE_OFFSET, expectedBookGenre);
        bufferMessageBook.putLong(PUBLISHED_DATE_OFFSET, expectedPublishedDate);
        bufferMessageBook.putInt(STOCK_OFFSET, expectedStock);
        bufferMessageBook.putLong(PRICE_OFFSET, expectedPrice);
        bufferMessageBook.putByte(AUTHOR_LENGTH_OFFSET, expectedAuthorLength);
        bufferMessageBook.putStringWithoutLengthAscii(AUTHOR_VARDATA_OFFSET, expectedAuthorVarData);
        bufferMessageBook.putByte(BOOK_LENGTH_OFFSET, expectedBookLength);
        bufferMessageBook.putStringWithoutLengthAscii(BOOK_VARDATA_OFFSET, expectedBookVarData);

        final int bookId = bufferMessageBook.getInt(BOOK_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte bookGenre = bufferMessageBook.getByte(BOOK_GENRE_OFFSET);
        final long publishedDate = bufferMessageBook.getLong(PUBLISHED_DATE_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int stock = bufferMessageBook.getInt(STOCK_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long price = bufferMessageBook.getLong(PRICE_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte authorLength = bufferMessageBook.getByte(AUTHOR_LENGTH_OFFSET);
        final String authorVarData = bufferMessageBook.getStringWithoutLengthAscii(AUTHOR_VARDATA_OFFSET, expectedAuthorLength);
        final byte bookLength = bufferMessageBook.getByte(BOOK_LENGTH_OFFSET);
        final String bookVarData = bufferMessageBook.getStringWithoutLengthAscii(BOOK_VARDATA_OFFSET, expectedBookLength);

        assertEquals(expectedBookId, bookId);
        assertEquals(expectedBookGenre, bookGenre);
        assertEquals(expectedPublishedDate, publishedDate);
        assertEquals(expectedStock, stock);
        assertEquals(expectedPrice, price);
        assertEquals(expectedAuthorLength, authorLength);
        assertEquals(expectedAuthorVarData, authorVarData);
        assertEquals(expectedBookLength, bookLength);
        assertEquals(expectedBookVarData, bookVarData);

        BookInfoMessageDecoder bookDecoder = new BookInfoMessageDecoder();
        bookDecoder.wrap(bufferMessageBook, 0, 25, 100);
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

        final short EMPLOYEE_ID_OFFSET = 0;
        final short DT_BIRTH_OFFSET = 4;
        final short DAILY_HOURS_OFFSET = 12;
        final short PAYMENT_FOR_HOUR_OFFSET = 20;
        final short ENTRY_DT_OFFSET = 24;
        final short EMPLOYEE_NAME_LENGTH_OFFSET = 32;
        final short EMPLOYEE_NAME_VARDATA_OFFSET = 33;
        final short SSN_LENGTH_OFFSET = (short) (EMPLOYEE_NAME_VARDATA_OFFSET + expectedEmployeeNameLength);
        final short SSN_VARDATA_OFFSET = SSN_LENGTH_OFFSET + 1;
        final short ACTUAL_EMPLOYEE_FUNCTION_LENGTH_OFFSET = (short) (SSN_VARDATA_OFFSET + expectedSSNLength);
        final short ACTUAL_EMPLOYEE_FUNCTION_VARDATA_OFFSET = ACTUAL_EMPLOYEE_FUNCTION_LENGTH_OFFSET + 1;
        final short EMPLOYEE_TELEPHONE_LENGTH_OFFSET = (short) (ACTUAL_EMPLOYEE_FUNCTION_VARDATA_OFFSET + expectedActualEmployeeFunctionLength);
        final short EMPLOYEE_TELEPHONE_VARDATA_OFFSET = EMPLOYEE_TELEPHONE_LENGTH_OFFSET + 1;
        final short EMPLOYEE_EMAIL_LENGTH_OFFSET = (short) (EMPLOYEE_TELEPHONE_VARDATA_OFFSET + expectedEmployeeTelephoneLength);
        final short EMPLOYEE_EMAIL_VARDATA_OFFSET = EMPLOYEE_EMAIL_LENGTH_OFFSET + 1;

        MutableDirectBuffer bufferMessageEmployee = new UnsafeBuffer(ByteBuffer.allocateDirect(
            4 + 8 + 8 + 4 + 8 +
                1 + expectedEmployeeNameLength +
                1 + expectedSSNLength +
                1 + expectedActualEmployeeFunctionLength +
                1 + expectedEmployeeTelephoneLength +
                1 + expectedEmployeeEmailLength));

        bufferMessageEmployee.putInt(EMPLOYEE_ID_OFFSET, expectedEmployeeId);
        bufferMessageEmployee.putLong(DT_BIRTH_OFFSET, expectedDtBirth);
        bufferMessageEmployee.putLong(DAILY_HOURS_OFFSET, expectedDailyHours);
        bufferMessageEmployee.putInt(PAYMENT_FOR_HOUR_OFFSET, expectedPaymentForHour);
        bufferMessageEmployee.putLong(ENTRY_DT_OFFSET, expectedEntryDt);
        bufferMessageEmployee.putByte(EMPLOYEE_NAME_LENGTH_OFFSET, expectedEmployeeNameLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(EMPLOYEE_NAME_VARDATA_OFFSET, expectedEmployeeNameVarData);
        bufferMessageEmployee.putByte(SSN_LENGTH_OFFSET, expectedSSNLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(SSN_VARDATA_OFFSET, expectedSSNVarData);
        bufferMessageEmployee.putByte(ACTUAL_EMPLOYEE_FUNCTION_LENGTH_OFFSET, expectedActualEmployeeFunctionLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(ACTUAL_EMPLOYEE_FUNCTION_VARDATA_OFFSET, expectedActualEmployeeFunctionVarData);
        bufferMessageEmployee.putByte(EMPLOYEE_TELEPHONE_LENGTH_OFFSET, expectedEmployeeTelephoneLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(EMPLOYEE_TELEPHONE_VARDATA_OFFSET, expectedEmployeeTelephoneVarData);
        bufferMessageEmployee.putByte(EMPLOYEE_EMAIL_LENGTH_OFFSET, expectedEmployeeEmailLength);
        bufferMessageEmployee.putStringWithoutLengthAscii(EMPLOYEE_EMAIL_VARDATA_OFFSET, expectedEmployeeEmailVarData);

        final int employeeId = bufferMessageEmployee.getInt(EMPLOYEE_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long dtBirth = bufferMessageEmployee.getLong(DT_BIRTH_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long dailyHours = bufferMessageEmployee.getLong(DAILY_HOURS_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int paymentForHour = bufferMessageEmployee.getInt(PAYMENT_FOR_HOUR_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long entryDt = bufferMessageEmployee.getLong(ENTRY_DT_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte employeeNameLength = bufferMessageEmployee.getByte(EMPLOYEE_NAME_LENGTH_OFFSET);
        final String employeeNameVarData = bufferMessageEmployee.getStringWithoutLengthAscii(EMPLOYEE_NAME_VARDATA_OFFSET, expectedEmployeeNameLength);
        final byte ssnLength = bufferMessageEmployee.getByte(SSN_LENGTH_OFFSET);
        final String ssnVarData = bufferMessageEmployee.getStringWithoutLengthAscii(SSN_VARDATA_OFFSET, expectedSSNLength);
        final byte actualEmployeeFunctionLength = bufferMessageEmployee.getByte(ACTUAL_EMPLOYEE_FUNCTION_LENGTH_OFFSET);
        final String actualEmployeeFunctionVarData = bufferMessageEmployee.getStringWithoutLengthAscii(ACTUAL_EMPLOYEE_FUNCTION_VARDATA_OFFSET,
            expectedActualEmployeeFunctionLength);
        final byte employeeTelephoneLength = bufferMessageEmployee.getByte(EMPLOYEE_TELEPHONE_LENGTH_OFFSET);
        final String employeeTelephoneVarData = bufferMessageEmployee.getStringWithoutLengthAscii(EMPLOYEE_TELEPHONE_VARDATA_OFFSET, expectedEmployeeTelephoneLength);
        final byte employeeEmailLength = bufferMessageEmployee.getByte(EMPLOYEE_EMAIL_LENGTH_OFFSET);
        final String employeeEmailVarData = bufferMessageEmployee.getStringWithoutLengthAscii(EMPLOYEE_EMAIL_VARDATA_OFFSET, expectedEmployeeEmailLength);

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

        EmployeeInfoMessageDecoder employeeDecoder = new EmployeeInfoMessageDecoder();
        employeeDecoder.wrap(bufferMessageEmployee, 0, 32, 100);
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

        final short SUPPLIER_ID_OFFSET = 0;
        final short BOOK_ID_OFFSET = 4;
        final short SUPPLIER_STOCK_OFFSET = 8;
        final short NEXT_DELIVERY_OFFSET = 12;
        final short SUPPLIER_NAME_LENGTH_OFFSET = 20;
        final short SUPPLIER_NAME_VARDATA_OFFSET = 21;
        final short EIN_LENGTH_OFFSET = (short) (SUPPLIER_NAME_VARDATA_OFFSET + expectedSupplierNameLength);
        final short EIN_VARDATA_OFFSET = EIN_LENGTH_OFFSET + 1;
        final short SUPPLIER_TELEPHONE_LENGTH_OFFSET = (short) (EIN_VARDATA_OFFSET + expectedEINLength);
        final short SUPPLIER_TELEPHONE_VARDATA_OFFSET = SUPPLIER_TELEPHONE_LENGTH_OFFSET + 1;
        final short SUPPLIER_EMAIL_LENGTH_OFFSET = (short) (SUPPLIER_TELEPHONE_VARDATA_OFFSET + expectedSupplierTelephoneLength);
        final short SUPPLIER_EMAIL_VARDATA_OFFSET = SUPPLIER_EMAIL_LENGTH_OFFSET + 1;

        MutableDirectBuffer bufferMessageSupplier = new UnsafeBuffer(ByteBuffer.allocateDirect(
            4 + 4 + 4 + 8 +
                1 + expectedSupplierNameLength +
                1 + expectedEINLength +
                1 + expectedSupplierTelephoneLength +
                1 + expectedSupplierEmailLength
        ));

        bufferMessageSupplier.putInt(SUPPLIER_ID_OFFSET, expectedSupplierId);
        bufferMessageSupplier.putInt(BOOK_ID_OFFSET, expectedBookId);
        bufferMessageSupplier.putInt(SUPPLIER_STOCK_OFFSET, expectedSupplierStock);
        bufferMessageSupplier.putLong(NEXT_DELIVERY_OFFSET, expectedNextDelivery);
        bufferMessageSupplier.putByte(SUPPLIER_NAME_LENGTH_OFFSET, expectedSupplierNameLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(SUPPLIER_NAME_VARDATA_OFFSET, expectedSupplierNameVarData);
        bufferMessageSupplier.putByte(EIN_LENGTH_OFFSET, expectedEINLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(EIN_VARDATA_OFFSET, expectedEINVarData);
        bufferMessageSupplier.putByte(SUPPLIER_TELEPHONE_LENGTH_OFFSET, expectedSupplierTelephoneLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(SUPPLIER_TELEPHONE_VARDATA_OFFSET, expectedSupplierTelephoneVarData);
        bufferMessageSupplier.putByte(SUPPLIER_EMAIL_LENGTH_OFFSET, expectedSupplierEmailLength);
        bufferMessageSupplier.putStringWithoutLengthAscii(SUPPLIER_EMAIL_VARDATA_OFFSET, expectedSupplierEmailVarData);

        final int supplierId = bufferMessageSupplier.getInt(SUPPLIER_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int bookId = bufferMessageSupplier.getInt(BOOK_ID_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final int supplierStock = bufferMessageSupplier.getInt(SUPPLIER_STOCK_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final long nextDelivery = bufferMessageSupplier.getLong(NEXT_DELIVERY_OFFSET, ByteOrder.LITTLE_ENDIAN);
        final byte supplierNameLength = bufferMessageSupplier.getByte(SUPPLIER_NAME_LENGTH_OFFSET);
        final String supplierNameVarData = bufferMessageSupplier.getStringWithoutLengthAscii(SUPPLIER_NAME_VARDATA_OFFSET, expectedSupplierNameLength);
        final byte einLength = bufferMessageSupplier.getByte(EIN_LENGTH_OFFSET);
        final String einVarData = bufferMessageSupplier.getStringWithoutLengthAscii(EIN_VARDATA_OFFSET, expectedEINLength);
        final byte supplierTelephoneLength = bufferMessageSupplier.getByte(SUPPLIER_TELEPHONE_LENGTH_OFFSET);
        final String supplierTelephoneVarData = bufferMessageSupplier.getStringWithoutLengthAscii(SUPPLIER_TELEPHONE_VARDATA_OFFSET, expectedSupplierTelephoneLength);
        final byte supplierEmailLength = bufferMessageSupplier.getByte(SUPPLIER_EMAIL_LENGTH_OFFSET);
        final String supplierEmailVarData = bufferMessageSupplier.getStringWithoutLengthAscii(SUPPLIER_EMAIL_VARDATA_OFFSET, expectedSupplierEmailLength);

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

        SupplierInfoMessageDecoder supplierDecoder = new SupplierInfoMessageDecoder();
        supplierDecoder.wrap(bufferMessageSupplier, 0, 20, 100);
        System.out.println(supplierDecoder);
    }

}

