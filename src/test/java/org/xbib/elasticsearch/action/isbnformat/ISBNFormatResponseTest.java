package org.xbib.elasticsearch.action.isbnformat;

import java.util.Map;
import java.util.Random;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Assert;
import org.junit.Test;

public class ISBNFormatResponseTest extends Assert {
	@Test
	public void testToXContent() throws Exception {
		final ISBNFormatResponse isbnFormatResponse = new ISBNFormatResponse();
		isbnFormatResponse.setIsbn13("9783161484100").setIsbn13Formatted("978-3-16-148410-0").setIsbn10("316148410X")
				.setIsbn10Formatted("3-16-148410-X").setInvalid(null);

		final XContentType[] values = XContentType.values();
		final XContentType xContentType = values[new Random().nextInt(values.length)];
		final BytesReference xContent = XContentHelper.toXContent(isbnFormatResponse, xContentType, true);
		final Map<String, Object> parsedMap = XContentHelper.convertToMap(xContent, false, xContentType).v2();

		@SuppressWarnings("unchecked")
		final Map<String, String> parsedResult = (Map<String, String>) parsedMap.get("result");

		assertEquals("316148410X", parsedResult.get("isbn10"));
		assertEquals("3-16-148410-X", parsedResult.get("isbn10formatted"));
		assertEquals("9783161484100", parsedResult.get("isbn13"));
		assertEquals("978-3-16-148410-0", parsedResult.get("isbn13formatted"));
		assertEquals(null, parsedResult.get("invalid"));
	}

}
