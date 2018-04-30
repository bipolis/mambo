package org.bipolis.mambo.jaxrs.openapi.api;

import javax.xml.bind.annotation.XmlType;

import lombok.Builder;
import lombok.Getter;

@XmlType
@Builder
@Getter
public class ApplicationBaseDTO {

	private String name;

	private String base;
}
