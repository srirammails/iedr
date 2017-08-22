package pl.nask.crs.iedrapi.handlerreg;

import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.CommandTypeEnum;

public class HandlerRegistryEntry {
	private CommandTypeEnum commandType;
	private String commandName;
	private Class commandClass;
	private APICommandHandler handler;

	public HandlerRegistryEntry(String commandType, String commandName, Class commandClass, APICommandHandler handler) {
		this.commandType = CommandTypeEnum.valueOf(commandType.toUpperCase());
		this.commandName = commandName;
		this.commandClass = commandClass;
		this.handler = handler;
	}
	
	public HandlerRegistryKey getRegistryKey() {
		return new HandlerRegistryKey(commandType, commandName, commandClass);
	}
	
	public APICommandHandler getHandler() {
		return handler;
	}
}
