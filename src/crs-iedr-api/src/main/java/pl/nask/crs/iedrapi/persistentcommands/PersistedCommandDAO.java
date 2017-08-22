package pl.nask.crs.iedrapi.persistentcommands;

/**
 * A DAO object to handle PersistentCommands 
 * 
 * @author Artur Gniadzik
 *
 */
public interface PersistedCommandDAO {

	/**
	 * Stores a response for the request sent by the given user
	 * @param nicHandle
	 * @param request
	 * @param response
	 */
	public void storeResponse(String nicHandle, String request, String response);

	/**
	 * Searches for the response to the given command sent by the user given with a parameter
	 * @param nh
	 * @param request
	 * @return
	 */
	public String getResponse(String nh, String request);

}
