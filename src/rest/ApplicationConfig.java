package rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.model.UserResource;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
	@Override
	public Set<Class<?>> getClasses() {


		return new HashSet<Class<?>>(Arrays.asList(
			UsersResource.class,
			RestaurantsResource.class,
			ReservationsResource.class,
			InvitationsResource.class,
			VisitsResource.class,
			OrdersResource.class
		));
	}
}
