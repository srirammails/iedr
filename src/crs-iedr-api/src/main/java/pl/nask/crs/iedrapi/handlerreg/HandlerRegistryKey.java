package pl.nask.crs.iedrapi.handlerreg;

import pl.nask.crs.iedrapi.CommandTypeEnum;

public class HandlerRegistryKey {
	private CommandTypeEnum commandType;
	private String commandName;
	private Class commandClass;

	public HandlerRegistryKey(CommandTypeEnum cmdType, String commandName, Class commandClass) {
		this.commandType = cmdType;
		this.commandName = commandName;
		this.commandClass = commandClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commandClass == null) ? 0 : commandClass.hashCode());
		result = prime * result
				+ ((commandName == null) ? 0 : commandName.hashCode());
		result = prime * result
				+ ((commandType == null) ? 0 : commandType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HandlerRegistryKey other = (HandlerRegistryKey) obj;
		if (commandClass == null) {
			if (other.commandClass != null)
				return false;
		} else if (!commandClass.equals(other.commandClass))
			return false;
		if (commandName == null) {
			if (other.commandName != null)
				return false;
		} else if (!commandName.equals(other.commandName))
			return false;
		if (commandType == null) {
			if (other.commandType != null)
				return false;
		} else if (!commandType.equals(other.commandType))
			return false;
		return true;
	}

	
	
}
