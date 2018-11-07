package org.xbib.elasticsearch.common.decompound.fst;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class FstDecompounderTest extends Assert {
	private static final String DECOMPOUND_FST_WORDS_FST = "/decompound/fst/words.fst";

	@Test
	public void testUseOfTwoDecompounders() throws Exception {
		final Set<String> expected = new HashSet<>(Arrays.asList("jahr", "jahres", "jahre", "feier"));

		final FstDecompounder firstDecompunder = new FstDecompounder(getClass().getResourceAsStream(DECOMPOUND_FST_WORDS_FST),
				null);
		final Set<String> firstDecompunderTokens = getWordTokens(firstDecompunder, "Jahresfeier");
		assertEquals(expected, firstDecompunderTokens);

		final FstDecompounder secondDecompunder = new FstDecompounder(getClass().getResourceAsStream(DECOMPOUND_FST_WORDS_FST),
				null);
		final Set<String> secondDecompunderTokens = getWordTokens(secondDecompunder, "Jahresfeier");
		assertEquals(expected, secondDecompunderTokens);
	}

	private Set<String> getWordTokens(final FstDecompounder decompunder, final String word) {
		final HashSet<String> result = new HashSet<>();
		for (final String suggestions : decompunder.decompound(word)) {
			for (final String suggestion : suggestions.split(",")) {
				for (final String token : suggestion.split("\\.")) {
					result.add(token);
				}
			}
		}
		return result;
	}
}
