package org.bipolis.mambo.jaxrs.openapi.basic;

import java.io.IOException;
import java.util.List;

import org.bipolis.mambo.jaxrs.openapi.api.MergeException;
import org.bipolis.mambo.jaxrs.openapi.api.MergerService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = { MergerService.class })
public class BaseMergerService implements MergerService {

	ObjectMapper mapper;

	@Activate
	public void activate() {

		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setDefaultMergeable(true);

	}

	@Override
	public <S> S merge(S base, List<S> merges) throws MergeException {

		JsonNode nodeBase = mapper.convertValue(base, JsonNode.class);

		for (S merge : merges) {
			try {
				base = mapper.readerForUpdating(merge).readValue(nodeBase);
			} catch (IOException e) {
				e.printStackTrace();
				throw new MergeException(base, merge, e);
			}
		}

		return base;
	}

}
