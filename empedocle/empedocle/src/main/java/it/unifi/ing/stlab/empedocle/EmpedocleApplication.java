package it.unifi.ing.stlab.empedocle;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import it.unifi.ing.stlab.woodelements.api.WoodElementResource;

@ApplicationPath("/api")
public class EmpedocleApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(WoodElementResource.class);

        return classes;
    }
}
