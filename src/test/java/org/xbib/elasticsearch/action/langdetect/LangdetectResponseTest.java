package org.xbib.elasticsearch.action.langdetect;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Assert;
import org.junit.Test;
import org.xbib.elasticsearch.common.langdetect.Language;

public class LangdetectResponseTest extends Assert {
	@Test
	public void testToXContent() throws Exception {
		final LangdetectResponse langdetectResponse = new LangdetectResponse();
		langdetectResponse.setLanguages(Collections.singletonList(new Language("en", 0.99999)));

		final XContentType[] values = XContentType.values();
		final XContentType xContentType = values[new Random().nextInt(values.length)];
		final BytesReference xContent = XContentHelper.toXContent(langdetectResponse, xContentType, true);
		final Map<String, Object> parsedMap = XContentHelper.convertToMap(xContent, false, xContentType).v2();

		@SuppressWarnings("unchecked")
		final List<Map<String, Object>> parsedLanguages = (List<Map<String, Object>>) parsedMap.get("languages");
		assertEquals(1, parsedLanguages.size());

		final Map<String, Object> parsedLanguage = parsedLanguages.get(0);
		assertEquals("en", parsedLanguage.get("language"));
		assertEquals(0.99999, (double) parsedLanguage.get("probability"), 0.001);
	}

}
