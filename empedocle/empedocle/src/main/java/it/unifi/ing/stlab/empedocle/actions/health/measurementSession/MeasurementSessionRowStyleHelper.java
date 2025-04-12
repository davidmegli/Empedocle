package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;

import java.util.List;

public class MeasurementSessionRowStyleHelper {

	public static String getRowStyleClasses(List<MeasurementSession> list) {
		StringBuffer buffer = new StringBuffer();

		boolean first = true;
		for (MeasurementSession measurementSession : list) {
			if (first) {
				first = false;
			} else {
				buffer.append(", ");
			}
			buffer.append(getRowStyleClass(measurementSession));
		}

		return buffer.toString();
	}

	public static String getRowStyleClass(MeasurementSession measurementSession) {
		if (measurementSession == null)
			return "";

		switch (measurementSession.getStatus()) {
		case TODO:
			switch (measurementSession.getSurveySchedule().getStatus()) {
			case BOOKED:
				return "booked";
			case ACCEPTED:
			default:
				return "success";
			}
		case RUNNING:
			return "error";
		case SUSPENDED:
			return "warning";
		case DONE:
			return "info";
		case CONCLUDED:
			return "concluded";
		default:
			return "base";
		}
	}

}
