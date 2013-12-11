package cz.muni.fi.pa165.library.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
 
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cz.muni.fi.pa165.library.rest.ServiceResource.class);
        resources.add(cz.muni.fi.pa165.library.rest.CustomerServiceResource.class);
        resources.add(cz.muni.fi.pa165.library.rest.CustomerResource.class);
    }
}
