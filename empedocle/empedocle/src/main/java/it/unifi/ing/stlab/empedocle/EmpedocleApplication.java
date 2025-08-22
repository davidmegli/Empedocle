package it.unifi.ing.stlab.empedocle;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import it.unifi.ing.stlab.woodelements.api.WoodElementResource;
import it.unifi.ing.stlab.woodelements.api.WoodElementActionResource;
import it.unifi.ing.stlab.empedocle.api.AgendaResource;
import it.unifi.ing.stlab.empedocle.api.MeasurementSessionResource;
import it.unifi.ing.stlab.empedocle.api.MessageResource;
import it.unifi.ing.stlab.empedocle.api.ServiceResource;
import it.unifi.ing.stlab.empedocle.api.StaffResource;
import it.unifi.ing.stlab.empedocle.api.SurveyScheduleResource;
import it.unifi.ing.stlab.empedocle.api.FactController;

@ApplicationPath("/api")
public class EmpedocleApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(WoodElementResource.class);
        classes.add(WoodElementActionResource.class);

        classes.add(AgendaResource.class);
        classes.add(MeasurementSessionResource.class);
        classes.add(MessageResource.class);
        classes.add(ServiceResource.class);
        classes.add(StaffResource.class);
        classes.add(SurveyScheduleResource.class);

        classes.add(FactController.class);

        classes.add(it.unifi.ing.stlab.empedocle.security.ApiKeyFilter.class);


        return classes;
    }
}
