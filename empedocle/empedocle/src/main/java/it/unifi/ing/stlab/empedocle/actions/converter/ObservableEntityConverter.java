package it.unifi.ing.stlab.empedocle.actions.converter;

import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;

import org.richfaces.component.UIParameter;

import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

@Named
@RequestScoped
public class ObservableEntityConverter implements Converter {
	
	@Inject
	private ObservableEntityDao observableEntityDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			assignNullToBeanProperty( context, component );
            return null;
        }

		lookup();
        ObservableEntity observableEntity = observableEntityDao.findByUuid( value );

        if (observableEntity == null) {
            throw new ConverterException();
        }

        return observableEntity;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof ObservableEntity)) {
			return null;
		}

		return ((ObservableEntity) value).getUuid();
	}
	
	//XXX aggiunto per evitare NullPointerException su agendaDao (non inizializzato in suggestionList)
	private void lookup() {
		if( observableEntityDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				observableEntityDao = (ObservableEntityDao) jndi.lookup("java:module/ObservableEntityDaoBean");
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	//XXX workaround: when converter returns null values, the <a4j:param> never assign null to the assignTo bean property.
	private void assignNullToBeanProperty( FacesContext context, UIComponent component ) {
		ELContext elContext = context.getELContext();
		
		if( component instanceof UIParameter)
			((UIParameter) component).getAssignToExpression().setValue( elContext, null );		
	}
}
