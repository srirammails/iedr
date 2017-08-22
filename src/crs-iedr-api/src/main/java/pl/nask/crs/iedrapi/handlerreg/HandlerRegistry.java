package pl.nask.crs.iedrapi.handlerreg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.CommandTypeEnum;

/**
 * holds the 
 * 
 * @author Artur Gniadzik
 *
 */
public class HandlerRegistry {

	
	private Map<HandlerRegistryKey, APICommandHandler> map = new HashMap<HandlerRegistryKey, APICommandHandler>();

	public HandlerRegistry(List<HandlerRegistryEntry> entries) {
		for (HandlerRegistryEntry entry: entries) {
			map.put(entry.getRegistryKey(), entry.getHandler());
		}
	}

	public  APICommandHandler getHandler(CommandTypeEnum cmdType, String commandName, Class<? extends Object> class1) {
		return map.get(new HandlerRegistryKey(cmdType, commandName, class1));
		
	}
		
	
}
