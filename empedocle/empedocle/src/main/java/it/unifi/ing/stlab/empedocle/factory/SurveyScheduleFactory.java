package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.SurveySchedule;

import java.util.UUID;

public class SurveyScheduleFactory {

	public static SurveySchedule createSurveySchedule() {
		return new SurveySchedule( UUID.randomUUID().toString() );
	}
}
