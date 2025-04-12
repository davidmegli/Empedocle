package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;

import java.util.UUID;

public class SurveyScheduleFactory {

	public static SurveySchedule createSurveySchedule() {
		return new SurveySchedule( UUID.randomUUID().toString() );
	}
}
