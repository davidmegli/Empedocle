package it.unifi.ing.stlab.wood-elements.factory;

import it.unifi.ing.stlab.wood-elements.model.WoodElement;
import it.unifi.ing.stlab.wood-elements.model.WoodElementIdentifier;

import java.util.UUID;

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
