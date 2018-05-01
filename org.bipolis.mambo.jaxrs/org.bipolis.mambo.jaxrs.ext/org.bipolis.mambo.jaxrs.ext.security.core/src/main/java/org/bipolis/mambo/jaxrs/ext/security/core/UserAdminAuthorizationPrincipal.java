package org.bipolis.mambo.jaxrs.ext.security.core;

import java.security.Principal;

import org.osgi.service.useradmin.Authorization;

public class UserAdminAuthorizationPrincipal implements Principal, Authorization {

	@Override
	public boolean hasRole(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
