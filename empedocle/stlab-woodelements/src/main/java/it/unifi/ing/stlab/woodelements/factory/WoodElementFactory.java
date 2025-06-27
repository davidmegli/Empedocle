package it.unifi.ing.stlab.woodelements.factory;

import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;

import java.util.UUID;

//TODO: extend class

public class WoodElementFactory {

	public static WoodElement createWoodElement() {
		WoodElement result = new WoodElement( UUID.randomUUID().toString() );
		result.init();
		return result;
	}
	
	public static WoodElementIdentifier createWoodElementIdentifier() {
		WoodElementIdentifier result = new WoodElementIdentifier( UUID.randomUUID().toString() );
		return result;
	}
}
