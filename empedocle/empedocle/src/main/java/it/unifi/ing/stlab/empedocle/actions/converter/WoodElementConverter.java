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

import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

@Named
@RequestScoped
public class WoodElementConverter implements Converter {
	
	@Inject
	private WoodElementDao woodElementDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			assignNullToBeanProperty( context, component );
            return null;
        }

		lookup();
        WoodElement woodElement = woodElementDao.findByUuid( value );

        if (woodElement == null) {
            throw new ConverterException();
        }

        return woodElement;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof WoodElement)) {
			return null;
		}

		return ((WoodElement) value).getUuid();
	}
	
	//XXX aggiunto per evitare NullPointerException su agendaDao (non inizializzato in suggestionList)
	private void lookup() {
		if( woodElementDao == null ) {
			try {
				InitialContext jndi = new InitialContext();
				woodElementDao = (WoodElementDao) jndi.lookup("java:module/WoodElementDaoBean");
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
