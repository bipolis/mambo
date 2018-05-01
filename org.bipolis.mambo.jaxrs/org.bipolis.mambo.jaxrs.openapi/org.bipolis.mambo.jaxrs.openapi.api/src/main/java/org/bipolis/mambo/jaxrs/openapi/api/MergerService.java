package org.bipolis.mambo.jaxrs.openapi.api;

import java.util.Arrays;
import java.util.List;

public interface MergerService {

	default <S> S merge(List<S> merges) throws MergeException {
		if (merges.isEmpty()) {
			return null;
		}
		if (merges.size() == 1) {
			return merges.get(0);
		}
		return merge(merges.get(0), merges.subList(1, merges.size()));

	}

	default <S> S merge(S base, S merge) throws MergeException {
		if (merge == null) {
			return base;
		}
		return merge(base, Arrays.asList(merge));

	}

	<S> S merge(S base, List<S> merges) throws MergeException;

}
