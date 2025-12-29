package it.unifi.ing.stlab.empedocle;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

import it.unifi.ing.stlab.football.api.PlayerController;
import it.unifi.ing.stlab.football.api.MatchController;
import it.unifi.ing.stlab.football.api.RosterController;
import it.unifi.ing.stlab.football.api.ParticipationController;
import it.unifi.ing.stlab.empedocle.api.AgendaController;
import it.unifi.ing.stlab.empedocle.api.MeasurementSessionController;
import it.unifi.ing.stlab.empedocle.api.MessageController;
import it.unifi.ing.stlab.empedocle.api.ServiceController;
import it.unifi.ing.stlab.empedocle.api.StaffController;
import it.unifi.ing.stlab.empedocle.api.SurveyScheduleController;
import it.unifi.ing.stlab.empedocle.api.FactController;
import it.unifi.ing.stlab.empedocle.api.TypeController;

@ApplicationPath("/api")
public class EmpedocleApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PlayerController.class);
        classes.add(MatchController.class);
        classes.add(RosterController.class);
        classes.add(ParticipationController.class);
        classes.add(AgendaController.class);
        classes.add(MeasurementSessionController.class);
        classes.add(MessageController.class);
        classes.add(ServiceController.class);
        classes.add(StaffController.class);
        classes.add(SurveyScheduleController.class);

        classes.add(FactController.class);
        classes.add(TypeController.class);

        // Register security filter
        classes.add(it.unifi.ing.stlab.security.ApiKeyFilter.class);

        return classes;
    }
}
