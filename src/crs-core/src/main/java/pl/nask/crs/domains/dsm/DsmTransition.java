package pl.nask.crs.domains.dsm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.Pair;

public class DsmTransition {
	private static final Logger LOG = Logger.getLogger(DsmTransition.class);
	private int targetState;
	private List<Pair<String, String>> actionNames;
	
	
	public void setTargetState(int targetState) {
		this.targetState = targetState;
	}
	
	public int getTargetState() {
		return targetState;
	}
	
	public void setActionNames(List<Pair<String, String>> actionNames) {
		this.actionNames = actionNames;
	}
	
	public List<Pair<String, String>> getActionNames() {
		return actionNames;
	}

	public List<DsmAction> getActions(DsmActionsRegistry actionsRegistry) {
		List<DsmAction> actions = new ArrayList<DsmAction>();
		if (actionNames != null) {
			for (Pair<String, String> name: actionNames) {
				DsmAction action = actionsRegistry.actionFor(name.getLeft(), name.getRight());
				if (action == null) {
					LOG.error("Couldn't find a DSM action for name: " + name);
					throw new IllegalStateException("No DSM action for name: " + name);
				} else {
					actions.add(action);
				}
			}
		}
		
		return actions;
	}
}
